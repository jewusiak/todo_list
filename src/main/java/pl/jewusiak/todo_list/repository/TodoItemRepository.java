package pl.jewusiak.todo_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jewusiak.todo_list.model.entities.TodoItem;

import java.util.UUID;

public interface TodoItemRepository extends JpaRepository<TodoItem, UUID> {
}
