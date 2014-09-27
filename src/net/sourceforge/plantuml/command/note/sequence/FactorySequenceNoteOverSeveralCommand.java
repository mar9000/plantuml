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
package net.sourceforge.plantuml.command.note.sequence;

import java.util.List;

import net.sourceforge.plantuml.command.Command;
import net.sourceforge.plantuml.command.CommandExecutionResult;
import net.sourceforge.plantuml.command.CommandMultilines2;
import net.sourceforge.plantuml.command.MultilinesStrategy;
import net.sourceforge.plantuml.command.SingleLineCommand2;
import net.sourceforge.plantuml.command.note.SingleMultiFactoryCommand;
import net.sourceforge.plantuml.command.regex.RegexConcat;
import net.sourceforge.plantuml.command.regex.RegexLeaf;
import net.sourceforge.plantuml.command.regex.RegexResult;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.sequencediagram.Note;
import net.sourceforge.plantuml.sequencediagram.NoteStyle;
import net.sourceforge.plantuml.sequencediagram.Participant;
import net.sourceforge.plantuml.sequencediagram.SequenceDiagram;
import net.sourceforge.plantuml.utils.StringUtils;

public final class FactorySequenceNoteOverSeveralCommand implements SingleMultiFactoryCommand<SequenceDiagram> {

	private RegexConcat getRegexConcatMultiLine() {
		return new RegexConcat( //
				new RegexLeaf("^"), //
				new RegexLeaf("VMERGE", "(/)?[%s]*"), //
				new RegexLeaf("STYLE", "(note|hnote|rnote)[%s]+over[%s]+"), //
				new RegexLeaf("P1", "([\\p{L}0-9_.@]+|[%g][^%g]+[%g])[%s]*\\,[%s]*"), //
				new RegexLeaf("P2", "([\\p{L}0-9_.@]+|[%g][^%g]+[%g])[%s]*"), //
				new RegexLeaf("COLOR", "(" + HtmlColorUtils.COLOR_REGEXP + ")?"), //
				new RegexLeaf("$") //
		);
	}

	private RegexConcat getRegexConcatSingleLine() {
		return new RegexConcat( //
				new RegexLeaf("^"), //
				new RegexLeaf("VMERGE", "(/)?[%s]*"), //
				new RegexLeaf("STYLE", "(note|hnote|rnote)[%s]+over[%s]+"), //
				new RegexLeaf("P1", "([\\p{L}0-9_.@]+|[%g][^%g]+[%g])[%s]*\\,[%s]*"), //
				new RegexLeaf("P2", "([\\p{L}0-9_.@]+|[%g][^%g]+[%g])[%s]*"), //
				new RegexLeaf("COLOR", "(" + HtmlColorUtils.COLOR_REGEXP + ")?"), //
				new RegexLeaf("[%s]*:[%s]*"), //
				new RegexLeaf("NOTE", "(.*)"), //
				new RegexLeaf("$"));
	}

	public Command<SequenceDiagram> createSingleLine() {
		return new SingleLineCommand2<SequenceDiagram>(getRegexConcatSingleLine()) {

			@Override
			protected CommandExecutionResult executeArg(final SequenceDiagram system, RegexResult arg) {
				final List<String> strings = StringUtils.getWithNewlines2(arg.get("NOTE", 0));

				return executeInternal(system, arg, strings);
			}

		};
	}

	public Command<SequenceDiagram> createMultiLine() {
		return new CommandMultilines2<SequenceDiagram>(getRegexConcatMultiLine(),
				MultilinesStrategy.KEEP_STARTING_QUOTE) {

			@Override
			public String getPatternEnd() {
				return "(?i)^end[%s]?(note|hnote|rnote)$";
			}

			public CommandExecutionResult executeNow(final SequenceDiagram system, List<String> lines) {
				final RegexResult line0 = getStartingPattern().matcher(lines.get(0).trim());
				final List<String> strings = StringUtils.removeEmptyColumns(lines.subList(1, lines.size() - 1));

				return executeInternal(system, line0, strings);
			}

		};
	}

	private CommandExecutionResult executeInternal(SequenceDiagram diagram, final RegexResult line0,
			final List<String> strings) {
		final Participant p1 = diagram.getOrCreateParticipant(StringUtils
				.eventuallyRemoveStartingAndEndingDoubleQuote(line0.get("P1", 0)));
		final Participant p2 = diagram.getOrCreateParticipant(StringUtils
				.eventuallyRemoveStartingAndEndingDoubleQuote(line0.get("P2", 0)));

		if (strings.size() > 0) {
			final boolean tryMerge = line0.get("VMERGE", 0) != null;
			final Note note = new Note(p1, p2, Display.create(strings));
			note.setSpecificBackcolor(diagram.getSkinParam().getIHtmlColorSet().getColorIfValid(line0.get("COLOR", 0)));
			note.setStyle(NoteStyle.getNoteStyle(line0.get("STYLE", 0)));
			diagram.addNote(note, tryMerge);
		}
		return CommandExecutionResult.ok();
	}

}
