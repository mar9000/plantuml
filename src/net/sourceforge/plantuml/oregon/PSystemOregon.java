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
package net.sourceforge.plantuml.oregon;

import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.plantuml.AbstractPSystem;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.core.DiagramDescription;
import net.sourceforge.plantuml.core.DiagramDescriptionImpl;
import net.sourceforge.plantuml.core.ImageData;
import net.sourceforge.plantuml.graphic.GraphicStrings;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.ugraphic.ColorMapperIdentity;
import net.sourceforge.plantuml.ugraphic.ImageBuilder;
import net.sourceforge.plantuml.ugraphic.UAntiAliasing;
import net.sourceforge.plantuml.ugraphic.UFont;
import net.sourceforge.plantuml.utils.StringUtils;

public class PSystemOregon extends AbstractPSystem {

	private Screen screen;
	private List<String> inputs;

	@Deprecated
	public PSystemOregon(Keyboard keyboard) {
		final BasicGame game = new OregonBasicGame();
		try {
			game.run(keyboard);
			this.screen = game.getScreen();
			// this.screen = new Screen();
			// screen.print("Game ended??");
		} catch (NoInputException e) {
			this.screen = game.getScreen();
		}
	}

	public PSystemOregon() {
		this.inputs = new ArrayList<String>();
	}

	public void add(String line) {
		if (StringUtils.isNotEmpty(line)) {
			inputs.add(line);
		}
	}

	private Screen getScreen() {
		if (screen == null) {
			final Keyboard keyboard = new KeyboardList(inputs);
			final BasicGame game = new OregonBasicGame();
			try {
				game.run(keyboard);
				this.screen = game.getScreen();
				// this.screen = new Screen();
				// screen.print("Game ended??");
			} catch (NoInputException e) {
				this.screen = game.getScreen();
			}
		}
		return screen;
	}

	public ImageData exportDiagram(OutputStream os, int num, FileFormatOption fileFormat) throws IOException {
		final GraphicStrings result = getGraphicStrings();
		final ImageBuilder imageBuilder = new ImageBuilder(new ColorMapperIdentity(), 1.0, result.getBackcolor(),
				getMetadata(), null, 0, 0, null);
		imageBuilder.addUDrawable(result);
		return imageBuilder.writeImageTOBEMOVED(fileFormat.getFileFormat(), os);
	}

	private GraphicStrings getGraphicStrings() throws IOException {
		final UFont font = new UFont("Monospaced", Font.PLAIN, 14);
		return new GraphicStrings(getScreen().getLines(), font, HtmlColorUtils.GREEN, HtmlColorUtils.BLACK,
				UAntiAliasing.ANTI_ALIASING_OFF);
	}

	public DiagramDescription getDescription() {
		return new DiagramDescriptionImpl("(The Oregon Trail)", getClass());
	}

}
