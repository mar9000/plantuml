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
package net.sourceforge.plantuml.xmi;

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.Log;
import net.sourceforge.plantuml.classdiagram.ClassDiagram;
import net.sourceforge.plantuml.cucadiagram.CucaDiagram;

public final class CucaDiagramXmiMaker {

	private final CucaDiagram diagram;
	private final FileFormat fileFormat;

	public CucaDiagramXmiMaker(CucaDiagram diagram, FileFormat fileFormat) throws IOException {
		this.diagram = diagram;
		this.fileFormat = fileFormat;
	}

	public void createFiles(OutputStream fos) throws IOException {
		try {
			final IXmiClassDiagram xmi;
			if (fileFormat == FileFormat.XMI_STANDARD) {
				xmi = new XmiClassDiagramStandard((ClassDiagram) diagram);
			} else if (fileFormat == FileFormat.XMI_ARGO) {
				xmi = new XmiClassDiagramArgo((ClassDiagram) diagram);
			} else if (fileFormat == FileFormat.XMI_STAR) {
				xmi = new XmiClassDiagramStar((ClassDiagram) diagram);
			} else {
				throw new UnsupportedOperationException();
			}
			xmi.transformerXml(fos);
			// fos.close();
			// return Collections.singletonList(suggestedFile);
		} catch (ParserConfigurationException e) {
			Log.error(e.toString());
			e.printStackTrace();
			throw new IOException(e.toString());
		} catch (TransformerException e) {
			Log.error(e.toString());
			e.printStackTrace();
			throw new IOException(e.toString());
		}
	}

}
