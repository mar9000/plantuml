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
package net.sourceforge.plantuml.ugraphic;

import java.util.ArrayList;
import java.util.List;

public class InflaterSet {

	static class Inflater {
		private final double start;
		private final double size;

		private Inflater(double start, double size) {
			this.start = start;
			this.size = size;
		}
	}

	private final List<Inflater> all = new ArrayList<Inflater>();

	public void addInflater(double start, double size) {
		all.add(new Inflater(start, size));
	}

	public double inflate(double v) {
		double result = v;
		for (Inflater in : all) {
			if (v > in.start) {
				result += in.size;
			}
		}
		return result;
	}

}
