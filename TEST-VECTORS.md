# Test Vectors

This document explains how to generate and update test vectors for cross-platform compatibility testing.

## Overview

Test vectors are generated from the JavaScript implementation and used to verify that the Java implementation produces identical output. This ensures cross-platform compatibility between the JavaScript and Java versions of Multiavatar.

## Test Vector Coverage

The current test vectors (`test-vectors.json`) include:

1. **Basic string-based generation**: Various input strings with different characteristics
2. **Sans environment mode**: Avatars without circular background
3. **Special characters**: Unicode, email addresses, special symbols
4. **All pure avatars**: All 16 character types × 3 themes × 2 background modes = 96 combinations
5. **Edge cases**: Empty strings, long strings, numbers

**Total**: 132 test vectors

## Generating Test Vectors

### Prerequisites

- Node.js installed
- The JavaScript implementation (`multiavatar.js`) must be present

### Steps

1. Modify `generate-test-vectors.cjs` if you want to add new test cases:
   ```javascript
   const testCases = [
       { input: "Your Input", sansEnv: false, version: null },
       // Add more test cases here
   ];
   ```

2. Run the generation script:
   ```bash
   node generate-test-vectors.cjs
   ```

3. The script will:
   - Load the JavaScript implementation
   - Generate SVG output for each test case
   - Write results to `test-vectors.json`
   - Display a summary of success/failure

4. Verify the Java implementation matches:
   ```bash
   mvn test -Dtest=CrossPlatformCompatibilityTest
   ```

## Test Case Format

Each test vector in `test-vectors.json` has the following structure:

```json
{
  "id": 0,
  "input": "Binx Bond",
  "sansEnv": false,
  "version": null,
  "output": "<svg xmlns=...",
  "length": 2077
}
```

### Fields

- `id`: Sequential identifier for the test case
- `input`: The input string used to generate the avatar
- `sansEnv`: Boolean indicating whether to omit the circular background
- `version`: For pure avatars, specifies `{ part: "00", theme: "A" }`, otherwise `null`
- `output`: The complete SVG output from the JavaScript implementation
- `length`: Length of the SVG output in characters

## Pure Avatar Test Cases

Pure avatars are automatically generated for all combinations:

- **Characters**: 00-15 (Robo, Girl, Blonde, Guy, Country, Geeknot, Asian, Punk, Afrohair, Normie Female, Older, Firehair, Blond, Ateam, Rasta, Street)
- **Themes**: A, B, C
- **Background modes**: With and without circular background

This ensures comprehensive coverage of all predefined avatar configurations.

## Implementation Notes

### JavaScript API

The JavaScript `multiavatar()` function signature:
```javascript
multiavatar(string, sansEnv, ver)
```

- `string`: Input string for avatar generation
- `sansEnv`: Boolean, true to omit background
- `ver`: Object `{ part: "00", theme: "A" }` or `undefined` for hash-based generation

### Java API

The Java implementation provides equivalent methods:
```java
// String-based generation
Multiavatar.generate(String id)
Multiavatar.generate(String id, boolean sansEnv)

// Pure avatar generation
Multiavatar.generate(CharacterType character, Theme theme)
Multiavatar.generate(CharacterType character, Theme theme, boolean sansEnv)
```

## Verifying Compatibility

The `CrossPlatformCompatibilityTest` class automatically:

1. Loads all test vectors from `test-vectors.json`
2. Generates the same avatars using the Java implementation
3. Compares Java output with JavaScript output character-by-character
4. Reports any mismatches

All test vectors must pass for the Java implementation to be considered compatible with the JavaScript version.

## Updating Test Vectors

When updating test vectors:

1. Modify `generate-test-vectors.cjs` to add/change test cases
2. Regenerate: `node generate-test-vectors.cjs`
3. Verify Java compatibility: `mvn test -Dtest=CrossPlatformCompatibilityTest`
4. Commit both `generate-test-vectors.cjs` and `test-vectors.json`

## File Extensions

**Important**: The script uses `.cjs` extension (CommonJS) because the project's `package.json` specifies `"type": "module"`. The `.cjs` extension explicitly marks the file as CommonJS, allowing the use of `require()` and `eval()` to load the JavaScript implementation.
