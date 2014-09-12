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
package net.sourceforge.plantuml;

import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;


public enum ColorParam {
	background(HtmlColorUtils.getColorIfValid("white")),
	hyperlink(HtmlColorUtils.BLUE),
	
	activityBackground(HtmlColorUtils.MY_YELLOW),
	activityBorder(HtmlColorUtils.MY_RED),
	activityStart(HtmlColorUtils.BLACK),
	activityEnd(HtmlColorUtils.BLACK),
	activityBar(HtmlColorUtils.BLACK),
	activityArrow(HtmlColorUtils.MY_RED),
	
	usecaseBorder(HtmlColorUtils.MY_RED),
	usecaseBackground(HtmlColorUtils.MY_YELLOW),
	usecaseArrow(HtmlColorUtils.MY_RED),

	objectBackground(HtmlColorUtils.MY_YELLOW),
	objectBorder(HtmlColorUtils.MY_RED),
	objectArrow(HtmlColorUtils.MY_RED),
	
	classBackground(HtmlColorUtils.MY_YELLOW),
	classBorder(HtmlColorUtils.MY_RED),
	stereotypeCBackground(HtmlColorUtils.getColorIfValid("#ADD1B2")),
	stereotypeABackground(HtmlColorUtils.getColorIfValid("#A9DCDF")),
	stereotypeIBackground(HtmlColorUtils.getColorIfValid("#B4A7E5")),
	stereotypeEBackground(HtmlColorUtils.getColorIfValid("#EB937F")),
	classArrow(HtmlColorUtils.MY_RED),
		
	packageBackground(HtmlColorUtils.MY_YELLOW),
	packageBorder(HtmlColorUtils.BLACK),

	partitionBackground(HtmlColorUtils.MY_YELLOW),
	partitionBorder(HtmlColorUtils.BLACK),

	componentBackground(HtmlColorUtils.MY_YELLOW),
	componentBorder(HtmlColorUtils.MY_RED),
	interfaceBackground(HtmlColorUtils.MY_YELLOW),
	interfaceBorder(HtmlColorUtils.MY_RED),
	// componentArrow,

	stateBackground(HtmlColorUtils.MY_YELLOW),
	stateBorder(HtmlColorUtils.MY_RED),
	stateArrow(HtmlColorUtils.MY_RED),
	stateStart(HtmlColorUtils.BLACK),
	stateEnd(HtmlColorUtils.BLACK),

	noteBackground(HtmlColorUtils.getColorIfValid("#FBFB77"), true),
	noteBorder(HtmlColorUtils.MY_RED),
	
	legendBackground(HtmlColorUtils.getColorIfValid("#DDDDDD"), true),
	legendBorder(HtmlColorUtils.BLACK),
	
	actorBackground(HtmlColorUtils.MY_YELLOW, true),
	actorBorder(HtmlColorUtils.MY_RED),
	participantBackground(HtmlColorUtils.MY_YELLOW, true),
	participantBorder(HtmlColorUtils.MY_RED),
	sequenceGroupBorder(HtmlColorUtils.BLACK),
	sequenceGroupBackground(HtmlColorUtils.getColorIfValid("#EEEEEE"), true),
	sequenceReferenceBorder(HtmlColorUtils.BLACK),
	sequenceReferenceHeaderBackground(HtmlColorUtils.getColorIfValid("#EEEEEE"), true),
	sequenceReferenceBackground(HtmlColorUtils.WHITE, true),
	sequenceDividerBackground(HtmlColorUtils.getColorIfValid("#EEEEEE"), true),
	sequenceLifeLineBackground(HtmlColorUtils.WHITE, true),
	sequenceLifeLineBorder(HtmlColorUtils.MY_RED),
	sequenceArrow(HtmlColorUtils.MY_RED),
	sequenceBoxBorder(HtmlColorUtils.MY_RED),
	sequenceBoxBackground(HtmlColorUtils.getColorIfValid("#DDDDDD"), true),
	
	artifactBackground(HtmlColorUtils.MY_YELLOW),
	artifactBorder(HtmlColorUtils.MY_RED),
	cloudBackground(HtmlColorUtils.MY_YELLOW),
	cloudBorder(HtmlColorUtils.MY_RED),
	databaseBackground(HtmlColorUtils.MY_YELLOW),
	databaseBorder(HtmlColorUtils.MY_RED),
	folderBackground(HtmlColorUtils.MY_YELLOW),
	folderBorder(HtmlColorUtils.MY_RED),
	frameBackground(HtmlColorUtils.MY_YELLOW),
	frameBorder(HtmlColorUtils.MY_RED),
	nodeBackground(HtmlColorUtils.MY_YELLOW),
	nodeBorder(HtmlColorUtils.MY_RED),
	rectangleBackground(HtmlColorUtils.MY_YELLOW),
	rectangleBorder(HtmlColorUtils.MY_RED),
	agentBackground(HtmlColorUtils.MY_YELLOW),
	agentBorder(HtmlColorUtils.MY_RED),
	storageBackground(HtmlColorUtils.MY_YELLOW),
	storageBorder(HtmlColorUtils.MY_RED),
	boundaryBackground(HtmlColorUtils.MY_YELLOW),
	boundaryBorder(HtmlColorUtils.MY_RED),
	controlBackground(HtmlColorUtils.MY_YELLOW),
	controlBorder(HtmlColorUtils.MY_RED),
	entityBackground(HtmlColorUtils.MY_YELLOW),
	entityBorder(HtmlColorUtils.MY_RED),

	
	iconPrivate(HtmlColorUtils.getColorIfValid("#C82930")),
	iconPrivateBackground(HtmlColorUtils.getColorIfValid("#F24D5C")),
	iconPackage(HtmlColorUtils.getColorIfValid("#1963A0")),
	iconPackageBackground(HtmlColorUtils.getColorIfValid("#4177AF")),
	iconProtected(HtmlColorUtils.getColorIfValid("#B38D22")),
	iconProtectedBackground(HtmlColorUtils.getColorIfValid("#FFFF44")),
	iconPublic(HtmlColorUtils.getColorIfValid("#038048")),
	iconPublicBackground(HtmlColorUtils.getColorIfValid("#84BE84"));
	
	private final boolean isBackground;
	private final HtmlColor defaultValue;
	
	private ColorParam(HtmlColor defaultValue) {
		this(defaultValue, false);
	}
	
	private ColorParam() {
		this(null, false);
	}
	
	private ColorParam(boolean isBackground) {
		this(null, isBackground);
	}
	
	private ColorParam(HtmlColor defaultValue, boolean isBackground) {
		this.isBackground = isBackground;
		this.defaultValue = defaultValue;
	}

	protected boolean isBackground() {
		return isBackground;
	}

	public final HtmlColor getDefaultValue() {
		return defaultValue;
	}
}
