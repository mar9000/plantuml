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
package net.sourceforge.plantuml.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;

import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.SpriteContainerEmpty;
import net.sourceforge.plantuml.cucadiagram.IEntity;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.StringBounderUtils;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.ugraphic.ColorMapper;

class EntityImageComponent extends AbstractEntityImage {

	final private TextBlock name;
	private final float thickness = (float) 1.6;

	public EntityImageComponent(IEntity entity) {
		super(entity);
		this.name = TextBlockUtils.create(entity.getDisplay(), new FontConfiguration(getFont14(), 
				HtmlColorUtils.BLACK, HtmlColorUtils.BLUE), HorizontalAlignment.CENTER, new SpriteContainerEmpty());
	}

	@Override
	public Dimension2D getDimension(StringBounder stringBounder) {
		final Dimension2D nameDim = name.calculateDimension(stringBounder);
		return Dimension2DDouble.delta(nameDim, 20, 14);
	}

	private void drawRect(ColorMapper colorMapper, Graphics2D g2d, double x, double y, double width, double height) {
		g2d.setStroke(new BasicStroke(thickness));
		final Shape head = new Rectangle2D.Double(x, y, width, height);
		g2d.setColor(colorMapper.getMappedColor(getYellow()));
		g2d.fill(head);
		g2d.setColor(colorMapper.getMappedColor(getRed()));
		g2d.draw(head);

		g2d.setStroke(new BasicStroke());
	}

	@Override
	public void draw(ColorMapper colorMapper, Graphics2D g2d) {
		final Dimension2D dimTotal = getDimension(StringBounderUtils.asStringBounder(g2d));
		name.calculateDimension(StringBounderUtils.asStringBounder(g2d));

		drawRect(colorMapper, g2d, 6, 0, dimTotal.getWidth(), dimTotal.getHeight());
		drawRect(colorMapper, g2d, 0, 7, 12, 6);
		drawRect(colorMapper, g2d, 0, dimTotal.getHeight() - 7 - 6, 12, 6);

		g2d.setColor(Color.BLACK);
//		name.drawTOBEREMOVED(colorMapper, g2d, 6 + (dimTotal.getWidth() - nameDim.getWidth()) / 2,
//				(dimTotal.getHeight() - nameDim.getHeight()) / 2);
	}
}
