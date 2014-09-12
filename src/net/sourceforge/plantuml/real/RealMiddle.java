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
package net.sourceforge.plantuml.real;

class RealMiddle implements Real {

	private final RealMoveable p1;
	private final RealMoveable p2;
	private final double delta;

	private RealMiddle(RealMoveable p1, RealMoveable p2, double delta) {
		this.p1 = p1;
		this.p2 = p2;
		this.delta = delta;
	}

	RealMiddle(RealMoveable p1, RealMoveable p2) {
		this(p1, p2, 0);
	}

	public double getCurrentValue() {
		return (p1.getCurrentValue() + p2.getCurrentValue()) / 2 + delta;
	}

	public Real addFixed(double diff) {
		return new RealMiddle(p1, p2, delta + diff);
	}

	public Real addAtLeast(double delta) {
		throw new UnsupportedOperationException();
	}

	public void ensureBiggerThan(Real other) {
		throw new UnsupportedOperationException();
	}

	public void compile() {
		p1.compile();
	}

	public String getName() {
		return "[Middle " + p1.getName() + " and " + p2.getName() + "]";
	}

}
