package com.todo.Todo.service;

import com.todo.Todo.dao.TodoRepository;
import com.todo.Todo.model.Item;
import com.todo.Todo.model.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Service
public class TodoServiceImpl implements ITodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public Mono<ItemDTO> addTodo(String userId, String year, String month, String day, Item e) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(UUID.randomUUID().toString());
        itemDTO.setDescription(e.description());
        itemDTO.setName(e.name());
        itemDTO.setCreatedDate(new Date().getTime());
        itemDTO.setDueDate(e.dueDate());
        return todoRepository.insert(itemDTO);
    }

    @Override
    public Flux<ItemDTO> getAllTodos() {
        return todoRepository.findAll();
    }
}
