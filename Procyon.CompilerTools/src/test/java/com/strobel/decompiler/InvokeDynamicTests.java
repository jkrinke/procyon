/*
 * InvokeDynamicTests.java
 *
 * Copyright (c) 2025 Mike Strobel
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

public class InvokeDynamicTests extends DecompilerTest {
    
    // Test class that uses invokedynamic for string concatenation
    private static class StringConcatWithChar {
        public String test(char c) {
            return c + " won";
        }
    }
    
    @Test
    public void testStringConcatWithChar() throws Throwable {
        // This test verifies that invokedynamic string concatenation is decompiled correctly
        // and produces compilable code (the decompiled code should compile without errors)
        verifyOutput(
            StringConcatWithChar.class,
            defaultSettings(),
            "private static class StringConcatWithChar {\n" +
            "    public String test(final char c) {\n" +
            "        return c + \" won\";\n" +
            "    }\n" +
            "}\n"
        );
    }
}
