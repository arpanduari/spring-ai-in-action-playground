package org.example.ch02.service;

import org.example.ch02.entity.Answer;
import org.example.ch02.entity.Question;
import org.example.ch02.exception.AnswerNotRelevantException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Primary;
import org.springframework.core.retry.RetryException;
import org.springframework.core.retry.RetryPolicy;
import org.springframework.core.retry.RetryTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 @author arpanduari
 @since 26/09/26
 */
@Service
@Primary
public class SelfEvaluatingBoardGameService implements BoardGameService {
    private final ChatClient chatClient;
    private final RelevancyEvaluator relevancyEvaluator;
    private final RetryTemplate retryTemplate;

    public SelfEvaluatingBoardGameService(ChatClient.Builder chatClientBuilder) {
        OllamaChatOptions.Builder chatOptionsBuilder = OllamaChatOptions.builder()
                .model("qwen3:8b")
                .disableThinking();

        this.chatClient = chatClientBuilder
                .defaultOptions(chatOptionsBuilder)
                .build();

        this.relevancyEvaluator = new RelevancyEvaluator(chatClient.mutate());

        this.retryTemplate = new RetryTemplate(
                RetryPolicy.builder()
                        .includes(AnswerNotRelevantException.class)
                        .maxRetries(2)
                        .delay(Duration.ofSeconds(1))
                        .build()
        );
    }

    @Override
    public Answer askQuestion(Question question) {
        try {
            return retryTemplate.execute(() -> generateAndEvaluate(question));
        } catch (RetryException e) {
            return recover(e);
        }
    }

    private Answer recover(RetryException ex) {
        return new Answer("I am sorry, I was not able to answer the question.");
    }

    private Answer generateAndEvaluate(Question question) {
        String answer = chatClient.prompt()
                .user(question.question())
                .call()
                .content();

        evaluateRelevancy(question.question(), answer);

        return new Answer(answer);
    }

    private void evaluateRelevancy(String question, String answer) {
        EvaluationRequest request = new EvaluationRequest(question, answer);
        EvaluationResponse response = relevancyEvaluator.evaluate(request);

        if (!response.isPass()) {
            throw new AnswerNotRelevantException(question, answer);
        }
    }

}
