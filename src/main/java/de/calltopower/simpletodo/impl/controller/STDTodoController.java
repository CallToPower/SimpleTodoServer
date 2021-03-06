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
import de.calltopower.simpletodo.impl.dto.STDTodoDto;
import de.calltopower.simpletodo.impl.dtoservice.STDTodoDtoService;
import de.calltopower.simpletodo.impl.requestbody.STDTodoMovementRequestBody;
import de.calltopower.simpletodo.impl.requestbody.STDTodoRequestBody;
import de.calltopower.simpletodo.impl.service.STDTodoService;

/**
 * Todo controller
 * 
 * Attention: This controller is located under the STDWorkspaceController and
 * STDListController path!
 */
@RestController
@RequestMapping(path = STDTodoController.PATH)
public class STDTodoController implements STDController {

    /**
     * The controller path
     */
    public static final String PATH = STDWorkspaceController.PATH;

    /**
     * The controller path - list
     */
    public static final String PATH_TODO = "/todos";

    private static final Logger LOGGER = LoggerFactory.getLogger(STDController.class);

    private STDTodoDtoService todoDtoService;
    private STDTodoService todoService;

    /**
     * Initializes the controller
     * 
     * @param todoDtoService Injected DTO service
     * @param todoService    Injected service
     */
    @Autowired
    public STDTodoController(STDTodoDtoService todoDtoService, STDTodoService todoService) {
        this.todoDtoService = todoDtoService;
        this.todoService = todoService;
    }

    @SuppressWarnings("javadoc")
    @GetMapping(path = "/{wsId}/" + STDListController.PATH_LIST + "/{lId}/"
            + PATH_TODO, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Set<STDTodoDto> getAllForList(@NotNull @PathVariable(name = "wsId") String wsId,
            @NotNull @PathVariable(name = "lId") String lId, @AuthenticationPrincipal UserDetails userDetails) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Requested all todos");
        }

        return todoDtoService.convert(todoService.getAllTodos(userDetails, wsId, lId));
    }

    @SuppressWarnings("javadoc")
    @GetMapping(path = "/{wsId}/" + STDListController.PATH_LIST + "/{lId}/" + PATH_TODO
            + "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public STDTodoDto getTodo(@NotNull @PathVariable(name = "wsId") String wsId,
            @NotNull @PathVariable(name = "lId") String lId, @NotNull @PathVariable(name = "id") String id,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Requested todo");
        }

        return todoDtoService.convert(todoService.getTodo(userDetails, wsId, lId, id));
    }

    @SuppressWarnings("javadoc")
    @PostMapping(path = "/{wsId}/" + STDListController.PATH_LIST + "/{lId}/"
            + PATH_TODO, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public STDTodoDto createTodo(@NotNull @PathVariable(name = "wsId") String wsId,
            @NotNull @PathVariable(name = "lId") String lId, @NotNull @RequestBody STDTodoRequestBody requestBody,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Requested creating todo");
        }

        return todoDtoService.convert(todoService.createTodo(userDetails, wsId, lId, requestBody));
    }

    @SuppressWarnings("javadoc")
    @PutMapping(path = "/{wsId}/" + STDListController.PATH_LIST + "/{lId}/" + PATH_TODO
            + "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public STDTodoDto updateTodo(@NotNull @PathVariable(name = "wsId") String wsId,
            @NotNull @PathVariable(name = "lId") String lId, @NotNull @PathVariable(name = "id") String id,
            @NotNull @RequestBody STDTodoRequestBody requestBody, @AuthenticationPrincipal UserDetails userDetails) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Requested updating todo");
        }

        return todoDtoService.convert(todoService.updateTodo(userDetails, wsId, lId, id, requestBody));
    }

    @SuppressWarnings("javadoc")
    @PutMapping(path = "/{wsId}/" + STDListController.PATH_LIST + "/{lId}/" + PATH_TODO
            + "/{id}/move", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public STDTodoDto moveTodo(@NotNull @PathVariable(name = "wsId") String wsId,
            @NotNull @PathVariable(name = "lId") String lId, @NotNull @PathVariable(name = "id") String id,
            @NotNull @RequestBody STDTodoMovementRequestBody requestBody,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Requested updating todo");
        }

        return todoDtoService.convert(todoService.moveTodo(userDetails, wsId, lId, id, requestBody));
    }

    @SuppressWarnings("javadoc")
    @DeleteMapping(path = "/{wsId}/" + STDListController.PATH_LIST + "/{lId}/" + PATH_TODO + "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void deleteTodo(@NotNull @PathVariable(name = "wsId") String wsId,
            @NotNull @PathVariable(name = "lId") String lId, @NotNull @PathVariable(name = "id") String id,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Requested deleting todo");
        }

        todoService.deleteTodo(userDetails, wsId, lId, id);
    }

    @Override
    public String getPath() {
        return PATH;
    }

}
