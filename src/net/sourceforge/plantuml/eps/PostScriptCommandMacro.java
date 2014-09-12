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
package net.sourceforge.plantuml.eps;

public class PostScriptCommandMacro implements PostScriptCommand {

	final private String name;
	final private PostScriptData data = new PostScriptData();

	public PostScriptCommandMacro(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void add(PostScriptCommand cmd) {
		data.add(cmd);
	}

	public String toPostString() {
		return name;
	}

	public String getPostStringDefinition() {
		final StringBuilder sb = new StringBuilder();
		sb.append("/" + name + " {\n");
		sb.append(data.toPostString());
		sb.append("} def\n");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return data.toPostString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		final PostScriptCommandMacro other = (PostScriptCommandMacro) obj;
		return this.data.toPostString().equals(other.data.toPostString());
	}

}
