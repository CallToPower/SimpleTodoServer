package de.calltopower.simpletodo.impl.dtoservice;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.calltopower.simpletodo.api.dtoservice.STDDtoService;
import de.calltopower.simpletodo.impl.dto.STDTodoDto;
import de.calltopower.simpletodo.impl.model.STDTodoModel;
import lombok.NoArgsConstructor;

/**
 * DTO service implementation for the todo DTO
 */
@NoArgsConstructor
@Service
public class STDTodoDtoService implements STDDtoService<STDTodoDto, STDTodoModel> {

    @Override
    public STDTodoDto convert(STDTodoModel model) {
        if (model == null) {
            return null;
        }

        // @formatter:off
        return STDTodoDto.builder()
                .id(model.getId())
                //.createdDate(model.getCreatedDate())
                .msg(model.getMsg())
                .url(model.getUrl())
                .done(model.isStatusDone())
                .dueDate(model.getDueDate())
                .workspaceId(model.getList().getWorkspace().getId())
                .workspaceName(model.getList().getWorkspace().getName())
                .listId(model.getList().getId())
                .listName(model.getList().getName())
                .jsonData(model.getJsonData())
                .build();
        // @formatter:on
    }

    @Override
    public STDTodoDto convertAbridged(STDTodoModel model) {
        if (model == null) {
            return null;
        }

        // @formatter:off
        return STDTodoDto.builder()
                .id(model.getId())
                .done(model.isStatusDone())
                .workspaceId(model.getList().getWorkspace().getId())
                .listId(model.getList().getId())
                .build();
        // @formatter:on
    }

    @Override
    public Set<STDTodoDto> convert(Set<STDTodoModel> models) {
        if (models == null) {
            return new HashSet<>();
        }

        // @formatter:off
        return models.stream()
                        .map(m -> convert(m))
                        .collect(Collectors.toSet());
        // @formatter:on
    }

    @Override
    public Set<STDTodoDto> convertAbridged(Set<STDTodoModel> models) {
        if (models == null) {
            return new HashSet<>();
        }

        // @formatter:off
        return models.stream()
                        .map(m -> convertAbridged(m))
                        .collect(Collectors.toSet());
        // @formatter:on
    }

}
