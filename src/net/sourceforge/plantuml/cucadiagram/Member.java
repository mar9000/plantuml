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
package net.sourceforge.plantuml.cucadiagram;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.plantuml.Url;
import net.sourceforge.plantuml.UrlBuilder;
import net.sourceforge.plantuml.UrlBuilder.ModeUrl;
import net.sourceforge.plantuml.command.regex.MyPattern;
import net.sourceforge.plantuml.skin.VisibilityModifier;

public class Member {

	private final String display;
	private final boolean staticModifier;
	private final boolean abstractModifier;
	private final Url url;
	private final boolean hasUrl;

	private final VisibilityModifier visibilityModifier;

	public Member(String display, boolean isMethod, boolean manageModifier) {
		this.hasUrl = new UrlBuilder(null, ModeUrl.ANYWHERE).getUrl(display) != null;
		final Pattern pstart = MyPattern.cmpile("^(" + UrlBuilder.getRegexp() + ")([^\\[\\]]+)$");
		final Matcher mstart = pstart.matcher(display);

		if (mstart.matches()) {
			if (mstart.groupCount() != 5) {
				throw new IllegalStateException();
			}
			final UrlBuilder urlBuilder = new UrlBuilder(null, ModeUrl.AT_START);
			url = urlBuilder.getUrl(mstart.group(1));
			url.setMember(true);
			display = /* mstart.group(1).trim() + */mstart.group(mstart.groupCount()).trim();
		} else {
			final Pattern pend = MyPattern.cmpile("^([^\\[\\]]+)(" + UrlBuilder.getRegexp() + ")$");
			final Matcher mend = pend.matcher(display);

			if (mend.matches()) {
				if (mend.groupCount() != 5) {
					throw new IllegalStateException();
				}
				final UrlBuilder urlBuilder = new UrlBuilder(null, ModeUrl.AT_END);
				url = urlBuilder.getUrl(mend.group(2));
				url.setMember(true);
				display = mend.group(1).trim();
			} else {
				url = null;
			}
		}

		final String lower = display.toLowerCase();

		if (manageModifier) {
			this.staticModifier = lower.contains("{static}") || lower.contains("{classifier}");
			this.abstractModifier = lower.contains("{abstract}");
			String displayClean = display.replaceAll("(?i)\\{(static|classifier|abstract)\\}", "").trim();
			if (displayClean.length() == 0) {
				displayClean = " ";
			}

			if (VisibilityModifier.isVisibilityCharacter(displayClean.charAt(0))) {
				visibilityModifier = VisibilityModifier.getVisibilityModifier(display.charAt(0), isMethod == false);
				this.display = displayClean.substring(1).trim();
			} else {
				this.display = displayClean;
				visibilityModifier = null;
			}
		} else {
			this.staticModifier = false;
			this.visibilityModifier = null;
			this.abstractModifier = false;
			display = display.trim();
			this.display = display.length() == 0 ? " " : display.trim();
		}
	}

	public String getDisplay(boolean withVisibilityChar) {
		if (withVisibilityChar) {
			return getDisplayWithVisibilityChar();
		}
		return getDisplayWithoutVisibilityChar();
	}

	public String getDisplayWithoutVisibilityChar() {
		// assert display.length() == 0 || VisibilityModifier.isVisibilityCharacter(display.charAt(0)) == false;
		return display;
	}

	public String getDisplayWithVisibilityChar() {
		if (isPrivate()) {
			return "-" + display;
		}
		if (isPublic()) {
			return "+" + display;
		}
		if (isPackagePrivate()) {
			return "~" + display;
		}
		if (isProtected()) {
			return "#" + display;
		}
		return display;
	}

	@Override
	public boolean equals(Object obj) {
		final Member other = (Member) obj;
		return this.display.equals(other.display);
	}

	@Override
	public int hashCode() {
		return display.hashCode();
	}

	public final boolean isStatic() {
		return staticModifier;
	}

	public final boolean isAbstract() {
		return abstractModifier;
	}

	private boolean isPrivate() {
		return visibilityModifier == VisibilityModifier.PRIVATE_FIELD
				|| visibilityModifier == VisibilityModifier.PRIVATE_METHOD;
	}

	private boolean isProtected() {
		return visibilityModifier == VisibilityModifier.PROTECTED_FIELD
				|| visibilityModifier == VisibilityModifier.PROTECTED_METHOD;
	}

	private boolean isPublic() {
		return visibilityModifier == VisibilityModifier.PUBLIC_FIELD
				|| visibilityModifier == VisibilityModifier.PUBLIC_METHOD;
	}

	private boolean isPackagePrivate() {
		return visibilityModifier == VisibilityModifier.PACKAGE_PRIVATE_FIELD
				|| visibilityModifier == VisibilityModifier.PACKAGE_PRIVATE_METHOD;
	}

	public final VisibilityModifier getVisibilityModifier() {
		return visibilityModifier;
	}

	public final Url getUrl() {
		return url;
	}

	public boolean hasUrl() {
		return hasUrl;
	}

}
