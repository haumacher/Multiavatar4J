package com.multiavatar;

/**
 * Avatar character types (16 base characters, 00-15)
 */
public enum CharacterType {
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