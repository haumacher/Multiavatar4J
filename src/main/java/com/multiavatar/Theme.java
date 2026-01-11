package com.multiavatar;

/**
 * Theme variants for each character
 */
public enum Theme {
    A('A'),
    B('B'),
    C('C');

    private final char code;

    Theme(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

    /**
     * Get theme by character code ('A', 'B', or 'C')
     */
    public static Theme fromCode(char code) {
        for (Theme theme : values()) {
            if (theme.code == code) {
                return theme;
            }
        }
        return null;
    }
}