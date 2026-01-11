# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Multiavatar is a multicultural avatar generator that creates unique SVG avatars from text strings. It can generate 12,230,590,464 (48^6) unique avatars by combining different parts (environment, clothes, head, mouth, eyes, top) from 16 initial characters with 3 color themes each.

**Available Implementations:**
- **JavaScript**: Single-file, dependency-free vanilla JavaScript implementation with embedded SHA-256 library
- **Java**: Maven-based library (Java 8+) with the same functionality

## Commands

### JavaScript Commands

#### Build
```bash
npm run build
```
Creates both CommonJS and ESM distributions in the `dist/` directory by:
- Creating necessary dist folders
- Adding a package.json with `"type": "commonjs"` to dist/commonjs
- Copying multiavatar.min.js and appending appropriate module exports

### Java Commands

#### Build
```bash
mvn clean compile
```
Compiles the Java source files to `target/classes/`.

#### Test
```bash
mvn test
```
Runs all unit tests (11 test cases covering basic functionality, determinism, special characters, version forcing, etc.).

#### Package
```bash
mvn package
```
Creates a JAR file in `target/multiavatar-1.0.7.jar`.

#### Install to Local Maven Repository
```bash
mvn install
```
Installs the JAR to your local ~/.m2 repository for use in other projects.

### Development Build (SVG → JS)
```bash
cd svg
php _build.php
```
Rebuilds multiavatar.js from the SVG source files. Run this after editing `*_final.svg` files in the svg/ directory. This extracts SVG elements by class (clothes, mouth, eyes, top) from the 16 character files (00_final.svg through 15_final.svg) and injects them into multiavatar.js between the `inject_start` and `inject_end` markers.

### Testing
Open `demo.html` in a browser to test avatar generation interactively.
Open `svg/index.html` to view all 48 initial unique avatars (16 characters × 3 themes).

## Architecture

### Core Algorithm

The library converts any input string into a deterministic avatar using this flow:

1. **Hash Generation**: Input string → SHA-256 hash → first 12 digits extracted
2. **Part Selection**: Each pair of digits (0-99) maps to one of 48 variants (0-47) for each of 6 parts
3. **Theme Assignment**: The 48 variants are distributed across 16 base characters × 3 themes (A/B/C):
   - 0-15: Theme A
   - 16-31: Theme B
   - 32-47: Theme C
4. **Color Application**: Each theme defines color palettes for parts; colors replace `#(.*?);` placeholders in SVG strings
5. **SVG Assembly**: Final SVG is assembled in order: environment → head → clothes → top → eyes → mouth

### Data Structure

**multiavatar.js** contains:
- `themes` object: Maps character IDs (00-15) → themes (A/B/C) → parts → color arrays
- `sP` object (SVG Parts): Maps character IDs → parts → SVG string templates with color placeholders
- `multiavatar(string, sansEnv, ver)` function

### The 16 Initial Characters

Each numbered 00-15 with descriptive names:
00=Robo, 01=Girl, 02=Blonde, 03=Evilnormie, 04=Country, 05=Geeknot, 06=Asian, 07=Punk, 08=Afrohair, 09=Normie Female, 10=Older, 11=Firehair, 12=Blond, 13=Ateam, 14=Rasta, 15=Meta

### SVG Source Files

Source files in `svg/`:
- `00.svg` through `15.svg`: Working files for editing
- `00_final.svg` through `15_final.svg`: Optimized SVG files (saved from Inkscape 1.0 as "Optimized SVG") used by _build.php
- Each SVG uses CSS classes to identify parts: `class="clothes"`, `class="mouth"`, `class="eyes"`, `class="top"`
- Head and environment parts use special handling in the build script

## Function Parameters

```javascript
multiavatar(string, sansEnv, ver)
```

- `string` (required): Input text that determines the avatar
- `sansEnv` (optional): If true, returns avatar without the circular background (environment part)
- `ver` (optional): Object to force a specific character/theme, e.g., `{part: '01', theme: 'A'}`

## TypeScript Support

TypeScript type definitions are included:
- `dist/commonjs/index.d.ts`
- `dist/esm/index.d.ts`

## Distribution

The package supports both CommonJS and ESM:
- CommonJS: `require('@multiavatar/multiavatar')` → dist/commonjs/index.js
- ESM: `import multiavatar from '@multiavatar/multiavatar/esm'` → dist/esm/index.js

Both built files are generated from `multiavatar.min.js` with appropriate module syntax appended.

## Design Workflow

1. Edit SVG files in svg/ directory using Inkscape or similar vector editor
2. Keep designs in grayscale (colors are applied by the script)
3. Save edited files as `*_final.svg` (Inkscape: "Save As" → "Optimized SVG")
4. Run `php svg/_build.php` to extract SVG elements and update multiavatar.js
5. Update color themes in the `themes` object in multiavatar.js if needed
6. Test changes using demo.html

## Java Implementation

### Project Structure

```
src/main/java/com/multiavatar/
├── Multiavatar.java    - Main public API class
├── ThemeData.java      - Color theme definitions (16 characters × 3 themes)
└── SvgData.java        - SVG template strings for all character parts
```

### Usage Example

```java
import com.multiavatar.Multiavatar;

public class Example {
    public static void main(String[] args) {
        // Basic usage
        String svg = Multiavatar.generate("Binx Bond");

        // Without background circle
        String svgNoBackground = Multiavatar.generate("Binx Bond", true);

        // Generate predefined avatar with specific character/theme
        String svgPredefined = Multiavatar.generate(Multiavatar.CharacterType.GIRL, Multiavatar.CharacterTheme.A);
    }
}
```

### Architecture Notes

The Java implementation follows the same algorithm as the JavaScript version:
- **ThemeData.java**: Contains all 48 theme configurations (16 characters × 3 themes A/B/C) with color arrays for each part
- **SvgData.java**: Contains all 96 SVG template strings (16 characters × 6 parts) with color placeholders
- **Multiavatar.java**: Main class implementing the algorithm (hashing → part selection → theme assignment → color application → SVG assembly)

The Java version uses Java's built-in `MessageDigest` for SHA-256 hashing (no external dependencies except JUnit for tests).

## Important Notes

- The JavaScript library is intentionally kept as a single file (multiavatar.js) with all SVG data and logic embedded
- SHA-256 library is included inline in JavaScript; Java uses built-in MessageDigest
- Not all 12 billion combinations are visually unique (some parts like eyes/mouth are similar)
- Parts can be hidden using `"none"` as a color value in theme definitions
- The Java implementation maintains full compatibility with the JavaScript version (same input produces same SVG output)
