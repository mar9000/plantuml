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
package net.sourceforge.plantuml.sequencediagram;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.plantuml.Url;

public class Notes implements Event, Iterable<Note> {

	private final List<Note> notes = new ArrayList<Note>();

	public Notes(Note n1, Note n2) {
		notes.add(n1);
		notes.add(n2);
	}

	public void add(Note n) {
		notes.add(n);
	}

	public boolean dealWith(Participant someone) {
		for (Note n : notes) {
			if (n.dealWith(someone)) {
				return true;
			}
		}
		return false;
	}

	public Url getUrl() {
		return null;
	}
	
	public boolean hasUrl() {
		return false;
	}

	public Iterator<Note> iterator() {
		return notes.iterator();
	}
}
