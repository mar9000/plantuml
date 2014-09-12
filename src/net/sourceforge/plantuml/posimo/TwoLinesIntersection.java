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
package net.sourceforge.plantuml.posimo;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class TwoLinesIntersection {

	private final Point2D inter;

	public TwoLinesIntersection(Line2D lineA, Line2D lineB) {
		final double x1 = lineA.getX1();
		final double y1 = lineA.getY1();
		final double x2 = lineA.getX2();
		final double y2 = lineA.getY2();
		final double x3 = lineB.getX1();
		final double y3 = lineB.getY1();
		final double x4 = lineB.getX2();
		final double y4 = lineB.getY2();

		final double den = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);

		final double uA1 = (x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3);
		final double uA = uA1 / den;

		// final double uB1 = (x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3);
		// uB = uB1 / den;

		final double x = x1 + uA * (x2 - x1);
		final double y = y1 + uA * (y2 - y1);

		inter = new Point2D.Double(x, y);
	}

	public final Point2D getIntersection() {
		return inter;
	}

}
