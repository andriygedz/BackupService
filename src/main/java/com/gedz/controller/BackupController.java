package com.gedz.controller;

/**
 * Created by Gedz on 04-Dec-16.
 */
import com.gedz.core.Backup;
import com.gedz.core.BackupRepository;
import com.gedz.service.SaveBackupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Future;

@RestController
public class BackupController {
    private static final Logger logger = LoggerFactory.getLogger(BackupController.class);

    @Autowired
    private BackupRepository backups;

    @Autowired
    private SaveBackupService saveBackupService;

    @RequestMapping(value="/backups", method=RequestMethod.GET)
    public @ResponseBody
    List<Backup> getBackups() {
        return backups.fetchAll();
    }

    @RequestMapping(value="/backups", method=RequestMethod.POST)
    public @ResponseBody
    String postBackup() {
        return saveBackupService.postBackup();
    }

    @RequestMapping(value="/exports/{backupId}", method=RequestMethod.GET, produces="text/plain")
    public @ResponseBody
    String getBackup(@PathVariable long backupId) {
        logger.info("export backup " + Long.toString(backupId));
        return backups.fetch(backupId).get().getBackupData();
    }
}
