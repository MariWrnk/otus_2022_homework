package ru.otus;

import ru.otus.annotations.usage.TestExecutor;

public class TestRunner {
    public static void main(String[] args) throws ClassNotFoundException {
        new TestExecutor().execute(Tests.class.getName());
    }
}
