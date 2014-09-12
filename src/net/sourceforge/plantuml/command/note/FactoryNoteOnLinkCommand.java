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
package net.sourceforge.plantuml.command.note;

import java.util.List;

import net.sourceforge.plantuml.StringUtils;
import net.sourceforge.plantuml.Url;
import net.sourceforge.plantuml.UrlBuilder;
import net.sourceforge.plantuml.UrlBuilder.ModeUrl;
import net.sourceforge.plantuml.command.Command;
import net.sourceforge.plantuml.command.CommandExecutionResult;
import net.sourceforge.plantuml.command.CommandMultilines2;
import net.sourceforge.plantuml.command.MultilinesStrategy;
import net.sourceforge.plantuml.command.Position;
import net.sourceforge.plantuml.command.SingleLineCommand2;
import net.sourceforge.plantuml.command.regex.RegexConcat;
import net.sourceforge.plantuml.command.regex.RegexLeaf;
import net.sourceforge.plantuml.command.regex.RegexResult;
import net.sourceforge.plantuml.cucadiagram.CucaDiagram;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;

public final class FactoryNoteOnLinkCommand implements SingleMultiFactoryCommand<CucaDiagram> {

	private RegexConcat getRegexConcatSingleLine() {
		return new RegexConcat(new RegexLeaf("^note[%s]+"), //
				new RegexLeaf("POSITION", "(right|left|top|bottom)?[%s]*on[%s]+link"), //
				new RegexLeaf("[%s]*"), //
				new RegexLeaf("COLOR", "(" + HtmlColorUtils.COLOR_REGEXP + ")?"), //
				new RegexLeaf("[%s]*:[%s]*"), //
				new RegexLeaf("NOTE", "(.*)"), //
				new RegexLeaf("$"));
	}

	private RegexConcat getRegexConcatMultiLine() {
		return new RegexConcat(new RegexLeaf("^note[%s]+"), //
				new RegexLeaf("POSITION", "(right|left|top|bottom)?[%s]*on[%s]+link"), //
				new RegexLeaf("[%s]*"), //
				new RegexLeaf("COLOR", "(" + HtmlColorUtils.COLOR_REGEXP + ")?"), //
				new RegexLeaf("$"));
	}

	public Command<CucaDiagram> createMultiLine() {
		return new CommandMultilines2<CucaDiagram>(getRegexConcatMultiLine(), MultilinesStrategy.KEEP_STARTING_QUOTE) {

			@Override
			public String getPatternEnd() {
				return "(?i)^end[%s]?note$";
			}

			public CommandExecutionResult executeNow(final CucaDiagram system, List<String> lines) {
				final List<String> strings = StringUtils.removeEmptyColumns(lines.subList(1, lines.size() - 1));
				if (strings.size() > 0) {
					final List<CharSequence> note = StringUtils.manageEmbededDiagrams2(strings);
					final RegexResult arg = getStartingPattern().matcher(lines.get(0));
					return executeInternal(system, note, arg);
				}
				return CommandExecutionResult.error("No note defined");
			}

		};
	}

	public Command<CucaDiagram> createSingleLine() {
		return new SingleLineCommand2<CucaDiagram>(getRegexConcatSingleLine()) {

			@Override
			protected CommandExecutionResult executeArg(final CucaDiagram system, RegexResult arg) {
				final List<String> note = StringUtils.getWithNewlines2(arg.get("NOTE", 0));
				return executeInternal(system, note, arg);
			}
		};
	}

	private CommandExecutionResult executeInternal(CucaDiagram system, List<? extends CharSequence> note,
			final RegexResult arg) {
		final Link link = system.getLastLink();
		if (link == null) {
			return CommandExecutionResult.error("No link defined");
		}
		Position position = Position.BOTTOM;
		if (arg.get("POSITION", 0) != null) {
			position = Position.valueOf(arg.get("POSITION", 0).toUpperCase());
		}
		Url url = null;
		if (note.size() > 0) {
			final UrlBuilder urlBuilder = new UrlBuilder(system.getSkinParam().getValue("topurl"), ModeUrl.STRICT);
			url = urlBuilder.getUrl(note.get(0).toString());
		}
		if (url != null) {
			note = note.subList(1, note.size());
		}
		link.addNote(Display.create(note), position, HtmlColorUtils.getColorIfValid(arg.get("COLOR", 0)));
		return CommandExecutionResult.ok();
	}

}
