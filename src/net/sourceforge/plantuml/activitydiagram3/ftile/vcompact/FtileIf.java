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
package net.sourceforge.plantuml.activitydiagram3.ftile.vcompact;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.Direction;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.activitydiagram3.Branch;
import net.sourceforge.plantuml.activitydiagram3.LinkRendering;
import net.sourceforge.plantuml.activitydiagram3.ftile.AbstractConnection;
import net.sourceforge.plantuml.activitydiagram3.ftile.AbstractFtile;
import net.sourceforge.plantuml.activitydiagram3.ftile.Arrows;
import net.sourceforge.plantuml.activitydiagram3.ftile.Connection;
import net.sourceforge.plantuml.activitydiagram3.ftile.ConnectionTranslatable;
import net.sourceforge.plantuml.activitydiagram3.ftile.Diamond;
import net.sourceforge.plantuml.activitydiagram3.ftile.Ftile;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileEmpty;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileFactory;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileGeometry;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileMinWidth;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileUtils;
import net.sourceforge.plantuml.activitydiagram3.ftile.Snake;
import net.sourceforge.plantuml.activitydiagram3.ftile.Swimlane;
import net.sourceforge.plantuml.activitydiagram3.ftile.vertical.FtileDiamond;
import net.sourceforge.plantuml.activitydiagram3.ftile.vertical.FtileDiamondInside;
import net.sourceforge.plantuml.creole.CreoleParser;
import net.sourceforge.plantuml.creole.Sheet;
import net.sourceforge.plantuml.creole.SheetBlock1;
import net.sourceforge.plantuml.creole.SheetBlock2;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.svek.ConditionStyle;
import net.sourceforge.plantuml.ugraphic.UFont;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UPolygon;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.UTranslate;

class FtileIf extends AbstractFtile {

	private final Ftile tile1;
	private final Ftile tile2;
	private final Ftile diamond1;
	private final Ftile diamond2;

	private final HtmlColor arrowColor;

	private FtileIf(Ftile diamond1, Ftile tile1, Ftile tile2, Ftile diamond2, HtmlColor arrowColor) {
		super(tile1.shadowing() || tile2.shadowing());
		this.diamond1 = diamond1;
		this.diamond2 = diamond2;
		this.tile1 = tile1;
		this.tile2 = tile2;

		this.arrowColor = arrowColor;

	}

	public Set<Swimlane> getSwimlanes() {
		final Set<Swimlane> result = new HashSet<Swimlane>();
		if (getSwimlaneIn() != null) {
			result.add(getSwimlaneIn());
		}
		result.addAll(tile1.getSwimlanes());
		result.addAll(tile2.getSwimlanes());
		return Collections.unmodifiableSet(result);
	}

	public Swimlane getSwimlaneIn() {
		return diamond1.getSwimlaneIn();
	}

	public Swimlane getSwimlaneOut() {
		return getSwimlaneIn();
	}

