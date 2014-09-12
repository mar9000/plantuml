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
package net.sourceforge.plantuml.flashcode;

public class FlashCodeFactory {

	public static FlashCodeUtils getFlashCodeUtils() {
		final String name = "net.sourceforge.plantuml.flashcode.FlashCodeUtilsZxing";
		try {
			final Class cl = Class.forName(name);
			return (FlashCodeUtils) cl.newInstance();
		} catch (ClassNotFoundException e) {
			return new FlashCodeUtilsNone();
		} catch (InstantiationException e) {
			return new FlashCodeUtilsNone();
		} catch (IllegalAccessException e) {
			return new FlashCodeUtilsNone();
		}
	}

}
