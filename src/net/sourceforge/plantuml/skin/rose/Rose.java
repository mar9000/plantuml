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

import net.sourceforge.plantuml.AlignParam;
import net.sourceforge.plantuml.ColorParam;
import net.sourceforge.plantuml.FontParam;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.LineParam;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.cucadiagram.Stereotype;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.skin.ArrowConfiguration;
import net.sourceforge.plantuml.skin.ArrowDirection;
import net.sourceforge.plantuml.skin.Component;
import net.sourceforge.plantuml.skin.ComponentType;
import net.sourceforge.plantuml.skin.Skin;
import net.sourceforge.plantuml.ugraphic.UFont;
import net.sourceforge.plantuml.ugraphic.UStroke;

public class Rose implements Skin {

	final private double paddingX = 5;
	final private double paddingY = 5;

	public HtmlColor getFontColor(ISkinParam skin, FontParam fontParam) {
		return skin.getFontHtmlColor(fontParam, null);
	}

	public HtmlColor getHtmlColor(ISkinParam param, ColorParam color) {
		return getHtmlColor(param, color, null);
	}

	public HtmlColor getHtmlColor(ISkinParam param, ColorParam color, Stereotype stereotype) {
		HtmlColor result = param.getHtmlColor(color, stereotype, false);
		if (result == null) {
			result = color.getDefaultValue();
			if (result == null) {
				throw new IllegalArgumentException();
			}
		}
		return result;
	}

