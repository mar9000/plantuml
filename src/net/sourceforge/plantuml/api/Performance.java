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
package net.sourceforge.plantuml.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import net.sourceforge.plantuml.Log;
import net.sourceforge.plantuml.PSystemBuilder;

public class Performance {

	// private final static AtomicLong dotDuration = new AtomicLong(0);
	private final static AtomicInteger maxThreadActiveCount = new AtomicInteger(0);
	// private final static AtomicInteger dotCount = new AtomicInteger(0);
	// private final static CountRate diagramCountRate = new CountRate();
	private final static AtomicInteger diagramCount = new AtomicInteger(0);
	private final static AtomicInteger dotInterruption1 = new AtomicInteger(0);
	private final static AtomicInteger dotInterruption2 = new AtomicInteger(0);
	private final static AtomicInteger dotInterruption3 = new AtomicInteger(0);

	private final static NumberAnalyzed dotTime = new NumberAnalyzed();

	public static void updateDotTime2(long duration) {
		dotTime.addValue((int) duration);
	}

	// public static void updateDotTime(long duration) {
	// dotDuration.addAndGet(duration);
	// }
	//
	// public static void incDotCount() {
	// dotCount.incrementAndGet();
	// }

	public static void incDotInterruption1() {
		dotInterruption1.incrementAndGet();
	}

	public static void incDotInterruption2() {
		dotInterruption2.incrementAndGet();
	}

	public static void incDotInterruption3() {
		dotInterruption3.incrementAndGet();
	}

	public static void incDiagramCount() {
		diagramCount.incrementAndGet();

		boolean done;
		do {
			final int max = maxThreadActiveCount.get();
			final int current = Thread.activeCount();
			if (max < current) {
				done = maxThreadActiveCount.compareAndSet(max, current);
			} else {
				done = true;
			}
		} while (done == false);

		// diagramCountRate.tick();
	}

	public static HealthCheck getHealthCheck() {
		final long timeStamp = System.currentTimeMillis();
		final long startTime = PSystemBuilder.startTime;
		// final long runningTime = timeStamp - PSystemBuilder.startTime;
		final int availableProcessors = Runtime.getRuntime().availableProcessors();
		final long freeMemory = Runtime.getRuntime().freeMemory();
		final long maxMemory = Runtime.getRuntime().maxMemory();
		final long totalMemory = Runtime.getRuntime().totalMemory();
		final long usedMemory = totalMemory - freeMemory;
		final int threadActiveCount = Thread.activeCount();
		final int maxThreadActiveCountValue = maxThreadActiveCount.get();

		// final int dotCountValue = dotCount.get();
		final int diagramCountValue = diagramCount.get();

		// final int diagramPerMinute = (int) diagramCountRate.perMinute();
		//
		// final int diagramPerHour = (int) diagramCountRate.perHour();

		// final long dotTimeValue = dotDuration.get() / 1000L / 1000L;
		final INumberAnalyzed dotCopy = dotTime.getCopyImmutable();

		long jvmCpuTimeTmp = 0;
		try {
			final Class<?> cl = Class.forName("com.sun.management.OperatingSystemMXBean");
			final OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean();
			if (bean != null && cl.isAssignableFrom(bean.getClass()) ) {
				final Method getProcessCpuTime = cl.getMethod("getProcessCpuTime");
				final Long result = (Long) getProcessCpuTime.invoke(bean);
				jvmCpuTimeTmp = result / 1000L / 1000L;
			}
		} catch (ClassNotFoundException e) {
			Log.debug("Cannot load com.sun.management.OperatingSystemMXBean");
		} catch (Exception e) {
			Log.debug("Exception "+e);
		}

		final long jvmCpuTime = jvmCpuTimeTmp;

		final String pid = ManagementFactory.getRuntimeMXBean().getName();

		final int dotInterruption1Value = dotInterruption1.get();
		final int dotInterruption2Value = dotInterruption2.get();
		final int dotInterruption3Value = dotInterruption3.get();

		return new HealthCheck() {

			public long totalMemory() {
				return totalMemory;
			}

			public int threadActiveCount() {
				return threadActiveCount;
			}

			public long maxMemory() {
				return maxMemory;
			}

			public long freeMemory() {
				return freeMemory;
			}

			public int availableProcessors() {
				return availableProcessors;
			}

			public long jvmCpuTime() {
				return jvmCpuTime;
			}

			// public int dotCount() {
			// return dotCountValue;
			// }
			//
			// public long totalDotTime() {
			// return dotTimeValue;
			// }

			public long timeStamp() {
				return timeStamp;
			}

			public String pid() {
				return pid;
			}

			public long usedMemory() {
				return usedMemory;
			}

			public long runningTime() {
				return timeStamp - startTime;
			}

			public int diagramCount() {
				return diagramCountValue;
			}

			public int dotInterruption1() {
				return dotInterruption1Value;
			}

			public int dotInterruption2() {
				return dotInterruption2Value;
			}

			public int dotInterruption3() {
				return dotInterruption3Value;
			}

			public long startTime() {
				return startTime;
			}

			public int maxThreadActiveCount() {
				return maxThreadActiveCountValue;
			}

			public INumberAnalyzed dotTime() {
				return dotCopy;
			}

			// public int diagramPerMinute() {
			// return diagramPerMinute;
			// }
			//
			// public int diagramPerHour() {
			// return diagramPerHour;
			// }
		};
	}

