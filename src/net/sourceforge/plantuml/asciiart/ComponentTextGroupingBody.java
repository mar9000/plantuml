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
package net.sourceforge.plantuml.asciiart;

import java.awt.geom.Dimension2D;
import java.util.List;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.skin.Area;
import net.sourceforge.plantuml.skin.ComponentType;
import net.sourceforge.plantuml.skin.Context2D;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.txt.UGraphicTxt;

public class ComponentTextGroupingBody extends AbstractComponentText {

	private final ComponentType type;
	private final List<? extends CharSequence> stringsToDisplay;
	private final FileFormat fileFormat;

	public ComponentTextGroupingBody(ComponentType type, List<? extends CharSequence> stringsToDisplay,
			FileFormat fileFormat) {
		this.type = type;
		this.stringsToDisplay = stringsToDisplay;
		this.fileFormat = fileFormat;
	}

	public void drawU(UGraphic ug, Area area, Context2D context) {
		final Dimension2D dimensionToUse = area.getDimensionToUse();
		final UmlCharArea charArea = ((UGraphicTxt) ug).getCharArea();
		final int width = (int) dimensionToUse.getWidth();
		final int height = (int) dimensionToUse.getHeight();

		if (fileFormat == FileFormat.UTXT) {
			charArea.drawVLine('\u2551', 0, 0, height);
			charArea.drawVLine('\u2551', width - 1, 0, height);
			charArea.drawHLine('\u2550', height - 1, 1, width - 1, '\u2502', '\u256a');
			charArea.drawChar('\u2560', 0, height - 1);
			charArea.drawChar('\u2563', width - 1, height - 1);
		} else {
			charArea.drawVLine('!', 0, 0, height);
			charArea.drawVLine('!', width - 1, 0, height);
			charArea.drawHLine('~', height - 1, 1, width - 1);
		}
	}

	public double getPreferredHeight(StringBounder stringBounder) {
		return 1;
	}

	public double getPreferredWidth(StringBounder stringBounder) {
		return 1;
	}

}
