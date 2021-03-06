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

import java.util.Collection;

import net.sourceforge.plantuml.activitydiagram3.ftile.Ftile;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileFactory;
import net.sourceforge.plantuml.activitydiagram3.ftile.Swimlane;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.sequencediagram.NotePosition;

public class Branch {

	private final InstructionList list;
	private final Display labelTest;
	private final Display labelPositive;
	private final HtmlColor color;

	private LinkRendering inlinkRendering;

	private Ftile ftile;

	public Branch(Swimlane swimlane, Display labelPositive, Display labelTest, HtmlColor color) {
		this.list = new InstructionList(swimlane);
		this.labelTest = labelTest;
		this.labelPositive = labelPositive;
		this.color = color;
	}

	public void add(Instruction ins) {
		list.add(ins);
	}

	public boolean kill() {
		return list.kill();
	}

	public void addNote(Display note, NotePosition position) {
		list.addNote(note, position);
	}

	public final void setInlinkRendering(LinkRendering inlinkRendering) {
		this.inlinkRendering = inlinkRendering;
	}

	public void updateFtile(FtileFactory factory) {
		this.ftile = factory.decorateOut(list.createFtile(factory), inlinkRendering);
	}

	public Collection<? extends Swimlane> getSwimlanes() {
		return list.getSwimlanes();
	}

	public final Display getLabelPositive() {
		return labelPositive;
	}

	public final Display getLabelTest() {
		return labelTest;
	}

	public final HtmlColor getInlinkRenderingColor() {
		return inlinkRendering == null ? null : inlinkRendering.getColor();
	}

	public final Ftile getFtile() {
		return ftile;
	}

	public boolean shadowing() {
		return ftile.shadowing();
	}

	public final HtmlColor getColor() {
		return color;
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}

}
