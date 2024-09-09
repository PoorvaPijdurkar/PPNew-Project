package com.procure.service;

import com.procure.config.EntitiesConfiguration;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntitiesService {

    @Autowired
    private EntitiesConfiguration configuration;

    public Map<String, List<String>> getResponse() {
        return configuration.getProfiles();
    }
}
