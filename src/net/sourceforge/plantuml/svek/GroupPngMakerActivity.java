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
package net.sourceforge.plantuml.svek;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sourceforge.plantuml.ColorParam;
import net.sourceforge.plantuml.FontParam;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.cucadiagram.CucaDiagram;
import net.sourceforge.plantuml.cucadiagram.EntityUtils;
import net.sourceforge.plantuml.cucadiagram.GroupHierarchy;
import net.sourceforge.plantuml.cucadiagram.GroupType;
import net.sourceforge.plantuml.cucadiagram.IEntity;
import net.sourceforge.plantuml.cucadiagram.IGroup;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.Stereotype;
import net.sourceforge.plantuml.cucadiagram.dot.DotData;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.skin.rose.Rose;
import net.sourceforge.plantuml.svek.image.EntityImageState;
import net.sourceforge.plantuml.ugraphic.UFont;

public final class GroupPngMakerActivity {

	private final CucaDiagram diagram;
	private final IGroup group;

	class InnerGroupHierarchy implements GroupHierarchy {

		public Collection<IGroup> getChildrenGroups(IGroup parent) {
			if (EntityUtils.groupRoot(parent)) {
				return diagram.getChildrenGroups(group);
			}
			return diagram.getChildrenGroups(parent);
		}

		public boolean isEmpty(IGroup g) {
			return diagram.isEmpty(g);
		}

	}

	public GroupPngMakerActivity(CucaDiagram diagram, IGroup group) {
		this.diagram = diagram;
		this.group = group;
	}

	private List<Link> getPureInnerLinks() {
		final List<Link> result = new ArrayList<Link>();
		for (Link link : diagram.getLinks()) {
			final IEntity e1 = (IEntity) link.getEntity1();
			final IEntity e2 = (IEntity) link.getEntity2();
			if (e1.getParentContainer() == group && e1.isGroup() == false && e2.getParentContainer() == group
					&& e2.isGroup() == false) {
				result.add(link);
			}
		}
		return result;
	}

	public IEntityImage getImage() throws IOException, InterruptedException {
		// final List<? extends CharSequence> display = group.getDisplay();
		// final TextBlock title = TextBlockUtils.create(display, new FontConfiguration(
		// getFont(FontParam.STATE), HtmlColorUtils.BLACK), HorizontalAlignment.CENTER, diagram.getSkinParam());

		if (group.size() == 0) {
			return new EntityImageState(group, diagram.getSkinParam());
		}
		final List<Link> links = getPureInnerLinks();
		final ISkinParam skinParam = diagram.getSkinParam();
		// if (OptionFlags.PBBACK && group.getSpecificBackColor() != null) {
		// skinParam = new SkinParamBackcolored(skinParam, null, group.getSpecificBackColor());
		// }
		final DotData dotData = new DotData(group, links, group.getLeafsDirect(), diagram.getUmlDiagramType(),
				skinParam, group.getRankdir(), new InnerGroupHierarchy(), diagram.getColorMapper(),
				diagram.getEntityFactory(), false, DotMode.NORMAL, diagram.getNamespaceSeparator(), diagram.getPragma());

		final CucaDiagramFileMakerSvek2 svek2 = new CucaDiagramFileMakerSvek2(dotData, diagram.getEntityFactory(),
				false, diagram.getSource(), diagram.getPragma());

		if (group.getGroupType() == GroupType.INNER_ACTIVITY) {
			final Stereotype stereo = group.getStereotype();
			final HtmlColor borderColor = getColor(ColorParam.activityBorder, stereo);
			final HtmlColor backColor = group.getSpecificBackColor() == null ? getColor(ColorParam.background, stereo)
					: group.getSpecificBackColor();
			return new InnerActivity(svek2.createFile(), borderColor, backColor, skinParam.shadowing());
		}

		throw new UnsupportedOperationException(group.getGroupType().toString());

	}

	private UFont getFont(FontParam fontParam) {
		final ISkinParam skinParam = diagram.getSkinParam();
		return skinParam.getFont(fontParam, null, false);
	}

	private final Rose rose = new Rose();

	protected final HtmlColor getColor(ColorParam colorParam, Stereotype stereo) {
		final ISkinParam skinParam = diagram.getSkinParam();
		return rose.getHtmlColor(skinParam, colorParam, stereo);
	}
}
