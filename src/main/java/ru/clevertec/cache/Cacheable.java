package ru.clevertec.cache;

public interface Cacheable<T> {
    T get(Long key);
    void put(Long key, T value);
    Long remove(Long key);
}
