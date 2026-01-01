/*
 * GenericTypeTests.java
 *
 * Copyright (c) 2026 Mike Strobel
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.NavigableSet;
import java.util.TreeSet;

public class GenericTypeTests extends DecompilerTest {
    
    @SuppressWarnings("UnusedDeclaration")
    private static class GenericCollectionTest {
        /**
         * Test case for issue where decompiler generates incorrect wildcard casts
         * when code is compiled with debug info.
         * 
         * The decompiler should not generate (Collection<?>) casts, but instead
         * should either omit the cast or use a properly bounded type.
         */
        public char testNavigableSetAddAll(ArrayList<Character> cells) {
            NavigableSet<Character> set = new TreeSet<Character>();
            set.addAll(cells);  // This should not generate (Collection<?>) cast
            set.remove('T');
            if (set.size() == 1) {
                return set.first();
            }
            return '.';
        }
        
        /**
         * Similar test with different generic types to ensure the fix is general.
         */
        public <T> T testGenericCollectionOps(ArrayList<T> list, T defaultValue) {
            TreeSet<T> set = new TreeSet<T>();
            set.addAll(list);  // This should not generate incorrect casts
            if (set.isEmpty()) {
                return defaultValue;
            }
            return set.first();
        }
    }
    
    @Test
    public void testNavigableSetAddAllWithDebugInfo() throws Throwable {
        // This test verifies that the decompiled code does not contain invalid casts
        // like (Collection<?>) that would cause compilation errors.
        // 
        // The expected output should compile without errors.
        verifyOutput(
            GenericCollectionTest.class,
            defaultSettings(),
            "private static class GenericCollectionTest {\n" +
            "    public char testNavigableSetAddAll(final ArrayList<Character> cells) {\n" +
            "        final NavigableSet<Character> set = new TreeSet<Character>();\n" +
            "        set.addAll(cells);\n" +
            "        set.remove('T');\n" +
            "        if (set.size() == 1) {\n" +
            "            return set.first();\n" +
            "        }\n" +
            "        return '.';\n" +
            "    }\n" +
            "    public <T> T testGenericCollectionOps(final ArrayList<T> list, final T defaultValue) {\n" +
            "        final TreeSet<T> set = new TreeSet<T>();\n" +
            "        set.addAll(list);\n" +
            "        if (set.isEmpty()) {\n" +
            "            return defaultValue;\n" +
            "        }\n" +
            "        return set.first();\n" +
            "    }\n" +
            "}\n"
        );
    }
}
