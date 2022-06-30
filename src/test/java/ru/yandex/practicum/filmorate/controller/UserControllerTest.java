package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testGetUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testPostUser() throws Exception {
        User normalUser = new User("name@ya.ru", "name", LocalDate.of(2000, 10, 11));
        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(normalUser)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testPutUser() throws Exception {
        User normalUser = new User("name@ya.ru", "name", LocalDate.of(2000, 10, 11));
        User updateUser = new User("name@ya.ru", "newName", LocalDate.of(2000, 10, 11));
        updateUser.setId(1);
        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(normalUser)))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult mvcResult = mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(result.contains("newName"));
    }

    @Test
    public void testPostAbnormalUser() throws Exception {
        User user = new User("nameya.ru", "name", LocalDate.of(2000, 10, 11));
        final NestedServletException exception = assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/users")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(user)))
                        .andExpect(status().isBadRequest())
                        .andReturn()
        );
        assertTrue(exception.getMessage().contains("Email не соответствует!"));
    }

    @Test
    public void testPostWithAbnormalDate() throws Exception {
        User user = new User("name@ya.ru", "name", LocalDate.of(2033, 10, 11));
        final NestedServletException exception = assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/users")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(user)))
                        .andExpect(status().isBadRequest())
                        .andReturn()
        );
        assertTrue(exception.getMessage().contains("Дата рождения не может быть в будущем!"));
    }

    @Test
    public void testPostWithAbnormalLogin() throws Exception {
        User user = new User("name@ya.ru", "na me", LocalDate.of(2033, 10, 11));
        final NestedServletException exception = assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/users")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(user)))
                        .andExpect(status().isBadRequest())
                        .andReturn()
        );
        assertTrue(exception.getMessage().contains("пробелы!"));
    }

    @Test
    public void putWithAbnormalId() {
        User user = new User("name@ya.ru", "name", LocalDate.of(2033, 10, 11));
        user.setId(3);
        final NestedServletException exception = assertThrows(NestedServletException.class,
                () -> mockMvc.perform(put("/users")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(user)))
                        .andExpect(status().isBadRequest())
                        .andReturn()
        );
        assertTrue(exception.getMessage().contains("Пользователь с данным id не найден!"));
    }
}
