package edu.upenn.cis455.searchengine.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class saveDocId
 */
public class saveDocId extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static long totalNum = 0L;
    private static int fileNum = 0;
    private static NumberFormat numberFormat = new DecimalFormat("000");
	private static PrintWriter writer = null;
	private static final int indexedNum = 17;  //TODO indexer number
	private static int receivedIndexer = 0;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public saveDocId() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String content = request.getParameter("content");
		String end = request.getParameter("end");
		if(end == null) {
			if(content != null) {
				receivedIndexer ++;
				if(receivedIndexer == indexedNum) {
					this.close();
				}
			}
			else {
				String from = request.getParameter("url1");
				String to = request.getParameter("url2");
				String[] tos = to.split(",");
				File file = new File("/home/muruoliu/in/part" + numberFormat.format(fileNum));
				if(!file.exists()) {
					file.createNewFile();
					writer = new PrintWriter(file);
				}
				for(String id: tos) {
					writer.append(from + "\t" + id + "\n");
					writer.flush();
					totalNum ++;
				}
				if(totalNum > 100000) {
					totalNum = 0;
					fileNum ++;
					writer.close();
				}
			}
		}
		else {
			this.close();				
		}
	}
	
	private void close(){
		writer.flush();
		writer.close();
		totalNum = 0L;
		fileNum = 0;
		receivedIndexer = 0;
	}

}
