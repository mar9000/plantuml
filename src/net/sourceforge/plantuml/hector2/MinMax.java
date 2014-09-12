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
package net.sourceforge.plantuml.hector2;

import java.util.Collection;

public class MinMax {

	private final int min;
	private final int max;

	private MinMax(int min, int max) {
		if (max < min) {
			throw new IllegalArgumentException();
		}
		this.min = min;
		this.max = max;
	}

	private MinMax(int value) {
		this(value, value);
	}

	public MinMax add(int value) {
		final int newMin = Math.min(min, value);
		final int newMax = Math.max(max, value);
		if (min == newMin && max == newMax) {
			return this;
		}
		return new MinMax(newMin, newMax);
	}

	public MinMax add(MinMax other) {
		final int newMin = Math.min(min, other.min);
		final int newMax = Math.max(max, other.max);
		if (min == newMin && max == newMax) {
			return this;
		}
		return new MinMax(newMin, newMax);
	}

	public final int getMin() {
		return min;
	}

	public final int getMax() {
		return max;
	}

	public static MinMax from(Collection<Integer> values) {
		MinMax result = null;
		for (Integer i : values) {
			if (result == null) {
				result = new MinMax(i);
			} else {
				result = result.add(i);
			}
		}
		return result;
	}

	public int getDiff() {
		return max - min;
	}

}
