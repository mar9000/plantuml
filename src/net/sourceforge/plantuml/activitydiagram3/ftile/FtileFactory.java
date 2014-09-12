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
package net.sourceforge.plantuml.activitydiagram3.ftile;

import java.util.List;

import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.Url;
import net.sourceforge.plantuml.activitydiagram3.Branch;
import net.sourceforge.plantuml.activitydiagram3.LinkRendering;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.sequencediagram.NotePosition;

public interface FtileFactory extends ISkinSimple {

	public StringBounder getStringBounder();

	public boolean shadowing();

	public Ftile start(Swimlane swimlane);

	public Ftile stop(Swimlane swimlane);

	public Ftile activity(Display label, HtmlColor color, Swimlane swimlane, BoxStyle style);

	public Ftile addNote(Ftile ftile, Display note, NotePosition notePosition);

	public Ftile addUrl(Ftile ftile, Url url);

	public Ftile decorateIn(Ftile ftile, LinkRendering linkRendering);

	public Ftile decorateOut(Ftile ftile, LinkRendering linkRendering);

	public Ftile assembly(Ftile tile1, Ftile tile2);

	public Ftile repeat(Swimlane swimlane, Ftile repeat, Display test, Display yes, Display out, HtmlColor color);

	public Ftile createWhile(Swimlane swimlane, Ftile whileBlock, Display test, Display yes, Display out,
			LinkRendering afterEndwhile, HtmlColor color);

	public Ftile createIf(Swimlane swimlane, List<Branch> thens, Branch elseBranch);

	public Ftile createFork(List<Ftile> all);

	public Ftile createSplit(List<Ftile> all);

	public Ftile createGroup(Ftile list, Display name, HtmlColor backColor, HtmlColor titleColor);

}
