package com.gedz.core;

import com.gedz.core.Backup;

import java.util.List;
import java.util.Optional;

/**
 * Created by Gedz on 07-Dec-16.
 */
public interface BackupRepository {
    List<Backup> fetchAll();

    Optional<Backup> fetch(long backupId);

    void save(Backup backupId);

    boolean exists(long backupId);
}
