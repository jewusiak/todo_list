package pl.jewusiak.todo_list.mapper;

import org.mapstruct.*;
import pl.jewusiak.todo_list.model.dtos.TodoListDto;
import pl.jewusiak.todo_list.model.entities.TodoList;
import pl.jewusiak.todo_list.model.requests.TodoListDetailsRequest;

@Mapper(componentModel = "spring")
public interface TodoListMapper {
    TodoList map(TodoListDetailsRequest request);

    TodoListDto map(TodoList todoList);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget TodoList entity, TodoListDetailsRequest changes);
}
