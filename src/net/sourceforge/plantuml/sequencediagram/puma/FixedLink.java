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
package net.sourceforge.plantuml.sequencediagram.puma;

public class FixedLink {

	final private SegmentPosition segmentPosition1;
	final private SegmentPosition segmentPosition2;

	public FixedLink(SegmentPosition segmentPosition1, SegmentPosition segmentPosition2) {
		this.segmentPosition1 = segmentPosition1;
		this.segmentPosition2 = segmentPosition2;
	}

	public boolean pushIfNeed() {
		final double p1 = segmentPosition1.getPosition();
		final double p2 = segmentPosition2.getPosition();
		if (p1 == p2) {
			return false;
		}
		final double diff = p1 - p2;
		segmentPosition2.getSegment().push(diff);
		assert segmentPosition1.getPosition() == segmentPosition2.getPosition();
		return true;
	}

}
