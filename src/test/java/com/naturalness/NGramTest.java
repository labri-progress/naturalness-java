package com.naturalness;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class NGramTest {

    @Test(expected = Exception.class)
    public void shouldCreateNGram() {
        new NGram<>((List<Event<String>>)null);
    }

    @Test
    public void shouldCreateSameHash() {
        List<Event> eventList = new ArrayList<Event>();
        NGram ng1 = new NGram(eventList);
        NGram ng2 = new NGram(eventList);
        assertTrue(ng1.hashCode() == ng2.hashCode());
    }

    @Test
    public void shouldEncapsulateEventList() {
        List<Event> eventList = new ArrayList<Event>();
        NGram ng1 = new NGram(eventList);
        int hash = ng1.hashCode();
        eventList.add(new Event("hey"));
        assertTrue(ng1.hashCode() == hash);
    }

    @Test
    public void shouldCreateSameNGram() {
        List<Event> eventList = new ArrayList<Event>();
        NGram ng1 = new NGram(eventList);
        NGram ng2 = new NGram(eventList);
        assertEquals(ng1, ng2);
    }
}