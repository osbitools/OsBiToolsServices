/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2016-06-09
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.model;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.osbitools.ws.core.shared.proc.AbstractDataSetProc;
import com.osbitools.ws.core.shared.producers.AbstractDataSetProducer;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.binding.ds.AutoIncColumn;
import com.osbitools.ws.shared.binding.ds.CalcColumn;
import com.osbitools.ws.shared.binding.ds.ConditionFilter;
import com.osbitools.ws.shared.binding.ds.SortCond;
import com.osbitools.ws.shared.binding.ds.SortTypes;
import com.osbitools.ws.shared.binding.ds.StringSort;
import com.osbitools.ws.shared.common.CommonConstants;

/**
 * DataSet Model class
 * 
 */
@JsonInclude(Include.NON_EMPTY)
public class DataSet implements Serializable {

  //Default Serial Version UID
  private static final long serialVersionUID = 1L;

  // Array of columns
  private final List<DataSetColumn> columns = new ArrayList<>();

  // Array with dataset
  private List<List<Object>> data = new ArrayList<>();

  @JsonIgnore
  private List<HashMap<String, Object>> _dmap = new ArrayList<>();

  // Trace records
  private final List<TraceRecord> trace;

  // Warn records
  private final List<String> warn;

  //HashMap columns & Types
  @JsonIgnore
  private HashMap<String, Constructor<?>> _cmap =
      new HashMap<String, Constructor<?>>();

  //Array of calculated columns
  @JsonIgnore
  private ArrayList<ColumnEval> _calc;

  // Values for current record. Used by expression evaluation
  @JsonIgnore
  private HashMap<String, Object[]> _records;

  // Language
  @JsonIgnore
  private String _lang;

  // Reference on top level procucer
  @JsonIgnore
  private final AbstractDataSetProducer<?> _dsp;

  public DataSet(AbstractDataSetProducer<?> dsProd, String lang,
      TraceRecorder trace, List<String> warn) {
    _lang = lang;
    _dsp = dsProd;

    this.trace = trace == null ? null : trace.getEventsList();
    this.warn = warn;

  }

  public DataSet(AbstractDataSetProducer<?> dsProd, String lang) {
    this(dsProd, lang, null, null);
  }

  public void addLocaleValue(String column, Object value, String lang) {
    int idx = data.size() - 1;

    _dmap.get(idx).put(column, value);
    data.get(idx).add(value);
  }

  public void appendData(DataSet ds) throws WsSrvException {
    List<HashMap<String, Object>> dmap = ds.getDataMap();

    for (HashMap<String, Object> record : dmap) {
      startRecord();

      for (DataSetColumn column : ds.getColumns())
        addValue(column.getName(), record.get(column.getName()));

      endRecordSuccess();
    }
  }

  public void addValue(String column, Object value) {
    Object v = checkLocaleValue(column, value, _lang);
    addLocaleValue(column, v, _lang);

    // Check if calc column activated
    if (_calc != null) {
      for (ColumnEval eval : _calc) {
        Integer[] arr = eval.getColumnIndexes(column);

        if (arr == null)
          continue;

        Object[] o = _records.get(eval.getColumnName());
        for (Integer idx : arr)
          o[idx] = v;
      }
    }
  }

  public void startRecord() {
    _records = null;
    _dmap.add(new HashMap<String, Object>());
    data.add(new ArrayList<Object>());

    if (_calc == null)
      return;

    _records = new HashMap<String, Object[]>();

    for (ColumnEval eval : _calc) {

      HashMap<String, ArrayList<Integer>> pval = eval.getColumnIndexes();
      if (pval == null)
        return;

      int len = 0;
      for (ArrayList<Integer> p : pval.values())
        len += p.size();

      _records.put(eval.getColumnName(), new Object[len]);
    }
  }

  public void startData() {
    data.clear();
    _dmap.clear();
  }

  @JsonIgnore
  public int getRecordCount() {
    return data.size();
  }

