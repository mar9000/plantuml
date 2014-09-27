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
package net.sourceforge.plantuml.ugraphic.html5;

import java.awt.geom.Rectangle2D;

import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorGradient;
import net.sourceforge.plantuml.ugraphic.ClipContainer;
import net.sourceforge.plantuml.ugraphic.ColorMapper;
import net.sourceforge.plantuml.ugraphic.UClip;
import net.sourceforge.plantuml.ugraphic.UDriver;
import net.sourceforge.plantuml.ugraphic.UParam;
import net.sourceforge.plantuml.ugraphic.URectangle;
import net.sourceforge.plantuml.ugraphic.UShape;
import net.sourceforge.plantuml.utils.StringUtils;

public class DriverRectangleHtml5 implements UDriver<Html5Drawer> {

	private final ClipContainer clipContainer;

	public DriverRectangleHtml5(ClipContainer clipContainer) {
		this.clipContainer = clipContainer;
	}

	public void draw(UShape ushape, double x, double y, ColorMapper mapper, UParam param, Html5Drawer html) {
		final URectangle rect = (URectangle) ushape;

		double width = rect.getWidth();
		double height = rect.getHeight();

		final UClip clip = clipContainer.getClip();
		if (clip != null) {
			final Rectangle2D.Double r = clip.getClippedRectangle(new Rectangle2D.Double(x, y, width, height));
			x = r.x;
			y = r.y;
			width = r.width;
			height = r.height;
		}

		final double rx = rect.getRx();
		final double ry = rect.getRy();

//		// Shadow
//		if (rect.getDeltaShadow() != 0) {
//			eps.epsRectangleShadow(x, y, width, height, rx / 2, ry / 2, rect.getDeltaShadow());
//		}

		final HtmlColor back = param.getBackcolor();
		if (back instanceof HtmlColorGradient) {
//			eps.setStrokeColor(mapper.getMappedColor(param.getColor()));
//			eps.epsRectangle(x, y, width, height, rx / 2, ry / 2, (HtmlColorGradient) back, mapper);
		} else {
			final String color = param.getColor() == null ? null : StringUtils.getAsHtml(mapper.getMappedColor(param
					.getColor()));
			final String backcolor = param.getColor() == null ? null : StringUtils.getAsHtml(mapper.getMappedColor(param
					.getBackcolor()));

			html.setStrokeColor(color);
			html.setFillColor(backcolor);
//			eps.setStrokeWidth("" + param.getStroke().getThickness(), param.getStroke().getDashVisible(), param
//					.getStroke().getDashSpace());
			html.htmlRectangle(x, y, width, height, rx / 2, ry / 2);
		}

	}
}