	static Ftile create(Swimlane swimlane, HtmlColor borderColor, HtmlColor backColor, UFont fontArrow, UFont fontTest,
			HtmlColor arrowColor, FtileFactory ftileFactory, ConditionStyle conditionStyle, Branch branch1,
			Branch branch2, ISkinParam skinParam, StringBounder stringBounder) {

		final Ftile tile1 = new FtileMinWidth(branch1.getFtile(), 30);
		final Ftile tile2 = new FtileMinWidth(branch2.getFtile(), 30);

		final FontConfiguration fcArrow = new FontConfiguration(fontArrow, HtmlColorUtils.BLACK,
				skinParam.getHyperlinkColor());
		final FontConfiguration fcTest = new FontConfiguration(fontTest, HtmlColorUtils.BLACK,
				skinParam.getHyperlinkColor());

		final TextBlock tb1 = TextBlockUtils.create(branch1.getLabelPositive(), fcArrow, HorizontalAlignment.LEFT,
				ftileFactory);
		final TextBlock tb2 = TextBlockUtils.create(branch2.getLabelPositive(), fcArrow, HorizontalAlignment.LEFT,
				ftileFactory);

		final Sheet sheet = new CreoleParser(fcTest, HorizontalAlignment.LEFT, skinParam, false).createSheet(branch1
				.getLabelTest());
		final SheetBlock1 sheetBlock1 = new SheetBlock1(sheet, 0);
		final TextBlock tbTest = new SheetBlock2(sheetBlock1, Diamond.asStencil(sheetBlock1), new UStroke(1.5));

		final Ftile diamond1;
		if (conditionStyle == ConditionStyle.INSIDE) {
			diamond1 = new FtileDiamondInside(tile1.shadowing(), backColor, borderColor, swimlane, tbTest)
					.withWest(tb1).withEast(tb2);
		} else if (conditionStyle == ConditionStyle.DIAMOND) {
			diamond1 = new FtileDiamond(tile1.shadowing(), backColor, borderColor, swimlane).withWest(tb1)
					.withEast(tb2).withNorth(tbTest);
		} else {
			throw new IllegalStateException();
		}

		final Ftile diamond2;
		if (tile1.calculateDimension(stringBounder).hasPointOut()
				&& tile2.calculateDimension(stringBounder).hasPointOut()) {
			diamond2 = new FtileDiamond(tile1.shadowing(), backColor, borderColor, swimlane);
		} else {
			diamond2 = new FtileEmpty(tile1.shadowing(), Diamond.diamondHalfSize * 2, Diamond.diamondHalfSize * 2,
					swimlane, swimlane);
		}
		final FtileIf result = new FtileIf(diamond1, tile1, tile2, diamond2, arrowColor);

		final List<Connection> conns = new ArrayList<Connection>();
		conns.add(result.new ConnectionHorizontalThenVertical(tile1));
		conns.add(result.new ConnectionHorizontalThenVertical(tile2));
		if (tile1.calculateDimension(stringBounder).hasPointOut()
				&& tile2.calculateDimension(stringBounder).hasPointOut()) {
			conns.add(result.new ConnectionVerticalThenHorizontal(tile1, branch1.getInlinkRenderingColor()));
			conns.add(result.new ConnectionVerticalThenHorizontal(tile2, branch2.getInlinkRenderingColor()));
		} else if (tile1.calculateDimension(stringBounder).hasPointOut()
				&& tile2.calculateDimension(stringBounder).hasPointOut() == false) {
			conns.add(result.new ConnectionVerticalThenHorizontalDirect(tile1, branch1.getInlinkRenderingColor()));
		} else if (tile1.calculateDimension(stringBounder).hasPointOut() == false
				&& tile2.calculateDimension(stringBounder).hasPointOut()) {
			conns.add(result.new ConnectionVerticalThenHorizontalDirect(tile2, branch2.getInlinkRenderingColor()));
		}
		return FtileUtils.addConnection(result, conns);
	}

	class ConnectionHorizontalThenVertical extends AbstractConnection implements ConnectionTranslatable {

		public ConnectionHorizontalThenVertical(Ftile tile) {
			super(diamond1, tile);
		}

		public void drawU(UGraphic ug) {
			final StringBounder stringBounder = ug.getStringBounder();
			final Point2D p1 = getP1(stringBounder);
			final Point2D p2 = getP2(stringBounder);
			final double x1 = p1.getX();
			final double y1 = p1.getY();
			final double x2 = p2.getX();
			final double y2 = p2.getY();

			final LinkRendering linkIn = getFtile2().getInLinkRendering();

			final Snake snake = new Snake(linkIn == null ? arrowColor : linkIn.getColor(), Arrows.asToDown());
			snake.addPoint(x1, y1);
			snake.addPoint(x2, y1);
			snake.addPoint(x2, y2);

			ug.draw(snake);
		}

		public void drawTranslate(UGraphic ug, UTranslate translate1, UTranslate translate2) {
			final StringBounder stringBounder = ug.getStringBounder();
			Point2D p1 = getP1(stringBounder);
			Point2D p2 = getP2(stringBounder);
			final Direction originalDirection = Direction.leftOrRight(p1, p2);
			p1 = translate1.getTranslated(p1);
			p2 = translate2.getTranslated(p2);
			final Direction newDirection = Direction.leftOrRight(p1, p2);
			final LinkRendering linkIn = getFtile2().getInLinkRendering();
			if (originalDirection != newDirection) {
				final double delta = (originalDirection == Direction.RIGHT ? -1 : 1) * Diamond.diamondHalfSize;
				final Dimension2D dimDiamond1 = diamond1.calculateDimension(stringBounder);
				final Snake small = new Snake(linkIn == null ? arrowColor : linkIn.getColor(), false);
				small.addPoint(p1);
				small.addPoint(p1.getX() + delta, p1.getY());
				small.addPoint(p1.getX() + delta, p1.getY() + dimDiamond1.getHeight());
				ug.draw(small);
				p1 = small.getLast();
			}
			final Snake snake = new Snake(linkIn == null ? arrowColor : linkIn.getColor(), Arrows.asToDown(), true);
			snake.addPoint(p1);
			snake.addPoint(p2.getX(), p1.getY());
			snake.addPoint(p2);
			ug.draw(snake);

		}

