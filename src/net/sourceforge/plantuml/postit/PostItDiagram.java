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
package net.sourceforge.plantuml.postit;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.plantuml.EmptyImageBuilder;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.UmlDiagram;
import net.sourceforge.plantuml.UmlDiagramType;
import net.sourceforge.plantuml.api.ImageDataSimple;
import net.sourceforge.plantuml.core.DiagramDescription;
import net.sourceforge.plantuml.core.DiagramDescriptionImpl;
import net.sourceforge.plantuml.core.ImageData;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.png.PngIO;
import net.sourceforge.plantuml.ugraphic.ColorMapperIdentity;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.eps.UGraphicEps;
import net.sourceforge.plantuml.ugraphic.g2d.UGraphicG2d;
import net.sourceforge.plantuml.ugraphic.svg.UGraphicSvg;

public class PostItDiagram extends UmlDiagram {

	private final Area defaultArea = new Area('\0', null);

	private final Map<String, PostIt> postIts = new HashMap<String, PostIt>();

	@Override
	public UmlDiagramType getUmlDiagramType() {
		return null;
	}

	@Override
	final protected ImageData exportDiagramInternal(OutputStream os, int index, FileFormatOption fileFormatOption,
			List<BufferedImage> flashcodes) throws IOException {
		final UGraphic ug = createImage(fileFormatOption);
		drawU(ug);
		if (ug instanceof UGraphicG2d) {
			final BufferedImage im = ((UGraphicG2d) ug).getBufferedImage();
			PngIO.write(im, os, fileFormatOption.isWithMetadata() ? getMetadata() : null, this.getDpi(fileFormatOption));
		} else if (ug instanceof UGraphicSvg) {
			final UGraphicSvg svg = (UGraphicSvg) ug;
			svg.createXml(os);
		} else if (ug instanceof UGraphicEps) {
			final UGraphicEps eps = (UGraphicEps) ug;
			os.write(eps.getEPSCode().getBytes());
		}
		return new ImageDataSimple();
	}

	public DiagramDescription getDescription() {
		return new DiagramDescriptionImpl("Board of post-it", getClass());
	}

	public Area getDefaultArea() {
		return defaultArea;
	}

	public Area createArea(char id) {
		throw new UnsupportedOperationException();
	}

	public PostIt createPostIt(String id, Display text) {
		if (postIts.containsKey(id)) {
			throw new IllegalArgumentException();
		}
		final PostIt postIt = new PostIt(id, text);
		postIts.put(id, postIt);
		getDefaultArea().add(postIt);
		return postIt;
	}

	void drawU(UGraphic ug) {
		getDefaultArea().drawU(ug, width);
	}

	private UGraphic createImage(FileFormatOption fileFormatOption) {
		final Color backColor = getSkinParam().getColorMapper()
				.getMappedColor(this.getSkinParam().getBackgroundColor());
		final FileFormat fileFormat = fileFormatOption.getFileFormat();
		if (fileFormat == FileFormat.PNG) {
			final double height = getDefaultArea().heightWhenWidthIs(width, TextBlockUtils.getDummyStringBounder());
			final EmptyImageBuilder builder = new EmptyImageBuilder(width, height, backColor);

			final Graphics2D graphics2D = builder.getGraphics2D();
			final double dpiFactor = this.getDpiFactor(fileFormatOption);
			final UGraphicG2d result = new UGraphicG2d(new ColorMapperIdentity(), graphics2D, dpiFactor);
			result.setBufferedImage(builder.getBufferedImage());
			return result;
		}
		throw new UnsupportedOperationException();
	}

	private int width = 800;

	public void setWidth(int width) {
		this.width = width;
	}

}
