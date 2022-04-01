package ru.clevertec.cache.impl;

import ru.clevertec.cache.Cacheable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * LFU(last frequency used) - cache contains the most using elements.
 * LFU has capacity - constant.
 * KV - cache with elements.
 * frequency - map with freq of using objects.
 *
 * @author tsimafeilabanovich
 * @param <T> it's object which will contains in LFU cache
 */
public class LFUCache<T> implements Cacheable<T> {
    private final int capacity;
    private final Map<Long, HitRate> frequency = new HashMap<>();
    private final Map<Long, T> KV = new HashMap<>();

    /**
     * @param capacity an integer
     */
    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Creating and modifying an object in the cache.
     *
     * @param key   an integer
     * @param value an integer
     */
    public void put(Long key, T value) {
        T v = KV.get(key);
        if (v == null) {
            if (frequency.size() == capacity) {
                Long k = getKickedKey();
                KV.remove(k);
                frequency.remove(k);
            }
            frequency.put(key, new HitRate(key, 1L, System.nanoTime()));
        } else {
            HitRate hitRate = frequency.get(key);
            hitRate.hitCount += 1;
            hitRate.lastTime = System.nanoTime();
        }
        KV.put(key, value);
    }

    /**
     * Remove an object in the cache
     *
     * @param key an integer
     */
    @Override
    public Long remove(Long key) {
        return KV.remove(key, KV.get(key)) ? key : null;
    }

    /**
     * @return an integer
     */
    public int size() {
        return KV.size();
    }

    /**
     * Getting an object from the cache
     *
     * @param key an integer
     * @return cache object
     */
    public T get(Long key) {
        T v = KV.get(key);
        if (v != null) {
            HitRate hitRate = frequency.get(key);
            hitRate.hitCount += 1;
            hitRate.lastTime = System.nanoTime();
            return v;
        }
        return null;
    }

    /**
     * @return replacement key
     */
    private Long getKickedKey() {
        HitRate min = Collections.min(frequency.values());
        return min.key;
    }

    /**
     * hitCount - number of hits
     * lastTime - last access time
     */
    static class HitRate implements Comparable<HitRate> {
        Long key;
        Long hitCount;
        Long lastTime;

        public HitRate(Long key, Long hitCount, Long lastTime) {
            this.key = key;
            this.hitCount = hitCount;
            this.lastTime = lastTime;
        }

        public int compareTo(HitRate o) {
            int hr = hitCount.compareTo(o.hitCount);
            return hr != 0 ? hr : lastTime.compareTo(o.lastTime);
        }
    }

}