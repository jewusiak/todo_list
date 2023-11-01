package pl.jewusiak.todo_list.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.jewusiak.todo_list.exceptions.NotFoundException;
import pl.jewusiak.todo_list.mapper.TodoItemMapper;
import pl.jewusiak.todo_list.model.entities.TodoItem;
import pl.jewusiak.todo_list.repository.TodoItemRepository;
import pl.jewusiak.todo_list.utils.ValidationUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoItemService {

    private final TodoItemRepository todoItemRepository;
    private final TodoItemMapper todoItemMapper;
    private final ValidationUtils validation;
    private final TodoListService todoListService;

    public TodoItem getItemById(UUID uuid) {
        return todoItemRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Item", uuid.toString()));
    }

    public TodoItem updateItem(UUID uuid, TodoItem changes) {
        var existingItem = getItemById(uuid);
        todoItemMapper.update(existingItem, changes);
        validation.validate(existingItem);
        return todoItemRepository.save(existingItem);
    }

    public TodoItem createItem(TodoItem item) {
        if (item.getStatus() == null) item.setStatus(false);
        validation.validate(item);
        return todoItemRepository.save(item);
    }

    public Page<TodoItem> getAllItems(Pageable pageable) {
        return todoItemRepository.findAll(pageable);
    }

    public TodoItem setParentList(UUID itemId, UUID listId) {
        var item = getItemById(itemId);
        item.setParentList(todoListService.getList(listId));
        return todoItemRepository.save(item);
    }

    public void deleteItem(UUID uuid) {
        var item = getItemById(uuid);
        todoItemRepository.delete(item);
    }
}
