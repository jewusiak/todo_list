package pl.jewusiak.todo_list.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jewusiak.todo_list.mapper.TodoItemMapper;
import pl.jewusiak.todo_list.model.dtos.TodoItemDto;
import pl.jewusiak.todo_list.model.requests.TodoItemDetailsRequest;
import pl.jewusiak.todo_list.service.TodoItemService;

import java.util.UUID;

@Tag(name = "Todo Item Controller", description = "Controller for managing todo items")
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class TodoItemController {
    private final TodoItemService itemService;
    private final TodoItemMapper todoItemMapper;

    @Operation(summary = "Delete item by uuid", responses = {
            @ApiResponse(responseCode = "204", description = "Item deleted"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    }, parameters = {
            @Parameter(in = ParameterIn.PATH, description = "Item UUID", required = true, schema = @Schema(implementation = UUID.class))
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteItem(@PathVariable UUID uuid) {
        itemService.deleteItem(uuid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update item by uuid", responses = {
            @ApiResponse(responseCode = "200", description = "Item updated"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = TodoItemDetailsRequest.class))), parameters = {
            @Parameter(in = ParameterIn.PATH, description = "Item UUID", required = true, schema = @Schema(implementation = UUID.class))
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<TodoItemDto> updateItem(@PathVariable UUID uuid, @RequestBody TodoItemDetailsRequest request) {
        return ResponseEntity.ok(todoItemMapper.map(itemService.updateItem(uuid, todoItemMapper.map(request))));
    }


    @Operation(summary = "Get item by uuid", responses = {
            @ApiResponse(responseCode = "200", description = "Item found"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    }, parameters = {
            @Parameter(in = ParameterIn.PATH, description = "Item UUID", required = true, schema = @Schema(implementation = UUID.class))
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<TodoItemDto> getItem(@PathVariable UUID uuid) {
        return ResponseEntity.ok(todoItemMapper.map(itemService.getItemById(uuid)));
    }

    @Operation(summary = "Create item", responses = {
            @ApiResponse(responseCode = "200", description = "Item created"),
            @ApiResponse(responseCode = "404", description = "Item not created")
    }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = TodoItemDetailsRequest.class))))
    @PostMapping
    public ResponseEntity<TodoItemDto> createItem(@RequestBody TodoItemDetailsRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoItemMapper.map(itemService.createItem(todoItemMapper.map(request))));
    }

    @Operation(summary = "Get all items", responses = {
            @ApiResponse(responseCode = "200", description = "Items found"),
            @ApiResponse(responseCode = "404", description = "Items not found")
    }, parameters = {
            @Parameter(description = "Pageable object", name = "pageable", schema = @Schema(implementation = Pageable.class))
    })
    @GetMapping
    public ResponseEntity<Page<TodoItemDto>> getAllItems(Pageable pageable) {
        return ResponseEntity.ok(itemService.getAllItems(pageable).map(todoItemMapper::map));
    }

    @Operation(summary = "Add item to list", responses = {
            @ApiResponse(responseCode = "200", description = "Item added"),
            @ApiResponse(responseCode = "404", description = "Item or list not found")
    }, parameters = {
            @Parameter(description = "Item uuid", required = true, name = "itemId", schema = @Schema(implementation = UUID.class)),
            @Parameter(description = "List uuid", required = true, name = "listId", schema = @Schema(implementation = UUID.class))
    })
    @PostMapping("/{itemId}/setlist/{listId}")
    public ResponseEntity<TodoItemDto> setList(@PathVariable UUID itemId, @PathVariable UUID listId) {
        return ResponseEntity.ok(todoItemMapper.map(itemService.setParentList(itemId, listId)));
    }
}
//list: ed27f716-613e-4c8a-bca3-eb43f5f299e4
//item: d5b50fb0-54c9-45b4-99ea-dd0d15f24b02