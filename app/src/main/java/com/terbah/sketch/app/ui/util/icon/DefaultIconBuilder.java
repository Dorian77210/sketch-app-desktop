package com.terbah.sketch.app.ui.util.icon;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import java.util.Map;

class DefaultIconBuilder implements IconBuilder {

    /**
     * Font used to create icons.
     */
    private static final GlyphFont FONT = GlyphFontRegistry.font("FontAwesome");

    /**
     * Map that associated a sketch icon with the corresponding glyph.
     */
    private static final Map<SketchIcon, FontAwesome.Glyph> ICONS = Map.of(
        SketchIcon.WARNING, FontAwesome.Glyph.WARNING,
        SketchIcon.PLAY, FontAwesome.Glyph.PLAY
    );

    @Override
    public Node getIcon(SketchIcon icon) {
        return getIcon(icon, Color.BLACK);
    }

    @Override
    public Node getIcon(SketchIcon icon, Color color) {
        Glyph glyph = FONT.create(ICONS.get(icon));
        glyph.setColor(color);
        return glyph;
    }
}
