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
package net.sourceforge.plantuml.golem;

import java.awt.Font;
import java.awt.geom.Dimension2D;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.SpriteContainerEmpty;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UEllipse;
import net.sourceforge.plantuml.ugraphic.UFont;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.URectangle;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class Tile implements TextBlock {

	private static double SIZE = 40;
	private final int num;

	private final UFont numberFont = new UFont("Monospaced", Font.PLAIN, 11);
	private final FontConfiguration fc = new FontConfiguration(numberFont, HtmlColorUtils.BLACK, HtmlColorUtils.BLUE);
	private final Map<TileGeometry, TileArea> geometries;

	Tile(int num) {
		this.num = num;
		final Map<TileGeometry, TileArea> tmp = new EnumMap<TileGeometry, TileArea>(TileGeometry.class);
		for (TileGeometry g : TileGeometry.values()) {
			tmp.put(g, new TileArea(this, g));
		}
		this.geometries = Collections.unmodifiableMap(tmp);
	}

	public TileArea getArea(TileGeometry geometry) {
		return this.geometries.get(geometry);
	}

	public void drawU(UGraphic ug) {
		ug = ug.apply(new UChangeColor(HtmlColorUtils.BLACK));
		final TextBlock n = TextBlockUtils.create(Display.create("" + num), fc, HorizontalAlignment.LEFT,
				new SpriteContainerEmpty());
		final Dimension2D dimNum = n.calculateDimension(ug.getStringBounder());
		final Dimension2D dimTotal = calculateDimension(ug.getStringBounder());
		final double diffx = dimTotal.getWidth() - dimNum.getWidth();
		final double diffy = dimTotal.getHeight() - dimNum.getHeight();
		final double radius = Math.max(dimNum.getWidth(), dimNum.getHeight());
		final double diffx2 = dimTotal.getWidth() - radius;
		final double diffy2 = dimTotal.getHeight() - radius;
		n.drawU(ug.apply(new UTranslate((diffx / 2), (diffy / 2))));
		ug.draw(new URectangle(SIZE, SIZE));
		ug.apply(new UTranslate(diffx2 / 2, diffy2 / 2)).draw(new UEllipse(radius, radius));
	}

	public Dimension2D calculateDimension(StringBounder stringBounder) {
		return new Dimension2DDouble(SIZE, SIZE);
	}
}
