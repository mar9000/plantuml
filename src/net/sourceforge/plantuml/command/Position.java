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
package net.sourceforge.plantuml.command;

import net.sourceforge.plantuml.cucadiagram.Rankdir;

public enum Position {
	RIGHT, LEFT, BOTTOM, TOP;

	public Position withRankdir(Rankdir rankdir) {
		if (rankdir == null) {
			throw new IllegalArgumentException();
		}
		if (rankdir == Rankdir.TOP_TO_BOTTOM) {
			// Default
			return this;
		}
		if (this == RIGHT) {
			return BOTTOM;
		}
		if (this == LEFT) {
			return TOP;
		}
		if (this == BOTTOM) {
			return RIGHT;
		}
		if (this == TOP) {
			return LEFT;
		}
		throw new IllegalStateException();
	}
}
