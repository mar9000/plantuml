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
package net.sourceforge.plantuml.core;

public enum DiagramType {
	SALT9000,   // mar9000: add ANTLR parser for SALT.
	UML, DITAA, DOT, PROJECT, JCCKIT, SALT, TURING, FLOW, CREOLE, JUNGLE, UNKNOWN;

	static public DiagramType getTypeFromArobaseStart(String s) {
		// mar9000: add ANTLR parser for SALT9000.
		if (s.startsWith("@startsalt9000")) {
			return SALT9000;
		}
		// End mar9000.
//		if (s.startsWith("@startuml2")) {
//			return UML2;
//		}
		if (s.startsWith("@startuml")) {
			return UML;
		}
		if (s.startsWith("@startdot")) {
			return DOT;
		}
		if (s.startsWith("@startjcckit")) {
			return JCCKIT;
		}
		if (s.startsWith("@startditaa")) {
			return DITAA;
		}
		if (s.startsWith("@startproject")) {
			return PROJECT;
		}
		if (s.startsWith("@startsalt")) {
			return SALT;
		}
		if (s.startsWith("@startturing")) {
			return TURING;
		}
		if (s.startsWith("@startflow")) {
			return FLOW;
		}
		if (s.startsWith("@startcreole")) {
			return CREOLE;
		}
		if (s.startsWith("@starttree")) {
			return JUNGLE;
		}
		return UNKNOWN;
	}
}
