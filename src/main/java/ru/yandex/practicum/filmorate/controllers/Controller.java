package ru.yandex.practicum.filmorate.controllers;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

public abstract class Controller<T> {
    protected int id;
    protected HashMap <Integer,T> base = new HashMap<>();

    public Collection<T> getAll(){
        return base.values();
    }

    public abstract T create(T t);

    public abstract T update (T t);

    public abstract void deleteAll();
}
