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
package net.sourceforge.plantuml.sequencediagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.plantuml.Url;
import net.sourceforge.plantuml.graphic.HtmlColor;

public class GroupingStart extends Grouping {

	private final List<GroupingLeaf> children = new ArrayList<GroupingLeaf>();
	private final HtmlColor backColorGeneral;

	final private GroupingStart parent;

	public GroupingStart(String title, String comment, HtmlColor backColorGeneral, HtmlColor backColorElement,
			GroupingStart parent) {
		super(title, comment, GroupingType.START, backColorElement);
		this.backColorGeneral = backColorGeneral;
		this.parent = parent;
	}

	List<GroupingLeaf> getChildren() {
		return Collections.unmodifiableList(children);
	}

	public void addChildren(GroupingLeaf g) {
		children.add(g);
	}

	public int getLevel() {
		if (parent == null) {
			return 0;
		}
		return parent.getLevel() + 1;
	}

	@Override
	public HtmlColor getBackColorGeneral() {
		return backColorGeneral;
	}

	public boolean dealWith(Participant someone) {
		return false;
	}

	public Url getUrl() {
		return null;
	}
	
	public boolean hasUrl() {
		return false;
	}


	@Override
	public boolean isParallel() {
		return getTitle().equals("par2");
	}

}
