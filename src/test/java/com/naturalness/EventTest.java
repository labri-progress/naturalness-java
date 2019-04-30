package com.naturalness;

import org.junit.Test;

import static org.junit.Assert.*;

public class EventTest {
    @Test(expected = Exception.class)
    public void shouldThrowAnExceptionWithNull() {
        new Event<>(null);
    }

    @Test
    public void shouldCreateSameHash() {
        Event<String> e1 = new Event("");
        Event<String> e2 = new Event("");
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    public void shouldCreateDifferentHash() {
        Event<String> e1 = new Event("hey");
        Event<String> e2 = new Event("hey!");
        assertNotEquals(e1.hashCode() , e2.hashCode());
    }

}
