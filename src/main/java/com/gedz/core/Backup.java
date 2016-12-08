package com.gedz.core;

/**
 * Created by Gedz on 04-Dec-16.
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;



//ignore all properties except name and blog
@JsonIgnoreProperties(ignoreUnknown=true)
public class Backup {

    private static final AtomicLong idCounter = new AtomicLong(0L);

    private long backupId;
    private Date date;
    private Status status;
    @JsonIgnore
    private String backupData;

    public static enum Status {
        IN_PROGRESS("In progress"),
        OK("Ok"),
        FAIL("Fail")
        ;

        private final String text;

        /**
         * @param text
         */
        private Status(final String text) {
            this.text = text;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return text;
        }
    }

    public static Backup create(){
        Backup backup = new Backup();
        backup.setBackupId(idCounter.getAndIncrement());
        backup.setDate(new Date());
        backup.setStatus(Status.IN_PROGRESS);
        return backup;
    }

    public void setBackupData(String backupData) {
        this.backupData = backupData;
    }

    public String getBackupData() {
        return backupData;
    }

    public long getBackupId() {
        return backupId;
    }

    public void setBackupId(long backupId) {
        this.backupId = backupId;
    }

    public String getDate() {
        return date.toString();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
