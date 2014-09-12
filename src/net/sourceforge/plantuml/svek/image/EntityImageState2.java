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

import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.LineConfigurable;
import net.sourceforge.plantuml.SkinParamUtils;
import net.sourceforge.plantuml.Url;
import net.sourceforge.plantuml.cucadiagram.BodyEnhanced;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.cucadiagram.IEntity;
import net.sourceforge.plantuml.cucadiagram.Member;
import net.sourceforge.plantuml.cucadiagram.Stereotype;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.SymbolContext;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.graphic.USymbol;
import net.sourceforge.plantuml.svek.AbstractEntityImage;
import net.sourceforge.plantuml.svek.ShapeType;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UStroke;

public class EntityImageState2 extends AbstractEntityImage {

	final private Url url;

	final private LineConfigurable lineConfig;

	private final TextBlock asSmall;

	public EntityImageState2(IEntity entity, ISkinParam skinParam) {
		super(entity, skinParam);
		this.lineConfig = entity;
		final Stereotype stereotype = entity.getStereotype();

		Display list = Display.empty();
		for (Member att : entity.getFieldsToDisplay()) {
			list = list.addAll(Display.getWithNewlines(att.getDisplay(true)));
		}

		final USymbol symbol = USymbol.FRAME;

		HtmlColor backcolor = getEntity().getSpecificBackColor();
		if (backcolor == null) {
			backcolor = SkinParamUtils.getColor(getSkinParam(), symbol.getColorParamBack(), getStereo());
		}
		// backcolor = HtmlColorUtils.BLUE;
		final HtmlColor forecolor = SkinParamUtils.getColor(getSkinParam(), symbol.getColorParamBorder(), getStereo());

		final SymbolContext ctx = new SymbolContext(backcolor, forecolor).withStroke(new UStroke(1.5)).withShadow(
				getSkinParam().shadowing());

		this.url = entity.getUrl99();
		TextBlock stereo = TextBlockUtils.empty(0, 0);

		final TextBlock desc = new BodyEnhanced(entity.getDisplay(), symbol.getFontParam(), skinParam,
				HorizontalAlignment.CENTER, stereotype, symbol.manageHorizontalLine(), false);

		asSmall = symbol.asSmall(desc, stereo, ctx);

	}

	public ShapeType getShapeType() {
		return ShapeType.RECTANGLE;
	}

	public int getShield() {
		return 0;
	}

	public Dimension2D calculateDimension(StringBounder stringBounder) {
		return asSmall.calculateDimension(stringBounder);
	}

	final public void drawU(UGraphic ug) {
		if (url != null) {
			ug.startUrl(url);
		}
		asSmall.drawU(ug);

		if (url != null) {
			ug.closeAction();
		}
	}

}
