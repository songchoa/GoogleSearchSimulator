package com.gses;

public class QualifiedLinks {
	
	private int score;
	
	private String title;
	
	private String filename;
	
	public QualifiedLinks(int score, String title, String filename) {
		this.score = score;
		this.title = title;
		this.filename = filename;
	}
	
	protected int getScore() {
		return score;
	}
	
	protected String getTitle() {
		return title;
	}
	
	protected String getFileName() {
		return filename;
	}
}
