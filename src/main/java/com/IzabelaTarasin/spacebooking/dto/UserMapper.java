package com.IzabelaTarasin.spacebooking.dto;

import com.IzabelaTarasin.spacebooking.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserMapper {
    public User toEntity(CreateUserRequest userRequest){
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setTelephoneNumber(userRequest.getTelephoneNumber());
        user.setPassword(userRequest.getPassword());
        user.setLastName(userRequest.getLastName());
        user.setFirstName(userRequest.getFirstName());
        return user;
    }

    public UserResponse toDTO(User user){
        UserResponse dtoUser = new UserResponse();
        dtoUser.setId(user.getId());
        dtoUser.setEmail(user.getEmail());
        dtoUser.setFirstName(user.getFirstName());
        dtoUser.setLastName(user.getLastName());
        dtoUser.setTelephoneNumber(user.getTelephoneNumber());
        return dtoUser;
    }

    public List<UserResponse> toUserResponseList(List<User> users){
        return users.stream() //przejdź po elementach listy w strumieniu
                .map(this::toDTO)//„użyj metody toDTO z tego samego mappera”.
                .toList(); //złóż wyniki w jedną listę i zwróć
//this to obiekt mappera (UserMapper)
    }


}
