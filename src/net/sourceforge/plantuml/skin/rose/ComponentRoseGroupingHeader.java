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
package net.sourceforge.plantuml.skin.rose;

import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.skin.AbstractTextualComponent;
import net.sourceforge.plantuml.skin.Area;
import net.sourceforge.plantuml.ugraphic.UChangeBackColor;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UFont;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UPolygon;
import net.sourceforge.plantuml.ugraphic.URectangle;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class ComponentRoseGroupingHeader extends AbstractTextualComponent {

	private final int cornersize = 10;
	private final int commentMargin = 0; // 8;

	private final TextBlock commentTextBlock;

	private final HtmlColor groupBackground;
	private final HtmlColor groupBorder;
	private final HtmlColor background;
	private final double deltaShadow;
	private final UStroke stroke;

	public ComponentRoseGroupingHeader(HtmlColor fontColor, HtmlColor hyperlinkColor, HtmlColor background, HtmlColor groupBackground,
			HtmlColor groupBorder, UFont bigFont, UFont smallFont, Display strings, ISkinSimple spriteContainer,
			double deltaShadow, UStroke stroke) {
		super(strings.get(0), fontColor, hyperlinkColor, bigFont, HorizontalAlignment.LEFT, 15, 30, 1, spriteContainer, 0);
		this.groupBackground = groupBackground;
		this.groupBorder = groupBorder;
		this.background = background;
		this.stroke = stroke;
		this.deltaShadow = deltaShadow;
		if (strings.size() == 1 || strings.get(1) == null) {
			this.commentTextBlock = null;
		} else {
			final Display display = Display.getWithNewlines("[" + strings.get(1) + "]");
			this.commentTextBlock = TextBlockUtils.create(display, new FontConfiguration(smallFont, fontColor, hyperlinkColor),
					HorizontalAlignment.LEFT, spriteContainer);
		}
		if (this.background == null) {
			throw new IllegalArgumentException();
		}

	}

	private double getSuppHeightForComment(StringBounder stringBounder) {
		if (commentTextBlock == null) {
			return 0;
		}
		final double height = commentTextBlock.calculateDimension(stringBounder).getHeight();
		if (height > 15) {
			return height - 15;
		}
		return 0;

	}

	@Override
	final public double getPreferredWidth(StringBounder stringBounder) {
		final double sup;
		if (commentTextBlock == null) {
			sup = commentMargin * 2;
		} else {
			final Dimension2D size = commentTextBlock.calculateDimension(stringBounder);
			sup = getMarginX1() + commentMargin + size.getWidth();

		}
		return getTextWidth(stringBounder) + sup;
	}

	@Override
	final public double getPreferredHeight(StringBounder stringBounder) {
		return getTextHeight(stringBounder) + 2 * getPaddingY() + getSuppHeightForComment(stringBounder);
	}

	@Override
	protected void drawBackgroundInternalU(UGraphic ug, Area area) {
		final Dimension2D dimensionToUse = area.getDimensionToUse();
		ug = ug.apply(stroke).apply(new UChangeColor(groupBorder));
		final URectangle rect = new URectangle(dimensionToUse.getWidth(), dimensionToUse.getHeight());
		rect.setDeltaShadow(deltaShadow);
		ug.apply(new UChangeBackColor(background)).draw(rect);
	}

	@Override
	protected void drawInternalU(UGraphic ug, Area area) {
		final Dimension2D dimensionToUse = area.getDimensionToUse();
		ug = ug.apply(stroke).apply(new UChangeColor(groupBorder));
		final URectangle rect = new URectangle(dimensionToUse.getWidth(), dimensionToUse.getHeight());
		ug.draw(rect);

		final StringBounder stringBounder = ug.getStringBounder();
		final int textWidth = (int) getTextWidth(stringBounder);
		final int textHeight = (int) getTextHeight(stringBounder);

		final UPolygon polygon = new UPolygon();
		polygon.addPoint(0, 0);
		polygon.addPoint(textWidth, 0);

		polygon.addPoint(textWidth, textHeight - cornersize);
		polygon.addPoint(textWidth - cornersize, textHeight);

		polygon.addPoint(0, textHeight);
		polygon.addPoint(0, 0);

		ug.apply(new UChangeColor(groupBorder)).apply(new UChangeBackColor(groupBackground)).draw(polygon);

		ug = ug.apply(new UStroke());

		getTextBlock().drawU(ug.apply(new UTranslate(getMarginX1(), getMarginY())));

		if (commentTextBlock != null) {
			final int x1 = getMarginX1() + textWidth;
			final int y2 = getMarginY() + 1;

			commentTextBlock.drawU(ug.apply(new UTranslate(x1 + commentMargin, y2)));
		}
	}

}