  public void endRecordSuccess() throws WsSrvException {
    endRecord(false);
  }

  public void endRecordError() throws WsSrvException {
    endRecord(true);
  }

  public void endRecord(boolean isError) throws WsSrvException {
    AbstractDataSetProc dsp = getDataSetProducer().getDataSetProc();

    if (dsp.hasExtraColumns()) {

      List<AutoIncColumn> acl = dsp.getAutoIncColumns();
      if (acl != null) {
        for (AutoIncColumn ac : acl)
          addValue(ac.getName(),
              ac.getStartFrom() + ac.getIncBy() * (data.size() - 1));
      }

      if (_calc != null) {
        for (ColumnEval eval : _calc) {
          String column = eval.getColumnName();

          if (isError) {
            addValue(column,
                createValue(column, eval.getCalcColumn().getErrorValue()));
          } else {
            Object[] record = _records.get(column);

            try {
              addValue(column, eval.evaluate(record));
            } catch (InvocationTargetException e) {
              HashMap<String, ArrayList<Integer>> eparams =
                  eval.getColumnIndexes();

              int len = (record == null) ? 0 : record.length;
              String[] dmsg = new String[len + 1];
              dmsg[0] = "Unable evaluate expression '" +
                  eval.getCalcColumn().getValue() + "'";

              if (e.getTargetException() != null)
                dmsg[0] +=
                    "; Reason: '" + e.getTargetException().getMessage() + "'";

              if (len > 0)
                for (String s : eparams.keySet())
                  for (int idx : eparams.get(s))
                    dmsg[idx + 1] = s + ": " + record[idx];

              CalcColumn calc = eval.getCalcColumn();
              Boolean stop = eval.getCalcColumn().isStopOnError();
              if (stop != null && stop)
                //-- 115
                throw new WsSrvException(115, dmsg);

              addValue(column, createValue(column, calc.getErrorValue()));

              //-- 111
              _dsp.getDataSetProc().error(111, dmsg);
            } catch (Exception e) {
              // Something wrong with parameters
              HashMap<String, ArrayList<Integer>> eparams =
                  eval.getColumnIndexes();
              List<Class<?>> classes = eval.getParamTypes();

              int len = (record == null) ? 0 : record.length;
              String[] dmsg = new String[len + 2];
              dmsg[0] = "Unable evaluate expression '" +
                  eval.getCalcColumn().getValue();
              dmsg[1] = e.getMessage();

              if (len > 0)
                for (String s : eparams.keySet())
                  for (int idx : eparams.get(s))
                    dmsg[idx + 2] = s + ": " + record[idx] + " (given " +
                        record[idx].getClass().getSimpleName() +
                        " - expected " + classes.get(idx).getSimpleName() + ")";
              //-- 106
              throw new WsSrvException(106, dmsg);
            }
          }
        }
      }
    }
  }

  public void endData() {
  }

  public void addColumn(String column) throws WsSrvException {
    addColumn(column, "java.lang.String");
  }

  public void addColumn(String column, String jtype) throws WsSrvException {
    Class<?> c;
    try {
      c = Class.forName(jtype);
    } catch (ClassNotFoundException e) {
      //-- 107
      throw new WsSrvException(107, "Unknown Java Type: " + jtype, e);
    }

    try {
      addColumn(column, c.getConstructor(String.class));
    } catch (NoSuchMethodException | SecurityException e) {
      //-- 108
      throw new WsSrvException(108,
          "Unable find constructor for class: " + c.getName(), e);
    }
  }

  public void addColumn(String name, Constructor<?> co) {
    _cmap.put(name, co);
    columns.add(new DataSetColumn(name, co.getDeclaringClass().getName()));
  }

  public void appendColumns(DataSet ds) {
    for (DataSetColumn column : ds.getColumns())
      if (!hasColumn(column.getName()))
        addColumn(column.getName(), ds.getColumnCo(column.getName()));
  }

