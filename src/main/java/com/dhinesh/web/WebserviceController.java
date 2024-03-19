package com.dhinesh.web;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
//import org.apache.log4j.Logger;

public class WebserviceController extends HttpServlet {


	private static final long serialVersionUID = 1L;
	//protected final static	Logger log = Logger.getLogger("HealthCheck");

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {				
		String url= request.getParameter("url");
		String wsrequest= request.getParameter("wsrequest").trim();
		//log.info("1:" +url + wsrequest);
		System.out.println("Test1:" +url + wsrequest);
		final HttpClient client = new HttpClient();
		PostMethod httpPost = null;
		httpPost = new PostMethod(url); 
		InputStream is = new ByteArrayInputStream(wsrequest.getBytes());
		RequestEntity entity = new InputStreamRequestEntity(is,"text/xml;charset=UTF-8"); 
		httpPost.setRequestEntity(entity);
		httpPost.setRequestHeader("SOAPAction", "\"\""); 

		// execute method and handle any error responses.
		//StringBuffer responseBuffer = new StringBuffer();
		String output ="";
		try {
			client.executeMethod(httpPost);
			InputStream in = httpPost.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				//responseBuffer.append(line);
				output = output +"\n" + line;
			}
			br.close();
						
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();			
		} finally {
			httpPost.releaseConnection();
		}
		//response.setContentType("text/plain;charset=UTF-8");		
		//response.setContentType("text/plain;charset=UTF-8");		
		response.getWriter().print("<html> <head><title>Blue2 Web Service Testing");
		response.getWriter().print("</title></head><body><h3>Blue2 Web Service Testing </h3><form method='post' action='Blue2Webservice'>");
		response.getWriter().print("<h4>URL</h4><input type='textfield' name='url' size='100' value='" + url +"'/>");
		response.getWriter().print("<table> <tr> <td> <h4>Web Service Request</h4></td> ");
		response.getWriter().print("<td> <h4>Web Service Response</h4></td> </tr><tr>");
		response.getWriter().print("<td><textarea rows='30' cols='80' name='wsrequest'>" +wsrequest+ "</textarea></td>");
		//response.getWriter().print("<td><textarea rows='30' cols='80' name='wsresponse'>" +responseBuffer.toString()+ "</textarea></td>");
		response.getWriter().print("<td><textarea rows='30' cols='80' name='wsresponse'>" +output+ "</textarea></td>");
		response.getWriter().print("</tr></table><input type='submit' value='submit'/>");
		response.getWriter().print("</form></body></html>");
		
		
	}
	
}

