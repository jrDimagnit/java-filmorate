package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbTest {

    private final UserDbStorage userStorage;

    @Test
    public void userStorageTest() {
        User newUser = new User("newMain@main", "Practicum", LocalDate.of(1988, 10, 10));
        newUser.setName(newUser.getLogin());
        Optional<User> user1 = Optional.of(userStorage.create(newUser));
        assertThat(user1)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("name", "Practicum")
                );
        Optional<User> getUser = Optional.of(userStorage.getById(1l));
        assertThat(getUser)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("name", "Practicum")
                );

        User updateUser = new User("updateMain@main", "Practicum", LocalDate.of(1988, 10, 10));
        updateUser.setName(updateUser.getLogin());
        updateUser.setId(1l);
        Optional<User> user2 = Optional.of(userStorage.update(updateUser));
        assertThat(user2)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("email", "updateMain@main")
                );
    }
}
