package com.todo.Todo.dao;

import com.todo.Todo.model.ItemDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    private ItemDTO buildItemDTO(final String id) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(id);
        itemDTO.setName(id);
        itemDTO.setDescription(id);
        itemDTO.setCreatedDate(System.currentTimeMillis());
        itemDTO.setDueDate(System.currentTimeMillis());
        return itemDTO;
    }

    @Test
    public void saveItem() {
        ItemDTO item1 = buildItemDTO("1");
        Publisher<ItemDTO> saveItem = todoRepository.save(item1);
        StepVerifier
                .create(saveItem)
                .expectNext(item1)
                .verifyComplete();
    }

    @Test
    public void fetchItems() {
        ItemDTO item1 = buildItemDTO("1");
        ItemDTO item2 = buildItemDTO("2");
        ItemDTO item3 = buildItemDTO("3");
        ItemDTO item4 = buildItemDTO("4");

        Publisher<ItemDTO> saveItem1 = todoRepository.save(item1);
        Publisher<ItemDTO> saveItem2 = todoRepository.save(item2);
        Publisher<ItemDTO> saveItem3 = todoRepository.save(item3);
        Flux<ItemDTO> saveItems = Flux.concat(saveItem1, saveItem2, saveItem3);
        StepVerifier
                .create(saveItems)
                .then(() -> todoRepository.findAll())
                .expectNextCount(3)
                .verifyComplete();

    }
}
