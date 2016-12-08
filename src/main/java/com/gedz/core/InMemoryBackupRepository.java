package com.gedz.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gedz on 07-Dec-16.
 */
@Repository
public class InMemoryBackupRepository implements BackupRepository {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryBackupRepository.class);

    private final ConcurrentHashMap<Long, Backup> backups = new ConcurrentHashMap();



    public InMemoryBackupRepository() {
    }

    @Override
    public List<Backup> fetchAll() {
        logger.info("Fetching " + this.backups.size() + " backups");
        List<Backup> backups = new ArrayList<>(this.backups.values());
        return backups;
    }

    @Override
    public Optional<Backup> fetch(long backupId) {
        logger.info("Fetching user with id: " + backupId);
        return Optional.ofNullable(this.backups.get(Long.valueOf(backupId)));
    }

    @Override
    public void save(Backup newBackup) {
        logger.info("Store user: " + newBackup);
        this.backups.put(Long.valueOf(newBackup.getBackupId()), newBackup);
        logger.info("User with id: " + newBackup.getBackupId() + " stored");
    }

    @Override
    public boolean exists(long backupId) {
        return this.backups.containsKey(Long.valueOf(backupId));
    }
}
