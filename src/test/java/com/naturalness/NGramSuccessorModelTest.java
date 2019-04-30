package com.naturalness;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NGramSuccessorModelTest {

    @Test
    public void shouldReturnNullProbability() {
        NGramSuccessorModel model = new NGramSuccessorModel();
        double proba = model.getProbability(new Event("hey"));   
        assertTrue(proba == 0);
    }

    @Test
    public void shouldLearnAndReturnKnownProbability() {
        NGramSuccessorModel model = new NGramSuccessorModel();
        model.learn(new Event("a"));
        double proba = model.getProbability(new Event("a"));   
        assertTrue(proba == 1);
        model.learn(new Event("a"));
        proba = model.getProbability(new Event("a"));   
        assertTrue(proba == 1);
        model.learn(new Event("b"));
        proba = model.getProbability(new Event("a"));   
        assertTrue(proba == 2/3);
        proba = model.getProbability(new Event("b"));   
        assertTrue(proba == 1/3);
        proba = model.getProbability(new Event("c"));   
        assertTrue(proba == 0);
    }
}