  public void append(DataSet ds) throws WsSrvException {
    appendColumns(ds);
    endColumn();

    appendData(ds);
  }

  public void endColumn() throws WsSrvException {
    AbstractDataSetProc dsp = getDataSetProducer().getDataSetProc();
    if (!dsp.hasExtraColumns())
      return;

    List<AutoIncColumn> acl = dsp.getAutoIncColumns();
    if (acl != null) {
      for (AutoIncColumn ac : acl)
        if (!hasColumn(ac.getName()))
          // Auto Inc columns always integer
          addColumn(ac.getName(), "java.lang.Integer");
    }

    List<CalcColumn> ccl = dsp.getCalcColumns();
    if (ccl != null) {
      _calc = new ArrayList<ColumnEval>();
      for (CalcColumn cc : ccl) {
        if (!hasColumn(cc.getName())) {
          addColumn(cc.getName(), cc.getJavaType());
          _calc.add(new ColumnEval(this, cc));
        }
      }
    }
  }

  public Object createValue(String column, String value) throws WsSrvException {
    Object res;
    Constructor<?> co = _cmap.get(column);

    if (co == null)
      //-- 109
      throw new WsSrvException(109,
          "Column definitions not found " + "for column : " + column);

    // For numeric and data types check for empty
    if (StringUtils.isEmpty(value) && isAssignableFrom(getColumnClass(co)))
      return null;

    try {
      res = co.newInstance(value);
    } catch (InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e) {
      //-- 110
      throw new WsSrvException(110, "Unable instantinate variable '" + value +
          "' for class: " + co.getName(), e);
    }

    return res;
  }

  /**
   * Checks if column class extends Date or Numeric class and need special
   * handling for empty/null values
   * 
   * @param clazz
   *          Column class
   * @return True if class extends Date or Numeric class and False if not
   */
  protected boolean isAssignableFrom(Class<?> clazz) {
    return Number.class.isAssignableFrom(clazz) ||
        Date.class.isAssignableFrom(clazz);
  }

  public Object checkLocaleValue(String column, Object value, String lang) {
    Object res = value;
    AbstractDataSetProducer<?> dsp = getDataSetProducer();
    if (dsp.getLangColumns().isLangColumn(column)) {
      // Find value in loaded languages
      String s = getDataSetProducer().findLocaleValue(lang, value.toString());
      if (s != null)
        res = s;
    }

    return res;
  }

  public Class<?> getColumnClass(String column) {
    return getColumnClass(_cmap.get(column));
  }

  public Class<?> getColumnClass(Constructor<?> co) {
    return (co != null) ? co.getDeclaringClass() : null;
  }

  public Constructor<?> getColumnCo(String column) {
    return _cmap.get(column);
  }

  @JsonIgnore
  public AbstractDataSetProducer<?> getDataSetProducer() {
    return _dsp;
  }

  @JsonIgnore
  public HashMap<String, Constructor<?>> getColumnMap() {
    return _cmap;
  }

  public boolean hasColumn(String column) {
    return _cmap.containsKey(column);
  }

