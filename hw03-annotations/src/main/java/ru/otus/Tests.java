package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class Tests {

    @Before
    public void init() {
        System.out.println("Run Before block");
    }

    @After
    public void shutDown() {
        System.out.println("Run After block");
    }

    @Test
    public void test1() {
        System.out.println("Run test 1");
    }

    @Test
    public void test2() {
        System.out.println("Run test 2");
        throw new RuntimeException();
    }

    @Test
    public void test3() {
        System.out.println("Run test 3");
    }
}