		private Point2D getP1(StringBounder stringBounder) {
			final Dimension2D dimDiamond1 = diamond1.calculateDimension(stringBounder);
			final double diamondWidth = dimDiamond1.getWidth();
			final double x;
			if (getFtile2() == tile1) {
				x = 0;
			} else if (getFtile2() == tile2) {
				x = diamondWidth;
			} else {
				throw new IllegalStateException();
			}
			return getTranslateDiamond1(stringBounder)
					.getTranslated(new Point2D.Double(x, dimDiamond1.getHeight() / 2));
		}

		private Point2D getP2(final StringBounder stringBounder) {
			return translate(stringBounder).getTranslated(getFtile2().calculateDimension(stringBounder).getPointIn());
		}

		private UTranslate translate(StringBounder stringBounder) {
			if (getFtile2() == tile1) {
				return getTranslate1(stringBounder);
			}
			if (getFtile2() == tile2) {
				return getTranslate2(stringBounder);
			}
			throw new IllegalStateException();
		}
	}

	class ConnectionVerticalThenHorizontal extends AbstractConnection implements ConnectionTranslatable {
		private final HtmlColor myArrowColor;

		public ConnectionVerticalThenHorizontal(Ftile tile, HtmlColor myArrowColor) {
			super(tile, diamond2);
			this.myArrowColor = myArrowColor == null ? arrowColor : myArrowColor;
		}

		public void drawU(UGraphic ug) {
			final StringBounder stringBounder = ug.getStringBounder();

			final FtileGeometry geo = getFtile1().calculateDimension(stringBounder);
			if (geo.hasPointOut() == false) {
				return;
			}
			final Point2D p1 = geo.translate(translate(stringBounder)).getPointOut();
			final Point2D p2 = getP2(stringBounder);

			final double x1 = p1.getX();
			final double y1 = p1.getY();
			final double x2 = p2.getX();
			final double y2 = p2.getY();

			final UPolygon arrow = x2 > x1 ? Arrows.asToRight() : Arrows.asToLeft();
			final Snake snake = new Snake(myArrowColor, arrow);
			snake.addPoint(x1, y1);
			snake.addPoint(x1, y2);
			snake.addPoint(x2, y2);

			ug.draw(snake);
		}

		public void drawTranslate(UGraphic ug, UTranslate translate1, UTranslate translate2) {
			final StringBounder stringBounder = ug.getStringBounder();
			final FtileGeometry geo = getFtile1().calculateDimension(stringBounder);
			if (geo.hasPointOut() == false) {
				return;
			}
			final Point2D p2 = getP2(stringBounder);
			final Point2D p1 = geo.translate(translate(stringBounder)).getPointOut();
			final Direction originalDirection = Direction.leftOrRight(p1, p2);

			final double x1 = p1.getX();
			final double x2 = p2.getX();
			final Point2D mp1a = translate1.getTranslated(p1);
			final Point2D mp2b = translate2.getTranslated(p2);
			final Direction newDirection = Direction.leftOrRight(mp1a, mp2b);
			final UPolygon arrow = x2 > x1 ? Arrows.asToRight() : Arrows.asToLeft();
			if (originalDirection == newDirection) {
				final double delta = (x2 > x1 ? -1 : 1) * 1.5 * Diamond.diamondHalfSize;
				final Point2D mp2bc = new Point2D.Double(mp2b.getX() + delta, mp2b.getY());
				final Snake snake = new Snake(myArrowColor, true);
				final double middle = (mp1a.getY() + mp2b.getY()) / 2.0;
				snake.addPoint(mp1a);
				snake.addPoint(mp1a.getX(), middle);
				snake.addPoint(mp2bc.getX(), middle);
				snake.addPoint(mp2bc);
				ug.draw(snake);
				final Snake small = new Snake(myArrowColor, arrow);
				small.addPoint(mp2bc);
				small.addPoint(mp2bc.getX(), mp2b.getY());
				small.addPoint(mp2b);
				ug.draw(small);
			} else {
				final double delta = (x2 > x1 ? -1 : 1) * 1.5 * Diamond.diamondHalfSize;
				final Point2D mp2bb = new Point2D.Double(mp2b.getX() + delta, mp2b.getY() - 3 * Diamond.diamondHalfSize);
				final Snake snake = new Snake(myArrowColor, true);
				snake.addPoint(mp1a);
				snake.addPoint(mp1a.getX(), mp2bb.getY());
				snake.addPoint(mp2bb);
				ug.draw(snake);
				final Snake small = new Snake(myArrowColor, arrow);
				small.addPoint(mp2bb);
				small.addPoint(mp2bb.getX(), mp2b.getY());
				small.addPoint(mp2b);
				ug.draw(small);

			}

		}

