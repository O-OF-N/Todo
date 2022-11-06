package com.todo.Todo.controller;

import com.todo.Todo.model.Item;
import com.todo.Todo.model.ItemDTO;
import com.todo.Todo.service.TodoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TodoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoServiceImpl todoService;

    @RequestMapping(value = "/todos/user/{userId}", method = RequestMethod.GET)
    public Flux<ItemDTO> getTodos(@PathVariable("userId") final String userId) {
        LOGGER.info("Get user id = " + userId);
        return todoService.getAllTodos();
    }


    @RequestMapping(value = "/todos/user/{userId}/{year}/{month}/{day}", method = RequestMethod.POST)
    public ResponseEntity<Mono<ItemDTO>> addTodo(@PathVariable("userId") final String userId,
                                                 @PathVariable("year") final String year,
                                                 @PathVariable("month") final String month,
                                                 @PathVariable("day") final String day,
                                                 @RequestBody Item e) {
        LOGGER.info("Add user id = " + userId);
        final Mono<ItemDTO> itemDTO = todoService.addTodo(userId, year, month, day, e);
        return ResponseEntity.ok().body(itemDTO);
    }

}
