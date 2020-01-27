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

package com.osbitools.ws.core.shared.service.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.osbitools.ws.core.shared.common.CoreConstants;
import com.osbitools.ws.core.shared.daemons.DsDescrResource;
import com.osbitools.ws.core.shared.model.DataSet;
import com.osbitools.ws.core.shared.model.TraceRecorder;
import com.osbitools.ws.core.shared.proc.DataSetProcessor;
import com.osbitools.ws.core.shared.service.CoreDsProducerSevice;
import com.osbitools.ws.shared.service.RequestLogger;
import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.binding.ds.DataSetDescr;
import com.osbitools.ws.shared.binding.ds.RequestParameter;
import com.osbitools.ws.shared.binding.ds.RequestParameters;

@Service
public class CoreDsProducerSeviceImpl extends BaseCoreSeviceImpl
    implements CoreDsProducerSevice {

  @Autowired
  @Qualifier("rlog")
  private RequestLogger _log;

  @Lazy
  @Autowired(required = false)
  private DataSource _ds;

  @Override
  public DataSet getDataSet(String mname, String lang, String trace,
      Map<String, String[]> params) throws WsSrvException {

    // Trace process start
    long dts = System.currentTimeMillis();

    // Detect language
    lang = (BaseUtils.isEmpty(lang)) ? getDefaultLang() : lang;

    // Load map
    String name = mname + ".xml";
    DsDescrResource dsr = getDsFilesCheck().getResource(name);
    if (dsr == null)
      //-- 100
      throw new WsSrvException(100, "Map not found for '" + mname + "'");

    // Init warnings array
    ArrayList<String> warn = new ArrayList<String>();

    // Check if trace required
    TraceRecorder trecorder = new TraceRecorder(dts,
        getTrace() && "on".equals(trace));

    // Ready to launch
    trecorder.record(CoreConstants.TRACE_START_PROC);

    DataSetProcessor dsp = new DataSetProcessor(dsr,
        getRequestParams(params, dsr.getResource()), _log);

    // Set JdbcTemplate for SQL based processors
    if (_ds != null)
      dsp.getDataSetProc().init(_ds);

    return dsp.read(name, params, lang, trecorder, warn, getLsFilesCheck(),
        getWsConfig());
  }

  HashMap<String, Object> getRequestParams(Map<String, String[]> params,
      DataSetDescr dsDescr) throws WsSrvException {
    HashMap<String, Object> res = new HashMap<String, Object>();

    // Check request parameters
    RequestParameters rparams = dsDescr.getReqParams();
    if (rparams != null) {
      for (RequestParameter param : rparams.getParam()) {
        String pname = param.getName();
        String jtype = param.getJavaType();

        String pvalue = getRequestParameter(params, pname);
        if (pvalue == null)
          continue;

        // Check if parameter correct type/size
        Object value;
        try {
          value = instValue(pvalue, jtype);
        } catch (ClassNotFoundException e) {
          //-- 127
          throw new WsSrvException(127, "Invalid class name " + jtype, e);
        } catch (Exception e) {
          //-- 130
          throw new WsSrvException(130, "Unable instantinate variable '" +
              pvalue + "' with class " + jtype, e);
        }

        // Check size for string types
        Integer psize = param.getSize();
        if (jtype.equals("java.lang.String") && psize != null && psize > 0 &&
            value.toString().length() > psize) {

          //-- 131
          throw new WsSrvException(131, "Value '" + value +
              "' for parameter '" + pname + "' exceed size " + psize);
        }

        res.put(pname, value);
      }
    }

    return res;
  }

  private String getRequestParameter(Map<String, String[]> params,
      String name) {
    String[] list = params.get(name);
    return list == null ? null : list[0];
  }

  Object instValue(String pvalue, String ptype)
      throws ClassNotFoundException, NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    Class<?> ctype = Class.forName(ptype);

    Constructor<?> co = ctype.getConstructor(String.class);
    return co.newInstance(pvalue);
  }

}
