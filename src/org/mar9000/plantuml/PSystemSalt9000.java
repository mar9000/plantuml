/**
 * This file is part of the PlantUML9000 project.
 * 
 * PlantUML9000 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * PlantUML9000 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with the PlantUML9000 project.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2014 Marco LOMBARDO, mar9000 near google.com .
 */
package org.mar9000.plantuml;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mar9000.salt.SaltCLI;
import org.mar9000.salt.SaltProcessor;

import net.sourceforge.plantuml.AbstractPSystem;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.core.DiagramDescription;
import net.sourceforge.plantuml.core.DiagramDescriptionImpl;
import net.sourceforge.plantuml.core.ImageData;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.graphic.UDrawable;
import net.sourceforge.plantuml.ugraphic.ColorMapperIdentity;
import net.sourceforge.plantuml.ugraphic.ImageBuilder;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UImage;

public class PSystemSalt9000 extends AbstractPSystem {

	private final List<String> data;

	@Deprecated
	public PSystemSalt9000(List<String> data) {
		this.data = data;
	}

	public PSystemSalt9000() {
		this(new ArrayList<String>());
	}

	public void add(String s) {
		data.add(s);
	}

	public ImageData exportDiagram(OutputStream os, int num, FileFormatOption fileFormat) throws IOException {
		// Copy data to diagramString.
		StringBuffer diagramString = new StringBuffer();
		Iterator<String> iter = data.iterator();
		while (iter.hasNext()) {
			diagramString.append(iter.next()).append("\n");
		}
		// Process diagramString with Salt9000.
		SaltProcessor salt = new SaltProcessor(diagramString.toString());
		salt.setLookAndFeel(SaltProcessor.JGOODIES_PLASTIC_LOOKANDFEEL);
		final BufferedImage image = salt.getImage();
		final ImageBuilder builder = new ImageBuilder(new ColorMapperIdentity(), 1.0, HtmlColorUtils.WHITE, null,
				null, 5, 5, null);
		builder.addUDrawable(new UDrawable() {
			public void drawU(UGraphic ug) {
				ug = ug.apply(new UChangeColor(HtmlColorUtils.BLACK));
				ug.draw(new UImage(image));
			}
		});
		return builder.writeImageTOBEMOVED(fileFormat.getFileFormat(), os);
	}

	public DiagramDescription getDescription() {
		return new DiagramDescriptionImpl("(Salt9000)", getClass());
	}

}
