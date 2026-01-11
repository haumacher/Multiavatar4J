package com.multiavatar;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains all theme color data for the 16 base characters (00-15).
 * Each character has 3 themes (A, B, C) with colors for 6 parts:
 * env, clo, head, mouth, eyes, top
 */
class ThemeData {

    static class Theme {
        String[] env;
        String[] clo;
        String[] head;
        String[] mouth;
        String[] eyes;
        String[] top;

        Theme(String[] env, String[] clo, String[] head, String[] mouth, String[] eyes, String[] top) {
            this.env = env;
            this.clo = clo;
            this.head = head;
            this.mouth = mouth;
            this.eyes = eyes;
            this.top = top;
        }
    }

    static class CharacterThemes {
        Theme A;
        Theme B;
        Theme C;

        CharacterThemes(Theme A, Theme B, Theme C) {
            this.A = A;
            this.B = B;
            this.C = C;
        }

        Theme getTheme(char themeName) {
            switch (themeName) {
                case 'A': return A;
                case 'B': return B;
                case 'C': return C;
                default: return A;
            }
        }
    }

    private static final Map<String, CharacterThemes> THEMES = createThemes();

    static CharacterThemes getCharacterThemes(String characterId) {
        return THEMES.get(characterId);
    }

    private static Map<String, CharacterThemes> createThemes() {
        Map<String, CharacterThemes> themes = new HashMap<>();

        // Character 00 - Robo
        themes.put("00", new CharacterThemes(
            new Theme(
                new String[]{"#ff2f2b"},
                new String[]{"#fff", "#000"},
                new String[]{"#fff"},
                new String[]{"#fff", "#000", "#000"},
                new String[]{"#000", "none", "#00FFFF"},
                new String[]{"#fff", "#fff"}
            ),
            new Theme(
                new String[]{"#ff1ec1"},
                new String[]{"#000", "#fff"},
                new String[]{"#ffc1c1"},
                new String[]{"#fff", "#000", "#000"},
                new String[]{"#FF2D00", "#fff", "none"},
                new String[]{"#a21d00", "#fff"}
            ),
            new Theme(
                new String[]{"#0079b1"},
                new String[]{"#0e00b1", "#d1fffe"},
                new String[]{"#f5aa77"},
                new String[]{"#fff", "#000", "#000"},
                new String[]{"#0c00de", "#fff", "none"},
                new String[]{"#acfffd", "#acfffd"}
            )
        ));

        // Character 01 - Girl
        themes.put("01", new CharacterThemes(
            new Theme(
                new String[]{"#a50000"},
                new String[]{"#f06", "#8e0039"},
                new String[]{"#85492C"},
                new String[]{"#000"},
                new String[]{"#000", "#ff9809"},
                new String[]{"#ff9809", "#ff9809", "none", "none"}
            ),
            new Theme(
                new String[]{"#40E83B"},
                new String[]{"#00650b", "#62ce5a"},
                new String[]{"#f7c1a6"},
                new String[]{"#6e1c1c"},
                new String[]{"#000", "#ff833b"},
                new String[]{"#67FFCC", "none", "none", "#ecff3b"}
            ),
            new Theme(
                new String[]{"#ff2c2c"},
                new String[]{"#fff", "#000"},
                new String[]{"#ffce8b"},
                new String[]{"#000"},
                new String[]{"#000", "#0072ff"},
                new String[]{"#ff9809", "none", "#ffc809", "none"}
            )
        ));

        // Character 02 - Blonde
        themes.put("02", new CharacterThemes(
            new Theme(
                new String[]{"#ff7520"},
                new String[]{"#d12823"},
                new String[]{"#fee3c5"},
                new String[]{"#d12823"},
                new String[]{"#000", "none"},
                new String[]{"#000", "none", "none", "#FFCC00", "red"}
            ),
            new Theme(
                new String[]{"#ff9700"},
                new String[]{"#000"},
                new String[]{"#d2ad6d"},
                new String[]{"#000"},
                new String[]{"#000", "#00ffdc"},
                new String[]{"#fdff00", "#fdff00", "none", "none", "none"}
            ),
            new Theme(
                new String[]{"#26a7ff"},
                new String[]{"#d85cd7"},
                new String[]{"#542e02"},
                new String[]{"#f70014"},
                new String[]{"#000", "magenta"},
                new String[]{"#FFCC00", "#FFCC00", "#FFCC00", "#ff0000", "yellow"}
            )
        ));

        // Character 03 - Evilnormie
        themes.put("03", new CharacterThemes(
            new Theme(
                new String[]{"#6FC30E"},
                new String[]{"#b4e1fa", "#5b5d6e", "#515262", "#a0d2f0", "#a0d2f0"},
                new String[]{"#fae3b9"},
                new String[]{"#fff", "#000"},
                new String[]{"#000"},
                new String[]{"#8eff45", "#8eff45", "none", "none"}
            ),
            new Theme(
                new String[]{"#00a58c"},
                new String[]{"#000", "none", "none", "none", "none"},
                new String[]{"#FAD2B9"},
                new String[]{"#fff", "#000"},
                new String[]{"#000"},
                new String[]{"#FFC600", "none", "#FFC600", "none"}
            ),
            new Theme(
                new String[]{"#ff501f"},
                new String[]{"#000", "#ff0000", "#ff0000", "#7d7d7d", "#7d7d7d"},
                new String[]{"#fff3dc"},
                new String[]{"#d2001b", "none"},
                new String[]{"#000"},
                new String[]{"#D2001B", "none", "none", "#D2001B"}
            )
        ));

        // Character 04 - Country
        themes.put("04", new CharacterThemes(
            new Theme(
                new String[]{"#fc0"},
                new String[]{"#901e0e", "#ffbe1e", "#ffbe1e", "#c55f54"},
                new String[]{"#f8d9ad"},
                new String[]{"#000", "none", "#000", "none"},
                new String[]{"#000"},
                new String[]{"#583D00", "#AF892E", "#462D00", "#a0a0a0"}
            ),
            new Theme(
                new String[]{"#386465"},
                new String[]{"#fff", "#333", "#333", "#333"},
                new String[]{"#FFD79D"},
                new String[]{"#000", "#000", "#000", "#000"},
                new String[]{"#000"},
                new String[]{"#27363C", "#5DCAD4", "#314652", "#333"}
            ),
            new Theme(
                new String[]{"#DFFF00"},
                new String[]{"#304267", "#aab0b1", "#aab0b1", "#aab0b1"},
                new String[]{"#e6b876"},
                new String[]{"#50230a", "#50230a", "#50230a", "#50230a"},
                new String[]{"#000"},
                new String[]{"#333", "#afafaf", "#222", "#6d3a1d"}
            )
        ));

        // Character 05 - Geeknot
        themes.put("05", new CharacterThemes(
            new Theme(
                new String[]{"#a09300"},
                new String[]{"#c7d4e2", "#435363", "#435363", "#141720", "#141720", "#e7ecf2", "#e7ecf2"},
                new String[]{"#f5d4a6"},
                new String[]{"#000", "#cf9f76"},
                new String[]{"#000", "#000", "#000", "#000", "#000", "#000", "#fff", "#fff", "#fff", "#fff", "#000", "#000"},
                new String[]{"none", "#fdff00"}
            ),
            new Theme(
                new String[]{"#b3003e"},
                new String[]{"#000", "#435363", "#435363", "#000", "none", "#e7ecf2", "#e7ecf2"},
                new String[]{"#f5d4a6"},
                new String[]{"#000", "#af9f94"},
                new String[]{"#9ff3ff;opacity:0.96", "#000", "#9ff3ff;opacity:0.96", "#000", "#2f508a", "#000", "#000", "#000", "none", "none", "none", "none"},
                new String[]{"#ff9a00", "#ff9a00"}
            ),
            new Theme(
                new String[]{"#884f00"},
                new String[]{"#ff0000", "#fff", "#fff", "#141720", "#141720", "#e7ecf2", "#e7ecf2"},
                new String[]{"#c57b14"},
                new String[]{"#000", "#cf9f76"},
                new String[]{"none", "#000", "none", "#000", "#5a0000", "#000", "#000", "#000", "none", "none", "none", "none"},
                new String[]{"#efefef", "none"}
            )
        ));

        // Character 06 - Asian
        themes.put("06", new CharacterThemes(
            new Theme(
                new String[]{"#8acf00"},
                new String[]{"#ee2829", "#ff0"},
                new String[]{"#ffce73"},
                new String[]{"#fff", "#000"},
                new String[]{"#000"},
                new String[]{"#000", "#000", "none", "#000", "#ff4e4e", "#000"}
            ),
            new Theme(
                new String[]{"#00d2a3"},
                new String[]{"#0D0046", "#ffce73"},
                new String[]{"#ffce73"},
                new String[]{"#000", "none"},
                new String[]{"#000"},
                new String[]{"#000", "#000", "#000", "none", "#ffb358", "#000", "none", "none"}
            ),
            new Theme(
                new String[]{"#ff184e"},
                new String[]{"#000", "none"},
                new String[]{"#ffce73"},
                new String[]{"#ff0000", "none"},
                new String[]{"#000"},
                new String[]{"none", "none", "none", "none", "none", "#ffc107", "none", "none"}
            )
        ));

        // Character 07 - Punk
        themes.put("07", new CharacterThemes(
            new Theme(
                new String[]{"#00deae"},
                new String[]{"#ff0000"},
                new String[]{"#ffce94"},
                new String[]{"#f73b6c", "#000"},
                new String[]{"#e91e63", "#000", "#e91e63", "#000", "#000", "#000"},
                new String[]{"#dd104f", "#dd104f", "#f73b6c", "#dd104f"}
            ),
            new Theme(
                new String[]{"#181284"},
                new String[]{"#491f49", "#ff9809", "#491f49"},
                new String[]{"#f6ba97"},
                new String[]{"#ff9809", "#000"},
                new String[]{"#c4ffe4", "#000", "#c4ffe4", "#000", "#000", "#000"},
                new String[]{"none", "none", "#d6f740", "#516303"}
            ),
            new Theme(
                new String[]{"#bcf700"},
                new String[]{"#ff14e4", "#000", "#14fffd"},
                new String[]{"#7b401e"},
                new String[]{"#666", "#000"},
                new String[]{"#00b5b4", "#000", "#00b5b4", "#000", "#000", "#000"},
                new String[]{"#14fffd", "#14fffd", "#14fffd", "#0d3a62"}
            )
        ));

        // Character 08 - Afrohair
        themes.put("08", new CharacterThemes(
            new Theme(
                new String[]{"#0df"},
                new String[]{"#571e57", "#ff0"},
                new String[]{"#f2c280"},
                new String[]{"#ff0000"},
                new String[]{"#795548", "#000"},
                new String[]{"#de3b00", "none"}
            ),
            new Theme(
                new String[]{"#B400C2"},
                new String[]{"#0D204A", "#00ffdf"},
                new String[]{"#ca8628"},
                new String[]{"#1a1a1a"},
                new String[]{"#cbbdaf", "#000"},
                new String[]{"#000", "#000"}
            ),
            new Theme(
                new String[]{"#ffe926"},
                new String[]{"#00d6af", "#000"},
                new String[]{"#8c5100"},
                new String[]{"#7d0000"},
                new String[]{"none", "#000"},
                new String[]{"#f7f7f7", "none"}
            )
        ));

        // Character 09 - Normie Female
        themes.put("09", new CharacterThemes(
            new Theme(
                new String[]{"#4aff0c"},
                new String[]{"#101010", "#fff", "#fff"},
                new String[]{"#dbbc7f"},
                new String[]{"#000"},
                new String[]{"#000", "none", "none"},
                new String[]{"#531148", "#531148", "#531148", "none"}
            ),
            new Theme(
                new String[]{"#FFC107"},
                new String[]{"#033c58", "#fff", "#fff"},
                new String[]{"#dbc97f"},
                new String[]{"#000"},
                new String[]{"none", "#fff", "#000"},
                new String[]{"#FFEB3B", "#FFEB3B", "none", "#FFEB3B"}
            ),
            new Theme(
                new String[]{"#FF9800"},
                new String[]{"#b40000", "#fff", "#fff"},
                new String[]{"#E2AF6B"},
                new String[]{"#000"},
                new String[]{"none", "#fff", "#000"},
                new String[]{"#ec0000", "#ec0000", "none", "none"}
            )
        ));

        // Character 10 - Older
        themes.put("10", new CharacterThemes(
            new Theme(
                new String[]{"#104c8c"},
                new String[]{"#354B65", "#3D8EBB", "#89D0DA", "#00FFFD"},
                new String[]{"#cc9a5c"},
                new String[]{"#222", "#fff"},
                new String[]{"#000", "#000"},
                new String[]{"#fff", "#fff", "none"}
            ),
            new Theme(
                new String[]{"#0DC15C"},
                new String[]{"#212121", "#fff", "#212121", "#fff"},
                new String[]{"#dca45f"},
                new String[]{"#111", "#633b1d"},
                new String[]{"#000", "#000"},
                new String[]{"none", "#792B74", "#792B74"}
            ),
            new Theme(
                new String[]{"#ffe500"},
                new String[]{"#1e5e80", "#fff", "#1e5e80", "#fff"},
                new String[]{"#e8bc86"},
                new String[]{"#111", "none"},
                new String[]{"#000", "#000"},
                new String[]{"none", "none", "#633b1d"}
            )
        ));

        // Character 11 - Firehair
        themes.put("11", new CharacterThemes(
            new Theme(
                new String[]{"#4a3f73"},
                new String[]{"#e6e9ee", "#f1543f", "#ff7058", "#fff", "#fff"},
                new String[]{"#b27e5b"},
                new String[]{"#191919", "#191919"},
                new String[]{"#000", "#000", "#57FFFD"},
                new String[]{"#ffc", "#ffc", "#ffc"}
            ),
            new Theme(
                new String[]{"#00a08d"},
                new String[]{"#FFBA32", "#484848", "#4e4e4e", "#fff", "#fff"},
                new String[]{"#ab5f2c"},
                new String[]{"#191919", "#191919"},
                new String[]{"#000", "#ff23fa;opacity:0.39", "#000"},
                new String[]{"#ff90f4", "#ff90f4", "#ff90f4"}
            ),
            new Theme(
                new String[]{"#22535d"},
                new String[]{"#000", "#ff2500", "#ff2500", "#fff", "#fff"},
                new String[]{"#a76c44"},
                new String[]{"#191919", "#191919"},
                new String[]{"#000", "none", "#000"},
                new String[]{"none", "#00efff", "none"}
            )
        ));

        // Character 12 - Blond
        themes.put("12", new CharacterThemes(
            new Theme(
                new String[]{"#2668DC"},
                new String[]{"#2385c6", "#b8d0e0", "#b8d0e0"},
                new String[]{"#ad8a60"},
                new String[]{"#000", "#4d4d4d"},
                new String[]{"#7fb5a2", "#d1eddf", "#301e19"},
                new String[]{"#fff510", "#fff510"}
            ),
            new Theme(
                new String[]{"#643869"},
                new String[]{"#D67D1B", "#b8d0e0", "#b8d0e0"},
                new String[]{"#CC985A", "none0000"},
                new String[]{"#000", "#ececec"},
                new String[]{"#1f2644", "#9b97ce", "#301e19"},
                new String[]{"#00eaff", "none"}
            ),
            new Theme(
                new String[]{"#F599FF"},
                new String[]{"#2823C6", "#b8d0e0", "#b8d0e0"},
                new String[]{"#C7873A"},
                new String[]{"#000", "#4d4d4d"},
                new String[]{"#581b1b", "#FF8B8B", "#000"},
                new String[]{"none", "#9c0092"}
            )
        ));

        // Character 13 - Ateam
        themes.put("13", new CharacterThemes(
            new Theme(
                new String[]{"#d10084"},
                new String[]{"#efedee", "#00a1e0", "#00a1e0", "#efedee", "#ffce1c"},
                new String[]{"#b35f49"},
                new String[]{"#3a484a", "#000"},
                new String[]{"#000"},
                new String[]{"#000", "none", "#000", "none"}
            ),
            new Theme(
                new String[]{"#E6C117"},
                new String[]{"#efedee", "#ec0033", "#ec0033", "#efedee", "#f2ff05"},
                new String[]{"#ffc016"},
                new String[]{"#4a3737", "#000"},
                new String[]{"#000"},
                new String[]{"#ffe900", "#ffe900", "none", "#ffe900"}
            ),
            new Theme(
                new String[]{"#1d8c00"},
                new String[]{"#e000cb", "#fff", "#fff", "#e000cb", "#ffce1c"},
                new String[]{"#b96438"},
                new String[]{"#000", "#000"},
                new String[]{"#000"},
                new String[]{"#53ffff", "#53ffff", "none", "none"}
            )
        ));

        // Character 14 - Rasta
        themes.put("14", new CharacterThemes(
            new Theme(
                new String[]{"#fc0065"},
                new String[]{"#708913", "#fdea14", "#708913", "#fdea14", "#708913"},
                new String[]{"#DEA561"},
                new String[]{"#444", "#000"},
                new String[]{"#000"},
                new String[]{"#32393f", "#32393f", "#32393f", "#32393f", "#32393f", "#32393f", "#32393f", "#32393f", "#32393f", "#32393f", "#32393f", "#32393f", "#32393f", "#32393f", "#32393f", "#32393f", "#32393f"}
            ),
            new Theme(
                new String[]{"#81f72e"},
                new String[]{"#ff0000", "#ffc107", "#ff0000", "#ffc107", "#ff0000"},
                new String[]{"#ef9831"},
                new String[]{"#6b0000", "#000"},
                new String[]{"#000"},
                new String[]{"#FFFAAD", "#FFFAAD", "#FFFAAD", "#FFFAAD", "#FFFAAD", "#FFFAAD", "#FFFAAD", "#FFFAAD", "#FFFAAD", "#FFFAAD", "#FFFAAD", "#FFFAAD", "#FFFAAD", "none", "none", "none", "none"}
            ),
            new Theme(
                new String[]{"#00D872"},
                new String[]{"#590D00", "#FD1336", "#590D00", "#FD1336", "#590D00"},
                new String[]{"#c36c00"},
                new String[]{"#56442b", "#000"},
                new String[]{"#000"},
                new String[]{"#004E4C", "#004E4C", "#004E4C", "#004E4C", "#004E4C", "#004E4C", "#004E4C", "#004E4C", "#004E4C", "none", "none", "none", "none", "none", "none", "none", "none"}
            )
        ));

        // Character 15 - Meta
        themes.put("15", new CharacterThemes(
            new Theme(
                new String[]{"#111"},
                new String[]{"#000", "#00FFFF"},
                new String[]{"#755227"},
                new String[]{"#fff", "#000"},
                new String[]{"black", "#008;opacity:0.67", "aqua"},
                new String[]{"#fff", "#fff", "#fff", "#fff", "#fff"}
            ),
            new Theme(
                new String[]{"#00D0D4"},
                new String[]{"#000", "#fff"},
                new String[]{"#755227"},
                new String[]{"#fff", "#000"},
                new String[]{"black", "#1df7ff;opacity:0.64", "#fcff2c"},
                new String[]{"#fff539", "none", "#fff539", "none", "#fff539"}
            ),
            new Theme(
                new String[]{"#DC75FF"},
                new String[]{"#000", "#FFBDEC"},
                new String[]{"#997549"},
                new String[]{"#fff", "#000"},
                new String[]{"black", "black", "aqua"},
                new String[]{"#00fffd", "none", "none", "none", "none"}
            )
        ));

        return themes;
    }
}
