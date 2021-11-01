package com.emotie.api.emotion.domain;

import com.emotie.api.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class EmotionTest {
    private static final String EMOTION_BASE_PACKAGE = "com.emotie.api.emotion";

    @Test
    void testEmotionCreated() {
        List<? extends Emotion> emotions = new Reflections(EMOTION_BASE_PACKAGE, new SubTypesScanner(false))
                .getSubTypesOf(Emotion.class).stream()
                .map(concreteEmotionClass -> {
                    try {
                        return concreteEmotionClass.getDeclaredConstructor(Member.class)
                                .newInstance(Member.builder().build());
                    } catch (Exception e) {
                        throw new RuntimeException("Couldn't create concrete Emotion class\n" + e.getMessage());
                    }
                })
                .collect(Collectors.toList());

        emotions.forEach(emotion ->
                assertThat(emotion.getClass().getSimpleName().replace("Emotion", "").toLowerCase())
                        .withFailMessage("Emotion class name should be in format {EmotionName}Emotion." +
                                " Errored class: %s", emotion.getClass().getSimpleName())
                        .isEqualTo(emotion.getName()));
    }

    @Test
    void testDeepenScore() {
        Emotion angryEmotion = new AngryEmotion(Member.builder().build(), 0.0);
        angryEmotion.deepenScore(1.0);

        assertThat(Math.round(angryEmotion.getScore() * 100.0)).isEqualTo(21L);
    }

    @Test
    void testReduceScore() {
        Emotion angryEmotion = new AngryEmotion(Member.builder().build(), 1.0);
        angryEmotion.reduceScore(0.5);

        assertThat(Math.round(angryEmotion.getScore() * 100.0)).isEqualTo(113L);
    }
}
