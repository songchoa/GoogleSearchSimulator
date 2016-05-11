<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
  int sleeptime = 1000;
  Thread.sleep(sleeptime); 
%>
<HTML>
<HEAD> 
<TITLE>Sleepy JSP</TITLE> 
</HEAD>
<BODY> 
<HR>
<P><CENTER>Sleepy JSP slept for <%= sleeptime %> milliseconds!</CENTER></P>
<HR> 
</BODY>
</HTML>