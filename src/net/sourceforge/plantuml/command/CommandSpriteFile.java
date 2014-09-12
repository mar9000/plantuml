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
package net.sourceforge.plantuml.command;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.plantuml.FileSystem;
import net.sourceforge.plantuml.Log;
import net.sourceforge.plantuml.UmlDiagram;
import net.sourceforge.plantuml.command.regex.RegexConcat;
import net.sourceforge.plantuml.command.regex.RegexLeaf;
import net.sourceforge.plantuml.command.regex.RegexResult;
import net.sourceforge.plantuml.ugraphic.SpriteImage;

public class CommandSpriteFile extends SingleLineCommand2<UmlDiagram> {

	public CommandSpriteFile() {
		super(getRegexConcat());
	}

	private static RegexConcat getRegexConcat() {
		return new RegexConcat(new RegexLeaf("^"), //
				new RegexLeaf("sprite[%s]+\\$?"), //
				new RegexLeaf("NAME", "([\\p{L}0-9_]+)[%s]*"), //
				new RegexLeaf("[%s]+"), //
				new RegexLeaf("FILE", "(.*)"), //
				new RegexLeaf("$"));
	}

	@Override
	protected CommandExecutionResult executeArg(UmlDiagram system, RegexResult arg) {
		final String src = arg.get("FILE", 0);
		final BufferedImage im;
		try {
			final File f = FileSystem.getInstance().getFile(src);
			if (f.exists() == false) {
				return CommandExecutionResult.error("File does not exist: " + src);
			}
			im = ImageIO.read(f);
		} catch (IOException e) {
			Log.error("Error reading " + src + " " + e);
			return CommandExecutionResult.error("Cannot read: " + src);
		}
		system.addSprite(arg.get("NAME", 0), new SpriteImage(im));
		return CommandExecutionResult.ok();
	}

}
