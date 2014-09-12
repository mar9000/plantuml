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
package net.sourceforge.plantuml;

import net.sourceforge.plantuml.cucadiagram.Stereotype;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.skin.rose.Rose;
import net.sourceforge.plantuml.ugraphic.UFont;

public class SkinParamUtils {

	private static final Rose rose = new Rose();

	public static UFont getFont(ISkinParam skinParam, FontParam fontParam, Stereotype stereo) {
		return skinParam.getFont(fontParam, stereo);
	}

	public static HtmlColor getFontColor(ISkinParam skinParam, FontParam fontParam, Stereotype stereo) {
		return skinParam.getFontHtmlColor(fontParam, stereo);
	}

	public static HtmlColor getColor(ISkinParam skinParam, ColorParam colorParam, Stereotype stereo) {
		return rose.getHtmlColor(skinParam, colorParam, stereo);
	}

}
