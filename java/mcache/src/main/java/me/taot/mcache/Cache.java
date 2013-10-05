package me.taot.mcache;

interface Cache<T, K> {

    T get(K id);

    void put(K id, T value);

    void remove(K id);
}
