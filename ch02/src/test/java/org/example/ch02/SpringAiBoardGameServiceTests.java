package org.example.ch02;

import org.assertj.core.api.Assertions;
import org.example.ch02.entity.Answer;
import org.example.ch02.entity.Question;
import org.example.ch02.service.BoardGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 @author arpanduari
 @since 26/09/26
 */
@SpringBootTest
public class SpringAiBoardGameServiceTests {
    @Autowired
    private BoardGameService boardGameService;

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    private RelevancyEvaluator relevancyEvaluator;

    @BeforeEach
    void setUp() {
        this.relevancyEvaluator = new RelevancyEvaluator(chatClientBuilder);
    }

    @Test
    public void evaluateRelevancy() {
        String userText = "Why is the sky blue?";
        Question question = new Question(userText);
        Answer answer = boardGameService.askQuestion(question);

        EvaluationRequest evaluationRequest = new EvaluationRequest(userText, answer.answer());

        EvaluationResponse evaluationResponse = this.relevancyEvaluator.evaluate(evaluationRequest);

        Assertions.assertThat(evaluationResponse.isPass())
                .withFailMessage("""
                        ========================================
                        The answer "%s"
                        is not considered correct for the question "%s".
                        ========================================
                        """, answer.answer(), userText)
                .isTrue();
    }
}
