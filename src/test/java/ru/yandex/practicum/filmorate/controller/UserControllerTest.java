package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
@AfterEach
 void clean() throws Exception{
    MvcResult mvcResult = mockMvc.perform(delete("/users"))
            .andExpect(status().isOk())
            .andReturn();
}

    @Test
    public void getUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void addCorrectUser() throws Exception {
        User normalUser = new User("name@ya.ru", "name", LocalDate.of(2000, 10, 11));
        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(normalUser)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void updateCorrectUser() throws Exception {
        User normalUser = new User("name@ya.ru", "name", LocalDate.of(2000, 10, 11));
        User updateUser = new User("name@ya.ru", "newName", LocalDate.of(2000, 10, 11));
        MvcResult mvcResult = mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(normalUser)))
                .andExpect(status().isOk())
                .andReturn();
        JsonElement jsonElement = JsonParser.parseString(mvcResult.getResponse().getContentAsString());
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        updateUser.setId(jsonObject.get("id").getAsInt());
        mockMvc.perform(put("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("newName"))
                .andReturn();
    }

    @Test
    public void addUserWithAbnormalEmail() throws Exception {
        User user = new User("nameya.ru", "name", LocalDate.of(2000, 10, 11));
        mockMvc.perform(post("/users")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(user)))
                        .andExpect(status().isBadRequest())
                        .andReturn();
    }

    @Test
    public void addUserWithIncorrectDate() throws Exception {
        User user = new User("name@ya.ru", "name", LocalDate.of(2033, 10, 11));
        mockMvc.perform(post("/users")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(user)))
                        .andExpect(status().isBadRequest())
                        .andReturn();

    }

    @Test
    public void addUserWithIncorrectLogin() throws Exception {
        User user = new User("name@ya.ru", "na me", LocalDate.of(2033, 10, 11));
         mockMvc.perform(post("/users")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(user)))
                        .andExpect(status().isBadRequest())
                        .andReturn();
    }

    @Test
    public void updateUserWithIncorrectId() throws Exception {
        User user = new User("name@ya.ru", "name", LocalDate.of(2033, 10, 11));
        user.setId(3);
        MvcResult mvcResult = mockMvc.perform(put("/users")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(user)))
                        .andExpect(status().isBadRequest())
                        .andReturn();
    }
}
