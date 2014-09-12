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
package net.sourceforge.plantuml.skin.rose;

import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.skin.AbstractComponent;
import net.sourceforge.plantuml.skin.Area;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.ULine;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class ComponentRoseDestroy extends AbstractComponent {

	private final HtmlColor foregroundColor;

	public ComponentRoseDestroy(HtmlColor foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	private final int crossSize = 9;

	@Override
	protected void drawInternalU(UGraphic ug, Area area) {
		ug = ug.apply(new UStroke(2)).apply(new UChangeColor(foregroundColor));

		ug.draw(new ULine(2 * crossSize, 2 * crossSize));
		ug.apply(new UTranslate(0, 2 * crossSize)).draw(new ULine(2 * crossSize, -2 * crossSize));
	}

	@Override
	public double getPreferredHeight(StringBounder stringBounder) {
		return crossSize * 2;
	}

	@Override
	public double getPreferredWidth(StringBounder stringBounder) {
		return crossSize * 2;
	}

}
