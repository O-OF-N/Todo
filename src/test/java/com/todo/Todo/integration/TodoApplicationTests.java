package com.todo.Todo.integration;

import com.todo.Todo.TodoApplication;
import com.todo.Todo.model.ItemDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class TodoApplicationTests {
    @Autowired WebTestClient webClient;
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:5"));

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("connectionString", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void contextLoads() {
        ItemDTO itemDTO1 = buildItemDTO("id1");
        ItemDTO itemDTO2 = buildItemDTO("id2");
        webClient.post().uri("/todos/user/{userId}/{year}/{month}/{day}", "Test", "2000", "01", "01")
                .header(HttpHeaders.ACCEPT, "application/json")
                .body(BodyInserters.fromValue(itemDTO1))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ItemDTO.class);

        webClient.post().uri("/todos/user/{userId}/{year}/{month}/{day}", "Test", "2000", "01", "01")
                .header(HttpHeaders.ACCEPT, "application/json")
                .body(BodyInserters.fromValue(itemDTO2))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ItemDTO.class);
        var fetchedValue = webClient.get().uri("/todos/user/{userId}", "Test")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange();
        var allItems =  fetchedValue.returnResult(ItemDTO.class).getResponseBody().toStream().toList();
        assert allItems.size() == 2;

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
