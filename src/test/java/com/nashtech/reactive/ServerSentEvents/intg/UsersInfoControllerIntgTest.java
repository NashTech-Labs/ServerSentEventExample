package com.nashtech.reactive.ServerSentEvents.intg;

import com.nashtech.reactive.ServerSentEvents.model.User;
import com.nashtech.reactive.ServerSentEvents.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class UsersInfoControllerIntgTest {

    static String USERS_URL = "/users";
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    UserService userService;

    @Test
    void addUser() {
        //given
        User user = new User(1, "ali", "ali@gmail.com");
        //when
        webTestClient
                .post()
                .uri(USERS_URL)
                .bodyValue(user)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(User.class)
                .consumeWith(userEntityExchangeResult -> {
                    var savedUser = userEntityExchangeResult.getResponseBody();
                    assert savedUser != null;
                    assert savedUser.getName() != null;
                });

        //then
    }

    @Test
    void getAllUsers() {
        webTestClient
                .get()
                .uri(USERS_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(User.class);
    }

    @Test
    void getAllUsersStream() {
        User user = new User(1, "ali", "ali@gmail.com");
        //when
        webTestClient
                .post()
                .uri(USERS_URL)
                .bodyValue(user)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(User.class)
                .consumeWith(userEntityExchangeResult -> {
                    var saveUser = userEntityExchangeResult.getResponseBody();
                    assert saveUser != null;
                    assert saveUser.getName() != null;
                });

        var usersStreamFlux = webTestClient
                .get()
                .uri(USERS_URL + "/stream")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(User.class)
                .getResponseBody();

        StepVerifier.create(usersStreamFlux)
                .assertNext(user1 -> {
                    assert user1.getName() != null;
                })
                .thenCancel()
                .verify();
    }
}