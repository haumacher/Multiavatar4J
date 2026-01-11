package com.multiavatar;

/**
 * Avatar part names in the order they should be rendered.
 * Each part represents a layer of the avatar SVG.
 */
public enum AvatarPart {
    /** Environment - the circular background layer */
    ENV,

    /** Head - the face/head shape layer */
    HEAD,

    /** Clothes - the clothing/body layer */
    CLO,

    /** Top - the hair/headwear layer */
    TOP,

    /** Eyes - the eyes layer */
    EYES,

    /** Mouth - the mouth/expression layer */
    MOUTH;
}