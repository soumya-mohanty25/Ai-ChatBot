package com.example.aichatbot.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> runQuery(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public String generateSchemaPrompt() {
        StringBuilder schema = new StringBuilder("Database schema:\n");

        // Get all table names from 'public' schema
        List<String> tableNames = jdbcTemplate.queryForList(
            "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'",
            String.class
        );

        for (String tableName : tableNames) {
            schema.append("Table: ").append(tableName).append("(");

            // Get column names for each table
            List<String> columns = jdbcTemplate.queryForList(
                "SELECT column_name FROM information_schema.columns WHERE table_name = ?",
                new Object[]{tableName},
                String.class
            );

            schema.append(String.join(", ", columns)).append(")\n");
        }

        return schema.toString();
    }

}
