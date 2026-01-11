package com.multiavatar;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Debug utility to compare JavaScript and Java outputs directly.
 * Run with: mvn test-compile exec:java -Dexec.mainClass="com.multiavatar.CompareJsJavaOutput" -Dexec.classpathScope=test
 */
public class CompareJsJavaOutput {
    public static void main(String[] args) throws Exception {
        String input = "Test";

        // Generate using Java
        String javaOutput = Multiavatar.generate(input, false, Multiavatar.CharacterType.GEEKNOT, Multiavatar.CharacterTheme.A);

        // Debug: Print which parts are being used
        System.out.println("=== DEBUG: Hash and Parts for: \"" + input + "\" ===");
        java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        String hashStr = hexString.toString();
        String digitsOnly = hashStr.replaceAll("\\D", "");
        String first12 = digitsOnly.substring(0, Math.min(12, digitsOnly.length()));
        System.out.println("Hash: " + hashStr);
        System.out.println("Digits only: " + digitsOnly);
        System.out.println("First 12: " + first12);
        System.out.println();

        System.out.println("=== Comparing Output for: \"" + input + "\" [05A] ===\n");

        System.out.println("Java output length: " + javaOutput.length());
        System.out.println("Java output (first 300 chars):");
        System.out.println(javaOutput.substring(0, Math.min(300, javaOutput.length())));
        System.out.println();

        // Load JavaScript output from test vectors
        try {
            String jsData = new String(Files.readAllBytes(Paths.get("test-vectors.json")));

            // Find the entry for case 15 (Test with version 05A)
            int idx = jsData.indexOf("\"id\": 15");
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
                System.out.println("Could not find case 15 in test vectors");
            }
        } catch (Exception e) {
            System.out.println("Error reading test vectors: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
