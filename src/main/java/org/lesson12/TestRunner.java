package org.lesson12;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestRunner {
    public static void runTest(Class c) throws InvocationTargetException, IllegalAccessException, RuntimeException {
        Method[] methods = c.getDeclaredMethods();

        // Count BeforeSuite and AfterSuite quantity
        int beforeSuiteCounter = 0;
        int afterSuiteCounter = 0;
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                beforeSuiteCounter++;
            }

            if (method.isAnnotationPresent(AfterSuite.class)) {
                afterSuiteCounter++;
            }
        }

        // Check valid annotations quantity
        if (beforeSuiteCounter > 1) {
            throw new RuntimeException("Too many BeforeSuite annotations");
        }

        if (afterSuiteCounter > 1) {
            throw new RuntimeException("Too many AfterSuite annotations");
        }

        methods = Arrays.stream(methods)
                // Filter methods by required annotations
                .filter(item -> item.isAnnotationPresent(AfterSuite.class) || item.isAnnotationPresent(Test.class) || item.isAnnotationPresent(BeforeSuite.class))
                // Sort methods by annotations
                .sorted(new MethodsWithAnnotationsComparator())
                .toArray(Method[]::new);

        // Invoke methods
        for (Method method : methods) {
            method.invoke(c);
        }
    }
}
