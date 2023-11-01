package pl.jewusiak.todo_list.model.dtos;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class TodoItemDto {
    private UUID uuid;
    private String name;
    private String description;
    private Boolean status;
    private UUID parentListUuid;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;
}
