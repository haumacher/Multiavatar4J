package com.multiavatar;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * Multiavatar - Multicultural Avatar Generator
 *
 * Generates unique SVG avatars from text strings, predefined characters, or random generation.
 * Can generate 12,230,590,464 unique avatars (48^6).
 *
 * Usage:
 * <pre>
 * // Generate from identifier (deterministic)
 * String svgCode = Multiavatar.generate("Binx Bond");
 * String svgWithoutBackground = Multiavatar.generate("Binx Bond", true);
 *
 * // Generate predefined avatar
 * String predefined = Multiavatar.generate(CharacterType.GIRL, Theme.A);
 *
 * // Generate random avatar
 * Random rnd = new Random();
 * String random = Multiavatar.generate(rnd);
 * </pre>
 *
 * @author Gie Katon
 * @version 1.0.7
 */
public class Multiavatar {

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
     * @param character The {@link CharacterType} to use (e.g., GIRL, ROBO)
     * @param theme     The {@link Theme} to use (A, B, or C)
     * @return The complete SVG code as a string
     */
    public static String generate(CharacterType character, Theme theme) {
        return generate(character, theme, false);
    }

    /**
     * Generates a predefined avatar SVG with specific character and theme.
     *
     * @param character The {@link CharacterType} to use (e.g., GIRL, ROBO)
     * @param theme     The {@link Theme} to use (A, B, or C)
     * @param sansEnv   If true, returns the avatar without the circular background
     * @return The complete SVG code as a string
     */
    public static String generate(CharacterType character, Theme theme, boolean sansEnv) {
        Avatar avatar = Avatar.pure(character, theme);
        return avatar.render(sansEnv);
    }

    /**
     * Generates a random avatar SVG using the provided {@link Random} instance.
     *
     * @param rnd The {@link Random} instance to use for generating random parts
     * @return The complete SVG code as a string
     */
    public static String generate(Random rnd) {
        return generate(rnd, false);
    }

    /**
     * Generates a random avatar SVG using the provided {@link Random} instance.
     *
     * @param rnd     The {@link Random} instance to use for generating random parts
     * @param sansEnv If true, returns the avatar without the circular background
     * @return The complete SVG code as a string
     */
    public static String generate(Random rnd, boolean sansEnv) {
        Avatar avatar = Avatar.fromRandom(rnd);
        return avatar.render(sansEnv);
    }

}
