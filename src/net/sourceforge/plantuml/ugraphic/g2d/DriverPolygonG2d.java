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
package net.sourceforge.plantuml.ugraphic.g2d;

import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import net.sourceforge.plantuml.EnsureVisible;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorGradient;
import net.sourceforge.plantuml.ugraphic.ColorMapper;
import net.sourceforge.plantuml.ugraphic.UDriver;
import net.sourceforge.plantuml.ugraphic.UParam;
import net.sourceforge.plantuml.ugraphic.UPolygon;
import net.sourceforge.plantuml.ugraphic.UShape;

public class DriverPolygonG2d extends DriverShadowedG2d implements UDriver<Graphics2D> {

	private final double dpiFactor;
	private final EnsureVisible visible;

	public DriverPolygonG2d(double dpiFactor, EnsureVisible visible) {
		this.dpiFactor = dpiFactor;
		this.visible = visible;
	}

	public void draw(UShape ushape, double x, double y, ColorMapper mapper, UParam param, Graphics2D g2d) {
		final UPolygon shape = (UPolygon) ushape;

		g2d.setStroke(new BasicStroke((float) param.getStroke().getThickness()));

		final GeneralPath path = new GeneralPath();

		boolean first = true;
		for (Point2D pt : shape.getPoints()) {
			final double xp = pt.getX() + x;
			final double yp = pt.getY() + y;
			visible.ensureVisible(xp, yp);
			if (first) {
				path.moveTo((float) xp, (float) yp);
			} else {
				path.lineTo((float) xp, (float) yp);
			}
			first = false;
		}

		if (first == false) {
			path.closePath();
		}

		if (shape.getDeltaShadow() != 0) {
			drawShadow(g2d, path, shape.getDeltaShadow(), dpiFactor);
		}

		final HtmlColor back = param.getBackcolor();
		if (back instanceof HtmlColorGradient) {
			final HtmlColorGradient gr = (HtmlColorGradient) back;
			final char policy = gr.getPolicy();
			final GradientPaint paint;
//			final Rectangle2D bound = path.getBounds();
			if (policy == '|') {
				paint = new GradientPaint((float) x, (float) (y + shape.getHeight()) / 2, mapper.getMappedColor(gr
						.getColor1()), (float) (x + shape.getWidth()), (float) (y + shape.getHeight()) / 2,
						mapper.getMappedColor(gr.getColor2()));
			} else if (policy == '\\') {
				paint = new GradientPaint((float) x, (float) (y + shape.getHeight()), mapper.getMappedColor(gr
						.getColor1()), (float) (x + shape.getWidth()), (float) y, mapper.getMappedColor(gr.getColor2()));
			} else if (policy == '-') {
				paint = new GradientPaint((float) (x + shape.getWidth()) / 2, (float) y, mapper.getMappedColor(gr
						.getColor1()), (float) (x + shape.getWidth()) / 2, (float) (y + shape.getHeight()),
						mapper.getMappedColor(gr.getColor2()));
			} else {
				// for /
				paint = new GradientPaint((float) x, (float) y, mapper.getMappedColor(gr.getColor1()),
						(float) (x + shape.getWidth()), (float) (y + shape.getHeight()), mapper.getMappedColor(gr
								.getColor2()));
			}
			g2d.setPaint(paint);
			g2d.fill(path);
		} else if (back!=null) {
			g2d.setColor(mapper.getMappedColor(back));
			DriverRectangleG2d.managePattern(param, g2d);
			g2d.fill(path);
		}

		if (param.getColor() != null) {
			g2d.setColor(mapper.getMappedColor(param.getColor()));
			DriverLineG2d.manageStroke(param, g2d);
			g2d.draw(path);
		}
	}
}
