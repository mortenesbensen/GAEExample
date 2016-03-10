package dk.itu.pervasive.gae;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class ExampleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	DatastoreService dss;

	/*
	 * Handles HTTP GET request to this servlet
	 * 
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		// Get parameters
		int id = Integer.parseInt(req.getParameter("id"));

		// Create query
		Query q = new Query("NameEntity");
		Filter f = new FilterPredicate("id", FilterOperator.EQUAL, id);
		q.setFilter(f);

		// Execute query
		dss = DatastoreServiceFactory.getDatastoreService();
		Entity queryResults = dss.prepare(q).asSingleEntity();

		// Write output
		resp.getWriter().write(queryResults.getProperty("name").toString());
	}

	/*
	 * Handles HTTP POST request to this servlet
	 * 
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// Get parameters

		String name = req.getParameter("name");
		int id = Integer.parseInt(req.getParameter("id"));

		// Create entity
		Entity e = new Entity("NameEntity");
		e.setProperty("id", id);
		e.setProperty("name", name);

		// Store in datastore
		dss = DatastoreServiceFactory.getDatastoreService();
		dss.put(e);
	}
}
