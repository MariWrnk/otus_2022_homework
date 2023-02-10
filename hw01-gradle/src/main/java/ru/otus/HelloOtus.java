package ru.otus;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * To start the application:
 * ./gradlew build
 * java -jar ./L01-gradle/build/libs/gradleHelloOtus-0.1.jar
 */
public class HelloOtus {

    public static void main(String[] args) {
        List<String> catNames = Lists.newArrayList("Boris", "Vasiliy", "Murzik");
        System.out.println(Lists.reverse(catNames));
    }

}
