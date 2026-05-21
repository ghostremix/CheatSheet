<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
session.invalidate(); // Session ဖျက်မယ်
response.sendRedirect("login.jsp"); // Login ပြန်ပို့မယ်
%>