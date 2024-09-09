package com.procure.web.rest;

import com.procure.service.EntitiesService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/entities")
public class EntitiesResource {

    @Autowired
    private EntitiesService entitiesService;

    @GetMapping("/get")
    public ResponseEntity<Map<String, List<String>>> getDetails() {
        return ResponseEntity.ok().body(entitiesService.getResponse());
    }
}
