package edu.upenn.cis455.searchengine.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class StartSearchServlet
 */
public class StartSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String myForm = "<html><head><title>Homepage</title></head><body>"
				+ "<h1> Welcome to my Search Engine!</h1>"
				+ "<form name='search' action='startsearch' method='post'>"
				+ "<input type='text' name='searchwords'>"
				+ "<input type='submit' value='Look up!'></form>"
				+ "</body></html>";
//		ServletContext servletContext = this.getServletContext();
//		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/jsp/index.jsp");
//		requestDispatcher.forward(request, response);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(myForm);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
