package com.example.todo_api.service;

import com.example.todo_api.entity.Todo;
import com.example.todo_api.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public Page<Todo> getAllTodos(Pageable pageable){
        return todoRepository.findAll(pageable);
    }

    public Optional<Todo> getTodoById(Long id){
        return todoRepository.findById(id);
    }

    public Todo createTodo(Todo todo){
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo todoDetails){
        Todo todo = todoRepository.findById(id).orElseThrow( () -> new RuntimeException("Todo not found"));
        todo.setTitle(todoDetails.getTitle());
        todo.setCompleted(todoDetails.getCompleted());
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id){
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
        todoRepository.delete(todo);
    }
}
