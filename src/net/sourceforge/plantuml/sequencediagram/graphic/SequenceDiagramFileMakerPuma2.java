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
package net.sourceforge.plantuml.sequencediagram.graphic;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.plantuml.CMapData;
import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.FontParam;
import net.sourceforge.plantuml.Url;
import net.sourceforge.plantuml.activitydiagram3.ftile.EntityImageLegend;
import net.sourceforge.plantuml.api.ImageDataComplex;
import net.sourceforge.plantuml.api.ImageDataSimple;
import net.sourceforge.plantuml.core.ImageData;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.eps.EpsStrategy;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorGradient;
import net.sourceforge.plantuml.graphic.HtmlColorSimple;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.graphic.UDrawable;
import net.sourceforge.plantuml.png.PngTitler;
import net.sourceforge.plantuml.sequencediagram.Event;
import net.sourceforge.plantuml.sequencediagram.LifeEvent;
import net.sourceforge.plantuml.sequencediagram.LifeEventType;
import net.sourceforge.plantuml.sequencediagram.Message;
import net.sourceforge.plantuml.sequencediagram.Newpage;
import net.sourceforge.plantuml.sequencediagram.Participant;
import net.sourceforge.plantuml.sequencediagram.SequenceDiagram;
import net.sourceforge.plantuml.skin.Area;
import net.sourceforge.plantuml.skin.Component;
import net.sourceforge.plantuml.skin.ComponentType;
import net.sourceforge.plantuml.skin.SimpleContext2D;
import net.sourceforge.plantuml.skin.Skin;
import net.sourceforge.plantuml.ugraphic.ImageBuilder;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UGraphic2;
import net.sourceforge.plantuml.ugraphic.UTranslate;
import net.sourceforge.plantuml.ugraphic.eps.UGraphicEps;
import net.sourceforge.plantuml.ugraphic.g2d.UGraphicG2d;
import net.sourceforge.plantuml.ugraphic.html5.UGraphicHtml5;
import net.sourceforge.plantuml.ugraphic.svg.UGraphicSvg;
import net.sourceforge.plantuml.ugraphic.tikz.UGraphicTikz;
import net.sourceforge.plantuml.ugraphic.visio.UGraphicVdx;
import net.sourceforge.plantuml.utils.StringUtils;

public class SequenceDiagramFileMakerPuma2 implements FileMaker {

	private static final StringBounder dummyStringBounder = TextBlockUtils.getDummyStringBounder();

	private final SequenceDiagram diagram;
	private final DrawableSet drawableSet;
	private final Dimension2D fullDimension;
	private final List<Page> pages;
	private final FileFormatOption fileFormatOption;

	private double scale;

	public SequenceDiagramFileMakerPuma2(SequenceDiagram sequenceDiagram, Skin skin, FileFormatOption fileFormatOption) {
		this.diagram = sequenceDiagram;
		this.fileFormatOption = fileFormatOption;
		final DrawableSetInitializer initializer = new DrawableSetInitializer(skin, sequenceDiagram.getSkinParam(),
				sequenceDiagram.isShowFootbox(), sequenceDiagram.getAutonewpage());

		for (Participant p : sequenceDiagram.participants().values()) {
			initializer.addParticipant(p, sequenceDiagram.getEnglober(p));
		}

		for (Event ev : sequenceDiagram.events()) {
			initializer.addEvent(ev);
			if (ev instanceof Message) {
				// TODO mieux faire
				final Message m = (Message) ev;
				for (LifeEvent lifeEvent : m.getLiveEvents()) {
					if (lifeEvent.getType() == LifeEventType.DESTROY
					/*
					 * || lifeEvent.getType() == LifeEventType.CREATE
					 */) {
						initializer.addEvent(lifeEvent);
					}
				}
			}
		}
		drawableSet = initializer.createDrawableSet(dummyStringBounder);
		final List<Newpage> newpages = new ArrayList<Newpage>();
		for (Event ev : drawableSet.getAllEvents()) {
			if (ev instanceof Newpage) {
				newpages.add((Newpage) ev);
			}
		}
		fullDimension = drawableSet.getDimension();
		final Map<Newpage, Double> positions = new LinkedHashMap<Newpage, Double>();
		for (Newpage n : newpages) {
			positions.put(n, initializer.getYposition(dummyStringBounder, n));
		}
		pages = create(drawableSet, positions, sequenceDiagram.isShowFootbox(), sequenceDiagram.getTitle()).getPages();
	}

	public int getNbPages() {
		return pages.size();
	}

