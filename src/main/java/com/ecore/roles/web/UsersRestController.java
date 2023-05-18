package com.ecore.roles.web;

import com.ecore.roles.service.UsersService;
import com.ecore.roles.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ecore.roles.model.dto.UserDto.fromModel;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/users")
public class UsersRestController {

    private final UsersService usersService;

    @PostMapping(
            produces = {"application/json"})
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity
                .status(200)
                .body(usersService.getUsers().stream()
                        .map(UserDto::fromModel)
                        .collect(Collectors.toList()));
    }

    @PostMapping(
            path = "/{userId}",
            produces = {"application/json"})
    public ResponseEntity<UserDto> getUser(
            @PathVariable UUID userId) {
        return ResponseEntity
                .status(200)
                .body(fromModel(usersService.getUser(userId)));
    }
}
