/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2014, Arnaud Roques
 *
 * Project Info:  http://plantuml.sourceforge.net
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 *
 * Original Author:  Arnaud Roques
 */
package net.sourceforge.plantuml.graphic;

import java.util.EnumSet;
import java.util.Map;

import net.sourceforge.plantuml.FontParam;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.SkinParamUtils;
import net.sourceforge.plantuml.cucadiagram.Stereotype;
import net.sourceforge.plantuml.ugraphic.UFont;

public class FontConfiguration {

	private final EnumSet<FontStyle> styles;
	private final UFont currentFont;
	private final UFont motherFont;
	private final HtmlColor motherColor;
	private final HtmlColor hyperlinkColor;
	private final HtmlColor currentColor;
	private final HtmlColor extendedColor;
	private final FontPosition fontPosition;
	private final SvgAttributes svgAttributes;
	private final boolean hyperlink;

	public FontConfiguration(UFont font, HtmlColor color, HtmlColor hyperlinkColor) {
		this(getStyles(font), font, color, font, color, null, FontPosition.NORMAL, new SvgAttributes(), false, hyperlinkColor);
	}

	public FontConfiguration(ISkinParam skinParam, FontParam fontParam, Stereotype stereo) {
		this(SkinParamUtils.getFont(skinParam, fontParam, stereo), SkinParamUtils
				.getFontColor(skinParam, fontParam, stereo), skinParam.getHyperlinkColor());
	}

	private static EnumSet<FontStyle> getStyles(UFont font) {
		final boolean bold = font.isBold();
		final boolean italic = font.isItalic();
		if (bold && italic) {
			return EnumSet.of(FontStyle.ITALIC, FontStyle.BOLD);
		}
		if (bold) {
			return EnumSet.of(FontStyle.BOLD);
		}
		if (italic) {
			return EnumSet.of(FontStyle.ITALIC);
		}
		return EnumSet.noneOf(FontStyle.class);
	}

	@Override
	public String toString() {
		return styles.toString() + " " + currentColor;
	}

	private FontConfiguration(EnumSet<FontStyle> styles, UFont motherFont, HtmlColor motherColor, UFont currentFont,
			HtmlColor currentColor, HtmlColor extendedColor, FontPosition fontPosition, SvgAttributes svgAttributes,
			boolean hyperlink, HtmlColor hyperlinkColor) {
		this.styles = styles;
		this.currentFont = currentFont;
		this.motherFont = motherFont;
		this.currentColor = currentColor;
		this.motherColor = motherColor;
		this.extendedColor = extendedColor;
		this.fontPosition = fontPosition;
		this.svgAttributes = svgAttributes;
		this.hyperlink = hyperlink;
		this.hyperlinkColor = hyperlinkColor;
	}

	public FontConfiguration changeAttributes(SvgAttributes toBeAdded) {
		return new FontConfiguration(styles, motherFont, motherColor, currentFont, currentColor, extendedColor,
				fontPosition, svgAttributes.add(toBeAdded), hyperlink, hyperlinkColor);
	}

	private FontConfiguration withHyperlink() {
		return new FontConfiguration(styles, motherFont, motherColor, currentFont, currentColor, extendedColor,
				fontPosition, svgAttributes, true, hyperlinkColor);
	}

	public FontConfiguration changeColor(HtmlColor htmlColor) {
		return new FontConfiguration(styles, motherFont, motherColor, currentFont, htmlColor, extendedColor,
				fontPosition, svgAttributes, hyperlink, hyperlinkColor);
	}

	FontConfiguration changeExtendedColor(HtmlColor newExtendedColor) {
		return new FontConfiguration(styles, motherFont, motherColor, currentFont, currentColor, newExtendedColor,
				fontPosition, svgAttributes, hyperlink, hyperlinkColor);
	}

	public FontConfiguration changeSize(float size) {
		return new FontConfiguration(styles, motherFont, motherColor, currentFont.deriveSize(size), currentColor,
				extendedColor, fontPosition, svgAttributes, hyperlink, hyperlinkColor);
	}

	public FontConfiguration bigger(double delta) {
		return changeSize((float) (currentFont.getSize() + delta));
	}

	public FontConfiguration changeFontPosition(FontPosition fontPosition) {
		return new FontConfiguration(styles, motherFont, motherColor, currentFont, currentColor, extendedColor,
				fontPosition, svgAttributes, hyperlink, hyperlinkColor);
	}

	public FontConfiguration changeFamily(String family) {
		return new FontConfiguration(styles, motherFont, motherColor, new UFont(family, currentFont.getStyle(),
				currentFont.getSize()), currentColor, extendedColor, fontPosition, svgAttributes, hyperlink, hyperlinkColor);
	}

	public FontConfiguration resetFont() {
		return new FontConfiguration(styles, motherFont, motherColor, motherFont, motherColor, null,
				FontPosition.NORMAL, new SvgAttributes(), hyperlink, hyperlinkColor);
	}

	FontConfiguration add(FontStyle style) {
		final EnumSet<FontStyle> r = styles.clone();
		r.add(style);
		return new FontConfiguration(r, motherFont, motherColor, currentFont, currentColor, extendedColor,
				fontPosition, svgAttributes, hyperlink, hyperlinkColor);
	}

	public FontConfiguration italic() {
		return add(FontStyle.ITALIC);
	}

	public FontConfiguration bold() {
		return add(FontStyle.BOLD);
	}

	public FontConfiguration underline() {
		return add(FontStyle.UNDERLINE);
	}

	public FontConfiguration hyperlink() {
		return add(FontStyle.UNDERLINE).withHyperlink();
	}

	FontConfiguration remove(FontStyle style) {
		final EnumSet<FontStyle> r = styles.clone();
		r.remove(style);
		return new FontConfiguration(r, motherFont, motherColor, currentFont, currentColor, extendedColor,
				fontPosition, svgAttributes, hyperlink, hyperlinkColor);
	}

	public UFont getFont() {
		UFont result = currentFont;
		for (FontStyle style : styles) {
			result = style.mutateFont(result);
		}
		return fontPosition.mute(result);
	}

	public HtmlColor getColor() {
		if (hyperlink) {
			return hyperlinkColor;
		}
		return currentColor;
	}

	public HtmlColor getExtendedColor() {
		return extendedColor;
	}

	public boolean containsStyle(FontStyle style) {
		return styles.contains(style);
	}

	public int getSpace() {
		return fontPosition.getSpace();
	}

	public Map<String, String> getAttributes() {
		return svgAttributes.attributes();
	}

	public double getSize2D() {
		return currentFont.getSize2D();
	}

}
