package com.nashtech.reactive.ServerSentEvents.controller;

import com.nashtech.reactive.ServerSentEvents.model.User;
import com.nashtech.reactive.ServerSentEvents.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
public class SinkRequestController {
    UserService userService;
    Sinks.Many<User> usersSink = Sinks.many().replay().all();

    public SinkRequestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Flux<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> addUser(@RequestBody User user) {
        return userService.addUser(user).doOnNext(user1 -> usersSink.tryEmitNext(user1));
    }

    @GetMapping(value = "/users/stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<User> getUser() {
        return usersSink.asFlux();
    }
}
