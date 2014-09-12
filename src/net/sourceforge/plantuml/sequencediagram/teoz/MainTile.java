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
package net.sourceforge.plantuml.sequencediagram.teoz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.graphic.UGraphicInterceptorUDrawable;
import net.sourceforge.plantuml.real.Real;
import net.sourceforge.plantuml.real.RealMax;
import net.sourceforge.plantuml.real.RealMin;
import net.sourceforge.plantuml.sequencediagram.Event;
import net.sourceforge.plantuml.sequencediagram.SequenceDiagram;
import net.sourceforge.plantuml.skin.Skin;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class MainTile implements Tile {

	private final RealMin min = new RealMin();
	private final RealMax max = new RealMax();
	private double height;

	private final List<Tile> tiles = new ArrayList<Tile>();

	public MainTile(SequenceDiagram diagram, Skin skin, Real omega, LivingSpaces livingSpaces, Real origin) {

		min.put(origin);
		max.put(omega);

		final ISkinParam skinParam = diagram.getSkinParam();
		final StringBounder stringBounder = TextBlockUtils.getDummyStringBounder();

		final TileArguments tileArguments = new TileArguments(stringBounder, omega, livingSpaces, skin, skinParam,
				origin);

		tiles.addAll(TileBuilder.buildSeveral(diagram.events().iterator(), tileArguments, null));

		for (Tile tile : tiles) {
			height += tile.getPreferredHeight(stringBounder);
			min.put(tile.getMinX(stringBounder));
			max.put(tile.getMaxX(stringBounder));
			// if (tile instanceof DelayTile) {
			// for (LivingSpace livingSpace : livingSpaces.values()) {
			// livingSpace.addDelayTile((DelayTile) tile);
			// }
			// }
		}
	}

	private void beforeDrawing(StringBounder stringBounder, Collection<LivingSpace> livingSpaces) {
		double h = 0;
		for (Tile tile : tiles) {
			System.err.println("tile=" + tile);
			// if (tile instanceof DelayTile) {
			// ((DelayTile) tile).setStartingY(h);
			// }
			h += tile.getPreferredHeight(stringBounder);
		}
	}

	public void drawU(UGraphic ug) {
		final StringBounder stringBounder = ug.getStringBounder();
		final LiveBoxFinder liveBoxFinder = new LiveBoxFinder(stringBounder);

		drawUInternal(liveBoxFinder);
		drawUInternal(new UGraphicInterceptorUDrawable(ug));
	}

	private void drawUInternal(UGraphic ug) {
		final StringBounder stringBounder = ug.getStringBounder();
		double h = 0;
		for (Tile tile : tiles) {
			// tile.drawU(ug.apply(new UTranslate(0, h)));
			ug.apply(new UTranslate(0, h)).draw(tile);
			h += tile.getPreferredHeight(stringBounder);
		}
	}

	public double getPreferredHeight(StringBounder stringBounder) {
		return height;
	}

	public void addConstraints(StringBounder stringBounder) {
		for (Tile tile : tiles) {
			tile.addConstraints(stringBounder);
		}
	}

	public Real getMinX(StringBounder stringBounder) {
		return min;
	}

	public Real getMaxX(StringBounder stringBounder) {
		return max;
	}

	public Event getEvent() {
		return null;
	}

}
