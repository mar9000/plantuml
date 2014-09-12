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
package net.sourceforge.plantuml.hector;

public class PinLink {

	private final Pin pin1;
	private final Pin pin2;
	private final Object userData;
	private final int length;

	public PinLink(Pin pin1, Pin pin2, int length, Object userData) {
		if (length < 1) {
			throw new IllegalArgumentException();
		}
		this.pin1 = pin1;
		this.pin2 = pin2;
		this.userData = userData;
		this.length = length;
	}

	public boolean contains(Pin pin) {
		return pin == pin1 || pin == pin2;
	}

	public boolean doesTouch(PinLink other) {
		return other.contains(pin1) || other.contains(pin2);
	}

	public Pin getPin1() {
		return pin1;
	}

	public Pin getPin2() {
		return pin2;
	}

	public int getLengthDot() {
		return length;
	}

	public int getLengthStandard() {
		return length - 1;
	}
}
