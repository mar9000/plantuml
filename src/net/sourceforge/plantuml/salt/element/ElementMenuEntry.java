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
package net.sourceforge.plantuml.salt.element;

import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.ugraphic.UChangeBackColor;
import net.sourceforge.plantuml.ugraphic.UFont;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.URectangle;

public class ElementMenuEntry extends AbstractElement {

	private final TextBlock block;
	private final String text;
	private HtmlColor background;
	private double xxx;

	public ElementMenuEntry(String text, UFont font, ISkinSimple spriteContainer) {
		final FontConfiguration config = new FontConfiguration(font, HtmlColorUtils.BLACK, HtmlColorUtils.BLUE);
		this.block = TextBlockUtils.create(Display.create(text), config, HorizontalAlignment.LEFT, spriteContainer);
		this.text = text;
	}

	public Dimension2D getPreferredDimension(StringBounder stringBounder, double x, double y) {
		if (text.equals("-")) {
			return new Dimension2DDouble(10, 5);
		}
		return block.calculateDimension(stringBounder);
	}

	public void drawU(UGraphic ug, int zIndex, Dimension2D dimToUse) {
		if (background != null) {
			final Dimension2D dim = getPreferredDimension(ug.getStringBounder(), 0, 0);
			ug.apply(new UChangeBackColor(background)).draw(new URectangle(dim.getWidth(), dim.getHeight()));
		}
		block.drawU(ug);
	}

	public double getX() {
		return xxx;
	}

	public void setX(double x) {
		this.xxx = x;
	}

	public String getText() {
		return text;
	}

	public HtmlColor getBackground() {
		return background;
	}

	public void setBackground(HtmlColor background) {
		this.background = background;
	}
}
