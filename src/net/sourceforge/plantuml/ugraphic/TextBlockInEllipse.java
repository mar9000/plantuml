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

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.svek.image.ContainingEllipse;
import net.sourceforge.plantuml.svek.image.Footprint;

public class TextBlockInEllipse implements TextBlock {

	private final TextBlock text;
	private final ContainingEllipse ellipse;

	public TextBlockInEllipse(TextBlock text, StringBounder stringBounder) {
		this.text = text;
		final Dimension2D textDim = text.calculateDimension(stringBounder);
		double alpha = textDim.getHeight() / textDim.getWidth();
		if (alpha < .2) {
			alpha = .2;
		} else if (alpha > .8) {
			alpha = .8;
		}
		final Footprint footprint = new Footprint(stringBounder);
		ellipse = footprint.getEllipse(text, alpha);

	}

	public UEllipse getUEllipse() {
		return ellipse.asUEllipse().bigger(6);
	}

	public void drawU(UGraphic ug) {
		final UEllipse sh = getUEllipse();
		final Point2D center = ellipse.getCenter();
		
		final double dx = sh.getWidth() / 2 - center.getX();
		final double dy = sh.getHeight() / 2 - center.getY();
		
		ug.draw(sh);
		
		text.drawU(ug.apply(new UTranslate(dx, (dy - 2))));
	}

	public Dimension2D calculateDimension(StringBounder stringBounder) {
		return getUEllipse().getDimension();
	}

	public void setDeltaShadow(double deltaShadow) {
		ellipse.setDeltaShadow(deltaShadow);
	}
}
