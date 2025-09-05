package com.example.aichatbot.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aichatbot.service.Ai_chatbotService;
@RestController
@RequestMapping("/chatbot")
public class Ai_chatbotController {

    @Autowired
    private Ai_chatbotService chatbotService;

    @PostMapping("/ask")
    public String askQuestion(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        return chatbotService.askQuestion(question);
}
}
