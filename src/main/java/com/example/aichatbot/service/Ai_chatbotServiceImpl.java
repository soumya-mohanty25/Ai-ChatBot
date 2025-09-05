package com.example.aichatbot.service;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Ai_chatbotServiceImpl implements Ai_chatbotService{

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Override
    public String askQuestion(String question) {
        
         return openAiChatModel.call(question);
    }

}
