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
package net.sourceforge.plantuml.api;

import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.core.ImageData;

public class ImageDataSimple implements ImageData {

	private final int width;
	private final int height;

	public ImageDataSimple(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public ImageDataSimple() {
		this(0, 0);
	}

	public ImageDataSimple(Dimension2D dim) {
		this((int) dim.getWidth(), (int) dim.getHeight());
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean containsCMapData() {
		return false;
	}

	public String getCMapData(String nameId) {
		throw new UnsupportedOperationException();
	}

	public String getWarningOrError() {
		return null;
	}

}
