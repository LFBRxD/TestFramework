package framework.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
    private static ReadProperties instance;
    private final Properties properties;

    private ReadProperties() {
        properties = new Properties();
        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("framework.properties")) {

            if (input == null) {
                throw new IOException("Arquivo framework.properties não encontrado no classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o arquivo properties", e);
        }
    }

    public static ReadProperties getInstance() {
        if (instance == null) {
            synchronized (ReadProperties.class) {
                if (instance == null) {
                    instance = new ReadProperties();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public String getDefaulWEBtUrl() {
        return properties.getProperty("base.web.url", "http://localhost");
    }

    public String getDefaulAPItUrl() {
        return properties.getProperty("base.api.url", "http://localhost");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "true"));
    }

    public int getDefaultTimeOut() {
        return Integer.parseInt(properties.getProperty("action.timeout", "60"));
    }

    public int getDefaultActionDelay() {
        return Integer.parseInt(properties.getProperty("action.delay", "0"));
    }
}