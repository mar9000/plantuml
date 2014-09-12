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
package net.sourceforge.plantuml.hector2.mpos;

import net.sourceforge.plantuml.cucadiagram.IEntity;
import net.sourceforge.plantuml.hector2.layering.Layer;

public class MutationLayerMove implements MutationLayer {

	private final Layer layer;
	private final IEntity entity;
	private final int newLongitude;

	public MutationLayerMove(Layer layer, IEntity entity, int newLongitude) {
		this.layer = layer;
		this.entity = entity;
		this.newLongitude = newLongitude;
	}

	public Layer mute() {
		final Layer result = layer.duplicate();
		result.put(entity, newLongitude);
		return result;
	}

	public Layer getOriginal() {
		return layer;
	}

	@Override
	public String toString() {
		return "{" + layer.getId() + "} " + entity + " moveto " + newLongitude;
	}
}
