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
package net.sourceforge.plantuml.svek.extremity;

import java.awt.geom.Point2D;

import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.ugraphic.UChangeBackColor;
import net.sourceforge.plantuml.ugraphic.UEllipse;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.UTranslate;

class ExtremityCircleConnect extends Extremity {

	private final double px;
	private final double py;
	private final Point2D dest;
	private final double radius = 6;
	private final double radius2 = 10;
	private final double ortho;

	public ExtremityCircleConnect(Point2D p1, double ortho) {
		this.px = p1.getX() - radius;
		this.py = p1.getY() - radius;
		this.dest = new Point2D.Double(p1.getX(), p1.getY());
		this.ortho = ortho;
	}

	public void drawU(UGraphic ug) {
		ug = ug.apply(new UStroke(1.5)).apply(new UChangeBackColor(HtmlColorUtils.WHITE));
		ug.apply(new UTranslate(dest.getX() - radius, dest.getY() - radius)).draw(new UEllipse(radius * 2, radius * 2));
		
		final double deg = -ortho * 180 / Math.PI + 90 - 45;
		final UEllipse arc1 = new UEllipse(2 * radius2, 2 * radius2, deg, 90);
		ug.apply(new UTranslate(dest.getX() - radius2, dest.getY() - radius2)).draw(arc1);
	}

	// private Point2D getPointOnCircle(double angle) {
	// final double x = px + radius + radius2 * Math.cos(angle);
	// final double y = py + radius + radius2 * Math.sin(angle);
	// return new Point2D.Double(x, y);
	// }
	//
	// static private void drawLine(UGraphic ug, double x, double y, Point2D p1, Point2D p2) {
	// final double dx = p2.getX() - p1.getX();
	// final double dy = p2.getY() - p1.getY();
	// ug.draw(x + p1.getX(), y + p1.getY(), new ULine(dx, dy));
	//
	// }

}
