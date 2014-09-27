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
package net.sourceforge.plantuml.ugraphic.svg;

import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorGradient;
import net.sourceforge.plantuml.svg.SvgGraphics;
import net.sourceforge.plantuml.ugraphic.ClipContainer;
import net.sourceforge.plantuml.ugraphic.ColorMapper;
import net.sourceforge.plantuml.ugraphic.UDriver;
import net.sourceforge.plantuml.ugraphic.UParam;
import net.sourceforge.plantuml.ugraphic.UPath;
import net.sourceforge.plantuml.ugraphic.UShape;
import net.sourceforge.plantuml.ugraphic.g2d.DriverShadowedG2d;
import net.sourceforge.plantuml.utils.StringUtils;

public class DriverPathSvg extends DriverShadowedG2d implements UDriver<SvgGraphics> {

	private final ClipContainer clipContainer;

	public DriverPathSvg(ClipContainer clipContainer) {
		this.clipContainer = clipContainer;
	}

	public void draw(UShape ushape, double x, double y, ColorMapper mapper, UParam param, SvgGraphics svg) {
		final UPath shape = (UPath) ushape;

		final String color = StringUtils.getAsSvg(mapper, param.getColor());
		if (shape.isOpenIconic()) {
			svg.setFillColor(color);
			svg.setStrokeColor("");
			svg.setStrokeWidth(0, "");
		} else {
			final HtmlColor back = param.getBackcolor();
			if (back instanceof HtmlColorGradient) {
				final HtmlColorGradient gr = (HtmlColorGradient) back;
				final String id = svg.createSvgGradient(StringUtils.getAsHtml(mapper.getMappedColor(gr.getColor1())),
						StringUtils.getAsHtml(mapper.getMappedColor(gr.getColor2())), gr.getPolicy());
				svg.setFillColor("url(#" + id + ")");
			} else {
				final String backcolor = StringUtils.getAsSvg(mapper, back);
				svg.setFillColor(backcolor);
			}
			svg.setStrokeColor(color);
			svg.setStrokeWidth(param.getStroke().getThickness(), param.getStroke().getDasharraySvg());
		}

		svg.svgPath(x, y, shape, shape.getDeltaShadow());

	}
}
