package com.ecore.roles.web;

import com.ecore.roles.mapper.UserMapper;
import com.ecore.roles.model.dto.UserDto;
import com.ecore.roles.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping(
            produces = {"application/json"})
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUsers().stream()
                        .map(UserMapper::from)
                        .collect(Collectors.toList()));
    }

    @GetMapping(
            path = "/{userId}",
            produces = {"application/json"})
    public ResponseEntity<UserDto> getUser(
            @PathVariable UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserMapper.from(userService.getUser(userId)));
    }
}
