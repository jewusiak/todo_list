package pl.jewusiak.todo_list.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID uuid;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @OneToMany(mappedBy = "parentList", cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    @ToString.Exclude
    private List<TodoItem> items;

}
