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

import net.sourceforge.plantuml.cucadiagram.Rankdir;
import net.sourceforge.plantuml.cucadiagram.Stereotype;
import net.sourceforge.plantuml.cucadiagram.dot.DotSplines;
import net.sourceforge.plantuml.cucadiagram.dot.GraphvizLayoutStrategy;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.svek.ConditionStyle;
import net.sourceforge.plantuml.svek.PackageStyle;
import net.sourceforge.plantuml.ugraphic.ColorMapper;
import net.sourceforge.plantuml.ugraphic.Sprite;
import net.sourceforge.plantuml.ugraphic.UFont;
import net.sourceforge.plantuml.ugraphic.UStroke;

public class SkinParamDelegator implements ISkinParam {

	final private ISkinParam skinParam;

	public SkinParamDelegator(ISkinParam skinParam) {
		this.skinParam = skinParam;
	}

	public HtmlColor getHyperlinkColor() {
		return skinParam.getHyperlinkColor();
	}

	public HtmlColor getBackgroundColor() {
		return skinParam.getBackgroundColor();
	}

	public int getCircledCharacterRadius() {
		return skinParam.getCircledCharacterRadius();
	}

	public UFont getFont(FontParam fontParam, Stereotype stereotype) {
		return skinParam.getFont(fontParam, stereotype);
	}

	public HtmlColor getFontHtmlColor(FontParam param, Stereotype stereotype) {
		return skinParam.getFontHtmlColor(param, stereotype);
	}

	public HtmlColor getHtmlColor(ColorParam param, Stereotype stereotype, boolean clickable) {
		return skinParam.getHtmlColor(param, stereotype, clickable);
	}

	public String getValue(String key) {
		return skinParam.getValue(key);
	}

	public int classAttributeIconSize() {
		return skinParam.classAttributeIconSize();
	}

	public int getDpi() {
		return skinParam.getDpi();
	}

	public DotSplines getDotSplines() {
		return skinParam.getDotSplines();
	}

	public GraphvizLayoutStrategy getStrategy() {
		return skinParam.getStrategy();
	}

	public HorizontalAlignment getHorizontalAlignment(AlignParam param) {
		return skinParam.getHorizontalAlignment(param);
	}

	public ColorMapper getColorMapper() {
		return skinParam.getColorMapper();
	}

	public boolean shadowing() {
		return skinParam.shadowing();
	}

	public PackageStyle getPackageStyle() {
		return skinParam.getPackageStyle();
	}

	public Sprite getSprite(String name) {
		return skinParam.getSprite(name);
	}

	public boolean useUml2ForComponent() {
		return skinParam.useUml2ForComponent();
	}

	public boolean stereotypePositionTop() {
		return skinParam.stereotypePositionTop();
	}

	public boolean useSwimlanes() {
		return skinParam.useSwimlanes();
	}

	public double getNodesep() {
		return skinParam.getNodesep();
	}

	public double getRanksep() {
		return skinParam.getRanksep();
	}

	public double getRoundCorner() {
		return skinParam.getRoundCorner();
	}

	public UStroke getThickness(LineParam param, Stereotype stereotype) {
		return skinParam.getThickness(param, stereotype);
	}

	public double maxMessageSize() {
		return skinParam.maxMessageSize();
	}

	public boolean strictUmlStyle() {
		return skinParam.strictUmlStyle();
	}

	public boolean forceSequenceParticipantUnderlined() {
		return skinParam.forceSequenceParticipantUnderlined();
	}

	public ConditionStyle getConditionStyle() {
		return skinParam.getConditionStyle();
	}

	public double minClassWidth() {
		return skinParam.minClassWidth();
	}

	public boolean sameClassWidth() {
		return skinParam.sameClassWidth();
	}

	public Rankdir getRankdir() {
		return skinParam.getRankdir();
	}
	
	public boolean useOctagonForActivity() {
		return skinParam.useOctagonForActivity();
	}


}
