package com.example.map;

import java.util.HashMap;
import java.util.Map;

/**
 * (student):
 * Implement Flyweight factory that caches MarkerStyle by a stable key.
 *
 * Suggested key format:
 *   shape + "|" + color + "|" + size + "|" + (filled ? "F" : "O")
 *
 * After refactor:
 * - MapDataSource should call this factory to obtain shared MarkerStyle instances.
 */
public class MarkerStyleFactory {

    private final Map<String, MarkerStyle> cache = new HashMap<>();

    public MarkerStyle get(String shape, String color, int size, boolean filled) {
        String key = shape + "|" + color + "|" + size + "|" + (filled ? "F" : "O");
        // return cached instance if present; otherwise create, cache, and return.
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        MarkerStyle markerStyle = new MarkerStyle(shape, color, size, filled);
        cache.put(key, markerStyle);
        return markerStyle;
    }

    public int cacheSize() {
        return cache.size();
    }
}
