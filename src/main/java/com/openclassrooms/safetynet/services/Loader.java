package com.openclassrooms.safetynet.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.models.Database;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class Loader {
    @Value("classpath:data.json")
    private Resource resourceFile;
    @Autowired
    private DatabaseService databaseService;

    @PostConstruct
    private void init() throws IOException {
        System.out.println("init");
        File file = resourceFile.getFile();
        ObjectMapper mapper = new ObjectMapper();

        try {
            Database database = mapper.readValue(file, Database.class);
            databaseService.setDatabase(database);
            System.out.println(database);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
