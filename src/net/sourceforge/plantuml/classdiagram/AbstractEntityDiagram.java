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
package net.sourceforge.plantuml.classdiagram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sourceforge.plantuml.core.DiagramDescription;
import net.sourceforge.plantuml.core.DiagramDescriptionImpl;
import net.sourceforge.plantuml.cucadiagram.CucaDiagram;

public abstract class AbstractEntityDiagram extends CucaDiagram {

	final protected List<String> getDotStrings() {
		final List<String> def = Arrays.asList("nodesep=.35;", "ranksep=0.8;", "edge [fontsize=11,labelfontsize=11];",
				"node [fontsize=11,height=.35,width=.55];");
		if (getPragma().isDefine("graphattributes") == false) {
			return def;
		}
		final String attribute = getPragma().getValue("graphattributes");
		final List<String> result = new ArrayList<String>(def);
		result.add(attribute);
		return Collections.unmodifiableList(result);
	}

	final public DiagramDescription getDescription() {
		return new DiagramDescriptionImpl("(" + getLeafssize() + " entities)", getClass());
	}

}
