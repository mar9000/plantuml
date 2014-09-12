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

import net.sourceforge.plantuml.Url;
import net.sourceforge.plantuml.activitydiagram3.ftile.BoxStyle;
import net.sourceforge.plantuml.activitydiagram3.ftile.Ftile;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileFactory;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileKilled;
import net.sourceforge.plantuml.activitydiagram3.ftile.Swimlane;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.sequencediagram.NotePosition;

public class InstructionSimple extends MonoSwimable implements Instruction {

	private boolean killed = false;
	private final Display label;
	private final HtmlColor color;
	private final LinkRendering inlinkRendering;
	private Display note;
	private NotePosition notePosition;
	private final BoxStyle style;
	private final Url url;

	public InstructionSimple(Display label, HtmlColor color, LinkRendering inlinkRendering, Swimlane swimlane,
			BoxStyle style, Url url) {
		super(swimlane);
		this.url = url;
		this.style = style;
		this.label = label;
		this.color = color;
		this.inlinkRendering = inlinkRendering;
	}

	public Ftile createFtile(FtileFactory factory) {
		Ftile result = factory.activity(label, color, getSwimlaneIn(), style);
		if (url != null) {
			result = factory.addUrl(result, url);
		}
		if (note != null) {
			result = factory.addNote(result, note, notePosition);
		}
		if (killed) {
			return new FtileKilled(result);
		}
		return result;
	}

	public void add(Instruction other) {
		throw new UnsupportedOperationException();
	}

	final public boolean kill() {
		this.killed = true;
		return true;
	}

	public LinkRendering getInLinkRendering() {
		return inlinkRendering;
	}

	public void addNote(Display note, NotePosition position) {
		this.note = note;
		this.notePosition = position;
	}

}
