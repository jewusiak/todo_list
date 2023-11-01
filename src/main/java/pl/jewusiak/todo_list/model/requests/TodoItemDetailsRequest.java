package pl.jewusiak.todo_list.model.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class TodoItemDetailsRequest {
    private String name;
    private String description;
    private Boolean status;
}
