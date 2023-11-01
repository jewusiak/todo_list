package pl.jewusiak.todo_list.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TodoListDto {
    private UUID uuid;
    private String name;
    @JsonIgnoreProperties("parentList")
    private List<TodoItemDto> items;
}
