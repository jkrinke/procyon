/*
 * VariableTests.java
 *
 * Copyright (c) 2015 Mike Strobel
 *
 * This source code is subject to terms and conditions of the Apache License, Version 2.0.
 * A copy of the license can be found in the License.html file at the root of this distribution.
 * By using this source code in any fashion, you are agreeing to be bound by the terms of the
 * Apache License, Version 2.0.
 *
 * You must not remove this notice, or any other, from this software.
 */

package com.strobel.decompiler;

import org.junit.Test;

public class VariableTests extends DecompilerTest {
    @SuppressWarnings("UnusedDeclaration")
    private static class A {
        public void test1() {
            final int min = Integer.parseInt("1");
            final int max = Integer.parseInt("2");
            final int min2 = Integer.parseInt("3");
            final int max2 = Integer.parseInt("4");
            final int min3 = Integer.parseInt("5");
            final int max3 = Integer.parseInt("6");

            System.out.println(max - min);
            System.out.println(max2 - min2);
            System.out.println(max3 - min3);
        }

        public void test2() {
            final int min1 = Integer.parseInt("1");
            final int max1 = Integer.parseInt("2");
            final int min2 = Integer.parseInt("3");
            final int max2 = Integer.parseInt("4");
            final int min3 = Integer.parseInt("5");
            final int max3 = Integer.parseInt("6");

            System.out.println(max1 - min1);
            System.out.println(max2 - min2);
            System.out.println(max3 - min3);
        }

        public void test3() {
            final int min2 = Integer.parseInt("1");
            final int max2 = Integer.parseInt("2");
            final int min3 = Integer.parseInt("3");
            final int max3 = Integer.parseInt("4");

            System.out.println(max2 - min2);
            System.out.println(max3 - min3);
        }

        public void test4() {
            final int min3 = Integer.parseInt("3");
            final int max3 = Integer.parseInt("4");
            final int min4 = Integer.parseInt("5");
            final int max4 = Integer.parseInt("6");

            System.out.println(max3 - min3);
            System.out.println(max4 - min4);
        }

        public void test5() {
            final int min3 = Integer.parseInt("3");
            final int max3 = Integer.parseInt("4");
            final int min = Integer.parseInt("5");
            final int max = Integer.parseInt("6");

            System.out.println(max3 - min3);
            System.out.println(max - min);
        }
    }

    @Test
    public void testNumberedVariablesDoNotBreakVariableNaming() throws Throwable {
        verifyOutput(
            A.class,
            defaultSettings(),
            "private static class A {\n" +
            "    public void test1() {\n" +
            "        final int min = Integer.parseInt(\"1\");\n" +
            "        final int max = Integer.parseInt(\"2\");\n" +
            "        final int min2 = Integer.parseInt(\"3\");\n" +
            "        final int max2 = Integer.parseInt(\"4\");\n" +
            "        final int min3 = Integer.parseInt(\"5\");\n" +
            "        final int max3 = Integer.parseInt(\"6\");\n" +
            "        System.out.println(max - min);\n" +
            "        System.out.println(max2 - min2);\n" +
            "        System.out.println(max3 - min3);\n" +
            "    }\n" +
            "    public void test2() {\n" +
            "        final int min1 = Integer.parseInt(\"1\");\n" +
            "        final int max1 = Integer.parseInt(\"2\");\n" +
            "        final int min2 = Integer.parseInt(\"3\");\n" +
            "        final int max2 = Integer.parseInt(\"4\");\n" +
            "        final int min3 = Integer.parseInt(\"5\");\n" +
            "        final int max3 = Integer.parseInt(\"6\");\n" +
            "        System.out.println(max1 - min1);\n" +
            "        System.out.println(max2 - min2);\n" +
            "        System.out.println(max3 - min3);\n" +
            "    }\n" +
            "    public void test3() {\n" +
            "        final int min2 = Integer.parseInt(\"1\");\n" +
            "        final int max2 = Integer.parseInt(\"2\");\n" +
            "        final int min3 = Integer.parseInt(\"3\");\n" +
            "        final int max3 = Integer.parseInt(\"4\");\n" +
            "        System.out.println(max2 - min2);\n" +
            "        System.out.println(max3 - min3);\n" +
            "    }\n" +
            "    public void test4() {\n" +
            "        final int min3 = Integer.parseInt(\"3\");\n" +
            "        final int max3 = Integer.parseInt(\"4\");\n" +
            "        final int min4 = Integer.parseInt(\"5\");\n" +
            "        final int max4 = Integer.parseInt(\"6\");\n" +
            "        System.out.println(max3 - min3);\n" +
            "        System.out.println(max4 - min4);\n" +
            "    }\n" +
            "    public void test5() {\n" +
            "        final int min3 = Integer.parseInt(\"3\");\n" +
            "        final int max3 = Integer.parseInt(\"4\");\n" +
            "        final int min4 = Integer.parseInt(\"5\");\n" +
            "        final int max4 = Integer.parseInt(\"6\");\n" +
            "        System.out.println(max3 - min3);\n" +
            "        System.out.println(max4 - min4);\n" +
            "    }\n" +
            "}\n"
        );
    }

    @SuppressWarnings("UnusedDeclaration")
    private static class B {
        // Test case for unused variable with string concatenation
        // This should not produce invalid expression statements
        public void testUnusedStringConcatenation(char c) {
            // This variable is never used
            String row = "" + c + c + c + c;
            
            // Do some other work
            System.out.println("Test");
        }
    }

    @Test
    public void testUnusedVariableWithStringConcatenation() throws Throwable {
        // The decompiler should handle unused variables with string concatenation
        // In simple cases like this, it keeps the declaration which is fine
        verifyOutput(
            B.class,
            defaultSettings(),
            "private static class B {\n" +
            "    public void testUnusedStringConcatenation(final char c) {\n" +
            "        final String row = \"\" + c + c + c + c;\n" +
            "        System.out.println(\"Test\");\n" +
            "    }\n" +
            "}\n"
        );
    }
}
