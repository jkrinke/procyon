# Procyon Repository Instructions

## Repository Overview

Procyon is a suite of Java metaprogramming tools focused on code generation and analysis. It consists of five main modules:

1. **Procyon.Core** - Core framework with common support classes (string manipulation, collections, filesystem utilities, freezable objects)
2. **Procyon.Reflection** - Reflection and code generation API with full support for generics, wildcards, and high-level Java type concepts (based on .NET's System.Reflection)
3. **Procyon.Expressions** - Expression tree API for natural code generation (similar to .NET's System.Linq.Expressions)
4. **Procyon.CompilerTools** - Class metadata, bytecode inspection/manipulation, optimization, and decompiler framework
5. **Procyon.Decompiler** - Standalone front-end for the Java decompiler (includes embedded dependencies)

## Technology Stack

- **Language**: Java (source compatibility: Java 7)
- **Build Tool**: Gradle (using Gradle wrapper)
- **Testing Framework**: JUnit 4.13.2
- **Package Management**: Maven Central (group ID: `org.bitbucket.mstrobel`)

## Build Instructions

### Building the Project

```bash
./gradlew build
```

This will compile all modules and run tests.

### Building Individual Modules

```bash
./gradlew :Procyon.Core:build
./gradlew :Procyon.Reflection:build
./gradlew :Procyon.Expressions:build
./gradlew :Procyon.CompilerTools:build
./gradlew :Procyon.Decompiler:build
```

### Running Tests

```bash
# Run all tests
./gradlew test

# Run tests for a specific module
./gradlew :Procyon.Core:test
./gradlew :Procyon.CompilerTools:test
```

Note: Test output is configured to show detailed information including stack traces for failures.

### Creating Distribution JARs

```bash
./gradlew jar
```

The Procyon.Decompiler JAR is standalone with all dependencies embedded and can be executed directly.

## Project Structure

```
procyon/
├── Procyon.Core/               # Core framework
│   └── src/main/java/com/strobel/
├── Procyon.Reflection/         # Reflection framework
│   └── src/main/java/com/strobel/reflection/
├── Procyon.Expressions/        # Expression trees framework
│   └── src/main/java/com/strobel/expressions/
├── Procyon.CompilerTools/      # Bytecode tools and decompiler
│   └── src/main/java/com/strobel/
│       ├── assembler/          # Bytecode assembly
│       └── decompiler/         # Decompilation logic
└── Procyon.Decompiler/         # Decompiler CLI
    └── src/main/java/com/strobel/decompiler/
```

## Code Conventions

### File Headers

All Java source files include an Apache License 2.0 header:

```java
/*
 * FileName.java
 *
 * Copyright (c) YYYY Mike Strobel
 *
 * This source code is subject to terms and conditions of the Apache License, Version 2.0.
 * A copy of the license can be found in the License.html file at the root of this distribution.
 * By using this source code in any fashion, you are agreeing to be bound by the terms of the
 * Apache License, Version 2.0.
 *
 * You must not remove this notice, or any other, from this software.
 */
```

### Package Structure

- Base package: `com.strobel`
- Sub-packages organized by functionality (e.g., `com.strobel.assembler`, `com.strobel.decompiler`)

### Annotations

The codebase uses custom annotations for null-safety:
- `@NotNull` - Indicates a parameter, field, or return value must not be null
- `@Nullable` - Indicates a parameter, field, or return value may be null

### Testing

- Test classes are located in `src/test/java` directories
- Tests use JUnit 4
- Abstract test base classes (e.g., `DecompilerTest`) provide common testing utilities
- Test encoding is UTF-8 (explicitly set in CompilerTools module)

## Module Dependencies

```
Procyon.Core (no dependencies)
    ↑
    ├── Procyon.Reflection
    │       ↑
    │       └── Procyon.Expressions
    │
    └── Procyon.CompilerTools
            ↑
            └── Procyon.Decompiler (also depends on jcommander:1.78)
```

## Common Development Tasks

### Adding a New Class

1. Place in appropriate module and package
2. Add Apache License header
3. Use `@NotNull` and `@Nullable` annotations where appropriate
4. Follow existing code style and conventions

### Running the Decompiler

The decompiler can be run as a command-line tool:

```bash
java -jar Procyon.Decompiler/build/libs/procyon-decompiler-*.jar <options>
```

Main class: `com.strobel.decompiler.DecompilerDriver`

### Version Management

Version is defined in `Procyon.Core/src/main/java/com/strobel/Procyon.java`:

```java
private static final String VERSION = "1.0-SNAPSHOT";
```

The build system automatically extracts this version for all modules.

## Important Notes

- Source compatibility is Java 7 to maintain broad compatibility
- The project uses Gradle 6.9 (though newer Java versions may cause compatibility warnings)
- Build artifacts are created in the `build/` directory
- The decompiler module creates a fat JAR with all dependencies embedded
- Not all modules have extensive test coverage (e.g., Core has minimal tests)

## CI/CD

The repository uses GitHub Actions:
- **Gradle Wrapper Validation** - Validates the Gradle wrapper on push and pull requests

## External Resources

- [Wiki - Reflection Framework](https://github.com/mstrobel/procyon/wiki/Reflection-Framework)
- [Wiki - Expressions Framework](https://github.com/mstrobel/procyon/wiki/Expressions-Framework)
- [Wiki - Java Decompiler](https://github.com/mstrobel/procyon/wiki/Java-Decompiler)
- Maven Central: `org.bitbucket.mstrobel`

## Getting Help

For issues and questions, use the GitHub issue tracker at https://github.com/mstrobel/procyon/issues
