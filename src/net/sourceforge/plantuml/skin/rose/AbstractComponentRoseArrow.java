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
package net.sourceforge.plantuml.skin.rose;

import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.skin.AbstractTextualComponent;
import net.sourceforge.plantuml.skin.ArrowComponent;
import net.sourceforge.plantuml.skin.ArrowConfiguration;
import net.sourceforge.plantuml.ugraphic.UFont;

public abstract class AbstractComponentRoseArrow extends AbstractTextualComponent implements ArrowComponent {

	private final int arrowDeltaX = 10;
	private final int arrowDeltaY = 4;
	private final HtmlColor foregroundColor;
	private final ArrowConfiguration arrowConfiguration;

	public AbstractComponentRoseArrow(HtmlColor foregroundColor, HtmlColor fontColor, HtmlColor hyperlinkColor, UFont font,
			Display stringsToDisplay, ArrowConfiguration arrowConfiguration, ISkinSimple spriteContainer,
			HorizontalAlignment textHorizontalAlignment, double maxMessageSize) {
		super(stringsToDisplay, fontColor, hyperlinkColor, font, textHorizontalAlignment, 7, 7, 1, spriteContainer, maxMessageSize, false);
		this.arrowConfiguration = arrowConfiguration;
		this.foregroundColor = foregroundColor;
	}

	protected final HtmlColor getForegroundColor() {
		return foregroundColor;
	}

	final protected int getArrowDeltaX() {
		return arrowDeltaX;
	}

	final protected int getArrowDeltaY() {
		return arrowDeltaY;
	}

	@Override
	public final double getPaddingY() {
		return 4;
	}

	public final ArrowConfiguration getArrowConfiguration() {
		return arrowConfiguration;
	}

}
