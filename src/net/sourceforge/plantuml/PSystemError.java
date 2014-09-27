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
package net.sourceforge.plantuml;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import net.sourceforge.plantuml.core.DiagramDescription;
import net.sourceforge.plantuml.core.DiagramDescriptionImpl;
import net.sourceforge.plantuml.core.ImageData;
import net.sourceforge.plantuml.core.UmlSource;
import net.sourceforge.plantuml.graphic.GraphicStrings;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.ugraphic.ColorMapperIdentity;
import net.sourceforge.plantuml.ugraphic.ImageBuilder;
import net.sourceforge.plantuml.utils.StringUtils;

public class PSystemError extends AbstractPSystem {

	private String getSuggestColor(boolean useRed) {
		if (useRed) {
			return "black";
		}
		return "white";
	}

	private String getRed(boolean useRed) {
		if (useRed) {
			return "#CD0A0A";
		}
		return "red";
	}

	private final int higherErrorPosition;
	private final List<ErrorUml> printedErrors;

	public PSystemError(UmlSource source, List<ErrorUml> all) {
		this.setSource(source);

		final int higherErrorPositionExecution = getHigherErrorPosition(ErrorUmlType.EXECUTION_ERROR, all);
		final int higherErrorPositionSyntax = getHigherErrorPosition(ErrorUmlType.SYNTAX_ERROR, all);

		if (higherErrorPositionExecution == Integer.MIN_VALUE && higherErrorPositionSyntax == Integer.MIN_VALUE) {
			throw new IllegalStateException();
		}

		if (higherErrorPositionExecution >= higherErrorPositionSyntax) {
			higherErrorPosition = higherErrorPositionExecution;
			printedErrors = getErrorsAt(higherErrorPositionExecution, ErrorUmlType.EXECUTION_ERROR, all);
		} else {
			assert higherErrorPositionSyntax > higherErrorPositionExecution;
			higherErrorPosition = higherErrorPositionSyntax;
			printedErrors = getErrorsAt(higherErrorPositionSyntax, ErrorUmlType.SYNTAX_ERROR, all);
		}

	}

	public PSystemError(UmlSource source, ErrorUml singleError) {
		this(source, Collections.singletonList(singleError));
	}
	
//	public ImageData exportDiagramOld(OutputStream os, int num, FileFormatOption fileFormat) throws IOException {
//		final boolean useRed = fileFormat.isUseRedForError();
//		final GraphicStrings result = GraphicStrings.createDefault(getHtmlStrings(useRed), useRed);
//		return result.exportDiagram(os, getMetadata(), fileFormat);
//	}


	public ImageData exportDiagram(OutputStream os, int num, FileFormatOption fileFormat) throws IOException {
		final boolean useRed = fileFormat.isUseRedForError();
		final GraphicStrings result = GraphicStrings.createDefault(getHtmlStrings(useRed), useRed);
		final ImageBuilder imageBuilder = new ImageBuilder(new ColorMapperIdentity(), 1.0, result.getBackcolor(),
				getMetadata(), null, 0, 0, null);
		imageBuilder.addUDrawable(result);
		return imageBuilder.writeImageTOBEMOVED(fileFormat.getFileFormat(), os);
	}

	private List<String> getHtmlStrings(boolean useRed) {
		final List<String> htmlStrings = new ArrayList<String>();

		final int limit = 4;
		int start;
		final int skip = higherErrorPosition - limit + 1;
		if (skip <= 0) {
			start = 0;
		} else {
			if (skip == 1) {
				htmlStrings.add("... (skipping 1 line) ...");
			} else {
				htmlStrings.add("... (skipping " + skip + " lines) ...");
			}
			start = higherErrorPosition - limit + 1;
		}
		for (int i = start; i < higherErrorPosition; i++) {
			htmlStrings.add(StringUtils.hideComparatorCharacters(getSource().getLine(i)));
		}
		final String errorLine = getSource().getLine(higherErrorPosition);
		final String err = StringUtils.hideComparatorCharacters(errorLine);
		if (StringUtils.isNotEmpty(err)) {
			htmlStrings.add("<w:" + getRed(useRed) + ">" + err + "</w>");
		}
		final StringBuilder underscore = new StringBuilder();
		for (int i = 0; i < errorLine.length(); i++) {
			underscore.append("^");
		}
		final Collection<String> textErrors = new LinkedHashSet<String>();
		for (ErrorUml er : printedErrors) {
			textErrors.add(er.getError());
		}
		for (String er : textErrors) {
			htmlStrings.add(" <color:" + getRed(useRed) + ">" + er + "</color>");
		}
		boolean first = true;
		for (String s : getSuggest()) {
			if (first) {
				htmlStrings.add(" <color:" + getSuggestColor(useRed) + "><i>" + s + "</i></color>");
			} else {
				htmlStrings.add("<color:" + getSuggestColor(useRed) + ">" + StringUtils.hideComparatorCharacters(s)
						+ "</color>");
			}
			first = false;
		}

		return htmlStrings;
	}

	public List<String> getSuggest() {
		boolean suggested = false;
		for (ErrorUml er : printedErrors) {
			if (er.hasSuggest()) {
				suggested = true;
			}
		}
		if (suggested == false) {
			return Collections.emptyList();
		}
		final List<String> result = new ArrayList<String>();
		result.add("Did you mean:");
		for (ErrorUml er : printedErrors) {
			if (er.hasSuggest()) {
				result.add(er.getSuggest().getSuggestedLine());
			}
		}
		return Collections.unmodifiableList(result);

	}

	private Collection<ErrorUml> getErrors(ErrorUmlType type, List<ErrorUml> all) {
		final Collection<ErrorUml> result = new LinkedHashSet<ErrorUml>();
		for (ErrorUml error : all) {
			if (error.getType() == type) {
				result.add(error);
			}
		}
		return result;
	}

	private int getHigherErrorPosition(ErrorUmlType type, List<ErrorUml> all) {
		int max = Integer.MIN_VALUE;
		for (ErrorUml error : getErrors(type, all)) {
			if (error.getPosition() > max) {
				max = error.getPosition();
			}
		}
		// if (max == Integer.MIN_VALUE) {
		// throw new IllegalStateException();
		// }
		return max;
	}

	private List<ErrorUml> getErrorsAt(int position, ErrorUmlType type, List<ErrorUml> all) {
		final List<ErrorUml> result = new ArrayList<ErrorUml>();
		for (ErrorUml error : getErrors(type, all)) {
			if (error.getPosition() == position && StringUtils.isNotEmpty(error.getError())) {
				result.add(error);
			}
		}
		return result;
	}

	public DiagramDescription getDescription() {
		return new DiagramDescriptionImpl("(Error)", getClass());
	}

	public final int getHigherErrorPosition() {
		return higherErrorPosition;
	}

	public final Collection<ErrorUml> getErrorsUml() {
		return Collections.unmodifiableCollection(printedErrors);
	}

	@Override
	public String getWarningOrError() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getDescription());
		sb.append('\n');
		for (CharSequence t : getTitle()) {
			sb.append(t);
			sb.append('\n');
		}
		sb.append('\n');
		for (String s : getSuggest()) {
			sb.append(s);
			sb.append('\n');
		}
		return sb.toString();
	}
}
