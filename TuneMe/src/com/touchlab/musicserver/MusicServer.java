package com.touchlab.musicserver;

import java.io.*;
import java.util.*;

/**
 * An example of subclassing NanoHTTPD to make a custom HTTP server.
 */
public class MusicServer extends NanoHTTPD {
	public MusicServer(File dir) throws IOException {
		super(8080, dir);
		
	}

	public Response serve(String uri, String method, Properties header,
			Properties parms, Properties files) {
		return super.serve(uri, method, header, parms, files);
		
//		System.out.println(method + " '" + uri + "' ");
//		String msg = "<html><body><h1>Hello server</h1>\n";
//		if (parms.getProperty("username") == null)
//			msg += "<form action='?' method='get'>\n"
//					+ "  <p>Your name: <input type='text' name='username'></p>\n"
//					+ "</form>\n";
//		else
//			msg += "<p>Hello, " + parms.getProperty("username") + "!</p>";
//
//		msg += "</body></html>\n";
//		return new NanoHTTPD.Response(HTTP_OK, MIME_HTML, msg);
	}

	
}
