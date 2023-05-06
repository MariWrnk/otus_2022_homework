package ru.otus.annotations.usage;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.reflection.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestExecutor {

    public void runTests(String className) throws ClassNotFoundException {
        Class<?> testClass = Class.forName(className);

        List<String> beforeMethodNames = new ArrayList<>();
        List<String> afterMethodNames = new ArrayList<>();
        List<String> testMethodNames = new ArrayList<>();

        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                testMethodNames.add(method.getName());
            } else if (method.isAnnotationPresent(Before.class)) {
                beforeMethodNames.add(method.getName());
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethodNames.add(method.getName());
            }
        }

        int succeed = 0;
        int failed = 0;

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
                succeed++;
            } else {
                System.out.println("------------" + testMethodName + ": run failed\n");
                failed++;
            }
        }

        System.out.println("------------ Test results ------------");
        System.out.println("Run: " + (succeed + failed) + " tests");
        System.out.println("Succeed: " + succeed + " tests");
        System.out.println("Failed: " + failed + " tests");

    }
}
