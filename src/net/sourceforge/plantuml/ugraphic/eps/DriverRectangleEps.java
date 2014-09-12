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

import java.awt.geom.Rectangle2D;

import net.sourceforge.plantuml.eps.EpsGraphics;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorGradient;
import net.sourceforge.plantuml.ugraphic.ClipContainer;
import net.sourceforge.plantuml.ugraphic.ColorMapper;
import net.sourceforge.plantuml.ugraphic.UClip;
import net.sourceforge.plantuml.ugraphic.UDriver;
import net.sourceforge.plantuml.ugraphic.UParam;
import net.sourceforge.plantuml.ugraphic.URectangle;
import net.sourceforge.plantuml.ugraphic.UShape;

public class DriverRectangleEps implements UDriver<EpsGraphics> {

	private final ClipContainer clipContainer;

	public DriverRectangleEps(ClipContainer clipContainer) {
		this.clipContainer = clipContainer;
	}

	public void draw(UShape ushape, double x, double y, ColorMapper mapper, UParam param, EpsGraphics eps) {
		final URectangle rect = (URectangle) ushape;

		double width = rect.getWidth();
		double height = rect.getHeight();

		final UClip clip = clipContainer.getClip();
		if (clip != null) {
			final Rectangle2D.Double orig = new Rectangle2D.Double(x, y, width, height);
			final Rectangle2D.Double r = clip.getClippedRectangle(orig);
			if (r.height < 0) {
				return;
			}
			x = r.x;
			y = r.y;
			width = r.width;
			height = r.height;
		}

		final double rx = rect.getRx();
		final double ry = rect.getRy();

		// Shadow
		if (rect.getDeltaShadow() != 0) {
			eps.epsRectangleShadow(x, y, width, height, rx / 2, ry / 2, rect.getDeltaShadow());
		}

		final HtmlColor back = param.getBackcolor();
		if (back instanceof HtmlColorGradient) {
			eps.setStrokeColor(mapper.getMappedColor(param.getColor()));
			eps.epsRectangle(x, y, width, height, rx / 2, ry / 2, (HtmlColorGradient) back, mapper);
		} else {
			eps.setStrokeColor(mapper.getMappedColor(param.getColor()));
			eps.setFillColor(mapper.getMappedColor(param.getBackcolor()));
			eps.setStrokeWidth("" + param.getStroke().getThickness(), param.getStroke().getDashVisible(), param
					.getStroke().getDashSpace());
			eps.epsRectangle(x, y, width, height, rx / 2, ry / 2);
		}

	}
}
