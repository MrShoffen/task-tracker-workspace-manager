package org.mrshoffen.tasktracker.workspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication

public class WorkspaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkspaceApplication.class, args);
    }

}
