package com.example.aichatbot.service;

import java.util.Map;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class Ai_chatbotService {

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Autowired
    private DatabaseService databaseService;

    // Define your schema here so GPT knows what tables/columns exist
//     private static final String SCHEMA = """
//          Database schema:
//     Table: t_sml_prasad_item(prasad_item_id, prasad_item_name_en, prasad_item_name_od, active)
// """;

     public Object askDatabase(String question) {
        String schema = databaseService.generateSchemaPrompt();
        String prompt = """
            You are a SQL assistant. Convert the following natural language question into a valid PostgreSQL SQL query.

            %s

            Question: %s

            Only return a valid SELECT SQL query.
        """.formatted(schema, question);

        String sql = openAiChatModel.call(prompt)
                .replaceAll("(?i)```sql", "")
                .replaceAll("```", "")
                .trim();

        if (!sql.toUpperCase().startsWith("SELECT")) {
            return Map.of("error", "❌ Only SELECT queries are allowed.", "sql", sql);
        }

        try {
            var results = databaseService.runQuery(sql);
            return results; // returns List<Map<String, Object>>
        } catch (Exception e) {
            return Map.of(
                "error", "⚠️ Error running query: " + e.getMessage(),
                "sql", sql
            );
        }
    }
}
