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
package net.sourceforge.plantuml.svek;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.plantuml.cucadiagram.IEntity;
import net.sourceforge.plantuml.cucadiagram.IGroup;
import net.sourceforge.plantuml.cucadiagram.ILeaf;

public class Bibliotekon {

	private final List<Cluster> allCluster = new ArrayList<Cluster>();

	private final Map<ILeaf, Shape> shapeMap = new LinkedHashMap<ILeaf, Shape>();;

	private final List<Line> lines0 = new ArrayList<Line>();
	private final List<Line> lines1 = new ArrayList<Line>();
	private final List<Line> allLines = new ArrayList<Line>();

	public void putShape(ILeaf ent, Shape shape) {
		shapeMap.put(ent, shape);
	}

	public Cluster getCluster(IGroup ent) {
		for (Cluster cl : allCluster) {
			if (cl.getGroup() == ent) {
				return cl;
			}
		}
		throw new IllegalArgumentException();
	}

	public void addLine(Line line) {
		allLines.add(line);
		if (first(line)) {
			if (line.hasNoteLabelText()) {
				// lines0.add(0, line);
				for (int i = 0; i < lines0.size(); i++) {
					final Line other = lines0.get(i);
					if (other.hasNoteLabelText() == false && line.sameConnections(other)) {
						lines0.add(i, line);
						return;
					}
				}
				lines0.add(line);
			} else {
				lines0.add(line);
			}
		} else {
			lines1.add(line);
		}
	}

	private static boolean first(Line line) {
		final int length = line.getLength();
		if (length == 1) {
			return true;
		}
		return false;
	}

	public void addCluster(Cluster current) {
		allCluster.add(current);
	}

	public Shape getShape(IEntity ent) {
		return shapeMap.get(ent);
	}

	public String getShapeUid(ILeaf ent) {
		final Shape result = getShape(ent);
		if (result != null) {
			String uid = result.getUid();
			if (result.isShielded()) {
				uid = uid + ":h";
			}
			return uid;
		}
		assert result == null;
		if (ent.isGroup()) {
			for (IEntity i : shapeMap.keySet()) {
				if (ent.getCode().equals(i.getCode())) {
					return getShape(i).getUid();
				}
			}
			return Cluster.getSpecialPointId(ent);
		}
		throw new IllegalStateException();
	}

	public String getWarningOrError(int warningOrError) {
		final StringBuilder sb = new StringBuilder();
		for (Map.Entry<ILeaf, Shape> ent : shapeMap.entrySet()) {
			final Shape sh = ent.getValue();
			final double maxX = sh.getMinX() + sh.getWidth();
			if (maxX > warningOrError) {
				final IEntity entity = ent.getKey();
				sb.append(entity.getCode() + " is overpassing the width limit.");
				sb.append("\n");
			}

		}
		return sb.length() == 0 ? "" : sb.toString();
	}

	public List<Line> allLines() {
		return Collections.unmodifiableList(allLines);
	}

	public List<Line> lines0() {
		return Collections.unmodifiableList(lines0);
	}

	public List<Line> lines1() {
		return Collections.unmodifiableList(lines1);
	}

	public List<Cluster> allCluster() {
		return Collections.unmodifiableList(allCluster);
	}

	public Collection<Shape> allShapes() {
		return Collections.unmodifiableCollection(shapeMap.values());
	}

	public List<Line> getAllLineConnectedTo(IEntity leaf) {
		final List<Line> result = new ArrayList<Line>();
		for (Line line : allLines) {
			if (line.isLinkFromOrTo(leaf)) {
				result.add(line);
			}
		}
		return Collections.unmodifiableList(result);
	}

}
