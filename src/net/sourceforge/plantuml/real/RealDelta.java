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

class RealDelta extends RealMoveable {

	private final Real delegated;
	private final double diff;

	RealDelta(Real delegated, double diff) {
		super("[Delegated {" + delegated.getName() + "} d=" + diff + "]");
		this.delegated = delegated;
		this.diff = diff;
	}

	public double getCurrentValue() {
		return delegated.getCurrentValue() + diff;
	}

	public Real addAtLeast(double delta) {
		return new RealDelta(delegated.addAtLeast(delta), diff);
	}

	public void ensureBiggerThan(Real other) {
		delegated.ensureBiggerThan(new RealDelta(other, -diff));

	}

	public void compile() {
		delegated.compile();
	}

	void move(double delta) {
		((RealMoveable) delegated).move(delta);
	}

	RealLine getLine() {
		return ((RealMoveable) delegated).getLine();
	}

}
