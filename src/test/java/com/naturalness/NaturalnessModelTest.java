package com.naturalness;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class NaturalnessModelTest {
    public static final double DELTA = 0;
    public static final double ZERO_PROBA = 0;
    private static List<Sequence> sequenceList;

    @BeforeClass
    public static void buildSequence() {
        sequenceList = new ArrayList<>();
        Event a = new Event("a");
        Event b = new Event("b");
        Event c = new Event("c");
        Event d = new Event("d");
        Event e = new Event("e");
        Event f = new Event("f");
        sequenceList.add(new Sequence(Arrays.asList(a, b, c, d, e)));
        sequenceList.add(new Sequence(Arrays.asList(a, b, c, d, c)));
        sequenceList.add(new Sequence(Arrays.asList(f, f, f, f, f, f)));  
    }      
    
    
    @Test
    public void regressionTest() {
        NaturalnessModel<String> model = new NaturalnessModel<>(3, ZERO_PROBA);
        Event<String> a = new Event<>("a");
        Event<String> b = new Event<>("b");
        Event<String> c = new Event<>("c");
        Event<String> d = new Event<>("d");
        model.learn(a, b, c);
        double probaA = model.getProbability(new NGram(), a);
        assertEquals(probaA, 1.0, DELTA);
        double probaB = model.getProbability(new NGram(a), b);
        assertEquals(probaB, 1.0, DELTA);
        double probaC = model.getProbability(new NGram(a,b), c);
        assertEquals(probaC, 1.0, DELTA);
        model.learn(new Sequence(Arrays.asList(a, b, d)));
        probaA = model.getProbability(new NGram(), a);
        assertEquals(probaA, 1.0, DELTA);
        probaB = model.getProbability(new NGram(a), b);
        assertEquals(probaB, 1.0, DELTA);
        probaC = model.getProbability(new NGram(a, b), c);
        assertEquals(0.5, probaC, DELTA);
        System.out.println(probaC);
        double probaD = model.getProbability(new NGram(a, b), d);
        assertEquals(probaD,0.5, DELTA);
        double proba = model.getProbability(new NGram(a, b), d);
        System.out.println(proba);
        double ce = model.crossEntropy(new Sequence(a, b, d));
        System.out.println(ce);
        assertTrue(ce > DELTA);
    }

    @Test
    public void shouldReturnInfinityCrossEntropy() {
        NaturalnessModel model = new NaturalnessModel(3, ZERO_PROBA);
        double ce = model.crossEntropy(sequenceList.get(0));
        assertTrue(Double.isInfinite(ce));
    }

    @Test
    public void shouldDealWithEmptySequence() {
        NaturalnessModel model = new NaturalnessModel(3, DELTA);
        Sequence emptySequence = new Sequence();
        double ce = model.crossEntropy(emptySequence);
        assertEquals(ce, 0, DELTA);
    }

    @Test
    public void shouldComputeAllRightCrossEntropy() {
        NaturalnessModel model = new NaturalnessModel(3, DELTA);
        model.learn(sequenceList.get(0));
        double ce = model.crossEntropy(sequenceList.get(0));
        double expected = -(5 * Math.log(1)/Math.log(2)) / 5;
        assertEquals(expected, ce, DELTA);
    }

    @Test
    public void shouldComputeAllRightButOneCrossEntropy() {
        NaturalnessModel model = new NaturalnessModel();
        model.learn(sequenceList.get(0));
        double ce = model.crossEntropy(sequenceList.get(1));
        double right = 1 - model.getProbaOfUnknown();
        double wrong = model.getProbaOfUnknown();
        double expected = -(4*Math.log(right)/Math.log(2) + Math.log(wrong)/Math.log(2)) / 5;
        assertTrue(ce == expected);
    }

    @Test
    public void shouldComputeAllWrongCrossEntropy() {
        NaturalnessModel model = new NaturalnessModel();
        model.learn(sequenceList.get(0));
        double ce = model.crossEntropy(sequenceList.get(2));
        double wrong = model.getProbaOfUnknown();
        double expected = -(5 * Math.log(wrong)/Math.log(2)) / 5;
        assertTrue(ce == expected);
    }
}