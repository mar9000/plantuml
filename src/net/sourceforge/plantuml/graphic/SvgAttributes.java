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
package net.sourceforge.plantuml.graphic;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.plantuml.StringUtils;
import net.sourceforge.plantuml.command.regex.MyPattern;

public class SvgAttributes {

	private final Map<String, String> attributes = new TreeMap<String, String>();

	public SvgAttributes() {
	}

	private SvgAttributes(SvgAttributes other) {
		this.attributes.putAll(other.attributes);
	}

	public SvgAttributes(String args) {
		final Pattern p = MyPattern.cmpile("(\\w+)\\s*=\\s*([%g][^%g]*[%g]|(?:\\w+))");
		final Matcher m = p.matcher(args);
		while (m.find()) {
			attributes.put(m.group(1), StringUtils.eventuallyRemoveStartingAndEndingDoubleQuote(m.group(2)));
		}
	}

	public Map<String, String> attributes() {
		return Collections.unmodifiableMap(attributes);
	}

	public SvgAttributes add(String key, String value) {
		final SvgAttributes result = new SvgAttributes(this);
		result.attributes.put(key, value);
		return result;
	}

	public SvgAttributes add(SvgAttributes toBeAdded) {
		final SvgAttributes result = new SvgAttributes(this);
		result.attributes.putAll(toBeAdded.attributes);
		return result;
	}

}
