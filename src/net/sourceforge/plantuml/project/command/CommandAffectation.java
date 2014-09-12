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
package net.sourceforge.plantuml.project.command;

import java.util.List;

import net.sourceforge.plantuml.command.CommandExecutionResult;
import net.sourceforge.plantuml.command.SingleLineCommand;
import net.sourceforge.plantuml.project.Expression;
import net.sourceforge.plantuml.project.PSystemProject;

public class CommandAffectation extends SingleLineCommand<PSystemProject> {

	public CommandAffectation() {
		super("(?i)^\\s*([~\\^]?[\\w$/]+)\\s*:=\\s*(.+)$");
	}

	@Override
	protected CommandExecutionResult executeArg(PSystemProject diagram, List<String> arg) {
		final Expression exp = diagram.getProject().getExpression(arg.get(1).trim());
		final boolean ok = diagram.getProject().affectation(arg.get(0).trim(), exp);
		if (ok) {
			return CommandExecutionResult.ok();
		}
		return CommandExecutionResult.error("Cannot execute");
	}
}
