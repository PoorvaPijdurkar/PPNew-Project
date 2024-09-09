package com.procure.config;

import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("resources")
public class EntitiesConfiguration {

    private Map<String, List<String>> profiles;

    public Map<String, List<String>> getProfiles() {
        return profiles;
    }

    public void setProfiles(Map<String, List<String>> profiles) {
        this.profiles = profiles;
    }
}
