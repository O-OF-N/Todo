package com.todo.Todo.controller;

import com.todo.Todo.model.Item;
import com.todo.Todo.model.ItemDTO;
import com.todo.Todo.service.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TodoController {

    @Autowired
    private TodoServiceImpl todoService;

    @RequestMapping(value = "/todos/user/{userId}", method = RequestMethod.GET)
    public Flux<ItemDTO> getTodos(@PathVariable("userId") final String userId) {
        System.out.println("request" + userId);
        return todoService.getAllTodos();
    }


    @RequestMapping(value = "/todos/user/{userId}/{year}/{month}/{day}", method = RequestMethod.POST)
    public ResponseEntity<Mono<ItemDTO>> addTodo(@PathVariable("userId") final String userId,
                                                 @PathVariable("year") final String year,
                                                 @PathVariable("month") final String month,
                                                 @PathVariable("day") final String day,
                                                 @RequestBody Item e) {
        System.out.println(e);
        final Mono<ItemDTO> itemDTO = todoService.addTodo(userId, year, month, day, e);
        return ResponseEntity.ok().body(itemDTO);
    }

}
