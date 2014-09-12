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
package net.sourceforge.plantuml.cucadiagram.dot;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class GraphvizWindows extends AbstractGraphviz {

	@Override
	protected File specificDotExe() {
		final File result = searchInDir(new File("c:/Program Files"));
		if (result != null) {
			return result;
		}
		final File result86 = searchInDir(new File("c:/Program Files (x86)"));
		if (result86 != null) {
			return result86;
		}
		return null;
	}

	private static File searchInDir(final File programFile) {
		if (programFile.exists() == false || programFile.isDirectory() == false) {
			return null;
		}
		final List<File> dots = new ArrayList<File>();
		for (File f : programFile.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory() && pathname.getName().startsWith("Graphviz");
			}
		})) {
			final File result = new File(new File(f, "bin"), "dot.exe");
			if (result.exists() && result.canRead()) {
				dots.add(result.getAbsoluteFile());
			}
		}
		return higherVersion(dots);
	}

	static File higherVersion(List<File> dots) {
		if (dots.size() == 0) {
			return null;
		}
		Collections.sort(dots, Collections.reverseOrder());
		return dots.get(0);
	}

	GraphvizWindows(String dotString, String... type) {
		super(dotString, type);
	}

}
