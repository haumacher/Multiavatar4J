package com.multiavatar;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Debug utility to compare JavaScript and Java outputs directly.
 * Run with: mvn test-compile exec:java -Dexec.mainClass="com.multiavatar.CompareJsJavaOutput" -Dexec.classpathScope=test
 */
public class CompareJsJavaOutput {
    public static void main(String[] args) throws Exception {
        String input = "Binx Bond";

        // Generate using Java
        String javaOutput = Multiavatar.generate(input);

        System.out.println("=== Comparing Output for: \"" + input + "\" ===\n");

        System.out.println("Java output length: " + javaOutput.length());
        System.out.println("Java output (first 300 chars):");
        System.out.println(javaOutput.substring(0, Math.min(300, javaOutput.length())));
        System.out.println();

        // Load JavaScript output from test vectors
        try {
            String jsData = new String(Files.readAllBytes(Paths.get("test-vectors.json")));

            // Find the entry for "Binx Bond"
            int idx = jsData.indexOf("\"input\": \"Binx Bond\"");
            if (idx > 0) {
                int outputIdx = jsData.indexOf("\"output\":", idx);
                int outputStart = jsData.indexOf("\"", outputIdx + 9) + 1;

                // Find the end quote (accounting for escaped quotes)
                int outputEnd = outputStart;
                while (outputEnd < jsData.length()) {
                    if (jsData.charAt(outputEnd) == '"' && jsData.charAt(outputEnd - 1) != '\\') {
                        break;
                    }
                    outputEnd++;
                }

                String jsOutput = jsData.substring(outputStart, outputEnd);
                // Unescape JSON
                jsOutput = jsOutput.replace("\\\"", "\"")
                                 .replace("\\\\", "\\")
                                 .replace("\\n", "\n")
                                 .replace("\\r", "\r")
                                 .replace("\\t", "\t");

                System.out.println("JavaScript output length: " + jsOutput.length());
                System.out.println("JavaScript output (first 300 chars):");
                System.out.println(jsOutput.substring(0, Math.min(300, jsOutput.length())));
                System.out.println();

                // Character-by-character comparison
                System.out.println("=== Character-by-Character Comparison (first 100 chars) ===");
                int compareLen = Math.min(100, Math.min(javaOutput.length(), jsOutput.length()));
                for (int i = 0; i < compareLen; i++) {
                    char javaChar = javaOutput.charAt(i);
                    char jsChar = jsOutput.charAt(i);
                    if (javaChar != jsChar) {
                        System.out.printf("Position %d: Java='%c' (0x%02x), JS='%c' (0x%02x)\n",
                            i, javaChar, (int)javaChar, jsChar, (int)jsChar);
                    }
                }

                // Compare structure
                System.out.println("\n=== Structural Comparison ===");
                System.out.println("Java starts with: " + javaOutput.substring(0, Math.min(80, javaOutput.length())));
                System.out.println("JS starts with:   " + jsOutput.substring(0, Math.min(80, jsOutput.length())));

                int len = Math.min(javaOutput.length(), jsOutput.length());
                int firstDiff = -1;
                for (int i = 0; i < len; i++) {
                    if (javaOutput.charAt(i) != jsOutput.charAt(i)) {
                        firstDiff = i;
                        break;
                    }
                }

                if (firstDiff >= 0) {
                    System.out.println("\nFirst difference at position " + firstDiff + ":");
                    int start = Math.max(0, firstDiff - 20);
                    int end = Math.min(len, firstDiff + 60);
                    System.out.println("Java: ..." + javaOutput.substring(start, end) + "...");
                    System.out.println("JS:   ..." + jsOutput.substring(start, end) + "...");
                } else {
                    System.out.println("\nNo character differences found in common length!");
                    if (javaOutput.length() != jsOutput.length()) {
                        System.out.println("But lengths differ: Java=" + javaOutput.length() + ", JS=" + jsOutput.length());
                    }
                }

                System.out.println("\nMatch: " + javaOutput.equals(jsOutput));

            } else {
                System.out.println("Could not find 'Binx Bond' in test vectors");
            }
        } catch (Exception e) {
            System.out.println("Error reading test vectors: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
