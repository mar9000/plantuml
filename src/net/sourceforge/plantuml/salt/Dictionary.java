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

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.SpriteContainer;
import net.sourceforge.plantuml.salt.element.Element;
import net.sourceforge.plantuml.salt.element.WrappedElement;
import net.sourceforge.plantuml.ugraphic.Sprite;

public class Dictionary implements SpriteContainer, ISkinSimple {

	private final Map<String, Element> data = new HashMap<String, Element>();

	public void put(String name, Element element) {
		data.put(name, element);
	}

	public Element get(String name) {
		final Element result = data.get(name);
		if (result == null) {
			throw new IllegalArgumentException();
		}
		return new WrappedElement(result);
	}

	public Sprite getSprite(String name) {
		return null;
	}

	public String getValue(String key) {
		return null;
	}
}
