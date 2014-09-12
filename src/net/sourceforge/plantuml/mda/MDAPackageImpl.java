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
package net.sourceforge.plantuml.mda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import net.sourceforge.plantuml.api.mda.option2.MDAEntity;
import net.sourceforge.plantuml.api.mda.option2.MDAPackage;
import net.sourceforge.plantuml.cucadiagram.GroupRoot;
import net.sourceforge.plantuml.cucadiagram.IGroup;
import net.sourceforge.plantuml.cucadiagram.ILeaf;

public class MDAPackageImpl implements MDAPackage {

	private final Collection<MDAEntity> entities = new ArrayList<MDAEntity>();
	private final IGroup group;

	public MDAPackageImpl(IGroup group) {
		this.group = group;
		for (ILeaf leaf : group.getLeafsDirect()) {
			entities.add(new MDAEntityImpl(leaf));
		}
	}

	public Collection<MDAEntity> getEntities() {
		return Collections.unmodifiableCollection(entities);
	}

	public String getName() {
		if (group instanceof GroupRoot) {
			return "";
		}
		return group.getCode().getFullName();
	}

}
