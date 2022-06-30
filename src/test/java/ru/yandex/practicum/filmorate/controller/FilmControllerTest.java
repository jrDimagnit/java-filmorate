package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FilmController.class)
public class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetFilm() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testPostFilm() throws Exception {
        Film film = new Film("name", "info", LocalDate.of(2000, 10, 11), 100);
        mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testPutFilm() throws Exception {
        Film film = new Film("name", "info", LocalDate.of(2000, 10, 11), 100);
        Film film1 = new Film("name", "Newinfo", LocalDate.of(2000, 10, 11), 100);
        film1.setId(1);
        mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult mvcResult = mockMvc.perform(put("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film1)))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(result.contains("Newinfo"));
    }

    @Test
    public void testPostAbnormalFilm() throws Exception {
        Film film = new Film("", "info", LocalDate.of(2000, 10, 11), 100);
        final NestedServletException exception = assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/films")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(film)))
                        .andExpect(status().isOk())
                        .andReturn()
        );
        assertTrue(exception.getMessage().contains("Название фильма отсутствует!"));
    }

    @Test
    public void testPostAbnormalDateFilm() throws Exception {
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
    public void testPostAbnormalDurationsFilm() throws Exception {
        Film film = new Film("name", "info", LocalDate.of(2000, 10, 11), -100);
        final NestedServletException exception = assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/films")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(film)))
                        .andExpect(status().isBadRequest())
                        .andReturn()
        );
        assertTrue(exception.getMessage().contains("Продолжительность не может быть отрицательной!"));
    }

    @Test
    public void testPostAbnormalDescriptionFilm() throws Exception {
        String[] dis = new String[100];
        String j = String.join("-", dis);
        Film film = new Film("name", j, LocalDate.of(2000, 10, 11), -100);
        final NestedServletException exception = assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/films")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(film)))
                        .andExpect(status().isBadRequest())
                        .andReturn()
        );
        assertTrue(exception.getMessage().contains("Описание не должно превышать 200 символов!"));
    }

    @Test
    public void testPutAbnormalIdFilm() throws Exception {
        Film film = new Film("name", "info", LocalDate.of(2000, 10, 11), 100);
        film.setId(3);
        final NestedServletException exception = assertThrows(NestedServletException.class,
                () -> mockMvc.perform(put("/films")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(film)))
                        .andExpect(status().isBadRequest())
                        .andReturn()
        );
        assertTrue(exception.getMessage().contains("Фильма с таким id не найдено!"));
    }

}
