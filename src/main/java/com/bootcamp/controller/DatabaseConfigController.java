package com.bootcamp.controller;

import com.bootcamp.service.DatabaseConfigService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class DatabaseConfigController {
    private final DatabaseConfigService databaseConfigService;

    public DatabaseConfigController(DatabaseConfigService databaseConfigService) {
        this.databaseConfigService = databaseConfigService;
    }

    @GetMapping("/database-url")
    public String getDatabaseUrl() {
        return databaseConfigService.getDatabaseUrl();
    }

    @GetMapping("/database-username")
    public String getDatabaseUsername() {
        return databaseConfigService.getDatabaseUsername();
    }

    @GetMapping("/database-password")
    public String getDatabasePassword() {
        return databaseConfigService.getDatabasePassword();
    }
}
