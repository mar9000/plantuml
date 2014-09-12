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
package net.sourceforge.plantuml.activitydiagram3.ftile.vcompact;

import java.util.List;

import net.sourceforge.plantuml.ColorParam;
import net.sourceforge.plantuml.FontParam;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.activitydiagram3.Branch;
import net.sourceforge.plantuml.activitydiagram3.ftile.Ftile;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileFactory;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileFactoryDelegator;
import net.sourceforge.plantuml.activitydiagram3.ftile.Swimlane;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.svek.ConditionStyle;
import net.sourceforge.plantuml.ugraphic.UFont;

public class FtileFactoryDelegatorIf extends FtileFactoryDelegator {

	public FtileFactoryDelegatorIf(FtileFactory factory, ISkinParam skinParam) {
		super(factory, skinParam);
	}

	@Override
	public Ftile createIf(Swimlane swimlane, List<Branch> thens, Branch elseBranch) {

		final ConditionStyle conditionStyle = getSkinParam().getConditionStyle();
		final UFont fontArrow = getSkinParam().getFont(FontParam.ACTIVITY_ARROW, null);
		final UFont fontTest = getSkinParam().getFont(
				conditionStyle == ConditionStyle.INSIDE ? FontParam.ACTIVITY_DIAMOND : FontParam.ACTIVITY_ARROW, null);
		final Branch branch0 = thens.get(0);

		final HtmlColor borderColor = getRose().getHtmlColor(getSkinParam(), ColorParam.activityBorder);
		final HtmlColor backColor = branch0.getColor() == null ? getRose().getHtmlColor(getSkinParam(),
				ColorParam.activityBackground) : branch0.getColor();
		final HtmlColor arrowColor = getRose().getHtmlColor(getSkinParam(), ColorParam.activityArrow);

		if (thens.size() > 1) {
			return FtileIfLong.create(swimlane, borderColor, backColor, fontArrow, arrowColor, getFactory(),
					conditionStyle, thens, elseBranch);
		}
		return FtileIf.create(swimlane, borderColor, backColor, fontArrow, fontTest, arrowColor, getFactory(),
				conditionStyle, thens.get(0), elseBranch, getSkinParam(), getStringBounder());
	}

}
