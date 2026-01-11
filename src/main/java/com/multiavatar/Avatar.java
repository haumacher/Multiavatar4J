package com.multiavatar;

import java.util.Random;

/**
 * The avatar configuration
 */
public class Avatar {
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
    
	public static Avatar pure(CharacterType character, Theme theme) {
		Avatar avatar = new Avatar();
		Coordinate coordinate = Coordinate.coordinate(character, theme);
		avatar.env = coordinate;
		avatar.clo = coordinate;
		avatar.head = coordinate;
		avatar.mouth = coordinate;
		avatar.eyes = coordinate;
		avatar.top = coordinate;
		return avatar;
	}

	public static Avatar fromId(String id) {
		// Get SHA-256 hash
        String hash = Multiavatar.sha256(id);

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
        avatar.env = Avatar.fromPartNumber(getPartNumber(hashString.substring(0, 2)));
        avatar.clo = Avatar.fromPartNumber(getPartNumber(hashString.substring(2, 4)));
        avatar.head = Avatar.fromPartNumber(getPartNumber(hashString.substring(4, 6)));
        avatar.mouth = Avatar.fromPartNumber(getPartNumber(hashString.substring(6, 8)));
        avatar.eyes = Avatar.fromPartNumber(getPartNumber(hashString.substring(8, 10)));
        avatar.top = Avatar.fromPartNumber(getPartNumber(hashString.substring(10, 12)));
        return avatar;
	}

	/**
	 * Creates a random avatar using the provided Random instance.
	 *
	 * @param rnd The Random instance to use for generating random parts
	 * @return A new Avatar with randomly selected parts
	 */
	public static Avatar fromRandom(Random rnd) {
		Avatar avatar = new Avatar();
		// Generate random part numbers (0-47) for each avatar part
		avatar.env = Avatar.fromPartNumber(rnd.nextInt(48));
		avatar.clo = Avatar.fromPartNumber(rnd.nextInt(48));
		avatar.head = Avatar.fromPartNumber(rnd.nextInt(48));
		avatar.mouth = Avatar.fromPartNumber(rnd.nextInt(48));
		avatar.eyes = Avatar.fromPartNumber(rnd.nextInt(48));
		avatar.top = Avatar.fromPartNumber(rnd.nextInt(48));
		return avatar;
	}

    /**
	 * Creates a PartWithTheme from a part number (0-47)
	 */
	private static Coordinate fromPartNumber(int nr) {
	    Theme theme;
	
	    if (nr > 31) {
	        nr = nr - 32;
	        theme = Theme.C;
	    } else if (nr > 15) {
	        nr = nr - 16;
	        theme = Theme.B;
	    } else {
	        theme = Theme.A;
	    }
	
	    CharacterType character = CharacterType.fromIndex(nr);
	    return Coordinate.coordinate(character, theme);
	}

	/**
     * Converts a 2-digit decimal string (0-99) to a part number (0-47)
     */
    private static int getPartNumber(String digitPair) {
        int value = Integer.parseInt(digitPair);
        return Math.round((47f / 100f) * value);
    }

	/**
	 * Renders this avatar to SVG format
	 *
	 * @param sansEnv If true, renders without the circular background
	 * @return The complete SVG code as a string
	 */
	public String render(boolean sansEnv) {
		StringBuilder result = new StringBuilder(Multiavatar.SVG_START);

		// Add generator attribution (fulfills license requirement)
		result.append("<metadata xmlns:dc=\"http://purl.org/dc/elements/1.1/\"><dc:creator>Multiavatar</dc:creator><dc:source>https://multiavatar.com</dc:source></metadata>");

		for (AvatarPart part : AvatarPart.values()) {
			if (part == AvatarPart.ENV && sansEnv) {
				continue; // Skip environment if sansEnv is true
			}

			Coordinate coordinate = getValue(part);
			coordinate.renderPart(result, part);
		}

		result.append(Multiavatar.SVG_END);
		return result.toString();
	}

}