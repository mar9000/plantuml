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
package net.sourceforge.plantuml.preproc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.plantuml.FileSystem;
import net.sourceforge.plantuml.Log;
import net.sourceforge.plantuml.OptionFlags;
import net.sourceforge.plantuml.command.regex.MyPattern;
import net.sourceforge.plantuml.utils.StringUtils;

class PreprocessorInclude implements ReadLine {

	private static final Pattern includePattern = MyPattern.cmpile("^[%s]*!include[%s]+[%g]?([^%g]+)[%g]?$");

	private final ReadLine reader2;
	private final String charset;
	private final Defines defines;

	private int numLine = 0;

	private PreprocessorInclude included = null;

	private final File oldCurrentDir;
	private final Set<File> filesUsed;

	public PreprocessorInclude(ReadLine reader, Defines defines, String charset, Set<File> filesUsed, File newCurrentDir) {
		this.defines = defines;
		this.charset = charset;
		this.reader2 = reader;
		this.filesUsed = filesUsed;
		if (newCurrentDir == null) {
			oldCurrentDir = null;
		} else {
			oldCurrentDir = FileSystem.getInstance().getCurrentDir();
			FileSystem.getInstance().setCurrentDir(newCurrentDir);
		}
	}

	private void restoreCurrentDir() {
		if (oldCurrentDir != null) {
			FileSystem.getInstance().setCurrentDir(oldCurrentDir);
		}
	}

	public String readLine() throws IOException {
		if (included != null) {
			final String s = included.readLine();
			if (s != null) {
				return s;
			}
			included.close();
			included = null;
		}

		final String s = reader2.readLine();
		numLine++;
		if (s == null) {
			return null;
		}
		if (OptionFlags.ALLOW_INCLUDE) {
			final Matcher m = includePattern.matcher(s);
			assert included == null;
			if (m.find()) {
				return manageInclude(m);
			}
		}

		return s;

	}

	private String manageInclude(Matcher m) throws IOException {
		String fileName = m.group(1);
		fileName = defines.applyDefines(fileName).get(0);
		final int idx = fileName.lastIndexOf('!');
		String suf = null;
		if (idx != -1) {
			suf = fileName.substring(idx + 1);
			fileName = fileName.substring(0, idx);
		}
		final File f = FileSystem.getInstance().getFile(withEnvironmentVariable(fileName));
		if (f.exists()) {
			filesUsed.add(f);
			included = new PreprocessorInclude(getReaderInclude(f, suf), defines, charset, filesUsed, f.getParentFile());
		} else {
			return "Cannot include " + f.getAbsolutePath();
		}
		return this.readLine();
	}

	static String withEnvironmentVariable(String s) {
		final Pattern p = Pattern.compile("%(\\w+)%");

		final Matcher m = p.matcher(s);
		final StringBuffer sb = new StringBuffer();
		while (m.find()) {
			final String var = m.group(1);
			final String value = getenv(var);
			if (value != null) {
				m.appendReplacement(sb, Matcher.quoteReplacement(value));
			}
		}
		m.appendTail(sb);
		s = sb.toString();
		return s;
	}

	private static String getenv(String var) {
		final String env = System.getProperty(var);
		if (StringUtils.isNotEmpty(env)) {
			return StringUtils.eventuallyRemoveStartingAndEndingDoubleQuote(env);
		}
		final String getenv = System.getenv(var);
		if (StringUtils.isNotEmpty(getenv)) {
			return StringUtils.eventuallyRemoveStartingAndEndingDoubleQuote(getenv);
		}
		return null;
	}

	private ReadLine getReaderInclude(final File f, String suf) throws IOException {
		if (StartDiagramExtractReader.containsStartDiagram(f, charset)) {
			int bloc = 0;
			if (suf != null && suf.matches("\\d+")) {
				bloc = Integer.parseInt(suf);
			}
			return new StartDiagramExtractReader(f, bloc, charset);
		}
		// if (f != null) {
		// final Throwable t = new Throwable();
		// t.fillInStackTrace();
		// final List<String> li = new ArrayList<String>();
		// li.add("charset=" + charset);
		// for (StackTraceElement e : t.getStackTrace()) {
		// li.add(e.toString());
		// }
		// return new StackReadLine(li);
		// }
		if (charset == null) {
			Log.info("Using default charset");
			return new ReadLineReader(new FileReader(f));
		}
		Log.info("Using charset " + charset);
		return new ReadLineReader(new InputStreamReader(new FileInputStream(f), charset));
	}

	public int getLineNumber() {
		return numLine;
	}

	public void close() throws IOException {
		restoreCurrentDir();
		reader2.close();
	}

}
