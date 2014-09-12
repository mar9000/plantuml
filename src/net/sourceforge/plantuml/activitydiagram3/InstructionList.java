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
package net.sourceforge.plantuml.activitydiagram3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sourceforge.plantuml.activitydiagram3.ftile.Ftile;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileEmpty;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileFactory;
import net.sourceforge.plantuml.activitydiagram3.ftile.Swimlane;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.sequencediagram.NotePosition;

public class InstructionList implements Instruction {

	private final List<Instruction> all = new ArrayList<Instruction>();
	private final Swimlane defaultSwimlane;

	public InstructionList() {
		this(null);
	}

	public boolean isEmpty() {
		return all.isEmpty();
	}

	public InstructionList(Swimlane defaultSwimlane) {
		this.defaultSwimlane = defaultSwimlane;
	}

	public void add(Instruction ins) {
		all.add(ins);
	}

	public Ftile createFtile(FtileFactory factory) {
		if (all.size() == 0) {
			return new FtileEmpty(factory.shadowing(), defaultSwimlane);
		}
		Ftile result = null;
		for (Instruction ins : all) {
			Ftile cur = ins.createFtile(factory);
			if (ins.getInLinkRendering() != null) {
				cur = factory.decorateIn(cur, ins.getInLinkRendering());
			}
			if (result == null) {
				result = cur;
			} else {
				result = factory.assembly(result, cur);
			}

		}
//		if (killed) {
//			result = new FtileKilled(result);
//		}
		return result;
	}

	final public boolean kill() {
		if (all.size() == 0) {
			return false;
		}
		return getLast().kill();
	}

	public LinkRendering getInLinkRendering() {
		return all.iterator().next().getInLinkRendering();
	}

	public Instruction getLast() {
		if (all.size() == 0) {
			return null;
		}
		return all.get(all.size() - 1);
	}

	public void addNote(Display note, NotePosition position) {
		getLast().addNote(note, position);
	}

	public Set<Swimlane> getSwimlanes() {
		return getSwimlanes2(all);
	}

	public Swimlane getSwimlaneIn() {
		return all.get(0).getSwimlaneIn();
	}

	public Swimlane getSwimlaneOut() {
		return getLast().getSwimlaneOut();
	}

	public static Set<Swimlane> getSwimlanes2(List<? extends Instruction> list) {
		final Set<Swimlane> result = new HashSet<Swimlane>();
		for (Instruction ins : list) {
			result.addAll(ins.getSwimlanes());
		}
		return Collections.unmodifiableSet(result);
	}

}
