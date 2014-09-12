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
import java.util.List;

import net.sourceforge.plantuml.cucadiagram.IEntity;
import net.sourceforge.plantuml.cucadiagram.ILeaf;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkDecor;
import net.sourceforge.plantuml.cucadiagram.LinkType;

public enum SingleStrategy {

	SQUARRE, HLINE, VLINE;

	public Collection<Link> generateLinks(List<ILeaf> standalones) {
		return putInSquare(standalones);
	}

	private Collection<Link> putInSquare(List<ILeaf> standalones) {
		final List<Link> result = new ArrayList<Link>();
		final LinkType linkType = new LinkType(LinkDecor.NONE, LinkDecor.NONE).getInvisible();
		final int branch = computeBranch(standalones.size());
		int headBranch = 0;
		for (int i = 1; i < standalones.size(); i++) {
			final int dist = i - headBranch;
			final IEntity ent2 = standalones.get(i);
			final Link link;
			if (dist == branch) {
				final IEntity ent1 = standalones.get(headBranch);
				link = new Link(ent1, ent2, linkType, null, 2);
				headBranch = i;
			} else {
				final IEntity ent1 = standalones.get(i - 1);
				link = new Link(ent1, ent2, linkType, null, 1);
			}
			result.add(link);
		}
		return Collections.unmodifiableCollection(result);
	}

	static int computeBranch(int size) {
		final double sqrt = Math.sqrt(size);
		final int r = (int) sqrt;
		if (r * r == size) {
			return r;
		}
		return r + 1;
	}

}
