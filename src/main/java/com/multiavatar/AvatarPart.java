package com.multiavatar;

/**
 * Avatar part names in the order they should be rendered
 */
public enum AvatarPart {
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
	 * Gets the color array for a specific part from theme data
	 */
	public String[] getColors(ThemeData.Colors theme) {
	    switch (this) {
	        case ENV: return theme.env;
	        case CLO: return theme.clo;
	        case HEAD: return theme.head;
	        case MOUTH: return theme.mouth;
	        case EYES: return theme.eyes;
	        case TOP: return theme.top;
	        default: return new String[0];
	    }
	}
}