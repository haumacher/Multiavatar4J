package com.multiavatar;

import java.security.MessageDigest;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Multiavatar - Multicultural Avatar Generator
 *
 * Generates unique SVG avatars from text strings.
 * Can generate 12,230,590,464 unique avatars (48^6).
 *
 * Usage:
 * <pre>
 * String svgCode = Multiavatar.generate("Binx Bond");
 * String svgWithoutBackground = Multiavatar.generate("Binx Bond", true);
 * </pre>
 *
 * @author Gie Katon
 * @version 1.0.7
 */
public class Multiavatar {

	static final String SVG_START = "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 231 231\">";
    static final String SVG_END = "</svg>";
    private static final String STROKE = "stroke-linecap:round;stroke-linejoin:round;stroke-width:";

    /**
     * Generates an avatar SVG from the given identifier.
     *
     * @param id The identifier to generate the avatar from (e.g., username, email)
     * @return The complete SVG code as a string
     */
    public static String generate(String id) {
        return generate(id, false);
    }

    /**
     * Generates an avatar SVG from the given identifier.
     *
     * @param id      The identifier to generate the avatar from (e.g., username, email)
     * @param sansEnv If true, returns the avatar without the circular background
     * @return The complete SVG code as a string
     */
    public static String generate(String id, boolean sansEnv) {
        if (id == null) {
            id = "";
        }

        // Return empty string for empty input (JavaScript compatibility)
        if (id.length() == 0) {
            return "";
        }

        Avatar avatar = Avatar.fromId(id);
        return avatar.render(sansEnv);
    }

    /**
     * Generates a predefined avatar SVG with specific character and theme.
     *
     * @param character The character to use (e.g., GIRL, ROBO)
     * @param theme     The theme to use (A, B, or C)
     * @return The complete SVG code as a string
     */
    public static String generate(CharacterType character, Theme theme) {
        return generate(character, theme, false);
    }

    /**
     * Generates a predefined avatar SVG with specific character and theme.
     *
     * @param character The character to use (e.g., GIRL, ROBO)
     * @param theme     The theme to use (A, B, or C)
     * @param sansEnv   If true, returns the avatar without the circular background
     * @return The complete SVG code as a string
     */
    public static String generate(CharacterType character, Theme theme, boolean sansEnv) {
        Avatar avatar = Avatar.pure(character, theme);
        return avatar.render(sansEnv);
    }

    /**
     * Generates a random avatar SVG using the provided Random instance.
     *
     * @param rnd The Random instance to use for generating random parts
     * @return The complete SVG code as a string
     */
    public static String generate(Random rnd) {
        return generate(rnd, false);
    }

    /**
     * Generates a random avatar SVG using the provided Random instance.
     *
     * @param rnd     The Random instance to use for generating random parts
     * @param sansEnv If true, returns the avatar without the circular background
     * @return The complete SVG code as a string
     */
    public static String generate(Random rnd, boolean sansEnv) {
        Avatar avatar = Avatar.fromRandom(rnd);
        return avatar.render(sansEnv);
    }

    /**
     * Calculates SHA-256 hash of a string
     */
    static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error calculating SHA-256", e);
        }
    }

}
