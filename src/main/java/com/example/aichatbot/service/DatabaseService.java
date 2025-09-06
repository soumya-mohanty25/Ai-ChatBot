package com.example.aichatbot.service;

import java.util.List;
import java.util.Map;

public interface DatabaseService {

    List<Map<String, Object>> runQuery(String sql);

    String generateSchemaPrompt();
}
