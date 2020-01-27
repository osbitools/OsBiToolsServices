package com.osbitools.ws.rest.prj.shared.service.impl;

import com.osbitools.ws.rest.prj.shared.it.utils.TestExFileInfo;
import com.osbitools.ws.rest.prj.shared.service.IExFileInfo;

public class TestTextExFileServiceImpl extends AbstractExFileServiceImpl<String> {

  @Override
  public IExFileInfo<String> getExFileInfo(String ext) {
    return new TestExFileInfo();
  }
}
