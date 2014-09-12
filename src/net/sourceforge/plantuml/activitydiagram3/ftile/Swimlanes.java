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
package net.sourceforge.plantuml.activitydiagram3.ftile;

import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.plantuml.FontParam;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.activitydiagram3.Instruction;
import net.sourceforge.plantuml.activitydiagram3.InstructionList;
import net.sourceforge.plantuml.activitydiagram3.LinkRendering;
import net.sourceforge.plantuml.activitydiagram3.ftile.vcompact.FtileFactoryDelegatorAddNote;
import net.sourceforge.plantuml.activitydiagram3.ftile.vcompact.FtileFactoryDelegatorAddUrl;
import net.sourceforge.plantuml.activitydiagram3.ftile.vcompact.FtileFactoryDelegatorAssembly;
import net.sourceforge.plantuml.activitydiagram3.ftile.vcompact.FtileFactoryDelegatorCreateFork;
import net.sourceforge.plantuml.activitydiagram3.ftile.vcompact.FtileFactoryDelegatorCreateGroup;
import net.sourceforge.plantuml.activitydiagram3.ftile.vcompact.FtileFactoryDelegatorCreateSplit;
import net.sourceforge.plantuml.activitydiagram3.ftile.vcompact.FtileFactoryDelegatorIf;
import net.sourceforge.plantuml.activitydiagram3.ftile.vcompact.FtileFactoryDelegatorRepeat;
import net.sourceforge.plantuml.activitydiagram3.ftile.vcompact.FtileFactoryDelegatorWhile;
import net.sourceforge.plantuml.activitydiagram3.ftile.vcompact.UGraphicInterceptorOneSwimlane;
import net.sourceforge.plantuml.activitydiagram3.ftile.vcompact.VCompactFactory;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.graphic.UGraphicDelegator;
import net.sourceforge.plantuml.svek.UGraphicForSnake;
import net.sourceforge.plantuml.ugraphic.LimitFinder;
import net.sourceforge.plantuml.ugraphic.MinMax;
import net.sourceforge.plantuml.ugraphic.UChange;
import net.sourceforge.plantuml.ugraphic.UChangeBackColor;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UFont;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.ULine;
import net.sourceforge.plantuml.ugraphic.URectangle;
import net.sourceforge.plantuml.ugraphic.UShape;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class Swimlanes implements TextBlock {

	private final ISkinParam skinParam;;

	private final List<Swimlane> swinlanes = new ArrayList<Swimlane>();
	private final FontConfiguration fontConfiguration;
	private Swimlane currentSwimlane = null;

	private final Instruction root = new InstructionList();
	private Instruction currentInstruction = root;

	private LinkRendering nextLinkRenderer;

	public Swimlanes(ISkinParam skinParam) {
		this.skinParam = skinParam;
		final UFont font = skinParam.getFont(FontParam.TITLE, null);
		this.fontConfiguration = new FontConfiguration(font, HtmlColorUtils.BLACK, skinParam.getHyperlinkColor());

	}

	private FtileFactory getFtileFactory() {
		FtileFactory factory = new VCompactFactory(skinParam, TextBlockUtils.getDummyStringBounder());
		factory = new FtileFactoryDelegatorAddUrl(factory, skinParam);
		factory = new FtileFactoryDelegatorAssembly(factory, skinParam);
		factory = new FtileFactoryDelegatorIf(factory, skinParam);
		factory = new FtileFactoryDelegatorWhile(factory, skinParam);
		factory = new FtileFactoryDelegatorRepeat(factory, skinParam);
		factory = new FtileFactoryDelegatorCreateFork(factory, skinParam);
		factory = new FtileFactoryDelegatorCreateSplit(factory, skinParam);
		factory = new FtileFactoryDelegatorAddNote(factory, skinParam);
		factory = new FtileFactoryDelegatorCreateGroup(factory, skinParam);
		return factory;
	}

	public void swimlane(String name, HtmlColor color, Display label) {
		currentSwimlane = getOrCreate(name);
		if (color != null) {
			currentSwimlane.setSpecificBackcolor(color);
		}
		if (label != null) {
			currentSwimlane.setDisplay(label);
		}
	}

	private Swimlane getOrCreate(String name) {
		for (Swimlane s : swinlanes) {
			if (s.getName().equals(name)) {
				return s;
			}
		}
		final Swimlane result = new Swimlane(name);
		swinlanes.add(result);
		return result;
	}

	class Cross extends UGraphicDelegator {

		private Cross(UGraphic ug) {
			super(ug);
		}

		@Override
		public void draw(UShape shape) {
			if (shape instanceof Ftile) {
				final Ftile tile = (Ftile) shape;
				tile.drawU(this);
			} else if (shape instanceof Connection) {
				final Connection connection = (Connection) shape;
				final Ftile tile1 = connection.getFtile1();
				final Ftile tile2 = connection.getFtile2();

				if (tile1 == null || tile2 == null) {
					return;
				}
				final Swimlane swimlane1 = tile1.getSwimlaneOut();
				final Swimlane swimlane2 = tile2.getSwimlaneIn();
				if (swimlane1 != swimlane2) {
					final ConnectionCross connectionCross = new ConnectionCross(connection);
					connectionCross.drawU(getUg());
				}
			}
		}

		public UGraphic apply(UChange change) {
			return new Cross(getUg().apply(change));
		}

	}

	static final double separationMargin = 10;

	public void drawU(UGraphic ug) {
		final FtileFactory factory = getFtileFactory();
		TextBlock full = root.createFtile(factory);
		ug = new UGraphicForSnake(ug);
		if (swinlanes.size() <= 1) {
			full = new TextBlockInterceptorUDrawable(full);
			// BUG42
			// full.drawU(ug);
			full.drawU(ug);
			ug.flushUg();
			return;
		}

		final StringBounder stringBounder = ug.getStringBounder();
		final Dimension2D dimensionFull = full.calculateDimension(stringBounder);

		final UTranslate titleHeightTranslate = getTitleHeightTranslate(stringBounder);

		computeSize(ug, full);

		double x2 = 0;
		for (Swimlane swimlane : swinlanes) {
			if (swimlane.getSpecificBackColor() != null) {
				final UGraphic background = ug.apply(new UChangeBackColor(swimlane.getSpecificBackColor()))
						.apply(new UChangeColor(swimlane.getSpecificBackColor())).apply(new UTranslate(x2, 0));
				background.draw(new URectangle(swimlane.getTotalWidth(), dimensionFull.getHeight()
						+ titleHeightTranslate.getDy()));
			}

			final TextBlock swTitle = TextBlockUtils.create(swimlane.getDisplay(), fontConfiguration,
					HorizontalAlignment.LEFT, skinParam);
			final double titleWidth = swTitle.calculateDimension(stringBounder).getWidth();
			final double posTitle = x2 + (swimlane.getTotalWidth() - titleWidth) / 2;
			swTitle.drawU(ug.apply(new UTranslate(posTitle, 0)));

			drawSeparation(ug.apply(new UTranslate(x2, 0)), dimensionFull.getHeight() + titleHeightTranslate.getDy());

			full.drawU(new UGraphicInterceptorOneSwimlane(ug, swimlane).apply(swimlane.getTranslate()).apply(
					titleHeightTranslate));
			x2 += swimlane.getTotalWidth();

		}
		drawSeparation(ug.apply(new UTranslate(x2, 0)), dimensionFull.getHeight() + titleHeightTranslate.getDy());
		final Cross cross = new Cross(ug.apply(titleHeightTranslate));
		full.drawU(cross);
		cross.flushUg();

		// getCollisionDetector(ug, titleHeightTranslate).drawDebug(ug);
	}

	private void computeSize(UGraphic ug, TextBlock full) {
		double x1 = 0;
		final StringBounder stringBounder = ug.getStringBounder();
		for (Swimlane swimlane : swinlanes) {

			final LimitFinder limitFinder = new LimitFinder(stringBounder, false);
			final UGraphicInterceptorOneSwimlane interceptor = new UGraphicInterceptorOneSwimlane(new UGraphicForSnake(
					limitFinder), swimlane);
			full.drawU(interceptor);
			interceptor.flushUg();
			final MinMax minMax = limitFinder.getMinMax();

			final double drawingWidth = minMax.getWidth() + 2 * separationMargin;
			final TextBlock swTitle = TextBlockUtils.create(swimlane.getDisplay(), fontConfiguration,
					HorizontalAlignment.LEFT, skinParam);
			final double titleWidth = swTitle.calculateDimension(stringBounder).getWidth();
			final double totalWidth = Math.max(drawingWidth, titleWidth + 2 * separationMargin);

			final UTranslate translate = new UTranslate(x1 - minMax.getMinX() + separationMargin
					+ (totalWidth - drawingWidth) / 2.0, 0);
			swimlane.setTranslateAndWidth(translate, totalWidth);

			x1 += totalWidth;
		}
	}

	private UTranslate getTitleHeightTranslate(final StringBounder stringBounder) {
		double titlesHeight = 0;
		for (Swimlane swimlane : swinlanes) {
			final TextBlock swTitle = TextBlockUtils.create(swimlane.getDisplay(), fontConfiguration,
					HorizontalAlignment.LEFT, skinParam);

			titlesHeight = Math.max(titlesHeight, swTitle.calculateDimension(stringBounder).getHeight());
		}
		final UTranslate titleHeightTranslate = new UTranslate(0, titlesHeight);
		return titleHeightTranslate;
	}

	private CollisionDetector getCollisionDetector(UGraphic ug, final UTranslate titleHeightTranslate) {
		final FtileFactory factory = getFtileFactory();
		final TextBlock full = root.createFtile(factory);
		ug = new UGraphicForSnake(ug);

		final CollisionDetector collisionDetector = new CollisionDetector(ug.getStringBounder());

		for (Swimlane swimlane : swinlanes) {
			full.drawU(new UGraphicInterceptorOneSwimlane(collisionDetector, swimlane).apply(swimlane.getTranslate())
					.apply(titleHeightTranslate));
		}

		collisionDetector.setManageSnakes(true);
		final Cross cross = new Cross(collisionDetector.apply(titleHeightTranslate));
		full.drawU(cross);
		cross.flushUg();

		return collisionDetector;
	}

	private void drawSeparation(UGraphic ug, double height) {
		ug.apply(new UStroke(2)).apply(new UChangeColor(HtmlColorUtils.BLACK)).draw(new ULine(0, height));
	}

	public Dimension2D calculateDimension(StringBounder stringBounder) {
		return TextBlockUtils.getMinMax(this, stringBounder).getDimension();
	}

	public Instruction getCurrent() {
		return currentInstruction;
	}

	public void setCurrent(Instruction current) {
		this.currentInstruction = current;
	}

	public LinkRendering nextLinkRenderer() {
		return nextLinkRenderer;
	}

	public void setNextLinkRenderer(LinkRendering link) {
		this.nextLinkRenderer = link;
	}

	public Swimlane getCurrentSwimlane() {
		return currentSwimlane;
	}

}
