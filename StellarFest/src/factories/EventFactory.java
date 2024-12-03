package factories;

import models.Event;

public class EventFactory {
	public static Event create(String event_id, String event_name, String event_date, String event_location, String event_description, String organizer_id) {
		return new Event(event_id, event_name, event_date, event_location, event_description, organizer_id);
	}

}
