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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.cucadiagram.IEntity;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.ugraphic.ColorMapper;

class EntityImageActivityCircle extends AbstractEntityImage {

	private final int diameterExternal;
	private final int diameterInternal;

	public EntityImageActivityCircle(IEntity entity, int diameterExternal, int diameterInternal) {
		super(entity);
		this.diameterExternal = diameterExternal;
		this.diameterInternal = diameterInternal;
	}

	@Override
	public Dimension2D getDimension(StringBounder stringBounder) {
		return new Dimension2DDouble(diameterExternal, diameterExternal);
	}

	@Override
	public void draw(ColorMapper colorMapper, Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		final int delta = diameterExternal - diameterInternal + 1;
		g2d.drawOval(0, 0, diameterExternal, diameterExternal);
		g2d.fillOval(delta / 2, delta / 2, diameterInternal, diameterInternal);
	}
}
