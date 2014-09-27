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

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.plantuml.command.regex.MyPattern;
import net.sourceforge.plantuml.cucadiagram.Code;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorTransparent;
import net.sourceforge.plantuml.preproc.ReadLineReader;
import net.sourceforge.plantuml.preproc.UncommentReadLine;
import net.sourceforge.plantuml.ugraphic.ColorMapper;

// Class moved to net.sourceforge.plantuml.utils
@Deprecated
public class StringUtils {

	@Deprecated
	public static String getPlateformDependentAbsolutePath(File file) {
		return file.getAbsolutePath();
	}

	@Deprecated
	public static List<String> getWithNewlines2(Code s) {
		return getWithNewlines2(s.getFullName());
	}

	@Deprecated
	public static List<String> getWithNewlines2(String s) {
		if (s == null) {
			return null;
		}
		final List<String> result = new ArrayList<String>();
		final StringBuilder current = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			if (c == '\\' && i < s.length() - 1) {
				final char c2 = s.charAt(i + 1);
				i++;
				if (c2 == 'n') {
					result.add(current.toString());
					current.setLength(0);
				} else if (c2 == 't') {
					current.append('\t');
				} else if (c2 == '\\') {
					current.append(c2);
				}
			} else {
				current.append(c);
			}
		}
		result.add(current.toString());
		return Collections.unmodifiableList(result);
	}

	@Deprecated
	public static String getMergedLines(List<? extends CharSequence> strings) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strings.size(); i++) {
			sb.append(strings.get(i));
			if (i < strings.size() - 1) {
				sb.append("\\n");
			}
		}
		return sb.toString();
	}

	@Deprecated
	final static public List<String> getSplit(Pattern pattern, String line) {
		final Matcher m = pattern.matcher(line);
		if (m.find() == false) {
			return null;
		}
		final List<String> result = new ArrayList<String>();
		for (int i = 1; i <= m.groupCount(); i++) {
			result.add(m.group(i));
		}
		return result;

	}

	@Deprecated
	public static boolean isNotEmpty(String input) {
		return input != null && input.trim().length() > 0;
	}

	@Deprecated
	public static boolean isNotEmpty(List<? extends CharSequence> input) {
		return input != null && input.size() > 0;
	}

	@Deprecated
	public static boolean isEmpty(String input) {
		return input == null || input.trim().length() == 0;
	}

	@Deprecated
	public static String manageHtml(String s) {
		s = s.replace("<", "&lt;");
		s = s.replace(">", "&gt;");
		return s;
	}

	@Deprecated
	public static String unicode(String s) {
		final StringBuilder result = new StringBuilder();
		for (char c : s.toCharArray()) {
			if (c > 127 || c == '&' || c == '|') {
				final int i = c;
				result.append("&#" + i + ";");
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}

	@Deprecated
	public static String unicodeForHtml(String s) {
		final StringBuilder result = new StringBuilder();
		for (char c : s.toCharArray()) {
			if (c > 127 || c == '&' || c == '|' || c == '<' || c == '>') {
				final int i = c;
				result.append("&#" + i + ";");
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}

	@Deprecated
	public static String unicodeForHtml(Display display) {
		final StringBuilder result = new StringBuilder();
		for (int i = 0; i < display.size(); i++) {
			result.append(unicodeForHtml(display.get(i).toString()));
			if (i < display.size() - 1) {
				result.append("<br>");
			}
		}
		return result.toString();
	}

	@Deprecated
	public static String manageArrowForSequence(String s) {
		s = s.replace('=', '-').toLowerCase();
		return s;
	}

	@Deprecated
	public static String capitalize(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	@Deprecated
	public static String manageArrowForCuca(String s) {
		final Direction dir = getArrowDirection(s);
		s = s.replace('=', '-');
		s = s.replaceAll("\\w*", "");
		if (dir == Direction.LEFT || dir == Direction.RIGHT) {
			s = s.replaceAll("-+", "-");
		}
		if (s.length() == 2 && (dir == Direction.UP || dir == Direction.DOWN)) {
			s = s.replaceFirst("-", "--");
		}
		return s;
	}

	@Deprecated
	public static String manageQueueForCuca(String s) {
		final Direction dir = getQueueDirection(s);
		s = s.replace('=', '-');
		s = s.replaceAll("\\w*", "");
		if (dir == Direction.LEFT || dir == Direction.RIGHT) {
			s = s.replaceAll("-+", "-");
		}
		if (s.length() == 1 && (dir == Direction.UP || dir == Direction.DOWN)) {
			s = s.replaceFirst("-", "--");
		}
		return s;
	}

	@Deprecated
	public static Direction getArrowDirection(String s) {
		if (s.endsWith(">")) {
			return getQueueDirection(s.substring(0, s.length() - 1));
		}
		if (s.startsWith("<")) {
			if (s.length() == 2) {
				return Direction.LEFT;
			}
			return Direction.UP;
		}
		throw new IllegalArgumentException(s);
	}

	@Deprecated
	public static Direction getQueueDirection(String s) {
		if (s.indexOf('<') != -1 || s.indexOf('>') != -1) {
			throw new IllegalArgumentException(s);
		}
		s = s.toLowerCase();
		if (s.contains("left")) {
			return Direction.LEFT;
		}
		if (s.contains("right")) {
			return Direction.RIGHT;
		}
		if (s.contains("up")) {
			return Direction.UP;
		}
		if (s.contains("down")) {
			return Direction.DOWN;
		}
		if (s.contains("l")) {
			return Direction.LEFT;
		}
		if (s.contains("r")) {
			return Direction.RIGHT;
		}
		if (s.contains("u")) {
			return Direction.UP;
		}
		if (s.contains("d")) {
			return Direction.DOWN;
		}
		if (s.length() == 1) {
			return Direction.RIGHT;
		}
		return Direction.DOWN;
	}

	// public static Code eventuallyRemoveStartingAndEndingDoubleQuote(Code s) {
	// return Code.of(eventuallyRemoveStartingAndEndingDoubleQuote(s.getCode()));
	// }

	@Deprecated
	public static String eventuallyRemoveStartingAndEndingDoubleQuote(String s) {
		if (s.length() > 1 && isDoubleQuote(s.charAt(0)) && isDoubleQuote(s.charAt(s.length() - 1))) {
			return s.substring(1, s.length() - 1);
		}
		if (s.startsWith("(") && s.endsWith(")")) {
			return s.substring(1, s.length() - 1);
		}
		if (s.startsWith("[") && s.endsWith("]")) {
			return s.substring(1, s.length() - 1);
		}
		if (s.startsWith(":") && s.endsWith(":")) {
			return s.substring(1, s.length() - 1);
		}
		return s;
	}

	@Deprecated
	private static boolean isDoubleQuote(char c) {
		return c == '\"' || c == '\u201c' || c == '\u201d' || c == '\u00ab' || c == '\u00bb';
	}

	@Deprecated
	public static boolean isCJK(char c) {
		final Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
		Log.println("block=" + block);
		return false;
	}

	@Deprecated
	public static char hiddenLesserThan() {
		return '\u0005';
	}

	@Deprecated
	public static char hiddenBiggerThan() {
		return '\u0006';
	}

	@Deprecated
	public static String hideComparatorCharacters(String s) {
		s = s.replace('<', hiddenLesserThan());
		s = s.replace('>', hiddenBiggerThan());
		return s;
	}

	@Deprecated
	public static String showComparatorCharacters(String s) {
		s = s.replace(hiddenLesserThan(), '<');
		s = s.replace(hiddenBiggerThan(), '>');
		return s;
	}

	@Deprecated
	public static int getWidth(List<? extends CharSequence> stringsToDisplay) {
		int result = 1;
		for (CharSequence s : stringsToDisplay) {
			if (result < s.length()) {
				result = s.length();
			}
		}
		return result;
	}

	@Deprecated
	public static int getWidth(Display stringsToDisplay) {
		int result = 1;
		for (CharSequence s : stringsToDisplay) {
			if (result < s.length()) {
				result = s.length();
			}
		}
		return result;
	}

	@Deprecated
	public static int getHeight(List<? extends CharSequence> stringsToDisplay) {
		return stringsToDisplay.size();
	}

	@Deprecated
	public static int getHeight(Display stringsToDisplay) {
		return stringsToDisplay.size();
	}

	@Deprecated
	private static void removeFirstColumn(List<String> data) {
		for (int i = 0; i < data.size(); i++) {
			final String s = data.get(i);
			if (s.length() > 0) {
				data.set(i, s.substring(1));
			}
		}
	}

	@Deprecated
	private static boolean firstColumnRemovable(List<String> data) {
		boolean allEmpty = true;
		for (String s : data) {
			if (s.length() == 0) {
				continue;
			}
			allEmpty = false;
			final char c = s.charAt(0);
			if (c != ' ' && c != '\t') {
				return false;
			}
		}
		return allEmpty == false;
	}

	@Deprecated
	public static List<String> removeEmptyColumns(List<String> data) {
		if (firstColumnRemovable(data) == false) {
			return data;
		}
		final List<String> result = new ArrayList<String>(data);
		do {
			removeFirstColumn(result);
		} while (firstColumnRemovable(result));
		return result;
	}

	@Deprecated
	public static void trim(List<String> data, boolean removeEmptyLines) {
		for (int i = 0; i < data.size(); i++) {
			final String s = data.get(i);
			data.set(i, s.trim());
		}
		if (removeEmptyLines) {
			for (final Iterator<String> it = data.iterator(); it.hasNext();) {
				if (it.next().length() == 0) {
					it.remove();
				}
			}
		}
	}

	@Deprecated
	public static String uncommentSource(String source) {
		final StringReader sr = new StringReader(source);
		final UncommentReadLine un = new UncommentReadLine(new ReadLineReader(sr));
		final StringBuilder sb = new StringBuilder();
		String s = null;
		try {
			while ((s = un.readLine()) != null) {
				sb.append(s);
				sb.append('\n');
			}
		} catch (IOException e) {
			Log.error("Error " + e);
			throw new IllegalStateException(e.toString());
		}

		sr.close();
		return sb.toString();
	}

	@Deprecated
	public static boolean isDiagramCacheable(String uml) {
		uml = uml.toLowerCase();
		if (uml.startsWith("@startuml\nversion\n")) {
			return false;
		}
		if (uml.startsWith("@startuml\ncheckversion")) {
			return false;
		}
		if (uml.startsWith("@startuml\ntestdot\n")) {
			return false;
		}
		if (uml.startsWith("@startuml\nsudoku\n")) {
			return false;
		}
		return true;
	}

	@Deprecated
	public static List<String> splitComma(String s) {
		s = s.trim();
		// if (s.matches("([\\p{L}0-9_.]+|[%g][^%g]+[%g])(\\s*,\\s*([\\p{L}0-9_.]+|[%g][^%g]+[%g]))*") == false) {
		// throw new IllegalArgumentException();
		// }
		final List<String> result = new ArrayList<String>();
		final Pattern p = MyPattern.cmpile("([\\p{L}0-9_.]+|[%g][^%g]+[%g])");
		final Matcher m = p.matcher(s);
		while (m.find()) {
			result.add(eventuallyRemoveStartingAndEndingDoubleQuote(m.group(0)));
		}
		return Collections.unmodifiableList(result);
	}

	@Deprecated
	public static String getAsHtml(Color color) {
		if (color == null) {
			return null;
		}
		return getAsHtml(color.getRGB());
	}

	@Deprecated
	public static String getAsSvg(ColorMapper mapper, HtmlColor color) {
		if (color == null) {
			return "none";
		}
		if (color instanceof HtmlColorTransparent) {
			return "#FFFFFF";
		}
		return getAsHtml(mapper.getMappedColor(color));
	}

	@Deprecated
	public static String getAsHtml(int color) {
		final int v = 0xFFFFFF & color;
		String s = "000000" + Integer.toHexString(v).toUpperCase();
		s = s.substring(s.length() - 6);
		return "#" + s;
	}

	@Deprecated
	public static String getUid(String uid1, int uid2) {
		return uid1 + String.format("%04d", uid2);
	}


	@Deprecated
	public static boolean isMethod(String s) {
		return s.contains("(") || s.contains(")");
	}

	@Deprecated
	public static <O> List<O> merge(List<O> l1, List<O> l2) {
		final List<O> result = new ArrayList<O>(l1);
		result.addAll(l2);
		return Collections.unmodifiableList(result);
	}

	@Deprecated
	public static boolean endsWithBackslash(final String s) {
		return s.endsWith("\\") && s.endsWith("\\\\") == false;
	}


}
