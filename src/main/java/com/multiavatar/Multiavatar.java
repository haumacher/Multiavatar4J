package com.multiavatar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
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

	private static final String SVG_START = "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 231 231\">";
    private static final String SVG_END = "</svg>";
    private static final String STROKE = "stroke-linecap:round;stroke-linejoin:round;stroke-width:";

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("#([^;]*);");

    /**
     * Avatar part names in the order they should be rendered
     */
    enum AvatarPart {
        ENV("env"),
        HEAD("head"),
        CLO("clo"),
        TOP("top"),
        EYES("eyes"),
        MOUTH("mouth");

        private final String name;

        AvatarPart(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        /**
         * Parse AvatarPart from string name
         */
        public static AvatarPart fromString(String name) {
            for (AvatarPart part : values()) {
                if (part.name.equals(name)) {
                    return part;
                }
            }
            return null;
        }
    }

    /**
     * Generates an avatar SVG from the given string.
     *
     * @param string The input string to generate the avatar from
     * @return The complete SVG code as a string
     */
    public static String generate(String string) {
        return generate(string, false, null);
    }

    /**
     * Generates an avatar SVG from the given string.
     *
     * @param string  The input string to generate the avatar from
     * @param sansEnv If true, returns the avatar without the circular background
     * @return The complete SVG code as a string
     */
    public static String generate(String string, boolean sansEnv) {
        return generate(string, sansEnv, null);
    }

    /**
     * Generates an avatar SVG from the given string.
     *
     * @param string  The input string to generate the avatar from
     * @param sansEnv If true, returns the avatar without the circular background
     * @param version Force a specific character version (e.g., {part: "01", theme: "A"})
     * @return The complete SVG code as a string
     */
    public static String generate(String string, boolean sansEnv, Version version) {
        if (string == null) {
            string = "";
        }

        // Return empty string for empty input (JavaScript compatibility)
        if (string.length() == 0) {
            return "";
        }

        // Get SHA-256 hash
        String hash = sha256(string);

        // Remove all non-digits from hash (JavaScript compatibility)
        String hashDigitsOnly = hash.replaceAll("\\D", "");

        // Extract first 12 digits
        String hashString = hashDigitsOnly.substring(0, Math.min(12, hashDigitsOnly.length()));

        // Convert hash string to parts (6 parts, 2 digits each)
        Parts parts = new Parts();

        // Get parts (range 0-47)
        parts.env = getPartNumber(hashString.substring(0, 2));
        parts.clo = getPartNumber(hashString.substring(2, 4));
        parts.head = getPartNumber(hashString.substring(4, 6));
        parts.mouth = getPartNumber(hashString.substring(6, 8));
        parts.eyes = getPartNumber(hashString.substring(8, 10));
        parts.top = getPartNumber(hashString.substring(10, 12));

        // Get parts (range 0-15) + define themes
        parts.envStr = getPartWithTheme(parts.env);
        parts.cloStr = getPartWithTheme(parts.clo);
        parts.headStr = getPartWithTheme(parts.head);
        parts.mouthStr = getPartWithTheme(parts.mouth);
        parts.eyesStr = getPartWithTheme(parts.eyes);
        parts.topStr = getPartWithTheme(parts.top);

        // Get the SVG code for each part
        StringBuilder result = new StringBuilder(SVG_START);

        // Add generator attribution (fulfills license requirement)
        result.append("<metadata xmlns:dc=\"http://purl.org/dc/elements/1.1/\"><dc:creator>Multiavatar</dc:creator><dc:source>https://multiavatar.com</dc:source></metadata>");

        for (AvatarPart part : AvatarPart.values()) {
            String partValue = parts.getValue(part);
            String partId = partValue.substring(0, 2);
            char theme = partValue.charAt(2);

            if (version != null) {
                partId = version.part;
                theme = version.theme;
            }

            String svgPart = getFinalSvg(part, partId, theme);

            if (part == AvatarPart.ENV && sansEnv) {
                continue; // Skip environment if sansEnv is true
            }

            result.append(svgPart);
        }

        result.append(SVG_END);

        return result.toString();
    }

    /**
     * Converts a 2-digit decimal string (0-99) to a part number (0-47)
     */
    private static int getPartNumber(String digitPair) {
        int value = Integer.parseInt(digitPair);
        return Math.round((47f / 100f) * value);
    }

    /**
     * Converts a part number (0-47) to a part ID with theme (e.g., "01A", "05B", "12C")
     */
    private static String getPartWithTheme(int nr) {
        String partId;
        char theme;

        if (nr > 31) {
            nr = nr - 32;
            theme = 'C';
        } else if (nr > 15) {
            nr = nr - 16;
            theme = 'B';
        } else {
            theme = 'A';
        }

        partId = String.format("%02d", nr);
        return partId + theme;
    }

    /**
     * Gets the final SVG string for a part with colors applied from the theme
     */
    private static String getFinalSvg(AvatarPart part, String partId, char theme) {
        // Get theme colors
        ThemeData.CharacterThemes characterThemes = ThemeData.getCharacterThemes(partId);
        if (characterThemes == null) {
            return "";
        }

        ThemeData.Theme themeData = characterThemes.getTheme(theme);
        if (themeData == null) {
            return "";
        }

        String[] colors = getColorsForPart(themeData, part);

        // Get SVG template
        String svgTemplate = SvgData.getSvgPart(partId, part);
        if (svgTemplate == null || svgTemplate.isEmpty()) {
            return "";
        }

        // Replace color placeholders
        // Use JavaScript-compatible replacement: String.replace() in a loop
        // This has a "bug" where replacing A→B then B→C will re-replace the new B
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(svgTemplate);

        // First, collect all matches
        java.util.List<String> matches = new java.util.ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group(0));
        }

        // Then replace them one by one (JavaScript behavior)
        String result = svgTemplate;
        for (int i = 0; i < matches.size() && i < colors.length; i++) {
            // JavaScript uses String.replace() which replaces FIRST occurrence
            result = result.replaceFirst(java.util.regex.Pattern.quote(matches.get(i)), colors[i] + ";");
        }

        return result;
    }

    /**
     * Gets the color array for a specific part from theme data
     */
    private static String[] getColorsForPart(ThemeData.Theme theme, AvatarPart part) {
        switch (part) {
            case ENV: return theme.env;
            case CLO: return theme.clo;
            case HEAD: return theme.head;
            case MOUTH: return theme.mouth;
            case EYES: return theme.eyes;
            case TOP: return theme.top;
            default: return new String[0];
        }
    }

    /**
     * Calculates SHA-256 hash of a string
     */
    private static String sha256(String input) {
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

    /**
     * Helper class to store part values
     */
    private static class Parts {
        int env;
        int clo;
        int head;
        int mouth;
        int eyes;
        int top;

        String envStr;
        String cloStr;
        String headStr;
        String mouthStr;
        String eyesStr;
        String topStr;

        String getValue(AvatarPart part) {
            switch (part) {
                case ENV: return envStr != null ? envStr : "";
                case CLO: return cloStr != null ? cloStr : "";
                case HEAD: return headStr != null ? headStr : "";
                case MOUTH: return mouthStr != null ? mouthStr : "";
                case EYES: return eyesStr != null ? eyesStr : "";
                case TOP: return topStr != null ? topStr : "";
                default: return "";
            }
        }
    }

    /**
     * Version specification for forcing a specific character/theme
     */
    public static class Version {
        public String part;  // e.g., "01"
        public char theme;   // 'A', 'B', or 'C'

        public Version(String part, char theme) {
            this.part = part;
            this.theme = theme;
        }
    }
}
