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

import net.sourceforge.plantuml.api.HealthCheck;
import net.sourceforge.plantuml.api.Performance;

public abstract class Log {

	private static final long start = System.currentTimeMillis();

	public synchronized static void debug(String s) {
	}

	public synchronized static void info(String s) {
		if (OptionFlags.getInstance().isVerbose()) {
			System.out.println(format(s));
		}
	}

	public synchronized static void error(String s) {
		System.err.println(s);
	}

	private static String format(String s) {
		final long delta = System.currentTimeMillis() - start;
		final HealthCheck healthCheck = Performance.getHealthCheck();
		final long cpu = healthCheck.jvmCpuTime() / 1000L / 1000L;
		final long dot = healthCheck.dotTime().getSum();
		final StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(delta / 1000L);
		sb.append(".");
		sb.append(String.format("%03d", delta % 1000L));
		if (cpu != -1) {
			sb.append(" - ");
			sb.append(cpu / 1000L);
			sb.append(".");
			sb.append(String.format("%03d", cpu % 1000L));
		}
		sb.append(" - ");
		sb.append(dot / 1000L);
		sb.append(".");
		sb.append(String.format("%03d", dot % 1000L));
		sb.append("(");
		sb.append(healthCheck.dotTime().getNb());
		sb.append(")");
		sb.append(" - ");
		final long total = (healthCheck.totalMemory()) / 1024 / 1024;
		final long free = (healthCheck.freeMemory()) / 1024 / 1024;
		sb.append(total);
		sb.append(" Mo) ");
		sb.append(free);
		sb.append(" Mo - ");
		sb.append(s);
		return sb.toString();

	}

	public static void println(Object s) {
		if (header == null) {
			System.err.println("L = " + s);
		} else {
			System.err.println(header + " " + s);
		}
	}

	private static String header;

	public static void header(String s) {
		header = s;
	}
}
