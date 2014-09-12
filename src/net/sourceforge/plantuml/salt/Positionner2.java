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
package net.sourceforge.plantuml.salt;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sourceforge.plantuml.salt.element.Element;

public class Positionner2 {

	private int row;
	private int col;

	private int maxRow;
	private int maxCol;

	private final Map<Element, Cell> positions = new LinkedHashMap<Element, Cell>();

	private Cell last;

	public void add(Terminated<Element> element) {
		addWithoutMove(element.getElement());
		final Terminator terminator = element.getTerminator();
		if (terminator == Terminator.NEWCOL) {
			moveNextColumn();
		} else {
			moveNextRow();
		}
	}

	private void moveNextColumn() {
		col++;
	}

	private void moveNextRow() {
		row++;
		col = 0;
	}

	private void addWithoutMove(Element elmt) {
		last = new Cell(row, col);
		positions.put(elmt, last);
		updateMax();
	}

	public void mergeLeft(Terminator terminator) {
		updateMax();
		if (terminator == Terminator.NEWCOL) {
			col++;
		} else {
			row++;
			col = 0;
		}
		last.mergeLeft();
	}

	private void updateMax() {
		if (row > maxRow) {
			maxRow = row;
		}
		if (col > maxCol) {
			maxCol = col;
		}
	}

	public Map<Element, Cell> getAll() {
		return Collections.unmodifiableMap(positions);
	}

	public final int getNbRows() {
		return maxRow + 1;
	}

	public final int getNbCols() {
		return maxCol + 1;
	}

}
