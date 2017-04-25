package com.hkmci;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.youtube.YouTubeScopes;

public class CalendarSample {

	private static final String APPLICATION_NAME = "Google-CalendarSample/1.0";
	private static final String SERVICE_ACCOUNT_EMAIL = "92008121558@developer.gserviceaccount.com";
	private static final String SERVICE_ACCOUNT_PRIVATE_KEY = "/Users/brianwang/Workspaces/Eclipse/ANZSeminar/resources/c67458d8a8fb4c71a871a9fb635a85144f3dc242-privatekey.p12";
	private static final String SERVICE_ACCOUNT_USER = "dennis@anzdemo.mygbiz.com";
	
	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	
	private static com.google.api.services.calendar.Calendar calendar;

	public static void main(String[] args) {
		try {
			GoogleCredential credential = new GoogleCredential.Builder()
				.setTransport(HTTP_TRANSPORT)
				.setJsonFactory(JSON_FACTORY)
				.setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
				.setServiceAccountScopes(CalendarScopes.CALENDAR)
				.setServiceAccountPrivateKeyFromP12File(new File(SERVICE_ACCOUNT_PRIVATE_KEY))
				.setServiceAccountUser(SERVICE_ACCOUNT_USER)
				.build();
			
			calendar = new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME)
				.build();
			
			Date startDate = new Date();
			Date endDate = new Date(startDate.getTime() + 3600000);
		    DateTime start = new DateTime(startDate, TimeZone.getTimeZone("Asia/Taipei"));
		    DateTime end = new DateTime(endDate, TimeZone.getTimeZone("Asia/Taipei"));
		   
			Event event = new Event();
			event.setSummary("Test...");
			event.setStart(new EventDateTime().setDateTime(start));
			event.setEnd(new EventDateTime().setDateTime(end));
			event.setDescription("<a href='http://code.google.com'>http://code.google.com</a>");
			
			Event createEvent = calendar.events().insert("primary", event).execute();
			
			System.out.println(createEvent.getId());
			
//			Event event = new Event();
//			event.setSummary("Appointment");
//			event.setLocation("Somewhere");
//
//			ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
//			attendees.add(new EventAttendee().setEmail("brian.wang@hkmci.com"));
//			event.setAttendees(attendees);
//
//			DateTime start = DateTime.parseRfc3339("2013-05-20T10:00:00.000-07:00");
//			DateTime end = DateTime.parseRfc3339("2013-05-20T10:25:00.000-07:00");
//			event.setStart(new EventDateTime().setDateTime(start).setTimeZone("America/Los_Angeles"));
//			event.setEnd(new EventDateTime().setDateTime(end).setTimeZone("America/Los_Angeles"));
//			event.setRecurrence(Arrays.asList("RRULE:FREQ=WEEKLY;UNTIL=20110701T100000-07:00"));
//
//			Event createdEvent = calendar.events().insert("primary", event).execute();
//
//			System.out.println(createdEvent.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
