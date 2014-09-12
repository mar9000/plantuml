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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sourceforge.plantuml.cucadiagram.CucaDiagram;
import net.sourceforge.plantuml.cucadiagram.GroupType;
import net.sourceforge.plantuml.cucadiagram.IGroup;
import net.sourceforge.plantuml.cucadiagram.LeafType;
import net.sourceforge.plantuml.svek.GroupPngMakerState;
import net.sourceforge.plantuml.svek.IEntityImage;

public final class CucaDiagramSimplifierState {

	private final CucaDiagram diagram;

	public CucaDiagramSimplifierState(CucaDiagram diagram, List<String> dotStrings) throws IOException,
			InterruptedException {
		this.diagram = diagram;
		boolean changed;
		do {
			changed = false;
			final Collection<IGroup> groups = putConcurrentStateAtEnd(diagram.getGroups(false));
			for (IGroup g : groups) {
				if (diagram.isAutarkic(g)) {
					final IEntityImage img = computeImage(g);
					g.overideImage(img, g.getGroupType() == GroupType.CONCURRENT_STATE ? LeafType.STATE_CONCURRENT
							: LeafType.STATE);

					changed = true;
				}
			}
		} while (changed);
	}

	private Collection<IGroup> putConcurrentStateAtEnd(Collection<IGroup> groups) {
		final List<IGroup> result = new ArrayList<IGroup>();
		final List<IGroup> end = new ArrayList<IGroup>();
		for (IGroup g : groups) {
			if (g.getGroupType() == GroupType.CONCURRENT_STATE) {
				end.add(g);
			} else {
				result.add(g);
			}
		}
		result.addAll(end);
		return result;
	}

	private IEntityImage computeImage(IGroup g) throws IOException, InterruptedException {
		final GroupPngMakerState maker = new GroupPngMakerState(diagram, g);
		return maker.getImage();
	}

}
