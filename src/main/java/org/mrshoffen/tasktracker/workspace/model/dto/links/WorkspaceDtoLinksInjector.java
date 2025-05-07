package org.mrshoffen.tasktracker.workspace.model.dto.links;

import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.commons.utils.link.Link;
import org.mrshoffen.tasktracker.commons.utils.link.Links;
import org.mrshoffen.tasktracker.commons.utils.link.LinksInjector;
import org.mrshoffen.tasktracker.commons.web.dto.WorkspaceResponseDto;

@RequiredArgsConstructor
public class WorkspaceDtoLinksInjector extends LinksInjector<WorkspaceResponseDto> {

    private final String apiPrefix;

    @Override
    protected Links generateLinks(WorkspaceResponseDto dto) {
        return Links.builder()
                .addLink(Link.forName("allDesks")
                        .andHref(apiPrefix + "/workspaces/" + dto.getId() + "/desks")
                        .andMethod("GET")
                        .build()
                )
                .addLink(Link.forName("createDesk")
                        .andHref(apiPrefix + "/workspaces/" + dto.getId() + "/desks")
                        .andMethod("POST")
                        .build()
                )
                .addLink(Link.forName("allWorkspaces")
                        .andHref(apiPrefix + "/workspaces")
                        .andMethod("GET")
                        .build()
                )
                .addLink(Link.forName("createWorkspace")
                        .andHref(apiPrefix + "/workspaces")
                        .andMethod("POST")
                        .build()
                )
                .addLink(Link.forName("self")
                        .andHref(apiPrefix + "/workspaces/" + dto.getId())
                        .andMethod("GET")
                        .build()
                )
                .build();

    }
}
