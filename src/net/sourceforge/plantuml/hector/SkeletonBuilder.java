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
package net.sourceforge.plantuml.hector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SkeletonBuilder {

	private List<PinLinksContinuousSet> sets = new ArrayList<PinLinksContinuousSet>();

	public void add(PinLink pinLink) {
		addInternal(pinLink);
		merge();

	}

	private void merge() {
		for (int i = 0; i < sets.size() - 1; i++) {
			for (int j = i + 1; j < sets.size(); j++) {
				if (sets.get(i).doesTouch(sets.get(j))) {
					sets.get(i).addAll(sets.get(j));
					sets.remove(j);
					return;
				}
			}
		}
	}

	private void addInternal(PinLink pinLink) {
		for (PinLinksContinuousSet set : sets) {
			if (set.doesTouch(pinLink)) {
				set.add(pinLink);
				return;
			}
		}
		final PinLinksContinuousSet newSet = new PinLinksContinuousSet();
		newSet.add(pinLink);
		sets.add(newSet);
	}

	public List<Skeleton> createSkeletons() {
		final List<Skeleton> result = new ArrayList<Skeleton>();

		for (PinLinksContinuousSet set : sets) {
			result.add(set.createSkeleton());
		}

		return Collections.unmodifiableList(result);
	}
}
