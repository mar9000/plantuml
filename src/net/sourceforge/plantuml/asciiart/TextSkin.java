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
package net.sourceforge.plantuml.asciiart;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.skin.ArrowConfiguration;
import net.sourceforge.plantuml.skin.ArrowDirection;
import net.sourceforge.plantuml.skin.Component;
import net.sourceforge.plantuml.skin.ComponentType;
import net.sourceforge.plantuml.skin.Skin;
import net.sourceforge.plantuml.skin.rose.ComponentRoseGroupingSpace;

public class TextSkin implements Skin {

	private final FileFormat fileFormat;

	public TextSkin(FileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}

	public Component createComponent(ComponentType type, ArrowConfiguration config, ISkinParam param,
			Display stringsToDisplay) {
		if (type == ComponentType.PARTICIPANT_HEAD || type == ComponentType.PARTICIPANT_TAIL) {
			return new ComponentTextParticipant(type, stringsToDisplay, fileFormat);
		}
		if (type == ComponentType.ACTOR_HEAD || type == ComponentType.ACTOR_TAIL) {
			return new ComponentTextActor(type, stringsToDisplay, fileFormat);
		}
		if (type.isArrow()
				&& ((config.getArrowDirection() == ArrowDirection.LEFT_TO_RIGHT_NORMAL) || (config.getArrowDirection() == ArrowDirection.RIGHT_TO_LEFT_REVERSE))) {
			return new ComponentTextArrow(type, config, stringsToDisplay, fileFormat);
		}
		if (type.isArrow() && config.isSelfArrow()) {
			return new ComponentTextSelfArrow(type, config, stringsToDisplay, fileFormat);
		}
		if (type == ComponentType.PARTICIPANT_LINE) {
			return new ComponentTextLine(fileFormat);
		}
		if (type == ComponentType.CONTINUE_LINE) {
			return new ComponentTextLine(fileFormat);
		}
		if (type == ComponentType.DELAY_LINE) {
			return new ComponentTextLine(fileFormat);
		}
		if (type == ComponentType.ALIVE_BOX_CLOSE_CLOSE) {
			return new ComponentTextActiveLine(fileFormat);
		}
		if (type == ComponentType.ALIVE_BOX_CLOSE_OPEN) {
			return new ComponentTextActiveLine(fileFormat);
		}
		if (type == ComponentType.ALIVE_BOX_OPEN_CLOSE) {
			return new ComponentTextActiveLine(fileFormat);
		}
		if (type == ComponentType.ALIVE_BOX_OPEN_OPEN) {
			return new ComponentTextActiveLine(fileFormat);
		}
		if (type == ComponentType.NOTE) {
			return new ComponentTextNote(type, stringsToDisplay, fileFormat);
		}
		if (type == ComponentType.DIVIDER) {
			return new ComponentTextDivider(type, stringsToDisplay, fileFormat);
		}
		if (type == ComponentType.GROUPING_HEADER) {
			return new ComponentTextGroupingHeader(type, stringsToDisplay, fileFormat);
		}
		if (type == ComponentType.GROUPING_SPACE) {
			return new ComponentRoseGroupingSpace(1);
		}
		if (type == ComponentType.GROUPING_ELSE) {
			return new ComponentTextGroupingElse(type, stringsToDisplay, fileFormat);
		}
		if (type == ComponentType.NEWPAGE) {
			return new ComponentTextNewpage(fileFormat);
		}
		throw new UnsupportedOperationException(type.toString());
	}

	public Object getProtocolVersion() {
		return 1;
	}

}
