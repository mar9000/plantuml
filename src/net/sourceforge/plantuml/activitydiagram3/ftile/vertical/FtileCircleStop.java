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
package net.sourceforge.plantuml.activitydiagram3.ftile.vertical;

import java.util.Collections;
import java.util.Set;

import net.sourceforge.plantuml.activitydiagram3.ftile.AbstractFtile;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileGeometry;
import net.sourceforge.plantuml.activitydiagram3.ftile.Swimlane;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.ugraphic.UChangeBackColor;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UEllipse;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class FtileCircleStop extends AbstractFtile {

	private static final int SIZE = 20;

	private final HtmlColor backColor;
	private final Swimlane swimlane;

	public FtileCircleStop(boolean shadowing, HtmlColor backColor, Swimlane swimlane) {
		super(shadowing);
		this.backColor = backColor;
		this.swimlane = swimlane;
	}

	public Set<Swimlane> getSwimlanes() {
		if (swimlane == null) {
			return Collections.emptySet();
		}
		return Collections.singleton(swimlane);
	}

	public Swimlane getSwimlaneIn() {
		return swimlane;
	}

	public Swimlane getSwimlaneOut() {
		return swimlane;
	}

	public void drawU(UGraphic ug) {
		double xTheoricalPosition = 0;
		double yTheoricalPosition = 0;
		xTheoricalPosition = Math.round(xTheoricalPosition);
		yTheoricalPosition = Math.round(yTheoricalPosition);

		final UEllipse circle = new UEllipse(SIZE, SIZE);
		if (shadowing()) {
			circle.setDeltaShadow(3);
		}
		ug.apply(new UChangeColor(backColor)).apply(new UChangeBackColor(null))
				.apply(new UTranslate(xTheoricalPosition, yTheoricalPosition)).draw(circle);

		final double delta = 4;
		final UEllipse circleSmall = new UEllipse(SIZE - delta * 2, SIZE - delta * 2);
		if (shadowing()) {
			circleSmall.setDeltaShadow(3);
		}
		ug.apply(new UChangeColor(null)).apply(new UChangeBackColor(backColor))
				.apply(new UTranslate(xTheoricalPosition + delta + .5, yTheoricalPosition + delta + .5))
				.draw(circleSmall);
	}

	public FtileGeometry calculateDimension(StringBounder stringBounder) {
		return new FtileGeometry(SIZE, SIZE, SIZE / 2, 0);
	}

}
