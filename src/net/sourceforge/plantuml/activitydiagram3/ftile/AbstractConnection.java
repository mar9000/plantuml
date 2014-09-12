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
package net.sourceforge.plantuml.activitydiagram3.ftile;

public abstract class AbstractConnection implements Connection {

	private final Ftile ftile1;
	private final Ftile ftile2;

	public AbstractConnection(Ftile ftile1, Ftile ftile2) {
		this.ftile1 = ftile1;
		this.ftile2 = ftile2;
	}

	@Override
	public String toString() {
		return "[" + ftile1 + "]->[" + ftile2 + "]";
	}

	final public Ftile getFtile1() {
		return ftile1;
	}

	final public Ftile getFtile2() {
		return ftile2;
	}

}
