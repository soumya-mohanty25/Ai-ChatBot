package com.example.aichatbot.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.aichatbot.service.Ai_chatbotService;
@Controller
@RequestMapping("/chat")
public class Ai_chatbotController {

    @Autowired
    private Ai_chatbotService chatService;

    @GetMapping
    public String chatPage() {
        return "chatbot"; // this will map to chat.jsp inside /WEB-INF/jsp/
    }

    @ResponseBody
    @PostMapping(value="/ask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object ask(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        return chatService.askDatabase(question);
    }
}
