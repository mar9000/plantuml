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
package net.sourceforge.plantuml.svek.image;

import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.ColorParam;
import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.SkinParamUtils;
import net.sourceforge.plantuml.cucadiagram.ILeaf;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.svek.AbstractEntityImage;
import net.sourceforge.plantuml.svek.ShapeType;
import net.sourceforge.plantuml.ugraphic.UChangeBackColor;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UEllipse;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UShape;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class EntityImageCircleEnd extends AbstractEntityImage {

	private static final int SIZE = 20;

	public EntityImageCircleEnd(ILeaf entity, ISkinParam skinParam) {
		super(entity, skinParam);
	}

	public Dimension2D calculateDimension(StringBounder stringBounder) {
		return new Dimension2DDouble(SIZE, SIZE);
	}

	final public void drawU(UGraphic ug) {
		final UEllipse circle = new UEllipse(SIZE, SIZE);
		if (getSkinParam().shadowing()) {
			circle.setDeltaShadow(3);
		}
		ug.apply(new UChangeBackColor(null))
				.apply(new UChangeColor(SkinParamUtils.getColor(getSkinParam(), ColorParam.activityEnd, getStereo())))
				.draw(circle);

		final double delta = 4;
		final UShape circleSmall = new UEllipse(SIZE - delta * 2, SIZE - delta * 2);
		ug.apply(new UChangeBackColor(SkinParamUtils.getColor(getSkinParam(), ColorParam.activityEnd, getStereo())))
		.apply(new UChangeColor(null)).apply(new UTranslate(delta + 0.5, delta + 0.5)).draw(circleSmall);
	}

	public ShapeType getShapeType() {
		return ShapeType.CIRCLE;
	}

	public int getShield() {
		return 0;
	}

}
