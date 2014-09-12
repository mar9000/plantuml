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
package net.sourceforge.plantuml.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class SortedCollectionLinked<S extends Comparable<S>> implements SortedCollection<S> {

	private final List<S> all = new LinkedList<S>();

	public Iterator<S> iterator() {
		return all.iterator();
	}

	public void add(S newEntry) {
		for (final ListIterator<S> it = all.listIterator(); it.hasNext();) {
			final S cur = it.next();
			if (cur.compareTo(newEntry) >= 0) {
				it.previous();
				it.add(newEntry);
				assert isSorted();
				return;
			}
		}
		all.add(newEntry);
		assert isSorted();
	}

	public int size() {
		return all.size();
	}

	List<S> toList() {
		return new ArrayList<S>(all);
	}

	boolean isSorted() {
		S before = null;
		for (S ent : all) {
			if (before != null && ent.compareTo(before) < 0) {
				return false;
			}
			before = ent;
		}
		return true;
	}

	public boolean contains(S entry) {
		return all.contains(entry);
	}

}
