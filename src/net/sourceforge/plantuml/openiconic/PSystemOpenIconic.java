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
package net.sourceforge.plantuml.openiconic;

import java.io.IOException;
import java.io.OutputStream;

import net.sourceforge.plantuml.AbstractPSystem;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.core.DiagramDescription;
import net.sourceforge.plantuml.core.DiagramDescriptionImpl;
import net.sourceforge.plantuml.core.ImageData;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.ugraphic.ColorMapperIdentity;
import net.sourceforge.plantuml.ugraphic.ImageBuilder;

public class PSystemOpenIconic extends AbstractPSystem {

	private final String iconName;
	private final double factor;

	public PSystemOpenIconic(String iconName, double factor) {
		this.iconName = iconName;
		this.factor = factor;
	}

	public ImageData exportDiagram(OutputStream os, int num, FileFormatOption fileFormat) throws IOException {
		final OpenIcon icon = OpenIcon.retrieve(iconName);
		// final Dimension2D dim = new Dimension2DDouble(100, 100);

		final ImageBuilder imageBuilder = new ImageBuilder(fileFormat.getFileFormat(), new ColorMapperIdentity(), 1.0,
				null, null, null, 5, 5);
		imageBuilder.addUDrawable(icon.asTextBlock(HtmlColorUtils.BLACK, factor));
		return imageBuilder.writeImageTOBEMOVED(os);

//		UGraphic2 ug = fileFormat.createUGraphic(dim);
//		ug = (UGraphic2) ug.apply(new UTranslate(10, 10));
//		// ug = ug.apply(new UChangeColor(HtmlColorUtils.BLACK));
//		// ug.draw(new URectangle(7, 6));
//		icon.asTextBlock(HtmlColorUtils.BLACK, factor).drawU(ug);
//		ug.writeImageTOBEMOVED(os, null, 96);
//		return new ImageDataSimple(dim);
	}

	// private GraphicStrings getGraphicStrings() throws IOException {
	// final UFont font = new UFont("SansSerif", Font.PLAIN, 12);
	// final GraphicStrings result = new GraphicStrings(strings, font, HtmlColorUtils.BLACK, HtmlColorUtils.WHITE,
	// UAntiAliasing.ANTI_ALIASING_ON);
	// return result;
	// }

	public DiagramDescription getDescription() {
		return new DiagramDescriptionImpl("(Open iconic)", getClass());
	}

}
