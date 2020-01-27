package com.osbitools.ws.rest.prj.rest.shared.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import com.osbitools.ws.rest.prj.shared.config.AbstractPrjWsConfig;
import com.osbitools.ws.rest.shared.base.controller.BaseController;
import com.osbitools.ws.base.WsSrvException;

public class BasePrjController extends BaseController {
  @Autowired
  @Qualifier("prj_ws_cfg")
  private AbstractPrjWsConfig _cfg;
  
  protected AbstractPrjWsConfig getWsConfig() {
    return _cfg;
  }
  
  protected InputStream getUploadedFile(MultipartFile file) throws WsSrvException {
    if (file == null || file.isEmpty())
      //-- 299
      throw new WsSrvException(299, "Failed to store empty uploaded file");

    if (getWsConfig().getMaxUploadFileSizeBytes() != null &&
        file.getSize() > getWsConfig().getMaxUploadFileSizeBytes())
      //-- 298
      throw new WsSrvException(298, "Uploaded file size " + file.getSize() +
          " exceed limit " + getWsConfig().getMaxUploadFileSizeBytes());

    InputStream in;
    try {
      in = file.getInputStream();
    } catch (IOException e) {
      //-- 297
      throw new WsSrvException(297, "Unable retrieve uploaded file.", e);
    }
    
    return in;
  }
}
