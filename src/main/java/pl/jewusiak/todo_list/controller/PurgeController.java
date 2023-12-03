package pl.jewusiak.todo_list.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jewusiak.todo_list.repository.TodoItemRepository;
import pl.jewusiak.todo_list.repository.TodoListRepository;

@RestController
@RequiredArgsConstructor
public class PurgeController {
    private final TodoItemRepository todoItemRepository;
    private final TodoListRepository todoListRepository;
    @DeleteMapping("/purge")
    public void purge(){
        todoItemRepository.deleteAll();
        todoListRepository.deleteAll();
    }
}
