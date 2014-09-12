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
package net.sourceforge.plantuml.creole;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.plantuml.command.regex.MyPattern;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;

public class CommandCreoleColorAndSizeChange implements Command {

	private final Pattern pattern;

	public static final String fontPattern = "\\<font(?:[%s]+size[%s]*=[%s]*[%g]?(\\d+)[%g]?|[%s]+color[%s]*=[%s]*[%g]?(#[0-9a-fA-F]{6}|\\w+)[%g]?)+[%s]*\\>";

	public static Command create() {
		return new CommandCreoleColorAndSizeChange("^(?i)(" + fontPattern + "(.*?)\\</font\\>)");
	}

	public static Command createEol() {
		return new CommandCreoleColorAndSizeChange("^(?i)(" + fontPattern + "(.*))$");
	}

	private CommandCreoleColorAndSizeChange(String p) {
		this.pattern = MyPattern.cmpile(p);

	}

	public int matchingSize(String line) {
		final Matcher m = pattern.matcher(line);
		if (m.find() == false) {
			return 0;
		}
		return m.group(1).length();
	}

	public String executeAndGetRemaining(String line, StripeSimple stripe) {
		final Matcher m = pattern.matcher(line);
		if (m.find() == false) {
			throw new IllegalStateException();
		}
		// for (int i = 1; i <= m.groupCount(); i++) {
		// System.err.println("i=" + i + " " + m.group(i));
		// }

		final FontConfiguration fc1 = stripe.getActualFontConfiguration();
		FontConfiguration fc2 = fc1;
		if (m.group(2) != null) {
			fc2 = fc2.changeSize(Integer.parseInt(m.group(2)));
		}
		if (m.group(3) != null) {
			final HtmlColor color = HtmlColorUtils.getColorIfValid(m.group(3));
			fc2 = fc2.changeColor(color);
		}

		stripe.setActualFontConfiguration(fc2);
		stripe.analyzeAndAdd(m.group(4));
		stripe.setActualFontConfiguration(fc1);
		return line.substring(m.group(1).length());
	}
}
