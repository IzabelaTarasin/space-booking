package com.IzabelaTarasin.spacebooking;

import com.IzabelaTarasin.spacebooking.controller.UserController;
import com.IzabelaTarasin.spacebooking.dto.CreateUserRequest;
import com.IzabelaTarasin.spacebooking.dto.FlightMapper;
import com.IzabelaTarasin.spacebooking.dto.UserMapper;
import com.IzabelaTarasin.spacebooking.dto.UserResponse;
import com.IzabelaTarasin.spacebooking.model.User;
import com.IzabelaTarasin.spacebooking.service.FlightService;
import com.IzabelaTarasin.spacebooking.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
public class UserControllerWebMvcTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private UserMapper userMapper;
    @MockitoBean
    private FlightMapper flightMapper;
    @MockitoBean
    private FlightService flightService;

    @Test
    public void createUser_returns201LocationAndBody() throws Exception {
        User userEntity = new User();
        User saved = new User();
        UserResponse userDTO = new UserResponse();

        UUID userId = UUID.fromString("a0000001-0000-0000-0000-000000000099");

        saved.setId(userId);

        userDTO.setId(userId);
        userDTO.setEmail("test@test.pl");
        userDTO.setFirstName("Ann");
        userDTO.setLastName("Kowalska");
        userDTO.setTelephoneNumber("678678678");

        when(userMapper.toEntity(any(CreateUserRequest.class))).thenReturn(userEntity);
        when(userService.createUser(userEntity)).thenReturn(saved);
        when(userMapper.toDTO(saved)).thenReturn(userDTO);

        String jsonBody = """
                {
                    "email": "test@test.pl",
                    "telephoneNumber": "678678678",
                    "password": "testtest",
                    "firstName": "Ann",
                    "lastName": "Kowalska"
                }
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/users/" + userId)))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("test@test.pl"));
    }

    @Test
    void createUser_invalidRequest_returns400WithApiError() throws Exception { //test walidacji wejścia (@Valid) gdy niepoprawne DTO
        String jsonBody = """
                {
                    "email": "",
                    "telephoneNumber": "678678678",
                    "password": "testtest",
                    "firstName": "Ann",
                    "lastName": "Kowalska"
                }
                """;
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("email"));
        verifyNoInteractions(userService);
    }
}
