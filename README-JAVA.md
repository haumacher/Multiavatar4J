# Multiavatar - Java Implementation

[Multiavatar](https://multiavatar.com) is a multicultural avatar generator implemented in Java.

This is a Maven-based Java port of the JavaScript Multiavatar library, maintaining full compatibility with the original implementation.

## Features

- Generate unique SVG avatars from any text string
- 12,230,590,464 possible unique avatars (48^6)
- Deterministic - same input always produces the same avatar
- No external dependencies (except JUnit for tests)
- Java 8+ compatible
- Thread-safe static methods

## Installation

### Maven

Add the project as a dependency (after installing to local Maven repository):

```bash
mvn install
```

Then add to your `pom.xml`:

```xml
<dependency>
    <groupId>com.multiavatar</groupId>
    <artifactId>multiavatar</artifactId>
    <version>1.0.7</version>
</dependency>
```

### Build from Source

```bash
# Clone the repository
git clone https://github.com/multiavatar/Multiavatar.git
cd Multiavatar

# Build
mvn clean compile

# Run tests
mvn test

# Package as JAR
mvn package
```

## Usage

### Basic Usage

```java
import com.multiavatar.Multiavatar;

public class Example {
    public static void main(String[] args) {
        // Generate avatar SVG
        String svgCode = Multiavatar.generate("Binx Bond");
        System.out.println(svgCode);

        // Save to file
        try (FileWriter writer = new FileWriter("avatar.svg")) {
            writer.write(svgCode);
        }
    }
}
```

### Advanced Usage

```java
import com.multiavatar.Multiavatar;
import com.multiavatar.CharacterType;
import com.multiavatar.Theme;
import java.util.Random;

public class AdvancedExample {
    public static void main(String[] args) {
        // Generate without background circle
        String svgWithoutBackground = Multiavatar.generate("User Name", true);

        // Generate predefined avatar with specific character and theme
        String svgPredefined = Multiavatar.generate(CharacterType.GIRL, Theme.A);

        // Generate random avatar
        Random rnd = new Random();
        String randomAvatar = Multiavatar.generate(rnd);

        // Generate random avatar without background
        String randomNoBackground = Multiavatar.generate(rnd, true);

        // Different inputs produce different avatars
        String avatar1 = Multiavatar.generate("Alice");
        String avatar2 = Multiavatar.generate("Bob");
        // avatar1 != avatar2

        // Same input always produces the same avatar
        String avatarA = Multiavatar.generate("Same User");
        String avatarB = Multiavatar.generate("Same User");
        // avatarA.equals(avatarB) == true
    }
}
```

## API Reference

### `Multiavatar.generate(String id)`

Generates an avatar SVG from the given identifier (deterministic).

**Parameters:**
- `id` - The identifier to generate the avatar from (e.g., username, email)

**Returns:** Complete SVG code as a String

### `Multiavatar.generate(String id, boolean sansEnv)`

Generates an avatar SVG from identifier with optional background.

**Parameters:**
- `id` - The identifier to generate the avatar from
- `sansEnv` - If `true`, returns the avatar without the circular background

**Returns:** Complete SVG code as a String

### `Multiavatar.generate(CharacterType character, Theme theme)`

Generates a predefined avatar SVG with specific character and theme.

**Parameters:**
- `character` - The character type to use (e.g., `CharacterType.GIRL`, `CharacterType.ROBO`)
- `theme` - The theme to use (`Theme.A`, `Theme.B`, or `Theme.C`)

**Returns:** Complete SVG code as a String

### `Multiavatar.generate(CharacterType character, Theme theme, boolean sansEnv)`

Generates a predefined avatar SVG with optional background.

**Parameters:**
- `character` - The character type to use
- `theme` - The theme to use
- `sansEnv` - If `true`, returns the avatar without the circular background

**Returns:** Complete SVG code as a String

### `Multiavatar.generate(Random rnd)`

Generates a random avatar SVG using the provided Random instance.

**Parameters:**
- `rnd` - The Random instance to use for generating random parts

**Returns:** Complete SVG code as a String

### `Multiavatar.generate(Random rnd, boolean sansEnv)`

Generates a random avatar SVG with optional background.

**Parameters:**
- `rnd` - The Random instance to use for generating random parts
- `sansEnv` - If `true`, returns the avatar without the circular background

**Returns:** Complete SVG code as a String

## Character IDs

The 16 base characters:
- 00: Robo
- 01: Girl
- 02: Blonde
- 03: Guy
- 04: Country
- 05: Geeknot
- 06: Asian
- 07: Punk
- 08: Afrohair
- 09: Normie Female
- 10: Older
- 11: Firehair
- 12: Blond
- 13: Ateam
- 14: Rasta
- 15: Street

Each character has 3 color themes: A, B, and C.

## Development

### Running Tests

```bash
mvn test
```

Test coverage includes:
- Basic SVG generation
- Deterministic output (same input → same output)
- Different inputs produce different outputs
- Null and empty string handling
- Special characters and Unicode support
- Version forcing
- Sans-environment mode

### Project Structure

```
src/main/java/com/multiavatar/
├── Multiavatar.java    - Main public API
├── ThemeData.java      - Color themes (48 configurations)
└── SvgData.java        - SVG templates (96 parts)

src/test/java/com/multiavatar/
└── MultiavatarTest.java - Unit tests
```

## Algorithm

1. **Hash**: Input string → SHA-256 hash → extract first 12 hex digits
2. **Part Selection**: Convert each pair of hex digits (0-99) to part number (0-47)
3. **Theme Assignment**: Map part numbers to character IDs (0-15) and themes (A/B/C)
4. **Color Application**: Replace color placeholders in SVG templates with theme colors
5. **SVG Assembly**: Combine parts in order: env → head → clothes → top → eyes → mouth

## License

See [LICENSE](LICENSE) file in the repository.

## Credits

- Original JavaScript implementation: [Multiavatar](https://github.com/multiavatar/Multiavatar)
- Author: Gie Katon
- Java port: Based on v1.0.7 of the JavaScript version

## Links

- Website: https://multiavatar.com
- JavaScript Repository: https://github.com/multiavatar/Multiavatar
- NPM Package: [@multiavatar/multiavatar](https://www.npmjs.com/package/@multiavatar/multiavatar)
