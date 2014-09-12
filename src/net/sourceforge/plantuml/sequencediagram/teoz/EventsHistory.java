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
package net.sourceforge.plantuml.sequencediagram.teoz;

import java.util.List;

import net.sourceforge.plantuml.sequencediagram.Event;
import net.sourceforge.plantuml.sequencediagram.LifeEvent;
import net.sourceforge.plantuml.sequencediagram.LifeEventType;
import net.sourceforge.plantuml.sequencediagram.Message;
import net.sourceforge.plantuml.sequencediagram.Participant;

public class EventsHistory {

	private final Participant p;
	private final List<Event> events;

	// private final Stairs stairs = new Stairs();
	//
	// public void addLifeEvent(Tile tile, LifeEvent lifeEvent) {
	// if (lifeEvent.getParticipant() != p) {
	// return;
	// }
	// System.err.println("Adding " + lifeEvent);
	// }
	//
	// int getLevel(double y) {
	// return stairs.getValue(y);
	// }
	//
	// public double getRightShift(double y) {
	// return getRightShiftAtLevel(getLevel(y));
	// }
	//
	// public double getLeftShift(double y) {
	// return getLeftShiftAtLevel(getLevel(y));
	// }
	//
	// private final double nominalPreferredWidth = 10;
	//
	// private double getRightShiftAtLevel(int level) {
	// if (level == 0) {
	// return 0;
	// }
	// return level * (nominalPreferredWidth / 2.0);
	// }
	//
	// private double getLeftShiftAtLevel(int level) {
	// if (level == 0) {
	// return 0;
	// }
	// return nominalPreferredWidth / 2.0;
	// }

	public EventsHistory(Participant p, List<Event> events) {
		this.p = p;
		this.events = events;
	}

	public Participant getParticipant() {
		return p;
	}

	public int getLevelAt(Event event) {
		int level = p.getInitialLife();
		for (Event current : events) {
			if (current instanceof Message) {
				final Message msg = (Message) current;
				for (LifeEvent le : msg.getLiveEvents()) {
					if (le.getParticipant() == p && le.getType() == LifeEventType.ACTIVATE) {
						level++;
					}
					if (le.getParticipant() == p && le.getType() == LifeEventType.DEACTIVATE) {
						level--;
					}
				}
			}
			if (event == current) {
				return level;
			}
		}
		return level;
	}

	private int getLevelAtOld(Event event) {
		int level = p.getInitialLife();
		for (Event current : events) {
			if (current instanceof Message) {
				final Message msg = (Message) current;
				for (LifeEvent le : msg.getLiveEvents()) {
					if (le.getParticipant() == p && le.getType() == LifeEventType.ACTIVATE) {
						level++;
					}
				}
			}
			if (event == current) {
				return level;
			}
			if (current instanceof Message) {
				final Message msg = (Message) current;
				for (LifeEvent le : msg.getLiveEvents()) {
					if (le.getParticipant() == p && le.getType() == LifeEventType.DEACTIVATE) {
						level--;
					}
				}
			}
		}
		return level;
	}

	// public final LivingSpace getNextTOBEREMOVED() {
	// return next;
	// }
	//
	// final void setNext(LivingSpace next) {
	// this.next = next;
	// }

}
