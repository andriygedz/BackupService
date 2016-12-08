package com.gedz.service;

/**
 * Created by Gedz on 04-Dec-16.
 */
import java.util.List;
import java.util.concurrent.Future;

import com.gedz.core.Backup;
import com.gedz.core.BackupRepository;
import com.gedz.core.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SaveBackupService {

    private static final Logger logger = LoggerFactory.getLogger(SaveBackupService.class);

    @Autowired
    private BackupRepository backups;

    private RestTemplate restTemplate;

    @Value("${todoItemServer.host:127.0.0.1}")
    private String host;

    @Value("${todoItemServer.port:9000}")
    private int port;

    public SaveBackupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String postBackup() {
        Backup backup = Backup.create();
        backups.save(backup);
        try {
            saveBackup(backup);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        String responce = "{ \"backupId\": "  + backup.getBackupId() + " }";
        return responce;
    }

    @Async
    public Future<String> saveBackup(Backup backup) throws InterruptedException {
        logger.info("Saving " + backup.getBackupId());
        String url = "http://" + host + ":" + port + "/users";
        try {
            ResponseEntity<List<User>> rateResponse =
                    restTemplate.exchange(url,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                            });
            List<User> users = rateResponse.getBody();
            StringBuilder sb = new StringBuilder();

            for (User user : users) {
                sb.append(user.toString());
            }

            backup.setBackupData(sb.toString());
            backup.setStatus(Backup.Status.OK);
            return new AsyncResult<>(sb.toString());

        } catch (Exception e){
            backup.setStatus(Backup.Status.FAIL);
            logger.info("Todo server connection error");
            return null;
        }
    }
}