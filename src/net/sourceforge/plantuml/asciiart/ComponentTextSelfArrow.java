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

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.StringUtils;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.skin.Area;
import net.sourceforge.plantuml.skin.ArrowConfiguration;
import net.sourceforge.plantuml.skin.Component;
import net.sourceforge.plantuml.skin.ComponentType;
import net.sourceforge.plantuml.skin.Context2D;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.txt.UGraphicTxt;

public class ComponentTextSelfArrow extends AbstractComponentText {

	private final ComponentType type;
	private final Display stringsToDisplay;
	private final FileFormat fileFormat;
	private final ArrowConfiguration config;

	public ComponentTextSelfArrow(ComponentType type, ArrowConfiguration config,
			Display stringsToDisplay, FileFormat fileFormat) {
		this.type = type;
		this.stringsToDisplay = stringsToDisplay;
		this.fileFormat = fileFormat;
		this.config = config;
	}

	public void drawU(UGraphic ug, Area area, Context2D context) {
		final Dimension2D dimensionToUse = area.getDimensionToUse();
		final UmlCharArea charArea = ((UGraphicTxt) ug).getCharArea();
		final int width = (int) dimensionToUse.getWidth();
		final int height = (int) dimensionToUse.getHeight() - 1;

		charArea.fillRect(' ', 0, 0, width, height);

		if (fileFormat == FileFormat.UTXT) {
			if (config.isDotted()) {
				charArea.drawStringLR("\u2500 \u2500 \u2510", 0, 0);
				charArea.drawStringLR("|", 4, 1);
				charArea.drawStringLR("< \u2500 \u2518", 0, 2);
			} else {
				charArea.drawStringLR("\u2500\u2500\u2500\u2500\u2510", 0, 0);
				charArea.drawStringLR("\u2502", 4, 1);
				charArea.drawStringLR("<\u2500\u2500\u2500\u2518", 0, 2);
			}
		} else if (config.isDotted()) {
			charArea.drawStringLR("- - .", 0, 0);
			charArea.drawStringLR("|", 4, 1);
			charArea.drawStringLR("< - '", 0, 2);
		} else {
			charArea.drawStringLR("----.", 0, 0);
			charArea.drawStringLR("|", 4, 1);
			charArea.drawStringLR("<---'", 0, 2);
		}

		charArea.drawStringsLR(stringsToDisplay.as(), 6, 1);
	}

	public double getPreferredHeight(StringBounder stringBounder) {
		return StringUtils.getHeight(stringsToDisplay) + 3;
	}

	public double getPreferredWidth(StringBounder stringBounder) {
		return StringUtils.getWidth(stringsToDisplay) + 6;
	}

}