  public void sort(List<SortCond> sortList) throws WsSrvException {
    // Last sorted column. Used for multi-column sorting
    String lcol = null;

    // Create array of unique values for the first sorted condition
    HashSet<Object> unq = null;

    boolean funq = sortList.get(0).isUnique();
    if (funq)
      unq = new HashSet<Object>();

    for (int i = 0; i < sortList.size(); i++) {
      SortCond sort = sortList.get(i);

      // Check if sorted column in list
      String cname = sort.getColumn();

      if (!hasColumn(cname))
        //-- 119
        throw new WsSrvException(119, new String[] {
            "Sorted column '" + cname + "' not found in result dataset" });

      ArrayList<SortedRecord> sorted = new ArrayList<SortedRecord>();

      int idx = 0;
      if (i == 0) {
        // Create subset of dataset with sorted column as key
        for (int j = 0; j < data.size(); j++) {
          Object val = _dmap.get(j).get(cname);

          if (i == 0 && funq) {
            if (!unq.contains(val)) {
              unq.add(val);
              sorted.add(new SortedRecord(j, val, sort, _lang));
            }
          } else {
            sorted.add(new SortedRecord(j, val, sort, _lang));
          }
        }

        resort(sorted, idx, funq);
      } else {
        // Sort partiall dataset
        if (data.size() == 0)
          continue;
        // Locate group of similar elements in dataset

        boolean f = false;
        Object obj = _dmap.get(0).get(lcol);
        sorted.add(new SortedRecord(0, _dmap.get(0).get(cname), sort, _lang));
        for (int j = 1; j < data.size(); j++) {
          if (_dmap.get(j).get(lcol).equals(obj)) {
            f = true;
            sorted
                .add(new SortedRecord(j, _dmap.get(j).get(cname), sort, _lang));
          } else if (f) {
            // Sort partial array first
            resort(sorted, idx);

            // Clear flag and arrays
            f = false;
            idx = j;
            sorted.clear();
            obj = _dmap.get(j).get(lcol);
            sorted
                .add(new SortedRecord(j, _dmap.get(j).get(cname), sort, _lang));
          } else {
            // Set new start point
            idx = j;
            sorted.clear();
            obj = _dmap.get(j).get(lcol);
            sorted
                .add(new SortedRecord(j, _dmap.get(j).get(cname), sort, _lang));
          }
        }

        // After check
        if (f)
          resort(sorted, idx);
      }

      lcol = sort.getColumn();
    }
  }

  public void filter(ConditionFilter filter) throws WsSrvException {

    // Create new dataset with different record order
    List<List<Object>> rds = new ArrayList<>(data.size());
    ArrayList<HashMap<String, Object>> records = new ArrayList<>(data.size());

    CalcColumn calc = new CalcColumn();
    calc.setValue(filter.getValue());
    calc.setJavaType("java.lang.Boolean");

    ColumnEval eval = new ColumnEval(this, calc);

    // Filter dataset
    for (int ii = 0; ii < data.size(); ii++) {
      HashMap<String, Object> record = _dmap.get(ii);

      // Define array of parameters
      Object[] params = new Object[eval.getParamCnt()];

      // Process column set
      for (DataSetColumn column : getColumns()) {

        Integer[] arr = eval.getColumnIndexes(column.getName());

        if (arr == null)
          continue;

        for (Integer idx : arr)
          params[idx] = record.get(column.getName());
      }

      // Process input parameters
      HashMap<String, Object> plist =
          getDataSetProducer().getDataSetProc().getRequestParameters();
      if (plist != null) {
        for (Map.Entry<String, Object> param : plist.entrySet()) {
          Integer[] arr = eval.getParamsIndexes(param.getKey());

          if (arr == null)
            continue;

          for (Integer idx : arr)
            params[idx] = param.getValue();
        }
      }

      boolean res;
      try {
        Boolean fres = (Boolean) eval.evaluate(params);
        res = fres;
      } catch (Exception e) {
        int len = params.length + 2;
        String[] str = new String[len];

        String msg =
            (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();

        if (msg == null)
          msg = e.getClass().getSimpleName();

        str[0] = msg;
        str[1] = eval.getExpression();
        for (int i = 2; i < len; i++) {
          Object o = params[i - 2];
          str[i] = "PARAM #" + (i - 2) + ": " + o + "; TYPE: " +
              o.getClass().getName();
        }

        //-- 121
        throw new WsSrvException(121, str);
      }

      if (!res)
        continue;

      if (res) {
        rds.add(data.get(ii));
        records.add(record);
      }
    }

    // Replace original dataset
    data = rds;
    _dmap = records;
  }

  private void resort(ArrayList<SortedRecord> list, int idx) {
    resort(list, idx, false);
  }

  private void resort(ArrayList<SortedRecord> list, int idx, boolean funq) {
    // Sort indexed list
    Collections.sort(list);

    // Create new dataset with different record order
    List<List<Object>> rds = new ArrayList<>(data.size());
    List<HashMap<String, Object>> records = new ArrayList<>(data.size());

    // Copy old records
    for (int i = 0; i < idx; i++) {
      rds.add(data.get(i));
      records.add(_dmap.get(i));
    }

    // Copy sorted records
    for (int i = 0; i < list.size(); i++) {
      rds.add(data.get(list.get(i).getId()));
      records.add(_dmap.get(list.get(i).getId()));
    }

    if (!funq)
      // Copy remaining records
      for (int i = idx + list.size(); i < data.size(); i++) {
        rds.add(data.get(i));
        records.add(_dmap.get(i));
      }

    // Replace original dataset
    data = rds;
    _dmap = records;
  }

  @JsonIgnore
  public List<HashMap<String, Object>> getDataMap() {
    return _dmap;
  }

  public List<List<Object>> getData() {
    return data;
  }

  public List<DataSetColumn> getColumns() {
    return columns;
  }

  /*
  public void setColumns(List<DataSetColumn> columns) {
    this.columns = columns;
  }
  */

  public List<TraceRecord> getTrace() {
    return trace;
  }

  public List<String> getWarn() {
    return warn;
  }
}

class ColumnEval {
  // Calc Column
  private CalcColumn _calc;

