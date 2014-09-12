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

import java.io.PrintStream;
import java.util.List;

public interface BasicCharArea {

	int getWidth();

	int getHeight();

	void drawChar(char c, int x, int y);

	void fillRect(char c, int x, int y, int width, int height);

	void drawStringLR(String string, int x, int y);

	void drawStringTB(String string, int x, int y);

	String getLine(int line);

	void print(PrintStream ps);

	List<String> getLines();

	void drawHLine(char c, int line, int col1, int col2);
	void drawHLine(char c, int line, int col1, int col2, char ifFound, char thenUse);

	void drawVLine(char c, int col, int line1, int line2);

}
