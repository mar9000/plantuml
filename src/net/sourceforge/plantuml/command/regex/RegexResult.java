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
package net.sourceforge.plantuml.command.regex;

import java.util.Collections;
import java.util.Map;

public class RegexResult {

	private final Map<String, RegexPartialMatch> data;

	public RegexResult(Map<String, RegexPartialMatch> data) {
		this.data = Collections.unmodifiableMap(data);
	}

	@Override
	public String toString() {
		return data.toString();
	}

	public RegexPartialMatch get(String key) {
		return data.get(key);
	}

	public String get(String key, int num) {
		final RegexPartialMatch reg = data.get(key);
		if (reg == null) {
			return null;
		}
		return reg.get(num);
	}

	public String getLazzy(String key, int num) {
		for (Map.Entry<String, RegexPartialMatch> ent : data.entrySet()) {
			if (ent.getKey().startsWith(key) == false) {
				continue;
			}
			final RegexPartialMatch match = ent.getValue();
			if (num >= match.size()) {
				continue;
			}
			if (match.get(num) != null) {
				return ent.getValue().get(num);
			}
		}
		return null;
	}

	public int size() {
		return data.size();
	}

}
