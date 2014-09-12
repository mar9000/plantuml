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
package net.sourceforge.plantuml.skin;

import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.FontParam;
import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.cucadiagram.BodyEnhanced2;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockEmpty;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.ugraphic.UFont;

public abstract class AbstractTextualComponent extends AbstractComponent {

	private final Display strings;

	private final int marginX1;
	private final int marginX2;
	private final int marginY;

	private final TextBlock textBlock;

	private final UFont font;
	private final HtmlColor fontColor;

	public AbstractTextualComponent(CharSequence label, HtmlColor fontColor, HtmlColor hyperlinkColor, UFont font,
			HorizontalAlignment horizontalAlignment, int marginX1, int marginX2, int marginY,
			ISkinSimple spriteContainer, double maxMessageSize) {
		this(Display.getWithNewlines(label == null ? "" : label.toString()), fontColor, hyperlinkColor, font, horizontalAlignment,
				marginX1, marginX2, marginY, spriteContainer, maxMessageSize, false);
	}

	public AbstractTextualComponent(Display strings, HtmlColor fontColor, HtmlColor hyperlinkColor, UFont font,
			HorizontalAlignment horizontalAlignment, int marginX1, int marginX2, int marginY,
			ISkinSimple spriteContainer, double maxMessageSize, boolean enhanced) {
		this.font = font;
		this.fontColor = fontColor;
		this.marginX1 = marginX1;
		this.marginX2 = marginX2;
		this.marginY = marginY;
		this.strings = strings;

		if (strings.size() == 1 && strings.get(0).length() == 0) {
			textBlock = new TextBlockEmpty();
		} else if (enhanced) {
			textBlock = new BodyEnhanced2(strings, FontParam.NOTE, spriteContainer, HorizontalAlignment.LEFT, font,
					fontColor, hyperlinkColor);
		} else {
			textBlock = TextBlockUtils.create(strings, new FontConfiguration(font, fontColor, hyperlinkColor), horizontalAlignment,
					spriteContainer, maxMessageSize, false);
		}
	}

	final protected TextBlock getTextBlock() {
		return textBlock;
	}

	final protected double getPureTextWidth(StringBounder stringBounder) {
		final TextBlock textBlock = getTextBlock();
		final Dimension2D size = textBlock.calculateDimension(stringBounder);
		return size.getWidth();
	}

	final public double getTextWidth(StringBounder stringBounder) {
		return getPureTextWidth(stringBounder) + marginX1 + marginX2;
	}

	// // For cache
	// private Dimension2D size;
	//
	// private Dimension2D getSize(StringBounder stringBounder, final TextBlock textBlock) {
	// if (size == null) {
	// size = textBlock.calculateDimension(stringBounder);
	// }
	// return size;
	// }

	final protected double getTextHeight(StringBounder stringBounder) {
		final TextBlock textBlock = getTextBlock();
		final Dimension2D size = textBlock.calculateDimension(stringBounder);
		return size.getHeight() + 2 * marginY;
	}

	final protected Display getLabels() {
		return strings;
	}

	final protected int getMarginX1() {
		return marginX1;
	}

	final protected int getMarginX2() {
		return marginX2;
	}

	final protected int getMarginY() {
		return marginY;
	}

	final protected UFont getFont() {
		return font;
	}

	protected HtmlColor getFontColor() {
		return fontColor;
	}

}
