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
package net.sourceforge.plantuml.sequencediagram.graphic;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.plantuml.sequencediagram.InGroupable;
import net.sourceforge.plantuml.sequencediagram.InGroupableList;

class InGroupablesStack {

	final private List<InGroupableList> inGroupableStack = new ArrayList<InGroupableList>();

	public void addList(InGroupableList inGroupableList) {
		for (InGroupableList other : inGroupableStack) {
			other.addInGroupable(inGroupableList);
		}
		inGroupableStack.add(inGroupableList);

	}

	public void pop() {
		final int idx = inGroupableStack.size() - 1;
		inGroupableStack.remove(idx);
	}

	public void addElement(InGroupable inGroupable) {
		for (InGroupableList groupingStructure : inGroupableStack) {
			groupingStructure.addInGroupable(inGroupable);
		}
	}

	public InGroupableList getTopGroupingStructure() {
		if (inGroupableStack.size() == 0) {
			return null;
		}
		return inGroupableStack.get(inGroupableStack.size() - 1);
	}

}
