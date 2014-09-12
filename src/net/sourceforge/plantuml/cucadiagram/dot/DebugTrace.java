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
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class DebugTrace {

	private static final File out = new File("debug" + System.currentTimeMillis() + ".txt");

	private static PrintWriter pw;

	private synchronized static PrintWriter getPrintWriter() {
		if (pw == null) {
			try {
				pw = new PrintWriter(out);
			} catch (FileNotFoundException e) {

			}
		}
		return pw;
	}

	public synchronized static void DEBUG(String s) {
		final PrintWriter pw = getPrintWriter();
		pw.println(s);
		pw.flush();
	}

	public synchronized static void DEBUG(String s, Throwable t) {
		DEBUG(s);
		t.printStackTrace(pw);
		pw.flush();
	}

}
