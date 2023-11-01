package pl.jewusiak.todo_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jewusiak.todo_list.model.entities.TodoList;

import java.util.UUID;

public interface TodoListRepository extends JpaRepository<TodoList, UUID> {
}
