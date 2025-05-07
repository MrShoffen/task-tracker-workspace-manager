package org.mrshoffen.tasktracker.workspace;

import org.mrshoffen.tasktracker.workspace.model.dto.links.WorkspaceDtoLinksInjector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkspaceBeansConfig {

    @Bean
    WorkspaceDtoLinksInjector workspaceDtoLinksInjector(@Value("${app.gateway.api-prefix}") String apiPrefix) {
        return new WorkspaceDtoLinksInjector(apiPrefix);
    }
}
