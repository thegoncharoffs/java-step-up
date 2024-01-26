package org.reflectionApi;

public class Box {
    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("beforeSuite");
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println("afterSuite");
    }

    @Test(priority = 3)
    public static void test3() {
        System.out.println("test3");
    }

    @Test
    public static void testDefault() {
        System.out.println("testDefault");
    }

    @Test(priority = 7)
    public static void test7() {
        System.out.println("test7");
    }

    public static void noAnnotation() {
        System.out.println("noAnnotation");
    }
}
