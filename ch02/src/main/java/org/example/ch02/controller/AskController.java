package org.example.ch02.controller;

import org.example.ch02.entity.Answer;
import org.example.ch02.entity.Question;
import org.example.ch02.service.BoardGameService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 @author arpanduari
 @since 25/09/26
 */
@RestController
public class AskController {
    private final BoardGameService boradGameService;

    public AskController(BoardGameService boardGameService) {
        this.boradGameService = boardGameService;
    }

    @PostMapping(value = "/ask", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Answer> askQuestion(@RequestBody Question question) {
        Answer answer = boradGameService.askQuestion(question);
        return ResponseEntity.ok(answer);
    }
}
