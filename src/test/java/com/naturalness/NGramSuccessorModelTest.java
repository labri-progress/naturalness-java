package com.naturalness;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NGramSuccessorModelTest {

    @Test
    public void shouldReturnNullProbability() {
        NGramSuccessorModel model = new NGramSuccessorModel();
        double proba = model.getProbability(new Event("hey"));   
        assertEquals(0, proba, 0);
    }

    @Test
    public void shouldLearnAndReturnKnownProbability() {
        NGramSuccessorModel model = new NGramSuccessorModel();
        model.learn(new Event("a"));
        double proba = model.getProbability(new Event("a"));   
        assertEquals(1, proba, 0);
        model.learn(new Event("a"));
        proba = model.getProbability(new Event("a"));   
        assertEquals(1, proba, 0);
        model.learn(new Event("b"));
        proba = model.getProbability(new Event("a"));   
        assertEquals(2d/3d, proba , 0);
        proba = model.getProbability(new Event("b"));   
        assertEquals(1d/3d, proba, 0);
        proba = model.getProbability(new Event("c"));   
        assertEquals(0, proba, 0);
    }
}