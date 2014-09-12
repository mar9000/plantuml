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

import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.sourceforge.plantuml.SpriteContainerEmpty;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.ugraphic.UFont;

public class DateEventUtils {

	public static TextBlock addEvent(TextBlock textBlock, HtmlColor color) {
		final DateFormat dateFormat = new SimpleDateFormat("MM-dd");
		final String today = dateFormat.format(new Date());

		if ("11-05".equals(today)) {
			final List<String> asList = Arrays.asList("<u>November 5th, 1955",
					"Doc Brown's discovery of the Flux Capacitor, that makes time-travel possible.");
			return TextBlockUtils.mergeTB(textBlock, getComment(asList, color), HorizontalAlignment.LEFT);
		} else if ("08-29".equals(today)) {
			final List<String> asList = Arrays.asList("<u>August 29th, 1997",
					"Skynet becomes self-aware at 02:14 AM Eastern Time.");
			return TextBlockUtils.mergeTB(textBlock, getComment(asList, color), HorizontalAlignment.LEFT);
		} else if ("06-29".equals(today)) {
			final List<String> asList = Arrays.asList("<u>June 29th, 1975",
					"\"It was the first time in history that anyone had typed",
					"a character on a keyboard and seen it show up on their",
					"own computer's screen right in front of them.\"", "\t\t\t\t\t\t\t\t\t\t<i>Steve Wozniak");
			return TextBlockUtils.mergeTB(textBlock, getComment(asList, color), HorizontalAlignment.LEFT);
		}

		return textBlock;
	}

	private static TextBlock getComment(final List<String> asList, HtmlColor color) {
		final UFont font = new UFont("SansSerif", Font.BOLD, 14);
		TextBlock comment = TextBlockUtils.create(Display.create(asList), new FontConfiguration(font,
				color, HtmlColorUtils.BLUE), HorizontalAlignment.LEFT, new SpriteContainerEmpty());
		comment = TextBlockUtils.withMargin(comment, 4, 4);
		comment = new TextBlockBordered(comment, color);
		comment = TextBlockUtils.withMargin(comment, 10, 10);
		return comment;
	}
}
