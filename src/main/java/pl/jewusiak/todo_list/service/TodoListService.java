package pl.jewusiak.todo_list.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.jewusiak.todo_list.exceptions.NotFoundException;
import pl.jewusiak.todo_list.mapper.TodoListMapper;
import pl.jewusiak.todo_list.model.entities.TodoList;
import pl.jewusiak.todo_list.model.requests.TodoListDetailsRequest;
import pl.jewusiak.todo_list.repository.TodoListRepository;
import pl.jewusiak.todo_list.utils.ValidationUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final TodoListMapper todoListMapper;
    private final ValidationUtils validation;

    public TodoList createList(TodoList list) {
        validation.validate(list);
        return todoListRepository.save(list);
    }

    public TodoList getList(UUID uuid) {
        return todoListRepository.findById(uuid).orElseThrow(() -> new NotFoundException("List", uuid.toString()));
    }

    public TodoList updateList(UUID listId, TodoListDetailsRequest updates) {
        var existingList = getList(listId);
        todoListMapper.update(existingList, updates);
        validation.validate(existingList);
        return todoListRepository.save(existingList);
    }

    public void deleteList(UUID uuid) {
        todoListRepository.delete(getList(uuid));
    }

    public Page<TodoList> getAllListsPaged(Pageable pageable) {
        return todoListRepository.findAll(pageable);
    }

}
