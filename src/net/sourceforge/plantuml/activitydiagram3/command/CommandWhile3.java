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
package net.sourceforge.plantuml.activitydiagram3.command;

import net.sourceforge.plantuml.activitydiagram3.ActivityDiagram3;
import net.sourceforge.plantuml.command.CommandExecutionResult;
import net.sourceforge.plantuml.command.SingleLineCommand2;
import net.sourceforge.plantuml.command.regex.RegexConcat;
import net.sourceforge.plantuml.command.regex.RegexLeaf;
import net.sourceforge.plantuml.command.regex.RegexOptional;
import net.sourceforge.plantuml.command.regex.RegexResult;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorSet;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;

public class CommandWhile3 extends SingleLineCommand2<ActivityDiagram3> {

	public CommandWhile3() {
		super(getRegexConcat());
	}

	static RegexConcat getRegexConcat() {
		return new RegexConcat(//
				new RegexLeaf("^"), //
				new RegexLeaf("COLOR", "(?:(" + HtmlColorUtils.COLOR_REGEXP + "):)?"), //
				new RegexLeaf("while"), //
				new RegexLeaf("TEST", "[%s]*\\((.+?)\\)"), //
				new RegexOptional(new RegexConcat(//
						new RegexLeaf("[%s]*(is|equals?)[%s]*"), //
						new RegexLeaf("YES", "\\((.+?)\\)"))), //
				new RegexLeaf(";?$"));
	}

	@Override
	protected CommandExecutionResult executeArg(ActivityDiagram3 diagram, RegexResult arg) {
		final HtmlColor color = diagram.getSkinParam().getIHtmlColorSet().getColorIfValid(arg.get("COLOR", 0));
		diagram.doWhile(Display.getWithNewlines(arg.get("TEST", 0)), Display.getWithNewlines(arg.get("YES", 0)), color);

		return CommandExecutionResult.ok();
	}

}
