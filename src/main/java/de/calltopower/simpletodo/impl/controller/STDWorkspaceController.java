package de.calltopower.simpletodo.impl.controller;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.calltopower.simpletodo.api.controller.STDController;
import de.calltopower.simpletodo.impl.dto.STDWorkspaceDto;
import de.calltopower.simpletodo.impl.dtoservice.STDWorkspaceDtoService;
import de.calltopower.simpletodo.impl.requestbody.STDWorkspaceRequestBody;
import de.calltopower.simpletodo.impl.service.STDWorkspaceService;

/**
 * Workspace controller
 */
@RestController
@RequestMapping(path = STDWorkspaceController.PATH)
public class STDWorkspaceController implements STDController {

    /**
     * The controller path
     */
    public static final String PATH = APIPATH + "/workspaces";

    private static final Logger LOGGER = LoggerFactory.getLogger(STDWorkspaceController.class);

    private STDWorkspaceDtoService workspaceDtoService;
    private STDWorkspaceService workspaceService;

    /**
     * Initializes the controller
     * 
     * @param workspaceDtoService Injected DTO service
     * @param workspaceService    Injected service
     */
    @Autowired
    public STDWorkspaceController(STDWorkspaceDtoService workspaceDtoService, STDWorkspaceService workspaceService) {
        this.workspaceDtoService = workspaceDtoService;
        this.workspaceService = workspaceService;
    }

    @SuppressWarnings("javadoc")
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Set<STDWorkspaceDto> getAllForUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Requested all workspaces for user");
        }

        return workspaceDtoService.convert(workspaceService.getAllWorkspacesForUser(userDetails));
    }

    @SuppressWarnings("javadoc")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public STDWorkspaceDto getWorkspace(@NotNull @PathVariable(name = "id") String id,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Requested workspace");
        }

        return workspaceDtoService.convert(workspaceService.getWorkspace(userDetails, id));
    }

    @SuppressWarnings("javadoc")
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public STDWorkspaceDto createWorkspace(@NotNull @RequestBody STDWorkspaceRequestBody requestBody,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Requested creating workspace");
        }

        return workspaceDtoService.convert(workspaceService.createWorkspace(userDetails, requestBody));
    }

    @SuppressWarnings("javadoc")
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public STDWorkspaceDto updateWorkspace(@NotNull @PathVariable(name = "id") String id,
            @NotNull @RequestBody STDWorkspaceRequestBody requestBody,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Requested updating workspace");
        }

        return workspaceDtoService.convert(workspaceService.updateWorkspace(userDetails, id, requestBody));
    }

    @SuppressWarnings("javadoc")
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void deleteWorkspace(@NotNull @PathVariable(name = "id") String id,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Requested deleting workspace");
        }

        workspaceService.deleteWorkspace(userDetails, id);
    }

    @Override
    public String getPath() {
        return PATH;
    }

}
