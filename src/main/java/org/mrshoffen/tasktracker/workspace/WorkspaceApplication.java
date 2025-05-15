package org.mrshoffen.tasktracker.workspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication

public class WorkspaceApplication {

    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>();

        SpringApplication.run(WorkspaceApplication.class, args);
    }

}
