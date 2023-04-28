package com.nashtech.reactive.ServerSentEvents.service;

import com.nashtech.reactive.ServerSentEvents.model.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    public Flux<User> getUsers() {
        return Flux.fromIterable(users);
    }

    public Mono<User> addUser(User user) {
        users.add(user);
        return Mono.just(user);
    }
}
