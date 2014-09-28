/**
 * This file is part of the PlantUML9000 project.
 * 
 * PlantUML9000 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * PlantUML9000 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with the PlantUML9000 project.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2014 Marco LOMBARDO, mar9000 near google.com .
 */
package org.mar9000.plantuml;

import net.sourceforge.plantuml.command.PSystemBasicFactory;
import net.sourceforge.plantuml.core.DiagramType;

public class PSystemSalt9000Factory extends PSystemBasicFactory<PSystemSalt9000> {

	public PSystemSalt9000Factory(DiagramType diagramType) {
		super(diagramType);
	}

	public PSystemSalt9000 init(String startLine) {
		if (getDiagramType() == DiagramType.UML) {
			return null;
		} else if (getDiagramType() == DiagramType.SALT9000) {
			return new PSystemSalt9000();
		} else {
			throw new IllegalStateException(getDiagramType().name());
		}

	}

	@Override
	public PSystemSalt9000 executeLine(PSystemSalt9000 system, String line) {
		if (system == null && line.equals("salt9000")) {
			return new PSystemSalt9000();
		}
		if (system == null) {
			return null;
		}
		system.add(line.trim());
		return system;
	}

}
