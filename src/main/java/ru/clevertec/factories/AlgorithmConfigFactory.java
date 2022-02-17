package ru.clevertec.factories;

import ru.clevertec.caches.Cacheable;
import ru.clevertec.caches.impl.LFUCache;
import ru.clevertec.caches.impl.LRUCache;
import ru.clevertec.entity.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AlgorithmConfigFactory {
    private static final String CONFIG_FILE_NAME = "src/main/resources/application.yml";
    private static final String cacheSizeRegex = "cache-size:\\s(\\d+)";
    private static final String algorithmTypeRegex = "cache-algorithm:\\s(\\w{3})";

    public static Cacheable<Product> getAlgorithmType() {
        Path pathOfConfigFile = Paths.get(CONFIG_FILE_NAME);
        Cacheable<Product> algorithmType = null;
        try {
            String stringValueOfAlgorithmType = getValueOfConfigParam(pathOfConfigFile, algorithmTypeRegex);
            int cacheSize = getCacheSize();

            if (stringValueOfAlgorithmType.equalsIgnoreCase("LFU")) {
                algorithmType = new LFUCache<>(cacheSize);
            } else {
                algorithmType = new LRUCache<>(cacheSize);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return algorithmType;
    }


    private static int getCacheSize() {
        Path pathOfConfigFile = Paths.get(CONFIG_FILE_NAME);
        int size = 0;
        try {
            String stringValueOfCacheSize = getValueOfConfigParam(pathOfConfigFile, cacheSizeRegex);

            size = Integer.parseInt(stringValueOfCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return size;
    }

    private static String getValueOfConfigParam(Path pathOfConfigFile, String regex) throws IOException {
        List<String> strings = Files.readAllLines(pathOfConfigFile);
        String stringValueOfConfigParam = strings.stream()
                .filter(s -> s.matches(regex))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        Matcher matcher = Pattern.compile(cacheSizeRegex).matcher(stringValueOfConfigParam);
        while (matcher.find()) {
            stringValueOfConfigParam = matcher.group(1);
        }
        return stringValueOfConfigParam;
    }
}
