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
package net.sourceforge.plantuml.graphic;

import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.ColorParam;
import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.FontParam;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.ULine;
import net.sourceforge.plantuml.ugraphic.UPolygon;
import net.sourceforge.plantuml.ugraphic.UTranslate;

class USymbolNode extends USymbol {

	public USymbolNode() {
		super(ColorParam.nodeBackground, ColorParam.nodeBorder, FontParam.NODE, FontParam.NODE_STEREOTYPE);
	}

	private void drawNode(UGraphic ug, double width, double height, boolean shadowing) {
		final UPolygon shape = new UPolygon();
		shape.addPoint(0, 10);
		shape.addPoint(10, 0);
		shape.addPoint(width, 0);
		shape.addPoint(width, height - 10);
		shape.addPoint(width - 10, height);
		shape.addPoint(0, height);
		shape.addPoint(0, 10);
		if (shadowing) {
			shape.setDeltaShadow(2);
		}
		ug.draw(shape);

		ug.apply(new UTranslate(width - 10, 10)).draw(new ULine(9, -9));
		ug.apply(new UTranslate(0, 10)).draw(new ULine(width - 10, 0));
		ug.apply(new UTranslate(width - 10, 10)).draw(new ULine(0, height - 10));

	}

	private Margin getMargin() {
		return new Margin(10 + 5, 20 + 5, 15 + 5, 5 + 5);
	}

	public TextBlock asSmall(final TextBlock label, final TextBlock stereotype, final SymbolContext symbolContext) {
		return new TextBlock() {

			public void drawU(UGraphic ug) {
				final Dimension2D dim = calculateDimension(ug.getStringBounder());
				ug = symbolContext.apply(ug);
				drawNode(ug, dim.getWidth(), dim.getHeight(), symbolContext.isShadowing());
				final Margin margin = getMargin();
				final TextBlock tb = TextBlockUtils.mergeTB(stereotype, label, HorizontalAlignment.CENTER);
				tb.drawU(ug.apply(new UTranslate(margin.getX1(), margin.getY1())));
			}

			public Dimension2D calculateDimension(StringBounder stringBounder) {
				final Dimension2D dimLabel = label.calculateDimension(stringBounder);
				final Dimension2D dimStereo = stereotype.calculateDimension(stringBounder);
				return getMargin().addDimension(Dimension2DDouble.mergeTB(dimStereo, dimLabel));
			}
		};
	}

	public TextBlock asBig(final TextBlock title, final TextBlock stereotype, final double width, final double height,
			final SymbolContext symbolContext) {
		return new TextBlock() {

			public void drawU(UGraphic ug) {
				final Dimension2D dim = calculateDimension(ug.getStringBounder());
				ug = symbolContext.apply(ug);
				drawNode(ug, dim.getWidth(), dim.getHeight(), symbolContext.isShadowing());
				ug = ug.apply(new UTranslate(-4, 11));
				
				final Dimension2D dimStereo = stereotype.calculateDimension(ug.getStringBounder());
				final double posStereo = (width - dimStereo.getWidth()) / 2;
				stereotype.drawU(ug.apply(new UTranslate(posStereo, 2)));
				final Dimension2D dimTitle = title.calculateDimension(ug.getStringBounder());
				final double posTitle = (width - dimTitle.getWidth()) / 2;
				title.drawU(ug.apply(new UTranslate(posTitle, 2 + dimStereo.getHeight())));

//				title.drawU(ug.apply(new UTranslate(3, 13)));
//				final Dimension2D dimStereo = stereotype.calculateDimension(ug.getStringBounder());
//				final double posStereo = (width - dimStereo.getWidth()) / 2;
//				final Dimension2D dimTitle = title.calculateDimension(ug.getStringBounder());
//				stereotype.drawU(ug.apply(new UTranslate(posStereo, dimTitle.getHeight() + 13)));
			}

			public Dimension2D calculateDimension(StringBounder stringBounder) {
				return new Dimension2DDouble(width, height);
			}
		};
	}

}
