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

class USymbolFolder extends USymbol {

	private final static int marginTitleX1 = 3;
	private final static int marginTitleX2 = 3;
	private final static int marginTitleX3 = 7;
	private final static int marginTitleY0 = 0;
	private final static int marginTitleY1 = 3;
	private final static int marginTitleY2 = 3;

	public USymbolFolder(ColorParam colorParamBack, ColorParam colorParamBorder) {
		super(colorParamBack, colorParamBorder, FontParam.FOLDER, FontParam.FOLDER_STEREOTYPE);
	}

	private void drawFolder(UGraphic ug, double width, double height, Dimension2D dimTitle, boolean shadowing) {

		final double wtitle;
		if (dimTitle.getWidth() == 0) {
			wtitle = Math.max(30, width / 4);
		} else {
			wtitle = dimTitle.getWidth() + marginTitleX1 + marginTitleX2;
		}
		final double htitle = getHTitle(dimTitle);

		final UPolygon shape = new UPolygon();
		shape.addPoint(0, 0);
		shape.addPoint(wtitle, 0);

		shape.addPoint(wtitle + marginTitleX3, htitle);
		shape.addPoint(width, htitle);
		shape.addPoint(width, height);
		shape.addPoint(0, height);
		shape.addPoint(0, 0);
		if (shadowing) {
			shape.setDeltaShadow(3.0);
		}
		ug.draw(shape);
		ug.apply(new UTranslate(0, htitle)).draw(new ULine(wtitle + marginTitleX3, 0));
	}

	private double getHTitle(Dimension2D dimTitle) {
		final double htitle;
		if (dimTitle.getWidth() == 0) {
			htitle = 10;
		} else {
			htitle = dimTitle.getHeight() + marginTitleY1 + marginTitleY2;
		}
		return htitle;
	}

	private Margin getMargin() {
		return new Margin(10, 10 + 10, 10 + 3, 10);
	}

	public TextBlock asSmall(final TextBlock label, final TextBlock stereotype, final SymbolContext symbolContext) {
		return new TextBlock() {

			public void drawU(UGraphic ug) {
				final Dimension2D dim = calculateDimension(ug.getStringBounder());
				ug = symbolContext.apply(ug);
				drawFolder(ug, dim.getWidth(), dim.getHeight(), new Dimension2DDouble(0, 0),
						symbolContext.isShadowing());
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
				final StringBounder stringBounder = ug.getStringBounder();
				final Dimension2D dim = calculateDimension(stringBounder);
				ug = symbolContext.apply(ug);
				final Dimension2D dimTitle = title.calculateDimension(stringBounder);
				drawFolder(ug, dim.getWidth(), dim.getHeight(), dimTitle, symbolContext.isShadowing());
				title.drawU(ug.apply(new UTranslate(4, 2)));
				final Dimension2D dimStereo = stereotype.calculateDimension(stringBounder);
				final double posStereo = (width - dimStereo.getWidth()) / 2;

				stereotype.drawU(ug.apply(new UTranslate(4 + posStereo, 2 + getHTitle(dimTitle))));
			}

			public Dimension2D calculateDimension(StringBounder stringBounder) {
				return new Dimension2DDouble(width, height);
			}

		};
	}

}
