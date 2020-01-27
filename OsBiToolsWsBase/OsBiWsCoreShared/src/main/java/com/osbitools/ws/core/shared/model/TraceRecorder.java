/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-07
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Trance Event Recorder
 * 
 */
public class TraceRecorder {
  // Start DateStamp
  private long _dts;

  // List of Trace Records
  private List<TraceRecord> _evl;

  // Enabled Flag
  private boolean _yes;

  public TraceRecorder() {
    this(System.currentTimeMillis(), false);
  }

  public TraceRecorder(long tstart, boolean enabled) {
    _dts = tstart;
    setEnabled(enabled);
  }

  public void record(String event) {
    if (_yes)
      _evl.add(new TraceRecord(event, System.currentTimeMillis() - _dts));
  }

  public boolean isEnabled() {
    return _yes;
  }

  public void setEnabled(boolean enabled) {
    _yes = enabled;
    if (_yes && _evl == null)
      _evl = new ArrayList<TraceRecord>();
    else if (!enabled && _evl != null)
      _evl.clear();
  }

  public List<TraceRecord> getEventsList() {
    return _evl;
  }
}