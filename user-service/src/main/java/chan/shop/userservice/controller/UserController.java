package chan.shop.userservice.controller;

import chan.shop.userservice.request.UserCreateRequest;
import chan.shop.userservice.request.UserLoginRequest;
import chan.shop.userservice.response.LoginResponse;
import chan.shop.userservice.response.UserResponse;
import chan.shop.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/join")
    public UserResponse create(@RequestBody UserCreateRequest request) {
        return userService.create(request);
    }

    @PostMapping("/user/login")
    public LoginResponse login(@RequestBody UserLoginRequest request) {
        return userService.login(request);
    }
}
