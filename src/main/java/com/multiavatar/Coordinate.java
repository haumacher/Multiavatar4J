package com.multiavatar;

import com.multiavatar.SvgData.Template;

/**
 * Value class holding an avatar character and theme together
 */
public class Coordinate {
    public static Coordinate coordinate(CharacterType character, Theme theme) {
		return new Coordinate(character, theme);
	}

	final CharacterType character;
    final Theme theme;

    private Coordinate(CharacterType character, Theme theme) {
        this.character = character;
        this.theme = theme;
    }
    
	/**
	 * Produces the final SVG string for a part with colors applied from the theme
	 */
	public void renderPart(StringBuilder result, AvatarPart part) {
	    if (character == null) {
	        return;
	    }
	
	    // Get theme colors
	    ThemeData.CharacterThemes characterThemes = ThemeData.getCharacterThemes(character);
	    if (characterThemes == null) {
	        return;
	    }
	
	    ThemeData.Colors themeData = characterThemes.getTheme(theme);
	    if (themeData == null) {
	        return;
	    }
	
	    String[] colors = part.getColors(themeData);
	
	    // Get SVG template
	    Template svgTemplate = SvgData.getSvgTemplate(character, part);
	
	    svgTemplate.render(result, colors);
	}
}