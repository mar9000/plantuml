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
package net.sourceforge.plantuml.svek;

import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.ugraphic.UChangeBackColor;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.URectangle;
import net.sourceforge.plantuml.ugraphic.UStroke;

public final class InnerActivity implements IEntityImage {

	private final IEntityImage im;
	private final HtmlColor borderColor;
	private final boolean shadowing;
	private final HtmlColor backColor;

	public InnerActivity(final IEntityImage im, HtmlColor borderColor, HtmlColor backColor, boolean shadowing) {
		this.im = im;
		this.backColor = backColor;
		this.borderColor = borderColor;
		this.shadowing = shadowing;
	}

	public final static double THICKNESS_BORDER = 1.5;

	public void drawU(UGraphic ug) {
		final Dimension2D total = calculateDimension(ug.getStringBounder());

		ug = ug.apply(new UChangeBackColor(backColor)).apply(new UChangeColor(borderColor))
				.apply(new UStroke(THICKNESS_BORDER));
		final URectangle rect = new URectangle(total.getWidth(), total.getHeight(), IEntityImage.CORNER,
				IEntityImage.CORNER);
		if (shadowing) {
			rect.setDeltaShadow(4);
		}
		ug.draw(rect);
		ug = ug.apply(new UStroke());
		im.drawU(ug);
	}

	public HtmlColor getBackcolor() {
		return im.getBackcolor();
	}

	public Dimension2D calculateDimension(StringBounder stringBounder) {
		final Dimension2D img = im.calculateDimension(stringBounder);
		return img;
	}

	public ShapeType getShapeType() {
		return ShapeType.ROUND_RECTANGLE;
	}

	public int getShield() {
		return 0;
	}

	public boolean isHidden() {
		return im.isHidden();
	}

}
