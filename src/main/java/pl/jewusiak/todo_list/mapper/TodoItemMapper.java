package pl.jewusiak.todo_list.mapper;

import org.mapstruct.*;
import pl.jewusiak.todo_list.model.dtos.TodoItemDto;
import pl.jewusiak.todo_list.model.entities.TodoItem;
import pl.jewusiak.todo_list.model.requests.TodoItemDetailsRequest;

@Mapper(componentModel = "spring")
public interface TodoItemMapper {

    TodoItem map(TodoItemDetailsRequest request);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget TodoItem existingItem, TodoItem changes);

    @Mapping(source = "parentList.uuid", target = "parentListUuid")
    TodoItemDto map(TodoItem todoItem);
}
