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
package net.sourceforge.plantuml.ugraphic.eps;

import net.sourceforge.plantuml.eps.EpsGraphics;
import net.sourceforge.plantuml.ugraphic.ColorMapper;
import net.sourceforge.plantuml.ugraphic.UDriver;
import net.sourceforge.plantuml.ugraphic.UEllipse;
import net.sourceforge.plantuml.ugraphic.UParam;
import net.sourceforge.plantuml.ugraphic.UShape;

public class DriverEllipseEps implements UDriver<EpsGraphics> {

	public void draw(UShape ushape, double x, double y, ColorMapper mapper, UParam param, EpsGraphics eps) {
		final UEllipse shape = (UEllipse) ushape;
		final double width = shape.getWidth();
		final double height = shape.getHeight();

		// Shadow
		if (shape.getDeltaShadow() != 0) {
			eps.epsEllipseShadow(x + width / 2, y + height / 2, width / 2, height / 2, shape.getDeltaShadow());
		}

		eps.setFillColor(mapper.getMappedColor(param.getBackcolor()));
		eps.setStrokeColor(mapper.getMappedColor(param.getColor()));
		eps.setStrokeWidth("" + param.getStroke().getThickness(), param.getStroke().getDashVisible(), param.getStroke()
				.getDashSpace());

		if (shape.getStart() == 0 && shape.getExtend() == 0) {
			eps.epsEllipse(x + width / 2, y + height / 2, width / 2, height / 2);
		} else {
			eps.epsEllipse(x + width / 2, y + height / 2, width / 2, height / 2, shape.getStart(), shape.getExtend());
		}
	}

}
