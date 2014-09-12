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

import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.Url;
import net.sourceforge.plantuml.UrlBuilder;
import net.sourceforge.plantuml.UrlBuilder.ModeUrl;
import net.sourceforge.plantuml.command.regex.MyPattern;

public class CommandCreoleUrl implements Command {

	private final Pattern pattern;
	private final ISkinSimple skinParam;

	public static Command create(ISkinSimple skinParam) {
		return new CommandCreoleUrl(skinParam, "^(" + UrlBuilder.getRegexp() + ")");
	}

	private CommandCreoleUrl(ISkinSimple skinParam, String p) {
		this.pattern = MyPattern.cmpile(p);
		this.skinParam = skinParam;

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
		final UrlBuilder urlBuilder = new UrlBuilder(skinParam.getValue("topurl"), ModeUrl.STRICT);
		final Url url = urlBuilder.getUrl(m.group(1));
		stripe.addUrl(url);

//		final int size = Integer.parseInt(m.group(2));
//		final FontConfiguration fc1 = stripe.getActualFontConfiguration();
//		final FontConfiguration fc2 = fc1.changeSize(size);
//		// final FontConfiguration fc2 = new AddStyle(style, null).apply(fc1);
//		stripe.setActualFontConfiguration(fc2);
		// stripe.analyzeAndAdd("AZERTY");
//		stripe.setActualFontConfiguration(fc1);
		return line.substring(m.group(1).length());
	}
}
