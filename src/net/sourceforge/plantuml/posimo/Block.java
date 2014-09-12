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

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.Locale;

import net.sourceforge.plantuml.Dimension2DDouble;

public class Block implements Clusterable {

	private final int uid;
	private final double width;
	private final double height;
	private double x;
	private double y;
	private final Cluster parent;

	public Block(int uid, double width, double height, Cluster parent) {
		this.uid = uid;
		this.width = width;
		this.height = height;
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "BLOCK " + uid;
	}

	public String toStringPosition() {
		return String.format(Locale.US, "x=%9.2f y=%9.2f w=%9.2f h=%9.2f", x, y, width, height);
	}

	public int getUid() {
		return uid;
	}

	public Cluster getParent() {
		return parent;
	}

	public Point2D getPosition() {
		return new Point2D.Double(x, y);
	}

	public Dimension2D getSize() {
		return new Dimension2DDouble(width, height);
	}

	public void setCenterX(double center) {
		this.x = center - width / 2;
	}

	public void setCenterY(double center) {
		this.y = center - height / 2;
	}

	public final void setX(double x) {
		this.x = x;
	}

	public final void setY(double y) {
		this.y = y;
	}
	
	public void moveSvek(double deltaX, double deltaY) {
		throw new UnsupportedOperationException();
	}


}
