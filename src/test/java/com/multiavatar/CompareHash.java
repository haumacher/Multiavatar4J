package com.multiavatar;

import java.security.MessageDigest;

/**
 * Debug utility to verify SHA-256 hash implementation matches between Java and JavaScript.
 * Run with: mvn test-compile exec:java -Dexec.mainClass="com.multiavatar.CompareHash" -Dexec.classpathScope=test
 */
public class CompareHash {
    public static void main(String[] args) throws Exception {
        String[] testInputs = {"Binx Bond", "Test", "Alice", "", "test"};

        System.out.println("=== SHA-256 Hash Comparison ===\n");

        for (String input : testInputs) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
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

            System.out.printf("Input: \"%s\"%n", input);
            System.out.printf("  Full hash:     %s%n", hashStr);
            System.out.printf("  Digits only:   %s%n", digitsOnly);
            System.out.printf("  First 12 digits: %s%n", first12);

            // Show how these map to part numbers
            if (first12.length() >= 12) {
                for (int i = 0; i < 6; i++) {
                    String digitPair = first12.substring(i * 2, i * 2 + 2);
                    int value = Integer.parseInt(digitPair);
                    int partNum = Math.round((47f / 100f) * value);
                    System.out.printf("    Part %d: %s (dec) -> %d (part 0-47)%n",
                        i, digitPair, partNum);
                }
            }
            System.out.println();
        }

        System.out.println("\nExpected hashes from JavaScript (verify these match):");
        System.out.println("  \"Binx Bond\" -> c24e86de81fa... (first 12: c24e86de81fa)");
        System.out.println("  \"Test\"      -> 532eaabd9574... (first 12: 532eaabd9574)");
    }
}
