/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-03-12
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.prj.rest.shared.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.rest.prj.rest.shared.common.PrjMgrRestConstants;
import com.osbitools.ws.rest.prj.shared.dto.FileInfoDto;
import com.osbitools.ws.rest.prj.shared.service.ExFileService;
import com.osbitools.ws.shared.*;

/**
 * 
 * API for External File processing.DsMapUtils Manager.
 * 
 */

public class AbstractExFileController<T> extends BasePrjController {

  protected static final String SRV_BASE_URL = CommonConstants.BASE_URL + "/ex_files/";

  protected static final String DNAME_REGEX = "{dname:" + Constants.ID_REGEX + "}";

  protected static final String FNAME_REGEX = "{fname:" + Constants.ID_REGEX;

  protected static final String SRV_PATH_SET = DNAME_REGEX + "/" + FNAME_REGEX + "\\." +
      Constants.ID_REGEX + "\\." + PrjMgrRestConstants.FILE_EXT_REGEX + "}";

  @Autowired
  private ExFileService<T> _exfile;

  @RequestMapping(value = SRV_BASE_URL + SRV_PATH_SET, method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public T processExFile(@PathVariable("dname") String dname,
      @PathVariable("fname") String fname,
      @RequestParam(value = "overwrite", required = false,
          defaultValue = "false") Boolean overwrite,
      MultipartFile file, HttpServletRequest req) throws WsSrvException {

    if (file == null || file.isEmpty())
      //-- 299
      throw new WsSrvException(299, "Failed to store empty uploaded file");

    if (getWsConfig().getMaxUploadFileSizeBytes() != null &&
        file.getSize() > getWsConfig().getMaxUploadFileSizeBytes())
      //-- 298
      throw new WsSrvException(298, "Uploaded file size " + file.getSize() + " exceed limit " +
          getWsConfig().getMaxUploadFileSizeBytes());

    InputStream in;
    try {
      in = file.getInputStream();
    } catch (IOException e) {
      //-- 297
      throw new WsSrvException(297, "Unable retrieve uploaded file.", e);
    }

    T exFile = _exfile.createFile(dname, fname, in,
        GenericServiceUtils.getReqParamValues(req.getParameterMap()), overwrite);

    // Clean up handle from upload file
    try {
      file.getInputStream().close();
    } catch (IOException e) {
      // Ignore
    }

    return exFile;
  }

  /**
   * Retrieve external file. Content type is dynamic
   * 
   * @param dname Project name
   * @param fname File name
   * @param resp Http Response
   * 
   * @throws WsSrvException
   */
  @RequestMapping(value = SRV_BASE_URL + SRV_PATH_SET, method = RequestMethod.GET)
  public void downloadExFile(@PathVariable("dname") String dname,
      @PathVariable("fname") String fname, HttpServletResponse resp) throws WsSrvException {
    // First time call send file without output stream to get path only
    Path path = _exfile.sendExFile(dname, fname, null);

    try {
      String mtype = Files.probeContentType(path);
      if (mtype != null)
        resp.addHeader(HttpHeaders.CONTENT_TYPE, mtype + ";charset=utf-8");
    } catch (IOException e) {
      //-- 296
      throw new WsSrvException(296,
          "Unable recognize mime type for file '" + path.toAbsolutePath() + "'", e);
    }

    resp.addHeader(HttpHeaders.CONTENT_DISPOSITION,
        "attachment;filename=" + path.getFileName().toString());

    try {
      _exfile.sendExFile(dname, fname, resp.getOutputStream());
    } catch (IOException e) {
      //-- 266
      throw new WsSrvException(266, e);
    }
  }

  @RequestMapping(value = SRV_BASE_URL + "file_info/" + SRV_PATH_SET,
      method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public FileInfoDto readExFileInfo(@PathVariable("dname") String dname,
      @PathVariable("fname") String fname) throws WsSrvException {
    return _exfile.getFileInfo(dname, fname);
  }

  @RequestMapping(value = SRV_BASE_URL + SRV_PATH_SET, method = RequestMethod.DELETE)
  public void deleteExFileInfo(@PathVariable("dname") String dname,
      @PathVariable("fname") String fname) throws WsSrvException {
    // Get file handler
    File f = _exfile.checkExFile(dname, fname, true);
    if (!f.delete())
      //-- 292
      throw new WsSrvException(292, "Error deleting file '" + f.getAbsolutePath() + "'");
  }

  @RequestMapping(value = SRV_BASE_URL + FNAME_REGEX + "}/" + DNAME_REGEX,
      method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String[] getExFileList(@PathVariable("fname") String pdir,
      @PathVariable("dname") String dname) throws WsSrvException {
    return _exfile.getExFileList(pdir, dname);
  }

  protected ExFileService<T> getExFileService() {
    return _exfile;
  }
}
