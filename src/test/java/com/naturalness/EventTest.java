package com.naturalness;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class EventTest {
    @Test
    public void shouldThrowAnExceptionWithNull() {
        try {
            new Event(null);
            fail("No exception");

        } catch (Exception e) {

        }
    }

    @Test
    public void shouldCreateSameHash() {
        Event e1 = new Event("");
        Event e2 = new Event("");
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    public void shouldCreateDifferentHash() {
        Event e1 = new Event("hey");
        Event e2 = new Event("hey!");
        assertTrue(e1.hashCode() != e2.hashCode());
    }

}
