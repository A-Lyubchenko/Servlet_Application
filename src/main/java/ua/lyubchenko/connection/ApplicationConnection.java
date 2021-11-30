package ua.lyubchenko.connection;

import lombok.SneakyThrows;


import java.util.Properties;

public class ApplicationConnection {
    private static ApplicationConnection instance;
    private final Properties properties;

    @SneakyThrows
    private ApplicationConnection() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        this.properties = new Properties();
            properties.load(classLoader.getResourceAsStream("application.properties"));

    }

    public static Properties getInstance() {
        if (instance == null)
            instance = new ApplicationConnection();
        return instance.properties;
    }
}

