package org.example.ch02.service;

import org.example.ch02.entity.Answer;
import org.example.ch02.entity.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 @author arpanduari
 @since 25/09/26
 */
@Service
public class SpringAiBoardGameService implements BoardGameService {
    private final ChatClient chatClient;

    public SpringAiBoardGameService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public Answer askQuestion(Question question) {
        String answerText = chatClient.prompt()
                .user(question.question())
                .call()
                .content();

        return new Answer(answerText);
    }
}