  // Pattern for column parameter
  public static final Pattern COLUMN_PARAM =
      Pattern.compile("\\@\\{([0-9A-Za-z\\.\\_\\\"\\(\\)\\!\\=\\&\\|]+)\\}");

  //Pattern for request parameter
  public static final Pattern RPARAM =
      Pattern.compile("\\$\\{([a-zA-Z0-9-_]+)\\}");

  // Temp List of Expression Data Column parameters
  private HashMap<String, ArrayList<Integer>> _eparams =
      new HashMap<String, ArrayList<Integer>>();

  //Temp List of Expression Request parameters
  private HashMap<String, ArrayList<Integer>> _rparams =
      new HashMap<String, ArrayList<Integer>>();

  // Pointer on Expression Evaluator object
  private ExpressionEvaluator _eval;

  // Pointer on parent dataset
  private DataSet _ds;

  // List of expression input parameters
  private List<String> _params = new ArrayList<String>();

  // List of expression classes for input parameters
  private List<Class<?>> _classes = new ArrayList<Class<?>>();

  // Final expression
  private String _expression;

  // Total number of parameters
  private int _pcnt;

  public ColumnEval(DataSet ds, CalcColumn calcColumn) throws WsSrvException {
    _ds = ds;
    _calc = calcColumn;
    _expression = _calc.getValue();

    String jtype = _calc.getJavaType();
    Class<?> c;
    try {
      c = Class.forName(jtype);
    } catch (ClassNotFoundException e) {
      //-- 112
      throw new WsSrvException(112, "Unknown Java Type: " + jtype, e);
    }

    // Parse expression
    Matcher m = COLUMN_PARAM.matcher(_expression);

    _pcnt = 0;
    while (m.find()) {
      String col = m.group(1);

      // Check if column exists in dataset and get it type
      Class<?> ctype = _ds.getColumnClass(col);
      if (ctype == null)
        //-- 113
        throw new WsSrvException(113,
            "Cannot find class type for column '" + col + "'");

      // Check if column already used
      ArrayList<Integer> cnt;
      if (_eparams.containsKey(col)) {
        cnt = _eparams.get(col);
        cnt.add(_pcnt);
      } else {
        cnt = new ArrayList<Integer>();
        cnt.add(_pcnt);
        _eparams.put(col, cnt);
      }

      String cn = "C_" + col + "_" + (cnt.size() - 1);
      _expression = _expression.replaceFirst("\\@\\{" + col + "\\}", cn);

      _params.add(cn);
      _classes.add(ctype);

      _pcnt++;
    }

    // Replace request parameters
    m = RPARAM.matcher(_expression);

    while (m.find()) {
      String pname = m.group(1);
      Object value = ds.getDataSetProducer().getDataSetProc()
          .getRequestParameters().get(pname);

      if (value == null)
        //-- 138
        throw new WsSrvException(138, "", "Mandatory parameter '" + pname +
            "' required for calculated expression not found");

      // Check if parameter already used
      ArrayList<Integer> cnt;
      if (_rparams.containsKey(pname)) {
        cnt = _eparams.get(pname);
        cnt.add(_pcnt);
      } else {
        cnt = new ArrayList<Integer>();
        cnt.add(_pcnt);
        _rparams.put(pname, cnt);
      }

      String cn = "R_" + pname + "_" + (cnt.size() - 1);
      cn = cn.toUpperCase();

      _expression = _expression.replaceFirst("\\$\\{" + pname + "\\}", cn);

      _params.add(cn);
      _classes.add(value.getClass());

      _pcnt++;
    }

    // Compile condition. Slow process
    try {
      compile(c);
    } catch (CompileException e) {
      //-- 114
      throw new WsSrvException(114,
          "Unable compile expression '" + _calc.getValue() + "'", e);
    }
  }

