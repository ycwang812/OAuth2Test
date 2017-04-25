package com.hkmci;

import java.io.File;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.YouTube.Videos;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

public class YouTubeASample {
	
	/**
	 * Be sure to specify the name of your application. If the application name is {@code null} or
	 * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
	 */
	private static final String APPLICATION_NAME = "Google-YouTubeSample/1.0";
	private static final String SERVICE_ACCOUNT_EMAIL = "92008121558@developer.gserviceaccount.com";
	private static final String SERVICE_ACCOUNT_PRIVATE_KEY = "/Users/brianwang/Workspaces/Eclipse/OAuth2Test/resources/c67458d8a8fb4c71a871a9fb635a85144f3dc242-privatekey.p12";
	private static final String SERVICE_ACCOUNT_USER = "dennis@anzdemo.mygbiz.com";
	
	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	
	/** Global instance of Youtube object to make all API requests. */
	private static YouTube youtube;
	
	public static void main(String[] args) {	
		// service account credential (uncomment setServiceAccountUser for domain-wide delegation)
		try {
			GoogleCredential credential = new GoogleCredential.Builder()
				.setTransport(HTTP_TRANSPORT)
				.setJsonFactory(JSON_FACTORY)
				.setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
				.setServiceAccountScopes(YouTubeScopes.YOUTUBE)
				.setServiceAccountPrivateKeyFromP12File(new File(SERVICE_ACCOUNT_PRIVATE_KEY))
				.setServiceAccountUser(SERVICE_ACCOUNT_USER)
				.build();
			
			// YouTube object used to make all API requests.
			youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME)
				.build();
			
			Videos videos = youtube.videos();
			VideoListResponse vlr = videos.list("Gei1ydh0GWQ", "status").execute();
			
			System.out.println("Begin:");
			
			for(Video v:vlr.getItems()) {
				System.out.println("Id:" + v.getId());
				System.out.println("Privacy:" + v.getStatus().getPrivacyStatus());
				v.getStatus().setPrivacyStatus("unlisted");
				System.out.println("Privacy:" + v.getStatus().getPrivacyStatus());
				videos.update("status", v).execute();			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
