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
package net.sourceforge.plantuml.posimo;

import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.FontParam;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.SpriteContainerEmpty;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.skin.rose.Rose;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class LabelImage {

	// private final Entity entity;
	final private ISkinParam param;
	final private Rose rose;
	final private TextBlock name;

	public LabelImage(Link link, Rose rose, ISkinParam param) {
		if (link == null) {
			throw new IllegalArgumentException();
		}
		// this.entity = entity;
		this.param = param;
		this.rose = rose;
		this.name = TextBlockUtils.create(link.getLabel(),
				new FontConfiguration(param.getFont(FontParam.CLASS, null), HtmlColorUtils.BLACK, param.getHyperlinkColor()), HorizontalAlignment.CENTER, new SpriteContainerEmpty());
	}

	public Dimension2D getDimension(StringBounder stringBounder) {
		final Dimension2D dim = name.calculateDimension(stringBounder);
		return dim;
		// return Dimension2DDouble.delta(dim, 2 * margin);
	}

	public void drawU(UGraphic ug, double x, double y) {
		// final Dimension2D dim = getDimension(ug.getStringBounder());
		// ug.getParam().setBackcolor(rose.getHtmlColor(param,
		// ColorParam.classBackground).getColor());
		// ug.getParam().setColor(rose.getHtmlColor(param,
		// ColorParam.classBorder).getColor());
		// ug.draw(x, y, new URectangle(dim.getWidth(), dim.getHeight()));
		name.drawU(ug.apply(new UTranslate(x, y)));
	}
}