		private Point2D getP2(StringBounder stringBounder) {
			final Dimension2D dimDiamond2 = diamond2.calculateDimension(stringBounder);
			final double diamondWidth = dimDiamond2.getWidth();
			final double x;
			if (getFtile1() == tile1) {
				x = 0;
			} else if (getFtile1() == tile2) {
				x = diamondWidth;
			} else {
				throw new IllegalStateException();
			}
			return getTranslateDiamond2(stringBounder)
					.getTranslated(new Point2D.Double(x, dimDiamond2.getHeight() / 2));
		}

		private UTranslate translate(StringBounder stringBounder) {
			if (getFtile1() == tile1) {
				return getTranslate1(stringBounder);
			}
			if (getFtile1() == tile2) {
				return getTranslate2(stringBounder);
			}
			throw new IllegalStateException();
		}
	}

	class ConnectionVerticalThenHorizontalDirect extends AbstractConnection implements ConnectionTranslatable {
		private final HtmlColor myArrowColor;

		public ConnectionVerticalThenHorizontalDirect(Ftile tile, HtmlColor myArrowColor) {
			super(tile, diamond2);
			this.myArrowColor = myArrowColor == null ? arrowColor : myArrowColor;
		}

		public void drawU(UGraphic ug) {

			final StringBounder stringBounder = ug.getStringBounder();
			final Dimension2D dimTotal = calculateDimensionInternal(stringBounder);

			final FtileGeometry geo = getFtile1().calculateDimension(stringBounder);
			if (geo.hasPointOut() == false) {
				return;
			}
			final Point2D p1 = geo.translate(translate(stringBounder)).getPointOut();
			final Point2D p2 = new Point2D.Double(getLeft(stringBounder), dimTotal.getHeight()
					- Diamond.diamondHalfSize);

			final double x1 = p1.getX();
			final double y1 = p1.getY();
			final double x2 = p2.getX();
			final double y2 = p2.getY();

			final Snake snake = new Snake(myArrowColor, true);

			snake.addPoint(x1, y1);
			snake.addPoint(x1, y2);
			snake.addPoint(x2, y2);
			snake.addPoint(x2, dimTotal.getHeight());

			ug.draw(snake);
		}

		private UTranslate translate(StringBounder stringBounder) {
			if (getFtile1() == tile1) {
				return getTranslate1(stringBounder);
			}
			if (getFtile1() == tile2) {
				return getTranslate2(stringBounder);
			}
			throw new IllegalStateException();
		}

		public void drawTranslate(UGraphic ug, UTranslate translate1, UTranslate translate2) {
			final StringBounder stringBounder = ug.getStringBounder();
			final Dimension2D dimTotal = calculateDimensionInternal(stringBounder);

			final FtileGeometry geo = getFtile1().calculateDimension(stringBounder);
			if (geo.hasPointOut() == false) {
				return;
			}
			final Point2D p1 = geo.translate(translate(stringBounder)).getPointOut();
			final Point2D p2 = new Point2D.Double(getLeft(stringBounder), dimTotal.getHeight()
					- Diamond.diamondHalfSize);

			final Point2D mp1a = translate1.getTranslated(p1);
			final Point2D mp2b = translate2.getTranslated(p2);

			final Snake snake = new Snake(myArrowColor, true);

			final double x1 = mp1a.getX();
			final double x2 = mp2b.getX();
			final double y2 = mp2b.getY();

			snake.addPoint(mp1a);
			snake.addPoint(x1, y2);
			snake.addPoint(mp2b);
			snake.addPoint(x2, dimTotal.getHeight());

			ug.draw(snake);
		}
	}

	private UTranslate getTranslate1(StringBounder stringBounder) {
		final Dimension2D dimTotal = calculateDimensionInternal(stringBounder);
		final Dimension2D dimDiamond1 = diamond1.calculateDimension(stringBounder);
		final Dimension2D dim1 = tile1.calculateDimension(stringBounder);

		final double x1 = 0;
		final double h = dimDiamond1.getHeight();
		final double y1 = (dimTotal.getHeight() - 2 * h - dim1.getHeight()) / 2 + h;
		return new UTranslate(x1, y1);
	}

