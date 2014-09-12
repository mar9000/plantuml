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

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.SpriteContainerEmpty;
import net.sourceforge.plantuml.cucadiagram.IEntity;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.skin.CircleInterface;
import net.sourceforge.plantuml.ugraphic.ColorMapper;

class EntityImageCircleInterface extends AbstractEntityImage {

	final private TextBlock name;
	final private CircleInterface circleInterface;

	public EntityImageCircleInterface(IEntity entity) {
		super(entity);
		this.name = TextBlockUtils.create(entity.getDisplay(), new FontConfiguration(getFont14(), 
				HtmlColorUtils.BLACK, HtmlColorUtils.BLUE), HorizontalAlignment.CENTER, new SpriteContainerEmpty());
		this.circleInterface = new CircleInterface(getYellow(), getRed());
	}

	@Override
	public Dimension2D getDimension(StringBounder stringBounder) {
		final Dimension2D nameDim = name.calculateDimension(stringBounder);
		final double manWidth = circleInterface.getPreferredWidth(stringBounder);
		final double manHeight = circleInterface.getPreferredHeight(stringBounder);
		return new Dimension2DDouble(Math.max(manWidth, nameDim.getWidth()), manHeight + nameDim.getHeight());
	}

	@Override
	public void draw(ColorMapper colorMapper, Graphics2D g2d) {
		throw new UnsupportedOperationException();
		// final Dimension2D dimTotal = getDimension(StringBounderUtils.asStringBounder(g2d));
		// final Dimension2D nameDim = name.calculateDimension(StringBounderUtils.asStringBounder(g2d));
		//
		// final double manWidth = circleInterface.getPreferredWidth(StringBounderUtils.asStringBounder(g2d));
		// final double manHeight = circleInterface.getPreferredHeight(StringBounderUtils.asStringBounder(g2d));
		//
		// final double manX = (dimTotal.getWidth() - manWidth) / 2;
		//
		// g2d.setColor(Color.WHITE);
		// g2d.fill(new Rectangle2D.Double(0, 0, dimTotal.getWidth(), dimTotal.getHeight()));
		//
		// g2d.translate(manX, 0);
		// circleInterface.draw(g2d);
		// g2d.translate(-manX, 0);
		//
		// g2d.setColor(Color.BLACK);
		// name.drawTOBEREMOVED(g2d, (dimTotal.getWidth() - nameDim.getWidth()) / 2, manHeight);
	}
}
