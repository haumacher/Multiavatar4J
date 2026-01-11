package com.multiavatar;

import org.junit.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Test that generates example SVG files for manual inspection.
 * The generated files are placed in target/examples/
 */
public class GenerateExamplesTest {

    @Test
    public void generateExampleAvatars() throws IOException {
        // Create examples directory
        File examplesDir = new File("target/examples");
        if (!examplesDir.exists()) {
            examplesDir.mkdirs();
        }

        // Sample input strings to generate avatars from
        String[] inputs = {
            "Binx Bond",
            "Test User",
            "Alice",
            "Bob",
            "Charlie",
            "Diana",
            "Emma",
            "Frank",
            "Grace",
            "Henry",
            "测试用户",
            "user@example.com",
            "John Doe",
            "Jane Smith",
            "Admin123",
            "guest",
            "developer",
            "designer",
            "manager",
            "support"
        };

        // Generate avatars with background
        for (String input : inputs) {
            String svg = Multiavatar.generate(input);
            String filename = sanitizeFilename(input) + ".svg";
            writeToFile(new File(examplesDir, filename), svg);
        }

        // Generate some examples without background
        String[] sansEnvInputs = {"Binx Bond", "Alice", "Charlie", "Emma"};
        for (String input : sansEnvInputs) {
            String svg = Multiavatar.generate(input, true);
            String filename = sanitizeFilename(input) + "_no_background.svg";
            writeToFile(new File(examplesDir, filename), svg);
        }

        // Generate examples with forced versions (different themes for same character)
        String testInput = "Version Test";
        for (int characterIndex = 0; characterIndex < 3; characterIndex++) {
            Multiavatar.AvatarCharacter character = Multiavatar.AvatarCharacter.fromIndex(characterIndex);
            for (Multiavatar.Theme theme : Multiavatar.Theme.values()) {
                String svg = Multiavatar.generate(testInput, false, character, theme);
                String filename = "version_char" + character.getId() + "_theme" + theme.getCode() + ".svg";
                writeToFile(new File(examplesDir, filename), svg);
            }
        }

        System.out.println("Generated " + (inputs.length + sansEnvInputs.length + 9) + " example SVG files in target/examples/");
        System.out.println("Open these files in a web browser to view the avatars.");
    }

    /**
     * Sanitize filename to be filesystem-safe
     */
    private String sanitizeFilename(String input) {
        return input.replaceAll("[^a-zA-Z0-9_-]", "_").toLowerCase();
    }

    /**
     * Write content to file
     */
    static void writeToFile(File file, String content) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }
}