	public static void printHtml(HealthCheck health) throws IOException {
		final PrintWriter pw = new PrintWriter("plantumlPerformance.html");
		pw.println("<html>");
		pw.println("<table border=1 cellpadding=4 cellspacing=0>");
		printHtmlTable(pw, health);
		pw.println("</table>");
		pw.println("</html>");
		pw.close();
	}

	public static void printHtmlTable(final Writer writer, HealthCheck health) {
		final PrintWriter pw = new PrintWriter(writer);
		printHtmlOut(pw, "PID", health.pid());
		printHtmlOut(pw, "AvailableProcessors", health.availableProcessors());
		printHtmlOut(pw, "Timestamp", new Date(health.timeStamp()));
		printHtmlOut(pw, "RunningTime", format(health.runningTime()));
		printHtmlOut(pw, "JvmCpuTime", format(health.jvmCpuTime()));
		printHtmlOut(pw, "TotalDotTime", format(health.dotTime().getSum()));
		printHtmlOut(pw, "ThreadActiveCount", health.threadActiveCount());
		printHtmlOut(pw, "MaxMemory", format(health.maxMemory()));
		printHtmlOut(pw, "TotalMemory", format(health.totalMemory()));
		printHtmlOut(pw, "UsedMemory", format(health.usedMemory()));
		printHtmlOut(pw, "FreeMemory", format(health.freeMemory()));
		printHtmlOut(pw, "DiagramCount", health.diagramCount());
		// printHtmlOut(pw, "DiagramPerMinute", health.diagramPerMinute());
		// printHtmlOut(pw, "DiagramPerHour", health.diagramPerHour());
		printHtmlOut(pw, "DotCount", health.dotTime().getNb());
		printHtmlOut(pw, "DotInterruption1", health.dotInterruption1());
		printHtmlOut(pw, "DotInterruption2", health.dotInterruption2());
		printHtmlOut(pw, "DotInterruption3", health.dotInterruption3());
		pw.flush();
	}

	public static String format(final long v) {
		final DecimalFormat df = new DecimalFormat();
		df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		df.setGroupingSize(3);
		df.setMaximumFractionDigits(0);
		final String t = df.format(v).replace(',', ' ');
		return t;
	}

	public static void printHtmlOut(final PrintWriter pw, String name, Object data) {
		pw.println("<tr>");
		pw.println("<td><b>" + name + "</b></td>");
		pw.println("<td align=right>" + data + "</td>");
		pw.println("</tr>");
		pw.println("<tr>");
	}
}
