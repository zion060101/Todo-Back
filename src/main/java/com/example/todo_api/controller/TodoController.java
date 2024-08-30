package com.example.todo_api.controller;

import com.example.todo_api.dto.PageDto;
import com.example.todo_api.dto.TodoDto;
import com.example.todo_api.entity.Todo;
import com.example.todo_api.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllTodos(@RequestParam (defaultValue = "0") int page,
                                  @RequestParam (defaultValue = "0") int size,
                                  @RequestParam (defaultValue = "id.desc") String[] sort){
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortBy = Sort.by(direction, sort[0]);

        Pageable pageable = PageRequest.of(page, size, sortBy);
        Page<Todo> todoPage = todoService.getAllTodos(pageable);

        List<TodoDto> todoDtoList = todoPage.getContent().stream()
                .map(TodoDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PageDto<>(todoDtoList, todoPage.getNumber(), todoPage.getSize(), todoPage.getTotalElements(), todoPage.getTotalPages()));
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo){
        return todoService.createTodo(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails){
        Todo updatedTodo = todoService.updateTodo(id, todoDetails);
        return  ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        return ResponseEntity.ok().build();
    }
}
