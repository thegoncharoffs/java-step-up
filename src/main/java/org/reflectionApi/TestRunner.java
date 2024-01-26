package org.reflectionApi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestRunner {
    public static void runTest(Class c) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = c.getDeclaredMethods();

        // Count BeforeSuite and AfterSuite quantity
        int beforeSuiteCounter = 0;
        int afterSuiteCounter = 0;
        for (Method method : methods) {
            // BeforeSuite annotation must be only once
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                beforeSuiteCounter++;
                if (beforeSuiteCounter > 1) {
                    throw new RuntimeException("Too many BeforeSuite annotations");
                }
            }

            // AfterSuite annotation must be only once
            if (method.isAnnotationPresent(AfterSuite.class)) {
                afterSuiteCounter++;
                if (afterSuiteCounter > 1) {
                    throw new RuntimeException("Too many AfterSuite annotations");
                }
            }

            // Check priority param to be from 1 to 10
            if (method.isAnnotationPresent(Test.class)) {
                int priority = method.getAnnotation(Test.class).priority();
                if (priority < 1 || priority > 10) {
                    throw new IllegalArgumentException("priority in Test annotation must be from 1 to 10");
                }
            }
        }

        methods = Arrays.stream(methods)
                // Filter methods by required annotations
                .filter(item -> item.isAnnotationPresent(BeforeSuite.class) || item.isAnnotationPresent(Test.class) || item.isAnnotationPresent(AfterSuite.class))
                // Sort methods by annotations
                .sorted(new MethodsWithAnnotationsComparator()).toArray(Method[]::new);

        // Invoke methods
        for (Method method : methods) {
            method.invoke(c);
        }
    }
}
