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

import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.ugraphic.UCenteredCharacter;
import net.sourceforge.plantuml.ugraphic.UChangeBackColor;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UEllipse;
import net.sourceforge.plantuml.ugraphic.UFont;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class CircledCharacter implements TextBlock {

	private final String c;
	private final UFont font;
	private final HtmlColor innerCircle;
	private final HtmlColor circle;
	private final HtmlColor fontColor;
	private final double radius;

	public CircledCharacter(char c, double radius, UFont font, HtmlColor innerCircle, HtmlColor circle,
			HtmlColor fontColor) {
		this.c = "" + c;
		this.radius = radius;
		this.font = font;
		this.innerCircle = innerCircle;
		this.circle = circle;
		this.fontColor = fontColor;
	}

	// public void draw(ColorMapper colorMapper, Graphics2D g2d, int x, int y, double dpiFactor) {
	// drawU(new UGraphicG2d(colorMapper, g2d, null, 1.0), x, y);
	// }

	public void drawU(UGraphic ug) {
		if (circle != null) {
			ug = ug.apply(new UChangeColor(circle));
		}
		// final HtmlColor back = ug.getParam().getBackcolor();
		ug = ug.apply(new UChangeBackColor(innerCircle));
		ug.draw(new UEllipse(radius * 2, radius * 2));
		ug = ug.apply(new UChangeColor(fontColor));
		ug = ug.apply(new UTranslate(radius, radius));
		ug.draw(new UCenteredCharacter(c.charAt(0), font));
	}

	final public double getPreferredWidth(StringBounder stringBounder) {
		return 2 * radius;
	}

	final public double getPreferredHeight(StringBounder stringBounder) {
		return 2 * radius;
	}

	// private PathIterator getPathIteratorCharacter(FontRenderContext frc) {
	// final TextLayout textLayout = new TextLayout(c, font.getFont(), frc);
	// final Shape s = textLayout.getOutline(null);
	// return s.getPathIterator(null);
	// }
	//
	// private UPath getUPath(FontRenderContext frc) {
	// final UPath result = new UPath();
	//
	// final PathIterator path = getPathIteratorCharacter(frc);
	//
	// final double coord[] = new double[6];
	// while (path.isDone() == false) {
	// final int code = path.currentSegment(coord);
	// result.add(coord, USegmentType.getByCode(code));
	// path.next();
	// }
	//
	// return result;
	// }

	public Dimension2D calculateDimension(StringBounder stringBounder) {
		return new Dimension2DDouble(getPreferredWidth(stringBounder), getPreferredHeight(stringBounder));
	}
}
