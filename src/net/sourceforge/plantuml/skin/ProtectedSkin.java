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
package net.sourceforge.plantuml.skin;

import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.cucadiagram.Display;

public class ProtectedSkin implements Skin {

	final private Skin skinToProtect;

	public ProtectedSkin(Skin skinToProtect) {
		this.skinToProtect = skinToProtect;

	}

	public Component createComponent(ComponentType type, ArrowConfiguration config, ISkinParam param, Display stringsToDisplay) {
		Component result = null;
		try {
			result = skinToProtect.createComponent(type, config, param, stringsToDisplay);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if (result == null) {
			return new GrayComponent(type);
		}
		return result;
	}

	public Object getProtocolVersion() {
		return skinToProtect.getProtocolVersion();
	}
}
