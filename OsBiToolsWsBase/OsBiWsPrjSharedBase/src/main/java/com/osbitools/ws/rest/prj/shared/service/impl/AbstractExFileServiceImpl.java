package com.osbitools.ws.rest.prj.shared.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import com.osbitools.ws.rest.prj.shared.dto.FileInfoDto;
import com.osbitools.ws.rest.prj.shared.service.ExFileService;
import com.osbitools.ws.rest.prj.shared.service.IExFileInfo;
import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.base.WsSrvException;

public abstract class AbstractExFileServiceImpl<T> extends AbstractBaseService
    implements ExFileService<T> {

  @Override
  public T createFile(String sdir, String name, InputStream in,
      Map<String, String> params, Boolean overwrite) throws WsSrvException {
    getReqLog().debug(overwrite ? "Updating existing file '" + sdir + "/" + name + "'"
        : "Creating file '" + sdir + "/" + name + "'");

    File f = checkExFile(sdir, name, overwrite);

    // Check if ext directory exists
    File fdir = f.getAbsoluteFile().getParentFile();

    if (!fdir.exists() && !fdir.mkdir())
      throw new WsSrvException(231,
          "Unable create subdirectory " + fdir.getAbsolutePath());

    GenericUtils.saveFile(f, in);

    getReqLog().debug(overwrite ? "File '" + name + "' successfully updated"
        : "File '" + name + "' successfully created");

    return postCreate(f, params);
  }

  @Override
  public HashSet<String> getExtListByDirName(String dname)
      throws WsSrvException {
    Map<String, String[]> map = getWsCfg().getSubDirExtList();
    if (map == null)
      //-- 232
      throw new WsSrvException(232, "Resource directories are not supported");

    HashSet<String> extl = getWsCfg().getExtList(dname);
    if (extl == null)
      //-- 234
      throw new WsSrvException(234,
          "Unknown resource directory '" + dname + "'");

    return extl;
  }

  /**
   * Get File Info
   * 
   * @param dname Resource Directory
   * @param name File Name
   * @param ext File Extension
   * @param params Set of input parameters
   * @return String with File Info
   * 
   * @throws WsSrvException
   */
  @Override
  public FileInfoDto getFileInfo(String dname, String name)
      throws WsSrvException {
    // Check if file extension supported
    String ext = GenericUtils.getFileExt(name);

    if (!getWsCfg().hasExt(ext))
      //-- 258
      throw new WsSrvException(258,
          "File extension '" + ext + "' is not supported");

    File f = checkExFile(dname, name, true);

    return new FileInfoDto(f.length(), f.canRead(), f.canWrite(),
        Instant.ofEpochMilli(f.lastModified()).atZone(ZoneId.systemDefault())
            .toLocalDateTime());
  }

  @Override
  public File checkExFile(String sdir, String name, Boolean overwrite)
      throws WsSrvException {
    HashSet<String> extl = getExtListByDirName(sdir);

    return GenericUtils.checkFile(getWsCfg().getBaseDir(), name, extl, sdir,
        overwrite);
  }

  @Override
  public String[] getExFileList(String pdir, String sdir)
      throws WsSrvException {
    // Get file filter
    FilenameFilter filter = getWsCfg().getExtLstFilenameFilter(sdir);

    if (filter == null)
      //-- 291
      throw new WsSrvException(291,
          "File filter not found for directory '" + sdir + "'");

    File extd = new File(getWsCfg().getBaseDir() + File.separator + pdir +
        File.separator + sdir);

    if (!extd.exists())
      //-- 290
      throw new WsSrvException(290,
          "Resource directory '" + sdir + "' doesn't exist");

    String[] flist = extd.list(filter);
    Arrays.sort(flist);

    return flist;
  }

  @Override
  public T getExtFileInfo(String dname, String fname,
      Map<String, String> params) throws WsSrvException {
    return postCreate(checkExFile(dname, fname, true), params);
  }

  @Override
  public Path sendExFile(String dname, String fname, OutputStream out)
      throws WsSrvException {
    // Get file handler
    File f = checkExFile(dname, fname, true);

    if (out == null)
      return f.toPath();
    
    try {
      BaseUtils.copy(new FileInputStream(f), out);
    } catch (IOException e) {
      //-- 294
      throw new WsSrvException(294, e);
    }
    
    return null;
  }
  
  @Override
  public T postCreate(File f, Map<String, String> params)
      throws WsSrvException {
    // Get file extension
    String ext = GenericUtils.getFileExt(f.getName());

    // Check if post-processing assigned
    IExFileInfo<T> efi = getExFileInfo(ext);
    if (efi == null)
      //-- 306
      throw new WsSrvException(306,
          "No post-processing module configured for extension '" + ext + "'");

    return efi.getSaveInfo(f, params);
  }

}
