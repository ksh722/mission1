<%@page import="Wifi.history"%>
<%@page import="java.util.List"%>
<%@page import="Wifi.MyOkHttp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>위치 히스토리 목록</title>
<h1>위치 히스토리 목록</h1>
<style>
table {
	width: 100%;
}

th, td {
	border: solid 1px #000;
}
</style>
</head>
	<body>
		
		<div style="font-size:small; display: inline-block;">
			<a href = "http://localhost:8080/jsp-study/home.jsp">홈</a> |
			<a href="http://localhost:8080/jsp-study/history.jsp" >위치 히스토리 목록</a> |
			<a href="http://localhost:8080/jsp-study/count.jsp" >Open API 와이파이 정보 가져오기</a>
		</div>
		
		
		
		<%
			MyOkHttp myOkHttp = new MyOkHttp();
			List<history> list = myOkHttp.historySelect();
		%>
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>조회일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<%
					for (history history: list) {
						out.write("<tr>");
						out.write("<td>" + history.getId() + "</td>");
						out.write("<td>" + history.getH_x() + "</td>");
						out.write("<td>" + history.getH_y() + "</td>");
						out.write("<td>" + history.getH_date() + "</td>");
						out.write("<td>" + "미구현" + "</td>");
						out.write("</tr>");
					}
				%>
			</tr>
		</tbody>
	</table>
		
	</body>
</html>