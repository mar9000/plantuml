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
package net.sourceforge.plantuml.ugraphic;

import net.sourceforge.plantuml.creole.Stencil;

public class UGraphicStencil extends AbstractUGraphicHorizontalLine {

	private final Stencil stencil;
	private final UStroke defaultStroke;

	public UGraphicStencil(UGraphic ug, Stencil stencil, UStroke defaultStroke) {
		super(ug);
		this.stencil = stencil;
		this.defaultStroke = defaultStroke;
	}

	@Override
	protected AbstractUGraphicHorizontalLine copy(UGraphic ug) {
		return new UGraphicStencil(ug, stencil, defaultStroke);
	}

	@Override
	protected void drawHline(UGraphic ug, UHorizontalLine line, UTranslate translate) {
		line.drawLineInternal(ug, stencil, translate.getDy(), defaultStroke);
		// final UDrawable ud = stencil.convert(line, ug.getStringBounder());
		// ud.drawU(ug.apply(translate));
		// line.drawLine(ug.apply(translate), startingX, endingX, 0, defaultStroke);
	}
}
