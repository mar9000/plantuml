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
package net.sourceforge.plantuml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileWatcher {

	private final Map<File, Long> modified2 = new HashMap<File, Long>();

	public FileWatcher(Set<File> files) {
		if (files.size() == 0) {
			throw new IllegalArgumentException();
		}
		for (File f : files) {
			modified2.put(f, f.lastModified());
		}
	}

	@Override
	public String toString() {
		return modified2.toString();
	}

	public boolean hasChanged() {
		for (Map.Entry<File, Long> ent : modified2.entrySet()) {
			final long nowModified = ent.getKey().lastModified();
			if (ent.getValue().longValue() != nowModified) {
				return true;
			}
		}
		return false;
	}

}
