package org.example.ch02;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.Assertions;
import org.example.ch02.entity.Answer;
import org.example.ch02.entity.Question;
import org.example.ch02.service.SpringAiBoardGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 @author arpanduari
 @since 25/09/26
 */
@EnableWireMock(
        @ConfigureWireMock(baseUrlProperties = "ollama.base.url")
)
@SpringBootTest(
        properties = {
                "spring.ai.ollama.base-url=${ollama.base.url}"
        }
)
public class SpringAiBoardGameServiceWireMockTests {
    @Value("classpath:/test-ollama-response.json")
    Resource responseResource;

    @Autowired
    ChatClient.Builder chatClientBuilder;

    @BeforeEach
    public void setup() throws IOException {
        var cannedResponse = responseResource.getContentAsString(Charset.defaultCharset());

        var mapper = new ObjectMapper();

        var responseNode = mapper.readTree(cannedResponse);

        WireMock.stubFor(WireMock.post("/api/chat")
                .willReturn(ResponseDefinitionBuilder.okForJson(responseNode)));
    }

    @Test
    public void testAskQuestion() {
        var boardGameService = new SpringAiBoardGameService(chatClientBuilder);
        Answer answer = boardGameService.askQuestion(new Question("What is the capital of India?"));

        Assertions.assertThat(answer).isNotNull();
        Assertions.assertThat(answer.answer()).isEqualTo("New Delhi");
    }
}
