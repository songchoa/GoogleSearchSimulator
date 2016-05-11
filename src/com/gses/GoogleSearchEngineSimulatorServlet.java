package com.gses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilePermission;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.*;


@SuppressWarnings("serial")
public class GoogleSearchEngineSimulatorServlet extends HttpServlet {
	private final int MOST_QUALIFIED = 5;
	private final int SECOND_QUALIFIED = 2;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		String output = request.getParameter("keywords");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		String keywords = request.getParameter("keywords");
		ArrayList<QualifiedLinks> result = scanDatabaseWithKeywords(keywords);
		
		if(result.size() > 0) {
			rankLinks(result);
//			for(QualifiedLinks q : result) {
//				insertHighlighter(q.getFileName(), keywords);
//			}
			
			String out = generateJson(result);
			
			request.setAttribute("linkshere", out);
			
			try {
				request.getRequestDispatcher("jsp/forward.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		} else {
			try {
				request.getRequestDispatcher("jsp/noresult.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private ArrayList<String> getFileNames(String sDir){
		ArrayList<String> fnames = new ArrayList<String>();
		File[] faFiles = new File(sDir).listFiles();
		for(File file: faFiles){
		  if(file.getName().matches("^(.*?)")){
			    //fnames.add(file.getName());
			    fnames.add(file.getAbsolutePath());
		  }
//		  if(file.isDirectory()){
//		    printFnames(file.getAbsolutePath());
//		  }
		}
			return fnames;
	}
	
	private ArrayList<QualifiedLinks> scanDatabaseWithKeywords(String k) {
		ArrayList<String> filenames = getFileNames("database");
		ArrayList<QualifiedLinks> qLinks = new ArrayList<QualifiedLinks>();
		
		for(String s : filenames) {
			if(isValidExtension(s)) {
				QualifiedLinks qualified = getQualifiedLink(s, k);
				if(qualified != null) {
					if(qualified.getScore() != 0) {
						qLinks.add(qualified);
					}
				}
			}	
		}
		
		
		return qLinks;
	}
	
	private boolean isValidExtension(String filename) {
		
		if(filename.substring(filename.length() - 5).equals(".html")) {
			return true;
		}
		
		return false;
	}
	
	private QualifiedLinks getQualifiedLink(String fname, String keywords) {
		
		QualifiedLinks qualified = null;
		
		try {
			
			int score = 0;
			
			File f = new File(fname);
			
			Scanner scan = new Scanner(f);
			
			scan.useDelimiter("\\Z");
			
			String content = scan.next();
			
			String tempTitle = getTitle(content);

			content = content.substring(content.indexOf(">", content.indexOf("<body>")) + 1, content.indexOf("</body>"));
			
			int occurrs = getOcurrences(content,keywords);
			
			score += occurrs * MOST_QUALIFIED;
			
			String keywordsParts[] = keywords.split(" ");
			
			for(String key: keywordsParts) {
				score += getOcurrences(content,key);
			}
			
			String title = tempTitle == null ? fname:tempTitle;
			
			qualified = new QualifiedLinks(score, title, fname);
			 
			scan.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return qualified;
	}
	
	private int getOcurrences(String content, String kws) {
		String str = content;
    	String findStr = kws;
    	
    	int lastIndex = 0;
    	int count = 0;

    	while (lastIndex != -1) {

    		lastIndex = str.indexOf(findStr, lastIndex);

    		if (lastIndex != -1) {
    			count++;
    			lastIndex += findStr.length();
    		}
    	}
    	
    	return count;
	}
	
	private String getTitle(String content) {
				
		String title = null;
		
		if(content.contains("<title>")) {
			int left = content.indexOf("<title>");
			int right = content.indexOf("</title>");
			title = content.substring(left + 7, right);
		} 
		
		return title;
	}
	
	private String validateName(String name) {
		
		int index = name.lastIndexOf("/");
		String filename = "database" + name.substring(index);
		filename = filename.replace(" ", "");
		return filename;
	}
	
	private ArrayList<QualifiedLinks> rankLinks(ArrayList<QualifiedLinks> list) {
		Collections.sort(list, new Comparator<QualifiedLinks>() {

			@Override
			public int compare(QualifiedLinks o1, QualifiedLinks o2) {
				
				if(o1.getScore() < o2.getScore()) return 1;
				
				if(o1.getScore() > o2.getScore()) return -1;
				
				return 0;
			}
			
		});
		
		return list;
	}
	
	private String generateJson(ArrayList<QualifiedLinks> links) {
		
		String s = "links=[";
		
		for(int i = 0; i < links.size() - 1; i++) {
			
			s += "{" +"addr:" +"'" + validateName(links.get(i).getFileName()) +"'," + "title:" + "'" + links.get(i).getTitle() + "'" + "},";
		}
		
		s += "{" +"addr:" +"'" + validateName(links.get(links.size() - 1).getFileName()) +"'," + "title:" + "'" + links.get(links.size() - 1).getTitle() + "'" + "}]";
		
		return s;
	}
	
	private void insertHighlighter(String filename, String key) {
		try {
			Scanner scan = new Scanner(new File(filename)).useDelimiter("\\Z");
			String content = scan.next();
			
//			<link rel="stylesheet" href="hili.css">
//			<script src = "hili.js"></script>
			
			String insertion = "<link rel = " + "'" + "stylesheet" + "'" + "href =" + "'" + "css/hili.css" + "'>";
			insertion += "<script src = " + "'" + "js/hili.js" + "'" +"></script>";
			insertion += "<script>highlightWord(document.body," + key + ");</script></body>";
			content.replace("</body>", insertion);
			
			PrintWriter pw = new PrintWriter(new File(filename));
			
			pw.write(insertion);
			
			pw.close();
			
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
