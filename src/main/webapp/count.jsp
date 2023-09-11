<%@page import="Wifi.MyOkHttp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
	h1 {
	text-align: center;
	font-size: large;
	font-weight: bold;
	}
	div {
	text-align: center;
	font-size: medium;
	}
</style>
<meta charset="UTF-8">
<title>개수 세기</title>
<%
	MyOkHttp myOkHttp = new MyOkHttp();
	int c = myOkHttp.dbCount();
	out.write("<h1>" + c +"개의 WIFI 정보를 정상적으로 저장하였습니다"+ "</h1>");
%>
</head>
<body>
	<div><a href = "http://localhost:8080/jsp-study/home.jsp">홈 으로 가기</a></div>
</body>
</html>