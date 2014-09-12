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
package net.sourceforge.plantuml.activitydiagram3.ftile.vcompact;

import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.activitydiagram3.ftile.Ftile;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileFactory;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileFactoryDelegator;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.sequencediagram.NotePosition;
import net.sourceforge.plantuml.skin.rose.Rose;

public class FtileFactoryDelegatorAddNote extends FtileFactoryDelegator {

	private final Rose rose = new Rose();

	public FtileFactoryDelegatorAddNote(FtileFactory factory, ISkinParam skinParam) {
		super(factory, skinParam);
	}

	@Override
	public Ftile addNote(Ftile ftile, Display note, NotePosition notePosition) {
		if (note == null) {
			throw new IllegalArgumentException();
		}
		// final HtmlColor colorlink;
		// final LinkRendering inlinkRendering = ftile.getInLinkRendering();
		// if (inlinkRendering == null || inlinkRendering.getColor() == null) {
		// colorlink = rose.getHtmlColor(getSkinParam(), ColorParam.activityArrow);
		// } else {
		// colorlink = inlinkRendering.getColor();
		// }
		return new FtileWithNoteOpale(ftile, note, notePosition, getSkinParam(), true);
	}
}
