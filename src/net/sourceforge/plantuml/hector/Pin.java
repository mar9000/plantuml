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


public class Pin {

	private int row;
	private int uid = -1;

	private final Object userData;

	public Pin(int row, Object userData) {
		this.row = row;
		this.userData = userData;
	}

	public void setUid(int uid) {
		if (this.uid != -1) {
			throw new IllegalStateException();
		}
		this.uid = uid;
	}

	public int getRow() {
		return row;
	}

	public int getUid() {
		return uid;
	}

	public Object getUserData() {
		return userData;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void push(int push) {
		setRow(getRow() + push);
	}

}
