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
package net.sourceforge.plantuml.sequencediagram.graphic;

import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.sequencediagram.InGroupableList;
import net.sourceforge.plantuml.skin.Area;
import net.sourceforge.plantuml.skin.Component;
import net.sourceforge.plantuml.skin.Context2D;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UTranslate;

class GroupingGraphicalElementElse extends GroupingGraphicalElement {

	private final Component compElse;
	private final Lazy afterY;
	private final boolean parallel;

	public GroupingGraphicalElementElse(double startingY, Component compElse, InGroupableList inGroupableList,
			boolean parallel, Lazy afterY) {
		super(startingY, inGroupableList);
		this.parallel = parallel;
		this.compElse = compElse;
		this.afterY = afterY;
	}

	@Override
	protected void drawInternalU(UGraphic ug, double maxX, Context2D context) {
		final StringBounder stringBounder = ug.getStringBounder();
		final double x1 = getInGroupableList().getMinX(stringBounder);
		final double x2 = getInGroupableList().getMaxX(stringBounder);
		ug = ug.apply(new UTranslate(x1, getStartingY()));

		final double height = afterY.getNow() - getStartingY();
		if (height <= 0) {
			return;
		}
		final Dimension2D dim = new Dimension2DDouble(x2 - x1, height);

		if (parallel == false) {
			compElse.drawU(ug, new Area(dim), context);
		}
	}

	@Override
	public double getPreferredHeight(StringBounder stringBounder) {
		if (parallel) {
			return 0;
		}
		return compElse.getPreferredHeight(stringBounder);
	}

	@Override
	public double getPreferredWidth(StringBounder stringBounder) {
		return compElse.getPreferredWidth(stringBounder);
	}

}
