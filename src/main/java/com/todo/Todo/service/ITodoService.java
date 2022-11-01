package com.todo.Todo.service;

import com.todo.Todo.model.Item;
import com.todo.Todo.model.ItemDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITodoService {

    public Mono<ItemDTO> addTodo(final String userId,
                                 final String year,
                                 final String month,
                                 final String day,
                                 Item e);

    public Flux<ItemDTO> getAllTodos();
}
