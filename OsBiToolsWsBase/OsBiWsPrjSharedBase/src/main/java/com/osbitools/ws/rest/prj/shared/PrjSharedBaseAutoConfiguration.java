/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-01-01
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

import com.osbitools.ws.rest.prj.shared.common.CustErrorList;
import com.osbitools.ws.rest.prj.shared.config.AbstractPrjWsConfig;

/**
 * Auto Configuratin class for Shared Project Management
 */

@Configuration
@ComponentScan("com.osbitools.ws.rest.prj.shared")
public class PrjSharedBaseAutoConfiguration {

  @Autowired
  @Qualifier("log")
  private Logger _log;

  @Autowired
  @Qualifier("prj_ws_cfg")
  private AbstractPrjWsConfig _cfg;

  @Bean
  @DependsOn("log")
  public Git git() throws Exception {
    // Quick check in home directory initialized
    if (_cfg.getBaseDir() == null) {
      _log.warn("Configuration directory was not initialized. Skipping Git storage initialization.");
      return null;
    }
    
    Git git;

    // Check if git repository exists and create one
    // Using ds subdirectory as git root repository 
    File drepo = new File(_cfg.getBaseDir() + File.separator + ".git");

    if (!drepo.exists()) {
      _log.info("Git repository not found on path: '" + drepo + "'");
      if (!drepo.mkdirs())
        throw new Exception(
            "Unable create directory '" + drepo.getAbsolutePath() + "'");

      try {
        git = createGitRepo(drepo);
      } catch (Exception e) {
        throw new Exception("Unable create new repo on path: " +
            drepo.getAbsolutePath() + ". " + e.getMessage());
      }

      _log.info("Created new git repository '" + drepo.getAbsolutePath() + "'");
    } else if (!drepo.isDirectory()) {
      throw new Exception(
          drepo.getAbsolutePath() + " is regular file and not a directory");
    } else {
      git = Git.open(drepo);
      _log.debug("Open existing repository " + drepo.getAbsolutePath());
    }

    return git;
  }

  private Git createGitRepo(File dir) throws Exception {
    // Repository repo = FileRepositoryBuilder.create(dir);
    FileRepositoryBuilder builder = new FileRepositoryBuilder();
    Repository repo = builder.setGitDir(dir).
                                  readEnvironment().findGitDir().build();
    repo.create(false);
    _log.info("Created new bare git repository path: '" + dir + "'");
    Git git = new Git(repo);

    // Check if remote name & url configured
    if (!StringUtils.isEmpty(_cfg.getGitRemoteName()) &&
        !StringUtils.isEmpty(_cfg.getGitRemoteUrl())) {
      StoredConfig cfg = repo.getConfig();
      RemoteConfig rcfg = new RemoteConfig(cfg, _cfg.getGitRemoteName());
      rcfg.addURI(new URIish(_cfg.getGitRemoteUrl()));
      rcfg.update(cfg);
      cfg.save();
    } else {
      boolean fboth = StringUtils.isEmpty(_cfg.getGitRemoteName()) &&
          StringUtils.isEmpty(_cfg.getGitRemoteUrl());
      _log.warn("Remote git is not configured: " +
          (StringUtils.isEmpty(_cfg.getGitRemoteName())
              ? "git_remote_name is empty"
              : "") +
          (fboth ? " and " : "") +
          (StringUtils.isEmpty(_cfg.getGitRemoteUrl())
              ? "git_remote_url " + (fboth ? "are" : "is") + " empty"
              : ""));
    }

    // Commit first revision
    git.commit().setMessage("Repository created").call();

    return git;
  }
  
  // @EventListener(ApplicationReadyEvent.class)
  @Bean
  public String initPrjSharedErrorList() throws ClassNotFoundException {
    String cname = CustErrorList.class.getName();
    Class.forName(cname);
    _log.info("Initialized error list from: " + cname);
    
    return "ERROR_LIST: " + cname;
  }
}
