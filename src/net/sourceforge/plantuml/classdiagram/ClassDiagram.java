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

import net.sourceforge.plantuml.UmlDiagramType;
import net.sourceforge.plantuml.cucadiagram.Code;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.cucadiagram.EntityUtils;
import net.sourceforge.plantuml.cucadiagram.GroupType;
import net.sourceforge.plantuml.cucadiagram.IGroup;
import net.sourceforge.plantuml.cucadiagram.ILeaf;
import net.sourceforge.plantuml.cucadiagram.LeafType;
import net.sourceforge.plantuml.graphic.USymbol;
import net.sourceforge.plantuml.objectdiagram.AbstractClassOrObjectDiagram;

public class ClassDiagram extends AbstractClassOrObjectDiagram {

	private String namespaceSeparator = ".";

	@Override
	public ILeaf getOrCreateLeaf(Code code, LeafType type, USymbol symbol) {
		if (namespaceSeparator != null) {
			code = code.withSeparator(namespaceSeparator);
		}
		if (type == null) {
			code = code.eventuallyRemoveStartingAndEndingDoubleQuote();
			if (namespaceSeparator == null) {
				return getOrCreateLeafDefault(code, LeafType.CLASS, symbol);
			}
			code = code.getFullyQualifiedCode(getCurrentGroup());
			if (super.leafExist(code)) {
				return getOrCreateLeafDefault(code, LeafType.CLASS, symbol);
			}
			return createEntityWithNamespace(code, Display.getWithNewlines(code.getShortName(getLeafs())),
					LeafType.CLASS, symbol);
		}
		if (namespaceSeparator == null) {
			return getOrCreateLeafDefault(code, LeafType.CLASS, symbol);
		}
		code = code.getFullyQualifiedCode(getCurrentGroup());
		if (super.leafExist(code)) {
			return getOrCreateLeafDefault(code, type, symbol);
		}
		return createEntityWithNamespace(code, Display.getWithNewlines(code.getShortName(getLeafs())), type, symbol);
	}

	@Override
	public ILeaf createLeaf(Code code, Display display, LeafType type, USymbol symbol) {
		if (namespaceSeparator != null) {
			code = code.withSeparator(namespaceSeparator);
		}
		if (type != LeafType.ABSTRACT_CLASS && type != LeafType.ANNOTATION && type != LeafType.CLASS
				&& type != LeafType.INTERFACE && type != LeafType.ENUM && type != LeafType.LOLLIPOP
				&& type != LeafType.NOTE) {
			return super.createLeaf(code, display, type, symbol);
		}
		if (namespaceSeparator == null) {
			return super.createLeaf(code, display, type, symbol);
		}
		code = code.getFullyQualifiedCode(getCurrentGroup());
		if (super.leafExist(code)) {
			throw new IllegalArgumentException("Already known: " + code);
		}
		return createEntityWithNamespace(code, display, type, symbol);
	}

	private ILeaf createEntityWithNamespace(Code fullyCode, Display display, LeafType type, USymbol symbol) {
		IGroup group = getCurrentGroup();
		final String namespace = getNamespace(fullyCode);
		if (namespace != null
				&& (EntityUtils.groupRoot(group) || group.getCode().getFullName().equals(namespace) == false)) {
			final Code namespace2 = Code.of(namespace);
			group = getOrCreateGroupInternal(namespace2, Display.getWithNewlines(namespace), namespace2,
					GroupType.PACKAGE, getRootGroup());
		}
		return createLeafInternal(fullyCode,
				display == null ? Display.getWithNewlines(fullyCode.getShortName(getLeafs())) : display, type, group,
				symbol);
	}

	private final String getNamespace(Code fullyCode) {
		String name = fullyCode.getFullName();
		do {
			final int x = name.lastIndexOf(namespaceSeparator);
			if (x == -1) {
				return null;
			}
			name = name.substring(0, x);
		} while (getLeafs().containsKey(Code.of(name, namespaceSeparator)));
		return name;
	}

	@Override
	public final boolean leafExist(Code code) {
		if (namespaceSeparator == null) {
			return super.leafExist(code);
		}
		code = code.withSeparator(namespaceSeparator);
		return super.leafExist(code.getFullyQualifiedCode(getCurrentGroup()));
	}

	@Override
	public UmlDiagramType getUmlDiagramType() {
		return UmlDiagramType.CLASS;
	}

	public void setNamespaceSeparator(String namespaceSeparator) {
		this.namespaceSeparator = namespaceSeparator;
	}

	public String getNamespaceSeparator() {
		return namespaceSeparator;
	}

}
