package org.example.ch02.service;

import org.example.ch02.entity.Answer;
import org.example.ch02.entity.Question;

/**
 @author arpanduari
 @since 25/09/26
 */
public interface BoardGameService {
    Answer askQuestion(Question question);
}
