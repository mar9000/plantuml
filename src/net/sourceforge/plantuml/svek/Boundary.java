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
package net.sourceforge.plantuml.svek;

import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.ugraphic.UChangeBackColor;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UEllipse;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.ULine;
import net.sourceforge.plantuml.ugraphic.UPath;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class Boundary implements TextBlock {

	private final double margin = 4;

	private final double radius = 12;
	private final double left = 17;
	private final HtmlColor backgroundColor;
	private final HtmlColor foregroundColor;
	private final double thickness;

	private final double deltaShadow;

	public Boundary(HtmlColor backgroundColor, HtmlColor foregroundColor, double deltaShadow, double thickness) {
		this.backgroundColor = backgroundColor;
		this.foregroundColor = foregroundColor;
		this.deltaShadow = deltaShadow;
		this.thickness = thickness;
	}

	public void drawU(UGraphic ug) {
		double x = 0;
		double y = 0;
		x += margin;
		y += margin;
		ug = ug.apply(new UStroke(thickness)).apply(new UChangeBackColor(backgroundColor))
				.apply(new UChangeColor(foregroundColor));
		final UEllipse circle = new UEllipse(radius * 2, radius * 2);
		circle.setDeltaShadow(deltaShadow);

		final UPath path1 = new UPath();
		path1.moveTo(0, 0);
		path1.lineTo(0, radius * 2);
		path1.setDeltaShadow(deltaShadow);

		final UPath path = new UPath();
		path.moveTo(0, 0);
		path.lineTo(0, radius * 2);
		path.moveTo(0, radius);
		path.lineTo(left, radius);
		path.setDeltaShadow(deltaShadow);
		ug.apply(new UTranslate(x, y)).draw(path);

		// final ULine line1 = new ULine(0, radius * 2);
		// line1.setDeltaShadow(deltaShadow);
		// ug.apply(new UTranslate(x, y)).draw(line1);
		// final ULine line2 = new ULine(left, 0);
		// line2.setDeltaShadow(deltaShadow);
		// ug.apply(new UTranslate(x, y + radius)).draw(line2);

		ug.apply(new UTranslate(x + left, y)).draw(circle);

	}

	public Dimension2D calculateDimension(StringBounder stringBounder) {
		return new Dimension2DDouble(radius * 2 + left + 2 * margin, radius * 2 + 2 * margin);
	}

}
