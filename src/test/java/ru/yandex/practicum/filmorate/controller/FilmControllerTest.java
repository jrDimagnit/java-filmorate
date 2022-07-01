package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FilmController.class)
public class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void clean() throws Exception{
        MvcResult mvcResult = mockMvc.perform(delete("/films"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getFilms() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void addNewNormalFilm() throws Exception {
        Film film = new Film("name", "info", LocalDate.of(2000, 10, 11), 100);
        mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void updateNormalFilm() throws Exception {
        Film film = new Film("name", "info", LocalDate.of(2000, 10, 11), 100);
        Film film1 = new Film("name", "Newinfo", LocalDate.of(2000, 10, 11), 100);
        MvcResult mvcResult = mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk())
                .andReturn();
        JsonElement jsonElement = JsonParser.parseString(mvcResult.getResponse().getContentAsString());
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        film1.setId(jsonObject.get("id").getAsInt());
        mockMvc.perform(put("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Newinfo"))
                .andReturn();
    }

    @Test
    public void addFilmWithoutName() throws Exception {
        Film film = new Film("", "info", LocalDate.of(2000, 10, 11), 100);
        mockMvc.perform(post("/films")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(film)))
                        .andExpect(status().isBadRequest())
                        .andReturn();
    }

    @Test
    public void addFilmWithIncorrectDate() throws Exception {
        Film film = new Film("name", "info", LocalDate.of(1700, 10, 11), 100);
        final NestedServletException exception = assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/films")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(film)))
                        .andExpect(status().isBadRequest())
                        .andReturn()
        );
        assertTrue(exception.getMessage().contains("Указанна неверная дата!"));
    }

    @Test
    public void addFilmWithAbnormalDuration() throws Exception {
        Film film = new Film("name", "info", LocalDate.of(2000, 10, 11), -100);
        mockMvc.perform(post("/films")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(film)))
                        .andExpect(status().isBadRequest())
                        .andReturn();
    }

    @Test
    public void addFilmWithAbnormalDescription() throws Exception {
        String[] dis = new String[100];
        String j = String.join("-", dis);
        Film film = new Film("name", j, LocalDate.of(2000, 10, 11), -100);
        mockMvc.perform(post("/films")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(film)))
                        .andExpect(status().isBadRequest())
                        .andReturn();
    }

    @Test
    public void updateFilmWithIncorrectId() throws Exception {
        Film film = new Film("name", "info", LocalDate.of(2000, 10, 11), 100);
        film.setId(3);
        final NestedServletException exception = assertThrows(NestedServletException.class,
                () -> mockMvc.perform(put("/films")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(film)))
                        .andExpect(status().isBadRequest())
                        .andReturn()
        );
        assertTrue(exception.getMessage().contains("id не найден!"));
    }

}
