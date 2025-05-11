package org.mrshoffen.tasktracker.workspace;

import org.mrshoffen.tasktracker.workspace.client.PermissionsClient;
import org.mrshoffen.tasktracker.workspace.model.dto.links.WorkspaceDtoLinksInjector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WorkspaceBeansConfig {

    @LoadBalanced
    @Bean
    WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public PermissionsClient permissionsClient(WebClient.Builder webClientBuilder) {
        return new PermissionsClient(webClientBuilder.baseUrl("http://user-permission-rs").build());
    }

    @Bean
    WorkspaceDtoLinksInjector workspaceDtoLinksInjector(@Value("${app.gateway.api-prefix}") String apiPrefix) {
        return new WorkspaceDtoLinksInjector(apiPrefix);
    }
}