	public Component createComponent(ComponentType type, ArrowConfiguration config, ISkinParam param,
			Display stringsToDisplay) {
		final HtmlColor background = param.getBackgroundColor();
		final HtmlColor hyperlinkColor = param.getHyperlinkColor();
		final HtmlColor groupBorder = getHtmlColor(param, ColorParam.sequenceGroupBorder);
		final HtmlColor groupBackground = getHtmlColor(param, ColorParam.sequenceGroupBackground);
		final HtmlColor sequenceDividerBackground = getHtmlColor(param, ColorParam.sequenceDividerBackground);
		final HtmlColor sequenceReferenceBackground = getHtmlColor(param, ColorParam.sequenceReferenceBackground);
		final HtmlColor sequenceReferenceHeaderBackground = getHtmlColor(param,
				ColorParam.sequenceReferenceHeaderBackground);
		final HtmlColor sequenceReferenceBorder = getHtmlColor(param, ColorParam.sequenceReferenceBorder);
		final HtmlColor lifeLineBackgroundColor = getHtmlColor(param, ColorParam.sequenceLifeLineBackground);
		final HtmlColor sequenceActorBackground = getHtmlColor(param, ColorParam.actorBackground);
		final HtmlColor sequenceParticipantBackground = getHtmlColor(param, ColorParam.participantBackground);

		final UFont fontArrow = param.getFont(FontParam.SEQUENCE_ARROW, null);
		final UFont fontGrouping = param.getFont(FontParam.SEQUENCE_GROUP, null);
		final UFont fontParticipant = param.getFont(FontParam.PARTICIPANT, null);
		final UFont fontActor = param.getFont(FontParam.ACTOR, null);

		final double deltaShadow = param.shadowing() ? 4.0 : 0;

		if (type.isArrow()) {
			// if (param.maxMessageSize() > 0) {
			// final FontConfiguration fc = new FontConfiguration(fontArrow, HtmlColorUtils.BLACK);
			// stringsToDisplay = DisplayUtils.breakLines(stringsToDisplay, fc, param, param.maxMessageSize());
			// }
			final HtmlColor sequenceArrow = config.getColor() == null ? getHtmlColor(param, ColorParam.sequenceArrow)
					: config.getColor();
			if (config.getArrowDirection() == ArrowDirection.SELF) {
				return new ComponentRoseSelfArrow(sequenceArrow, getFontColor(param, FontParam.SEQUENCE_ARROW),
						hyperlinkColor, fontArrow, stringsToDisplay, config, param, param.maxMessageSize(),
						param.strictUmlStyle() == false);
			}
			final HorizontalAlignment messageHorizontalAlignment = param
					.getHorizontalAlignment(AlignParam.SEQUENCE_MESSAGE_ALIGN);
			final HorizontalAlignment textHorizontalAlignment = param
					.getHorizontalAlignment(AlignParam.SEQUENCE_MESSAGETEXT_ALIGN);
			return new ComponentRoseArrow(sequenceArrow, getFontColor(param, FontParam.SEQUENCE_ARROW), hyperlinkColor,
					fontArrow, stringsToDisplay, config, messageHorizontalAlignment, param, textHorizontalAlignment,
					param.maxMessageSize(), param.strictUmlStyle() == false);
		}
		if (type == ComponentType.PARTICIPANT_HEAD) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.participantBorder);
			return new ComponentRoseParticipant(sequenceParticipantBackground, borderColor, getFontColor(param,
					FontParam.PARTICIPANT), hyperlinkColor, fontParticipant, stringsToDisplay, param, deltaShadow,
					param.getRoundCorner(), getStroke(param, LineParam.sequenceParticipantBorder, 1.5));
		}
		if (type == ComponentType.PARTICIPANT_TAIL) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.participantBorder);
			return new ComponentRoseParticipant(sequenceParticipantBackground, borderColor, getFontColor(param,
					FontParam.PARTICIPANT), hyperlinkColor, fontParticipant, stringsToDisplay, param, deltaShadow,
					param.getRoundCorner(), getStroke(param, LineParam.sequenceParticipantBorder, 1.5));
		}
		if (type == ComponentType.PARTICIPANT_LINE) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.sequenceLifeLineBorder);
			return new ComponentRoseLine(borderColor, false, getStroke(param, LineParam.sequenceLifeLineBorder, 1));
		}
		if (type == ComponentType.CONTINUE_LINE) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.sequenceLifeLineBorder);
			return new ComponentRoseLine(borderColor, true, getStroke(param, LineParam.sequenceLifeLineBorder, 1.5));
		}
		if (type == ComponentType.ACTOR_HEAD) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.actorBorder);
			return new ComponentRoseActor(sequenceActorBackground, borderColor, getFontColor(param, FontParam.ACTOR),
					hyperlinkColor, fontActor, stringsToDisplay, true, param, deltaShadow, getStroke(param,
							LineParam.sequenceActorBorder, 2));
		}
		if (type == ComponentType.ACTOR_TAIL) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.actorBorder);
			return new ComponentRoseActor(sequenceActorBackground, borderColor, getFontColor(param, FontParam.ACTOR),
					hyperlinkColor, fontActor, stringsToDisplay, false, param, deltaShadow, getStroke(param,
							LineParam.sequenceActorBorder, 2));
		}
		if (type == ComponentType.BOUNDARY_HEAD) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.actorBorder);
			return new ComponentRoseBoundary(sequenceActorBackground, borderColor,
					getFontColor(param, FontParam.ACTOR), hyperlinkColor, fontActor, stringsToDisplay, true, param,
					deltaShadow, getStroke(param, LineParam.sequenceActorBorder, 2));
		}
		if (type == ComponentType.BOUNDARY_TAIL) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.actorBorder);
			return new ComponentRoseBoundary(sequenceActorBackground, borderColor,
					getFontColor(param, FontParam.ACTOR), hyperlinkColor, fontActor, stringsToDisplay, false, param,
					deltaShadow, getStroke(param, LineParam.sequenceActorBorder, 2));
		}
		if (type == ComponentType.CONTROL_HEAD) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.actorBorder);
			return new ComponentRoseControl(sequenceActorBackground, borderColor, getFontColor(param, FontParam.ACTOR),
					hyperlinkColor, fontActor, stringsToDisplay, true, param, deltaShadow, getStroke(param,
							LineParam.sequenceActorBorder, 2));
		}
		if (type == ComponentType.CONTROL_TAIL) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.actorBorder);
			return new ComponentRoseControl(sequenceActorBackground, borderColor, getFontColor(param, FontParam.ACTOR),
					hyperlinkColor, fontActor, stringsToDisplay, false, param, deltaShadow, getStroke(param,
							LineParam.sequenceActorBorder, 2));
		}
		if (type == ComponentType.ENTITY_HEAD) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.actorBorder);
			return new ComponentRoseEntity(sequenceActorBackground, borderColor, getFontColor(param, FontParam.ACTOR),
					hyperlinkColor, fontActor, stringsToDisplay, true, param, deltaShadow, getStroke(param,
							LineParam.sequenceActorBorder, 2));
		}
		if (type == ComponentType.ENTITY_TAIL) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.actorBorder);
			return new ComponentRoseEntity(sequenceActorBackground, borderColor, getFontColor(param, FontParam.ACTOR),
					hyperlinkColor, fontActor, stringsToDisplay, false, param, deltaShadow, getStroke(param,
							LineParam.sequenceActorBorder, 2));
		}
		if (type == ComponentType.DATABASE_HEAD) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.actorBorder);
			return new ComponentRoseDatabase(sequenceActorBackground, borderColor,
					getFontColor(param, FontParam.ACTOR), hyperlinkColor, fontActor, stringsToDisplay, true, param,
					deltaShadow, getStroke(param, LineParam.sequenceActorBorder, 2));
		}
		if (type == ComponentType.DATABASE_TAIL) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.actorBorder);
			return new ComponentRoseDatabase(sequenceActorBackground, borderColor,
					getFontColor(param, FontParam.ACTOR), hyperlinkColor, fontActor, stringsToDisplay, false, param,
					deltaShadow, getStroke(param, LineParam.sequenceActorBorder, 2));
		}
		if (type == ComponentType.NOTE) {
			final HtmlColor noteBackgroundColor = getHtmlColor(param, ColorParam.noteBackground);
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.noteBorder);
			final UFont fontNote = param.getFont(FontParam.NOTE, null);
			return new ComponentRoseNote(noteBackgroundColor, borderColor, getFontColor(param, FontParam.NOTE),
					hyperlinkColor, fontNote, stringsToDisplay, paddingX, paddingY, param, deltaShadow, getStroke(
							param, LineParam.noteBorder, 1));
		}
		if (type == ComponentType.NOTE_HEXAGONAL) {
			final HtmlColor noteBackgroundColor = getHtmlColor(param, ColorParam.noteBackground);
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.noteBorder);
			final UFont fontNote = param.getFont(FontParam.NOTE, null);
			return new ComponentRoseNoteHexagonal(noteBackgroundColor, borderColor,
					getFontColor(param, FontParam.NOTE), hyperlinkColor, fontNote, stringsToDisplay, param,
					deltaShadow, getStroke(param, LineParam.noteBorder, 1));
		}
		if (type == ComponentType.NOTE_BOX) {
			final HtmlColor noteBackgroundColor = getHtmlColor(param, ColorParam.noteBackground);
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.noteBorder);
			final UFont fontNote = param.getFont(FontParam.NOTE, null);
			return new ComponentRoseNoteBox(noteBackgroundColor, borderColor, getFontColor(param, FontParam.NOTE),
					hyperlinkColor, fontNote, stringsToDisplay, param, deltaShadow, getStroke(param,
							LineParam.noteBorder, 1));
		}
		if (type == ComponentType.GROUPING_HEADER) {
			final UFont fontGroupingHeader = param.getFont(FontParam.SEQUENCE_GROUP_HEADER, null);
			return new ComponentRoseGroupingHeader(getFontColor(param, FontParam.SEQUENCE_GROUP_HEADER),
					hyperlinkColor, background, groupBackground, groupBorder, fontGroupingHeader, fontGrouping,
					stringsToDisplay, param, deltaShadow, getStroke(param, LineParam.sequenceGroupBorder, 2));
		}
		if (type == ComponentType.GROUPING_ELSE) {
			return new ComponentRoseGroupingElse(getFontColor(param, FontParam.SEQUENCE_GROUP), hyperlinkColor,
					groupBorder, fontGrouping, stringsToDisplay.get(0), param, background, getStroke(param,
							LineParam.sequenceGroupBorder, 2));
		}
		if (type == ComponentType.GROUPING_SPACE) {
			return new ComponentRoseGroupingSpace(7);
		}
		if (type == ComponentType.ALIVE_BOX_CLOSE_CLOSE) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.sequenceLifeLineBorder);
			return new ComponentRoseActiveLine(borderColor, lifeLineBackgroundColor, true, true, deltaShadow > 0);
		}
		if (type == ComponentType.ALIVE_BOX_CLOSE_OPEN) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.sequenceLifeLineBorder);
			return new ComponentRoseActiveLine(borderColor, lifeLineBackgroundColor, true, false, deltaShadow > 0);
		}
		if (type == ComponentType.ALIVE_BOX_OPEN_CLOSE) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.sequenceLifeLineBorder);
			return new ComponentRoseActiveLine(borderColor, lifeLineBackgroundColor, false, true, deltaShadow > 0);
		}
		if (type == ComponentType.ALIVE_BOX_OPEN_OPEN) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.sequenceLifeLineBorder);
			return new ComponentRoseActiveLine(borderColor, lifeLineBackgroundColor, false, false, deltaShadow > 0);
		}
		if (type == ComponentType.DELAY_LINE) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.sequenceLifeLineBorder);
			return new ComponentRoseDelayLine(borderColor);
		}
		if (type == ComponentType.DELAY_TEXT) {
			return new ComponentRoseDelayText(getFontColor(param, FontParam.SEQUENCE_DELAY), hyperlinkColor,
					param.getFont(FontParam.SEQUENCE_DELAY, null), stringsToDisplay, param);
		}
		if (type == ComponentType.DESTROY) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.sequenceLifeLineBorder);
			return new ComponentRoseDestroy(borderColor);
		}
		if (type == ComponentType.NEWPAGE) {
			return new ComponentRoseNewpage(getFontColor(param, FontParam.SEQUENCE_GROUP));
		}
		if (type == ComponentType.DIVIDER) {
			return new ComponentRoseDivider(getFontColor(param, FontParam.SEQUENCE_DIVIDER), hyperlinkColor,
					param.getFont(FontParam.SEQUENCE_DIVIDER, null), sequenceDividerBackground, stringsToDisplay,
					param, deltaShadow > 0, getStroke(param, LineParam.sequenceDividerBorder, 2));
		}
		if (type == ComponentType.REFERENCE) {
			final UFont fontGroupingHeader = param.getFont(FontParam.SEQUENCE_GROUP_HEADER, null);
			return new ComponentRoseReference(getFontColor(param, FontParam.SEQUENCE_REFERENCE), hyperlinkColor,
					getFontColor(param, FontParam.SEQUENCE_GROUP), param.getFont(FontParam.SEQUENCE_REFERENCE, null),
					sequenceReferenceBorder, sequenceReferenceHeaderBackground, sequenceReferenceBackground,
					fontGroupingHeader, stringsToDisplay,
					param.getHorizontalAlignment(AlignParam.SEQUENCE_REFERENCE_ALIGN), param, deltaShadow, getStroke(
							param, LineParam.sequenceReferenceBorder, 2));
		}
		if (type == ComponentType.TITLE) {
			return new ComponentRoseTitle(getFontColor(param, FontParam.SEQUENCE_TITLE), hyperlinkColor, param.getFont(
					FontParam.SEQUENCE_TITLE, null), stringsToDisplay, param);
		}
		if (type == ComponentType.SIGNATURE) {
			return new ComponentRoseTitle(HtmlColorUtils.BLACK, hyperlinkColor, fontGrouping, Display.create(
					"This skin was created ", "in April 2009."), param);
		}
		if (type == ComponentType.ENGLOBER) {
			final HtmlColor borderColor = getHtmlColor(param, ColorParam.sequenceBoxBorder);
			final HtmlColor backColor = getHtmlColor(param, ColorParam.sequenceBoxBackground);
			return new ComponentRoseEnglober(borderColor, backColor, stringsToDisplay, getFontColor(param,
					FontParam.SEQUENCE_BOX), hyperlinkColor, param.getFont(FontParam.SEQUENCE_BOX, null), param);
		}

		return null;
	}

	static public UStroke getStroke(ISkinParam param, LineParam lineParam, double defaultValue) {
		final UStroke result = param.getThickness(lineParam, null);
		if (result == null) {
			return new UStroke(defaultValue);
		}
		return result;
	}

	public Object getProtocolVersion() {
		return 1;
	}

}
