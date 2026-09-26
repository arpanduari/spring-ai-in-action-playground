package org.example.ch01.service;

import org.example.ch01.entity.Answer;
import org.example.ch01.entity.Question;

/**
 @author arpanduari
 @since 25/09/26
 */
public interface BoardGameService {
    Answer askQuestion(Question question);
}
