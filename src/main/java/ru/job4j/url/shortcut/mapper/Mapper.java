package ru.job4j.url.shortcut.mapper;

public interface Mapper<F, T> {
    T convert(F f);
}
