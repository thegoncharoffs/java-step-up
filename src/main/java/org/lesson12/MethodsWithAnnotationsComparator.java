package org.lesson12;

import java.lang.reflect.Method;
import java.util.Comparator;

public class MethodsWithAnnotationsComparator implements Comparator<Method> {
    @Override
    public int compare(Method m1, Method m2) {
        int p1 = m1.isAnnotationPresent(AfterSuite.class) ? 11 : m1.isAnnotationPresent(BeforeSuite.class) ? 0 : m1.getAnnotation(Test.class).priority();
        int p2 = m2.isAnnotationPresent(AfterSuite.class) ? 11 : m2.isAnnotationPresent(BeforeSuite.class) ? 0 : m2.getAnnotation(Test.class).priority();
        return p1 - p2;
    }
}
