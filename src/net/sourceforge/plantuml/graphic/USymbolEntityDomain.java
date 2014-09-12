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

import net.sourceforge.plantuml.ColorParam;
import net.sourceforge.plantuml.FontParam;
import net.sourceforge.plantuml.svek.EntityDomain;

class USymbolEntityDomain extends USymbolSimpleAbstract {

	private final double thickness;

	public USymbolEntityDomain(double thickness) {
		super(ColorParam.entityBackground, ColorParam.entityBorder, FontParam.ENTITY, FontParam.ENTITY_STEREOTYPE);
		this.thickness = thickness;
	}

	@Override
	protected TextBlock getDrawing(final SymbolContext symbolContext) {
		return new EntityDomain(symbolContext.getBackColor(), symbolContext.getForeColor(),
				symbolContext.isShadowing() ? 4.0 : 0.0, thickness);
	}

}
