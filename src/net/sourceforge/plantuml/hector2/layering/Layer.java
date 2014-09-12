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
package net.sourceforge.plantuml.hector2.layering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.plantuml.cucadiagram.IEntity;
import net.sourceforge.plantuml.hector2.MinMax;
import net.sourceforge.plantuml.hector2.mpos.MutationLayer;
import net.sourceforge.plantuml.hector2.mpos.MutationLayerMove;

public class Layer {

	private final int id;
	private final Map<IEntity, Integer> entities = new HashMap<IEntity, Integer>();

	public Layer(int id) {
		this.id = id;
	}

	public Layer duplicate() {
		final Layer result = new Layer(id);
		result.entities.putAll(this.entities);
		return result;
	}

	public List<MutationLayer> getPossibleMutations() {
		final List<MutationLayer> result = new ArrayList<MutationLayer>();
		for (Map.Entry<IEntity, Integer> ent : entities.entrySet()) {
			final IEntity entity = ent.getKey();
			final int longitude = ent.getValue();
			if (isLongitudeFree(longitude + 2)) {
				result.add(new MutationLayerMove(this, entity, longitude + 2));
			}
			if (isLongitudeFree(longitude - 2)) {
				result.add(new MutationLayerMove(this, entity, longitude - 2));
			}
		}
		return Collections.unmodifiableList(result);
	}

	private boolean isLongitudeFree(int longitude) {
		return entities.values().contains(longitude) == false;
	}

	public void put(IEntity ent, int longitude) {
		if (entities.containsKey(ent) == false) {
			throw new IllegalArgumentException();
		}
		this.entities.put(ent, longitude);
	}

	public void add(IEntity ent) {
		final int pos = entities.size() * 2;
		this.entities.put(ent, pos);
	}

	public Collection<IEntity> entities() {
		return Collections.unmodifiableCollection(entities.keySet());
	}

	public int getLongitude(IEntity ent) {
		return entities.get(ent);
	}

	public MinMax getMinMaxLongitudes() {
		return MinMax.from(entities.values());
	}

	@Override
	public String toString() {
		return "layer " + id + " " + entities;
	}

	public final int getId() {
		return id;
	}

}