	private PageSplitter create(DrawableSet drawableSet, Map<Newpage, Double> positions, boolean showFootbox,
			Display title) {

		final double headerHeight = drawableSet.getHeadHeight(dummyStringBounder);
		final double tailHeight = drawableSet.getTailHeight(dummyStringBounder, showFootbox);
		final double signatureHeight = 0;
		final double newpageHeight = drawableSet.getSkin()
				.createComponent(ComponentType.NEWPAGE, null, drawableSet.getSkinParam(), Display.create(""))
				.getPreferredHeight(dummyStringBounder);

		return new PageSplitter(fullDimension.getHeight(), headerHeight, positions, tailHeight, signatureHeight,
				newpageHeight, title);
	}

	public ImageData createOne(OutputStream os, final int index, boolean isWithMetadata) throws IOException {

		final Page page = pages.get(index);
		final SequenceDiagramArea area = new SequenceDiagramArea(fullDimension.getWidth(), page.getHeight());

		final Component compTitle;
		if (page.getTitle() == null) {
			compTitle = null;
		} else {
			compTitle = drawableSet.getSkin().createComponent(ComponentType.TITLE, null, drawableSet.getSkinParam(),
					page.getTitle());
			area.setTitleArea(compTitle.getPreferredWidth(dummyStringBounder),
					compTitle.getPreferredHeight(dummyStringBounder));
		}
		addFooter2(area);
		addHeader2(area);

		// final FileFormat fileFormat = fileFormatOption.getFileFormat();

		final Display legend = diagram.getLegend();
		final TextBlock legendBlock;
		if (legend == null) {
			legendBlock = TextBlockUtils.empty(0, 0);
		} else {
			legendBlock = EntityImageLegend.create(legend, diagram.getSkinParam());
		}
		final Dimension2D dimLegend = TextBlockUtils.getDimension(legendBlock);

		scale = getScale(area.getWidth(), area.getHeight());

		final double dpiFactor = diagram.getDpiFactor(fileFormatOption);
		// System.err.println("dpiFactor=" + dpiFactor);
		// System.err.println("scale=" + scale);

		final ImageBuilder imageBuilder = new ImageBuilder(diagram.getSkinParam().getColorMapper(), oneOf(scale,
				dpiFactor), diagram.getSkinParam().getBackgroundColor(), null, null, 3, 10, diagram.getAnimation());

		imageBuilder.addUDrawable(new UDrawable() {
			public void drawU(UGraphic ug) {

				double delta = 0;
				if (index > 0) {
					delta = page.getNewpage1() - page.getHeaderHeight();
				}
				if (delta < 0) {
					delta = 0;
				}

				if (compTitle != null) {
					final StringBounder stringBounder = ug.getStringBounder();
					final double h = compTitle.getPreferredHeight(stringBounder);
					final double w = compTitle.getPreferredWidth(stringBounder);
					compTitle.drawU(ug.apply(new UTranslate(area.getTitleX(), area.getTitleY())), new Area(
							new Dimension2DDouble(w, h)), new SimpleContext2D(false));
				}

				// ug.apply(new UTranslate(area.getSequenceAreaX() + delta1 / 2, area.getSequenceAreaY()))
				// final double delta1 = Math.max(0, dimLegend.getWidth() - area.getWidth());
				final double delta1 = Math.max(0, dimLegend.getWidth() - area.getWidth());

				drawableSet.drawU22(
						ug.apply(new UTranslate(area.getSequenceAreaX() + delta1 / 2, area.getSequenceAreaY())), delta,
						fullDimension.getWidth(), page, diagram.isShowFootbox());

				addHeader3(area, ug);
				addFooter3(area, ug);

				if (legend != null) {
					final double delta2;
					if (diagram.getLegendAlignment() == HorizontalAlignment.LEFT) {
						delta2 = 0;
					} else if (diagram.getLegendAlignment() == HorizontalAlignment.RIGHT) {
						delta2 = Math.max(0, area.getWidth() - dimLegend.getWidth());
					} else {
						delta2 = Math.max(0, area.getWidth() - dimLegend.getWidth()) / 2;
					}
					legendBlock.drawU(ug.apply(new UTranslate(delta2, area.getHeight())));
				}

			}
		});
		return imageBuilder.writeImageTOBEMOVED(fileFormatOption.getFileFormat(), os);
	}

	private double oneOf(double a, double b) {
		if (a == 1) {
			return b;
		}
		return a;
	}

	private double getImageWidth(SequenceDiagramArea area, double dpiFactor, double legendWidth) {
		final int minsize = diagram.getMinwidth();
		final double w = Math.max(area.getWidth() * getScale(area.getWidth(), area.getHeight()) * dpiFactor,
				legendWidth);
		if (minsize == Integer.MAX_VALUE) {
			return w;
		}
		if (w >= minsize) {
			return w;
		}
		return minsize;
	}

