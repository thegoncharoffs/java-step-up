package org.reflectionApi;

public class Priority {
    public static int checkPriority(int value) {
        if (value < 1 || value > 10) {
            throw new IllegalArgumentException("Priority must be from 1 to 10");
        }

        return value;
    }
}