	private UTranslate getTranslate2(StringBounder stringBounder) {
		final Dimension2D dimTotal = calculateDimensionInternal(stringBounder);
		final Dimension2D dimDiamond1 = diamond1.calculateDimension(stringBounder);
		final Dimension2D dim2 = tile2.calculateDimension(stringBounder);

		final double x2 = dimTotal.getWidth() - dim2.getWidth();
		final double h = dimDiamond1.getHeight();
		final double y2 = (dimTotal.getHeight() - 2 * h - dim2.getHeight()) / 2 + h;
		return new UTranslate(x2, y2);

	}

	private UTranslate getTranslateDiamond1(StringBounder stringBounder) {
		final double y1 = 0;
		final Dimension2D dimDiamond1 = diamond1.calculateDimension(stringBounder);
		final double x1 = getLeft(stringBounder) - dimDiamond1.getWidth() / 2;
		return new UTranslate(x1, y1);
	}

	private UTranslate getTranslateDiamond2(StringBounder stringBounder) {
		final Dimension2D dimTotal = calculateDimensionInternal(stringBounder);
		final Dimension2D dimDiamond2 = diamond2.calculateDimension(stringBounder);
		final double y2 = dimTotal.getHeight() - dimDiamond2.getHeight();
		final double x2 = getLeft(stringBounder) - dimDiamond2.getWidth() / 2;
		return new UTranslate(x2, y2);
	}

	@Override
	public UTranslate getTranslateFor(Ftile child, StringBounder stringBounder) {
		if (child == diamond1) {
			return getTranslateDiamond1(stringBounder);
		}
		if (child == tile1) {
			return getTranslate1(stringBounder);
		}
		if (child == tile2) {
			return getTranslate2(stringBounder);
		}
		if (child == diamond2) {
			return getTranslateDiamond2(stringBounder);
		}
		throw new UnsupportedOperationException();
	}

	public void drawU(UGraphic ug) {
		final StringBounder stringBounder = ug.getStringBounder();

		ug.apply(getTranslateDiamond1(stringBounder)).draw(diamond1);
		ug.apply(getTranslate1(stringBounder)).draw(tile1);
		ug.apply(getTranslate2(stringBounder)).draw(tile2);
		ug.apply(getTranslateDiamond2(stringBounder)).draw(diamond2);
	}

	public FtileGeometry calculateDimension(StringBounder stringBounder) {
		final Dimension2D dimTotal = calculateDimensionInternal(stringBounder);
		if (tile1.calculateDimension(stringBounder).hasPointOut()
				|| tile2.calculateDimension(stringBounder).hasPointOut()) {
			return new FtileGeometry(dimTotal, getLeft(stringBounder), 0, dimTotal.getHeight());
		}
		return new FtileGeometry(dimTotal, getLeft(stringBounder), 0);
	}

	private Dimension2D calculateDimensionInternal;

	private Dimension2D calculateDimensionInternal(StringBounder stringBounder) {
		if (calculateDimensionInternal == null) {
			calculateDimensionInternal = calculateDimensionInternalSlow(stringBounder);
		}
		return calculateDimensionInternal;
	}

	private Dimension2D calculateDimensionInternalSlow(StringBounder stringBounder) {
		final Dimension2D dim1 = tile1.calculateDimension(stringBounder);
		final Dimension2D dim2 = tile2.calculateDimension(stringBounder);
		final Dimension2D dim12 = Dimension2DDouble.mergeLR(dim1, dim2);
		final Dimension2D dimDiamond1 = diamond1.calculateDimension(stringBounder);
		final double widthA = dim12.getWidth();
		final double widthB = Math.max(dim1.getWidth(), dim2.getWidth()) + dimDiamond1.getWidth();
		final double width = Math.max(widthA, widthB);
		return new Dimension2DDouble(width + 30, dim12.getHeight() + dimDiamond1.getHeight() * 3 + 40);
	}

	private double getLeft(StringBounder stringBounder) {
		final double left1 = tile1.calculateDimension(stringBounder).translate(getTranslate1(stringBounder)).getLeft();
		final double left2 = tile2.calculateDimension(stringBounder).translate(getTranslate2(stringBounder)).getLeft();
		return (left1 + left2) / 2;
	}

}
