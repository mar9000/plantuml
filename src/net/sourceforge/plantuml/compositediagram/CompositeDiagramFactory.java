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
package net.sourceforge.plantuml.compositediagram;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.plantuml.command.Command;
import net.sourceforge.plantuml.command.UmlDiagramFactory;
import net.sourceforge.plantuml.compositediagram.command.CommandCreateBlock;
import net.sourceforge.plantuml.compositediagram.command.CommandCreatePackageBlock;
import net.sourceforge.plantuml.compositediagram.command.CommandEndPackageBlock;
import net.sourceforge.plantuml.compositediagram.command.CommandLinkBlock;

public class CompositeDiagramFactory extends UmlDiagramFactory {

	@Override
	protected List<Command> createCommands() {
		final List<Command> cmds = new ArrayList<Command>();
		cmds.add(new CommandCreateBlock());
		cmds.add(new CommandLinkBlock());
		cmds.add(new CommandCreatePackageBlock());
		cmds.add(new CommandEndPackageBlock());
		addCommonCommands(cmds);

		return cmds;
	}

	@Override
	public CompositeDiagram createEmptyDiagram() {
		return new CompositeDiagram();
	}
}