  public Integer[] getColumnIndexes(String column) {
    List<Integer> cnt = _eparams.get(column);
    return (cnt == null) ? null : cnt.toArray(new Integer[cnt.size()]);
  }

  public Integer[] getParamsIndexes(String column) {
    List<Integer> cnt = _rparams.get(column);
    return (cnt == null) ? null : cnt.toArray(new Integer[cnt.size()]);
  }

  public HashMap<String, ArrayList<Integer>> getColumnIndexes() {
    return _eparams;
  }

  public void compile(Class<?> jtype) throws CompileException {

    _eval = new ExpressionEvaluator(_expression, // expression
        jtype, // expressionType
        _params.toArray(new String[_params.size()]), // parameterNames
        _classes.toArray(new Class<?>[_classes.size()]) // parameterTypes
    );
  }

  public Object evaluate(Object[] values) throws InvocationTargetException {
    Object res = _eval.evaluate(values);
    // Check special condition for Double.INFINITY
    if (res.getClass().equals(Double.class) && ((Double) res).isInfinite())
      throw new InvocationTargetException(
          new ArithmeticException("Value is Infinite"));
    return res;
  }

  public String getExpression() {
    return _expression;
  }

  public String getColumnName() {
    return _calc.getName();
  }

  public CalcColumn getCalcColumn() {
    return _calc;
  }

  public int getParamCnt() {
    return _pcnt;
  }

  public List<Class<?>> getParamTypes() {
    return _classes;
  }
}

class SortedRecord implements Comparable<SortedRecord> {

  private int _id;
  private Object _val;
  String _vtype;
  Collator _cltr;
  SortCond _sort;

  SortedRecord(int id, Object value, SortCond sortCond, String lang)
      throws WsSrvException {
    _id = id;
    _val = value;
    _sort = sortCond;

    // Check object type
    _vtype = _val.getClass().getName().toString();

    // String requires collator
    if (_vtype.equals("java.lang.String")) {
      if (!CommonConstants.LOCALES.containsKey(lang))
        //-- 120
        throw new WsSrvException(120, "Locale '" + lang + "' is not found in " +
            "Constants.LOCALES list.");

      Locale l = CommonConstants.LOCALES.get(lang);
      _cltr = Collator.getInstance(l);
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public int compareTo(SortedRecord srec) {
    int res;
    int m = (_sort.getSequence().equals(SortTypes.DESC)) ? -1 : 1;

    if (_vtype.equals("java.lang.String") &&
        _sort.getStrSort().equals(StringSort.COLLATOR)) {
      res = _cltr.compare(_val, srec.getValue());
    } else {
      res = ((Comparable) _val).compareTo(srec.getValue());
    }

    return m * res;
  }

  public int getId() {
    return _id;
  }

  public Object getValue() {
    return _val;
  }
}