	private double getScale(double width, double height) {
		if (diagram.getScale() == null) {
			return 1;
		}
		return diagram.getScale().getScale(width, height);
	}

	private UGraphic2 createImage(final int diagramWidth, final Page page, final int indice) {
		double delta = 0;
		if (indice > 0) {
			delta = page.getNewpage1() - page.getHeaderHeight();
		}
		if (delta < 0) {
			delta = 0;
		}

		final SequenceDiagramArea area = new SequenceDiagramArea(diagramWidth, page.getHeight());

		Component compTitle = null;

		if (page.getTitle() != null) {
			compTitle = drawableSet.getSkin().createComponent(ComponentType.TITLE, null, drawableSet.getSkinParam(),
					page.getTitle());
			area.setTitleArea(compTitle.getPreferredWidth(dummyStringBounder),
					compTitle.getPreferredHeight(dummyStringBounder));
		}
		addFooter2(area);
		addHeader2(area);

		Color backColor = null;
		if (diagram.getSkinParam().getBackgroundColor() instanceof HtmlColorSimple) {
			backColor = diagram.getSkinParam().getColorMapper()
					.getMappedColor(diagram.getSkinParam().getBackgroundColor());
		}
		final FileFormat fileFormat = fileFormatOption.getFileFormat();
		final double dpiFactor = diagram.getDpiFactor(fileFormatOption);

		final Display legend = diagram.getLegend();
		final TextBlock legendBlock;
		if (legend == null) {
			legendBlock = TextBlockUtils.empty(0, 0);
		} else {
			legendBlock = EntityImageLegend.create(legend, diagram.getSkinParam());
		}
		final Dimension2D dimLegend = TextBlockUtils.getDimension(legendBlock);

		scale = getScale(area.getWidth(), area.getHeight());
		UGraphic2 ug;
		if (fileFormat == FileFormat.PNG) {
			double imageHeight = area.getHeight() * getScale(area.getWidth(), area.getHeight()) * 1;
			if (imageHeight == 0) {
				imageHeight = 1;
			}
			final double imageWidthWithDpi = getImageWidth(area, 1, dimLegend.getWidth());
			final Dimension2D dim = new Dimension2DDouble(imageWidthWithDpi, imageHeight + dimLegend.getHeight());

			ug = fileFormatOption.createUGraphic(diagram.getSkinParam().getColorMapper(), dpiFactor, dim, diagram
					.getSkinParam().getBackgroundColor(), diagram.isRotation());

			final AffineTransform scaleAt = ((UGraphicG2d) ug).getGraphics2D().getTransform();
			scaleAt.scale(scale, scale);
			((UGraphicG2d) ug).getGraphics2D().setTransform(scaleAt);
		} else if (fileFormat == FileFormat.SVG) {
			if (diagram.getSkinParam().getBackgroundColor() instanceof HtmlColorGradient) {
				ug = new UGraphicSvg(diagram.getSkinParam().getColorMapper(), (HtmlColorGradient) diagram
						.getSkinParam().getBackgroundColor(), false, scale);
			} else if (backColor == null || backColor.equals(Color.WHITE)) {
				ug = new UGraphicSvg(diagram.getSkinParam().getColorMapper(), false, scale);
			} else {
				ug = new UGraphicSvg(diagram.getSkinParam().getColorMapper(), StringUtils.getAsHtml(backColor), false,
						scale);
			}
		} else if (fileFormat == FileFormat.EPS) {
			ug = new UGraphicEps(diagram.getSkinParam().getColorMapper(), EpsStrategy.getDefault2());
		} else if (fileFormat == FileFormat.EPS_TEXT) {
			ug = new UGraphicEps(diagram.getSkinParam().getColorMapper(), EpsStrategy.WITH_MACRO_AND_TEXT);
		} else if (fileFormat == FileFormat.HTML5) {
			ug = new UGraphicHtml5(diagram.getSkinParam().getColorMapper());
		} else if (fileFormat == FileFormat.VDX) {
			ug = new UGraphicVdx(diagram.getSkinParam().getColorMapper());
		} else if (fileFormat == FileFormat.LATEX) {
			ug = new UGraphicTikz(diagram.getSkinParam().getColorMapper());
		} else {
			throw new UnsupportedOperationException();
		}

		if (compTitle != null) {
			final StringBounder stringBounder = ug.getStringBounder();
			final double h = compTitle.getPreferredHeight(stringBounder);
			final double w = compTitle.getPreferredWidth(stringBounder);
			compTitle.drawU(ug.apply(new UTranslate(area.getTitleX(), area.getTitleY())), new Area(
					new Dimension2DDouble(w, h)), new SimpleContext2D(false));
		}

		addHeader3(area, ug);
		addFooter3(area, ug);

		final double delta1 = Math.max(0, dimLegend.getWidth() - area.getWidth());
		// bugnewway X*58
		drawableSet.drawU(ug.apply(new UTranslate(area.getSequenceAreaX() + delta1 / 2, area.getSequenceAreaY())),
				delta, diagramWidth, page, diagram.isShowFootbox());

		if (legend != null) {
			final double delta2;
			if (diagram.getLegendAlignment() == HorizontalAlignment.LEFT) {
				delta2 = 0;
			} else if (diagram.getLegendAlignment() == HorizontalAlignment.RIGHT) {
				delta2 = Math.max(0, area.getWidth() - dimLegend.getWidth());
			} else {
				delta2 = Math.max(0, area.getWidth() - dimLegend.getWidth()) / 2;
			}
			legendBlock.drawU(ug.apply(new UTranslate(delta2, area.getHeight())));
		}
		return ug;
	}

