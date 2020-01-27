/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 * Date: 2018-01-01
 * 
 */

package com.osbitools.ws.rest.prj.rest.shared.rest;

import org.springframework.http.HttpStatus;

import com.osbitools.ws.rest.shared.web.rest.SharedRestWebTestUnit;

public abstract class PrjMgrSharedTestUnit extends SharedRestWebTestUnit {

  @Override
  protected String[] getWepAppPath() {
    String[] sres = super.getWepAppPath();
    int slen = sres.length;

    // Add /entities
    // TODO Add projects, ex_file, git, ll_set into list
    String[] pres = { "entities", "projects" };
    int plen = pres.length;

    String[] res = new String[slen + plen];
    for (int i = 0; i < slen; i++)
      res[i] = sres[i];

    for (int i = 0; i < plen; i++)
      res[slen + i] = pres[i];

    return res;
  }

  @Override
  protected byte getPenTestPathParamMask(String url, String method) {
    return (byte) (url.endsWith("entities") ? 0b10
        : (url.endsWith("projects") ? 0b11
            : super.getPenTestPathParamMask(url, method)));
  }

  @Override
  protected int getNoPenTestPathParamCode(String url, String method,
      boolean hasPathParam) {
    return (byte) (url.endsWith("entities") ? HttpStatus.NOT_FOUND.value()
        : (url.endsWith("projects") ?
              getMixNoPenTestPathParamCode(method, hasPathParam)
        : super.getNoPenTestPathParamCode(url, method, hasPathParam)));
  }

}
