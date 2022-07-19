package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.practicum.filmorate.models.Film;

import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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
        film1.setId(jsonObject.get("id").getAsLong());
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
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void addFilmWithIncorrectDate() throws Exception {
        Film film = new Film("name", "info", LocalDate.of(1700, 10, 11), 100);
        mockMvc.perform(post("/films")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(film)))
                        .andExpect(status().isBadRequest())
                        .andReturn();
    }

    @Test
    public void addFilmWithAbnormalDuration() throws Exception {
        Film film = new Film("name", "info", LocalDate.of(2000, 10, 11), -100);
        mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isInternalServerError())
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
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void updateFilmWithIncorrectId() throws Exception {
        Film film = new Film("name", "info", LocalDate.of(2000, 10, 11), 100);
        film.setId(3L);
        mockMvc.perform(put("/films")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(film)))
                        .andExpect(status().isNotFound())
                        .andReturn();
    }

}