	private void addFooter2(SequenceDiagramArea area) {
		final HtmlColor hyperlinkColor = diagram.getSkinParam().getHyperlinkColor();
		final HtmlColor titleColor = diagram.getSkinParam().getFontHtmlColor(FontParam.FOOTER, null);
		final String fontFamily = diagram.getSkinParam().getFont(FontParam.FOOTER, null, false).getFamily(null);
		final int fontSize = diagram.getSkinParam().getFont(FontParam.FOOTER, null, false).getSize();
		final PngTitler pngTitler = new PngTitler(titleColor, diagram.getFooter(), fontSize, fontFamily,
				diagram.getFooterAlignment(), hyperlinkColor);
		final Dimension2D dim = pngTitler.getTextDimension(dummyStringBounder);
		if (dim != null) {
			area.setFooterArea(dim.getWidth(), dim.getHeight(), 3);
		}
	}

	private void addHeader2(SequenceDiagramArea area) {
		final HtmlColor hyperlinkColor = diagram.getSkinParam().getHyperlinkColor();
		final HtmlColor titleColor = diagram.getSkinParam().getFontHtmlColor(FontParam.HEADER, null);
		final String fontFamily = diagram.getSkinParam().getFont(FontParam.HEADER, null, false).getFamily(null);
		final int fontSize = diagram.getSkinParam().getFont(FontParam.HEADER, null, false).getSize();
		final PngTitler pngTitler = new PngTitler(titleColor, diagram.getHeader(), fontSize, fontFamily,
				diagram.getHeaderAlignment(), hyperlinkColor);
		final Dimension2D dim = pngTitler.getTextDimension(dummyStringBounder);
		if (dim != null) {
			area.setHeaderArea(dim.getWidth(), dim.getHeight(), 3);
		}
	}

	private void addFooter3(SequenceDiagramArea area, UGraphic ug) {
		final HtmlColor hyperlinkColor = diagram.getSkinParam().getHyperlinkColor();
		final HtmlColor titleColor = diagram.getSkinParam().getFontHtmlColor(FontParam.FOOTER, null);
		final String fontFamily = diagram.getSkinParam().getFont(FontParam.FOOTER, null, false).getFamily(null);
		final int fontSize = diagram.getSkinParam().getFont(FontParam.FOOTER, null, false).getSize();
		final PngTitler pngTitler = new PngTitler(titleColor, diagram.getFooter(), fontSize, fontFamily,
				diagram.getFooterAlignment(), hyperlinkColor);
		final TextBlock text = pngTitler.getTextBlock();
		if (text == null) {
			return;
		}
		text.drawU(ug.apply(new UTranslate(area.getFooterX(diagram.getFooterAlignment()), area.getFooterY())));
	}

	private void addHeader3(SequenceDiagramArea area, UGraphic ug) {
		final HtmlColor hyperlinkColor = diagram.getSkinParam().getHyperlinkColor();
		final HtmlColor titleColor = diagram.getSkinParam().getFontHtmlColor(FontParam.HEADER, null);
		final String fontFamily = diagram.getSkinParam().getFont(FontParam.HEADER, null, false).getFamily(null);
		final int fontSize = diagram.getSkinParam().getFont(FontParam.HEADER, null, false).getSize();
		final PngTitler pngTitler = new PngTitler(titleColor, diagram.getHeader(), fontSize, fontFamily,
				diagram.getHeaderAlignment(), hyperlinkColor);
		final TextBlock text = pngTitler.getTextBlock();
		if (text == null) {
			return;
		}
		text.drawU(ug.apply(new UTranslate(area.getHeaderX(diagram.getHeaderAlignment()), area.getHeaderY())));
	}

}
