package com.todo.Todo.controller;

import com.todo.Todo.model.Item;
import com.todo.Todo.model.ItemDTO;
import com.todo.Todo.service.ITodoService;
import com.todo.Todo.service.TodoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TodoController.class)
class TodoControllerTest {

    @MockBean TodoServiceImpl todoService;
    @Autowired WebTestClient webClient;

    @Test
    public void getTodos() {
        ItemDTO itemDTO1 = buildItemDTO("id1");
        ItemDTO itemDTO2 = buildItemDTO("id2");
        ItemDTO itemDTO3 = buildItemDTO("id3");
        ItemDTO itemDTO4 = buildItemDTO("id4");
        doReturn(Flux.just(itemDTO1, itemDTO2, itemDTO3, itemDTO4)).when(todoService).getAllTodos();
        webClient.get().uri("/todos/user/{userId}", "Test")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ItemDTO.class);

        Mockito.verify(todoService, times(1)).getAllTodos();
    }

    @Test
    public void saveTodos() {
        Item itemDTO1 = new Item("name", "desc", System.currentTimeMillis());
        webClient.post().uri("/todos/user/{userId}/{year}/{month}/{day}", "Test", "2000", "01", "01")
                .header(HttpHeaders.ACCEPT, "application/json")
                .body(BodyInserters.fromValue(itemDTO1))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ItemDTO.class);

        Mockito.verify(todoService, times(1)).addTodo("Test", "2000", "01", "01",itemDTO1);
    }

    private ItemDTO buildItemDTO(final String id) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(id);
        itemDTO.setName(id);
        itemDTO.setDescription(id);
        itemDTO.setCreatedDate(System.currentTimeMillis());
        itemDTO.setDueDate(System.currentTimeMillis());
        return itemDTO;
    }

}
