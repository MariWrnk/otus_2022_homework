package ru.otus.annotations.usage;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.reflection.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestExecutor {

    private final List<String> beforeMethodNames = new ArrayList<>();
    private final List<String> afterMethodNames = new ArrayList<>();
    private final List<String> testMethodNames = new ArrayList<>();

    private int succeedTestCount = 0;
    private int failedTestCount = 0;

    public void execute(String className) throws ClassNotFoundException {
        Class<?> testClass = Class.forName(className);

        prepareTests(testClass);
        runTests(testClass);
        printResult();
    }

    private void prepareTests(Class<?> testClass) {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                testMethodNames.add(method.getName());
            } else if (method.isAnnotationPresent(Before.class)) {
                beforeMethodNames.add(method.getName());
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethodNames.add(method.getName());
            }
        }
    }

    private void runTests(Class<?> testClass) {
        for (String testMethodName : testMethodNames) {
            Object instance = ReflectionHelper.instantiate(testClass);
            boolean success = true;
            try {
                beforeMethodNames.forEach(methodName -> ReflectionHelper.callMethod(instance, methodName));
                ReflectionHelper.callMethod(instance, testMethodName);
            } catch (Exception e) {
                success = false;
            } finally {
                afterMethodNames.forEach(methodName -> ReflectionHelper.callMethod(instance, methodName));
            }

            if (success) {
                System.out.println("------------" + testMethodName + ": run succeed\n");
                succeedTestCount++;
            } else {
                System.out.println("------------" + testMethodName + ": run failed\n");
                failedTestCount++;
            }
        }
    }

    private void printResult() {
        System.out.println("------------ Test results ------------");
        System.out.println("Run: " + (succeedTestCount + failedTestCount) + " tests");
        System.out.println("Succeed: " + succeedTestCount + " tests");
        System.out.println("Failed: " + failedTestCount + " tests");
    }
}
