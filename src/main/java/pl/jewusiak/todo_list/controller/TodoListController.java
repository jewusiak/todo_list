package pl.jewusiak.todo_list.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jewusiak.todo_list.mapper.TodoListMapper;
import pl.jewusiak.todo_list.model.dtos.TodoListDto;
import pl.jewusiak.todo_list.model.requests.TodoListDetailsRequest;
import pl.jewusiak.todo_list.service.TodoListService;

import java.util.UUID;

@Tag(name = "Todo List Controller", description = "Controller for managing todo lists")
@RestController
@RequestMapping("/list")
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListMapper listMapper;
    private final TodoListService todoListService;

    @Operation(summary = "Create list", responses = {
            @ApiResponse(responseCode = "201", description = "List created"),
            @ApiResponse(responseCode = "400", description = "List not created")
    }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = TodoListDetailsRequest.class))))
    @PostMapping
    public ResponseEntity<TodoListDto> createList(@RequestBody TodoListDetailsRequest request) {
        var list = listMapper.map(request);
        var createdList = todoListService.createList(list);
        return ResponseEntity.status(HttpStatus.CREATED).body(listMapper.map(createdList));
    }

    @Operation(summary = "Modify list details", responses = {
            @ApiResponse(responseCode = "200", description = "List modified"),
            @ApiResponse(responseCode = "404", description = "List not found")
    }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = TodoListDetailsRequest.class))),
               parameters = {@Parameter(in = ParameterIn.PATH, description = "List UUID", required = true, schema = @Schema(implementation = UUID.class))}
    )
    @PutMapping("/{uuid}")
    public ResponseEntity<TodoListDto> modifyListDetails(@PathVariable UUID uuid, @RequestBody TodoListDetailsRequest request) {
        var createdList = todoListService.updateList(uuid, request);
        return ResponseEntity.ok(listMapper.map(createdList));
    }

    @Operation(summary = "Delete list", responses = {
            @ApiResponse(responseCode = "204", description = "List deleted"),
            @ApiResponse(responseCode = "404", description = "List not found")
    }, parameters = {@Parameter(in = ParameterIn.PATH, description = "List UUID", required = true, schema = @Schema(implementation = UUID.class))})
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteList(@PathVariable UUID uuid) {
        todoListService.deleteList(uuid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all lists", responses = {
            @ApiResponse(responseCode = "200", description = "Lists found"),
            @ApiResponse(responseCode = "404", description = "Lists not found")
    }, parameters = {
            @Parameter(description = "Pageable object", name = "pageable", schema = @Schema(implementation = Pageable.class))
    })
    @GetMapping
    public ResponseEntity<Page<TodoListDto>> getAllLists(Pageable pageable) {
        return ResponseEntity.ok(todoListService.getAllListsPaged(pageable).map(listMapper::map));
    }

    @Operation(summary = "Get list by uuid", responses = {
            @ApiResponse(responseCode = "200", description = "List found"),
            @ApiResponse(responseCode = "404", description = "List not found")
    }, parameters = {
            @Parameter(description = "List uuid", required = true, name = "uuid", schema = @Schema(implementation = UUID.class))
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<TodoListDto> getListByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(listMapper.map(todoListService.getList(uuid)));
    }
}
