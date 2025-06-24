package chan.shop.userservice.service;

import chan.shop.userservice.entity.User;
import chan.shop.userservice.request.UserCreateRequest;
import chan.shop.userservice.request.UserLoginRequest;
import chan.shop.userservice.response.LoginResponse;
import chan.shop.userservice.response.UserResponse;

public interface UserService {

    UserResponse create(UserCreateRequest request);

    LoginResponse login(UserLoginRequest request);
}
