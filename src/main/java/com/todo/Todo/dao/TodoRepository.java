package com.todo.Todo.dao;

import com.todo.Todo.model.ItemDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TodoRepository extends ReactiveMongoRepository<ItemDTO, String> {
}
