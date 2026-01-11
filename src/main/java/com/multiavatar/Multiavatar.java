package com.multiavatar;

import java.security.MessageDigest;
import java.util.regex.Pattern;

import com.multiavatar.SvgData.Template;

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
    public static String generate(CharacterType character, CharacterTheme theme) {
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
    public static String generate(CharacterType character, CharacterTheme theme, boolean sansEnv) {
        Avatar avatar = Avatar.fromCharacterTheme(character, theme);
        return avatar.render(sansEnv);
    }

    /**
     * Converts a 2-digit decimal string (0-99) to a part number (0-47)
     */
    private static int getPartNumber(String digitPair) {
        int value = Integer.parseInt(digitPair);
        return Math.round((47f / 100f) * value);
    }

    /**
     * Gets the final SVG string for a part with colors applied from the theme
     */
    private static String getFinalSvg(AvatarPart part, CharacterType character, CharacterTheme theme) {
        if (character == null) {
            return "";
        }

        // Get theme colors
        ThemeData.CharacterThemes characterThemes = ThemeData.getCharacterThemes(character);
        if (characterThemes == null) {
            return "";
        }

        ThemeData.Theme themeData = characterThemes.getTheme(theme);
        if (themeData == null) {
            return "";
        }

        String[] colors = getColorsForPart(themeData, part);

        // Get SVG template
        Template svgTemplate = SvgData.getSvgTemplate(character, part);

        return svgTemplate.expand(colors);
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
	 * The avatar configuration
	 */
	private static class Avatar {
	    Coordinate env;
	    Coordinate clo;
	    Coordinate head;
	    Coordinate mouth;
	    Coordinate eyes;
	    Coordinate top;
	
	    Coordinate getValue(AvatarPart part) {
	        switch (part) {
	            case ENV: return env;
	            case CLO: return clo;
	            case HEAD: return head;
	            case MOUTH: return mouth;
	            case EYES: return eyes;
	            case TOP: return top;
	            default: return null;
	        }
	    }
	    
		public static Avatar fromId(String id) {
			// Get SHA-256 hash
	        String hash = sha256(id);

	        // Remove all non-digits from hash (JavaScript compatibility)
	        String hashDigitsOnly = hash.replaceAll("\\D", "");

	        // Extract first 12 digits
	        String hashString = hashDigitsOnly.substring(0, Math.min(12, hashDigitsOnly.length()));

	        // Convert hash string to parts (6 parts, 2 digits each)
	        return Avatar.fromHash(hashString);
		}
	    
		private static Avatar fromHash(String hashString) {
			Avatar avatar = new Avatar();
			// Get parts (range 0-47) and convert to PartWithTheme (0-15 + theme)
	        avatar.env = Coordinate.fromPartNumber(getPartNumber(hashString.substring(0, 2)));
	        avatar.clo = Coordinate.fromPartNumber(getPartNumber(hashString.substring(2, 4)));
	        avatar.head = Coordinate.fromPartNumber(getPartNumber(hashString.substring(4, 6)));
	        avatar.mouth = Coordinate.fromPartNumber(getPartNumber(hashString.substring(6, 8)));
	        avatar.eyes = Coordinate.fromPartNumber(getPartNumber(hashString.substring(8, 10)));
	        avatar.top = Coordinate.fromPartNumber(getPartNumber(hashString.substring(10, 12)));
	        return avatar;
		}

		public static Avatar fromCharacterTheme(CharacterType character, CharacterTheme theme) {
			Avatar avatar = new Avatar();
			Coordinate coordinate = new Coordinate(character, theme);
			avatar.env = coordinate;
			avatar.clo = coordinate;
			avatar.head = coordinate;
			avatar.mouth = coordinate;
			avatar.eyes = coordinate;
			avatar.top = coordinate;
			return avatar;
		}

		/**
		 * Renders this avatar to SVG format
		 *
		 * @param sansEnv If true, renders without the circular background
		 * @return The complete SVG code as a string
		 */
		String render(boolean sansEnv) {
			StringBuilder result = new StringBuilder(SVG_START);

			// Add generator attribution (fulfills license requirement)
			result.append("<metadata xmlns:dc=\"http://purl.org/dc/elements/1.1/\"><dc:creator>Multiavatar</dc:creator><dc:source>https://multiavatar.com</dc:source></metadata>");

			for (AvatarPart part : AvatarPart.values()) {
				if (part == AvatarPart.ENV && sansEnv) {
					continue; // Skip environment if sansEnv is true
				}

				Coordinate coordinate = getValue(part);
				String svgPart = getFinalSvg(part, coordinate.character, coordinate.theme);
				result.append(svgPart);
			}

			result.append(SVG_END);
			return result.toString();
		}

	}

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
	 * Value class holding an avatar character and theme together
	 */
	static class Coordinate {
	    final CharacterType character;
	    final CharacterTheme theme;
	
	    Coordinate(CharacterType character, CharacterTheme theme) {
	        this.character = character;
	        this.theme = theme;
	    }
	
	    /**
	     * Creates a PartWithTheme from a part number (0-47)
	     */
	    static Coordinate fromPartNumber(int nr) {
	        CharacterTheme theme;
	
	        if (nr > 31) {
	            nr = nr - 32;
	            theme = CharacterTheme.C;
	        } else if (nr > 15) {
	            nr = nr - 16;
	            theme = CharacterTheme.B;
	        } else {
	            theme = CharacterTheme.A;
	        }
	
	        CharacterType character = CharacterType.fromIndex(nr);
	        return new Coordinate(character, theme);
	    }
	}

	/**
	 * Avatar character types (16 base characters, 00-15)
	 */
	enum CharacterType {
	    ROBO("00", "Robo"),
	    GIRL("01", "Girl"),
	    BLONDE("02", "Blonde"),
	    GUY("03", "Guy"),
	    COUNTRY("04", "Country"),
	    GEEKNOT("05", "Geeknot"),
	    ASIAN("06", "Asian"),
	    PUNK("07", "Punk"),
	    AFROHAIR("08", "Afrohair"),
	    NORMIE_FEMALE("09", "Normie Female"),
	    OLDER("10", "Older"),
	    FIREHAIR("11", "Firehair"),
	    BLOND("12", "Blond"),
	    ATEAM("13", "Ateam"),
	    RASTA("14", "Rasta"),
	    STREET("15", "Street");
	
	    private final String id;
	    private final String displayName;
	
	    CharacterType(String id, String displayName) {
	        this.id = id;
	        this.displayName = displayName;
	    }
	
	    public String getId() {
	        return id;
	    }
	
	    public String getDisplayName() {
	        return displayName;
	    }
	
	    /**
	     * Get character by ID string (e.g., "00", "15")
	     */
	    public static CharacterType fromId(String id) {
	        for (CharacterType character : values()) {
	            if (character.id.equals(id)) {
	                return character;
	            }
	        }
	        return null;
	    }
	
	    /**
	     * Get character by numeric index (0-15)
	     */
	    public static CharacterType fromIndex(int index) {
	        if (index < 0 || index >= values().length) {
	            return null;
	        }
	        return values()[index];
	    }
	}

	/**
	 * Theme variants for each character
	 */
	enum CharacterTheme {
	    A('A'),
	    B('B'),
	    C('C');
	
	    private final char code;
	
	    CharacterTheme(char code) {
	        this.code = code;
	    }
	
	    public char getCode() {
	        return code;
	    }
	
	    /**
	     * Get theme by character code ('A', 'B', or 'C')
	     */
	    public static CharacterTheme fromCode(char code) {
	        for (CharacterTheme theme : values()) {
	            if (theme.code == code) {
	                return theme;
	            }
	        }
	        return null;
	    }
	}

}
