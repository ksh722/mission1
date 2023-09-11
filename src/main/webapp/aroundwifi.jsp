<%@page import="java.io.ObjectOutputStream.PutField"%>
<%@page import="Wifi.infowifi"%>
<%@page import="Wifi.MyOkHttp"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<h1>와이파이 정보 구하기</h1>
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
	<script src="index.js"></script>
	
	<div style="font-size:small; display: inline-block;">
		<a href = "http://localhost:8080/jsp-study/home.jsp">홈</a> |
		<a href="http://localhost:8080/jsp-study/history.jsp" >위치 히스토리 목록</a> |
		<a href="http://localhost:8080/jsp-study/count.jsp" >Open API 와이파이 정보 가져오기</a>
	</div>
	
	<form action="aroundwifi.jsp" name="sendpos">

		LAT:<input type="text" name="lat" value=""/>,
		LNT:<input type="text" name="lnt" value=""/>
		<input type="button" value="내 위치 가져오기" onclick='kmValue()'/>
		<input type="button" value="근처 WIPI 정보 보기" onclick='sendData()'/>	
	</form>
	<%
		String lat = request.getParameter("lat");
		String lnt = request.getParameter("lnt");
		
		MyOkHttp myOkHttp = new MyOkHttp();
		
		List<infowifi> list = myOkHttp.dbSelect(Double.valueOf(lat), Double.valueOf(lnt));
	%>
	<table>
		<thead>
			<tr>
				<th>거리(Km)</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>설치위치(층)</th>
				<th>설치유형</th>
				<th>설치기관</th>
				<th>서비스구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외구분</th>
				<th>WIFI접속환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<%
					for (infowifi infowifi : list) {
						out.write("<tr>");
						out.write("<td>" + infowifi.getKm() + "</td>");
						out.write("<td>" + infowifi.getMgr_no() + "</td>");
						out.write("<td>" + infowifi.getWrdofc() + "</td>");
						out.write("<td>" + infowifi.getWifi_name() + "</td>");
						out.write("<td>" + infowifi.getAdres_rail() + "</td>");
						out.write("<td>" + infowifi.getAdres_detail() + "</td>");
						out.write("<td>" + infowifi.getInstl_floor() + "</td>");
						out.write("<td>" + infowifi.getInstl_ty() + "</td>");
						out.write("<td>" + infowifi.getInstl_mby() + "</td>");
						out.write("<td>" + infowifi.getSvc_se() + "</td>");
						out.write("<td>" + infowifi.getCmcwr() + "</td>");
						out.write("<td>" + infowifi.getYear() + "</td>");
						out.write("<td>" + infowifi.getInout_door() + "</td>");
						out.write("<td>" + infowifi.getRemars3() + "</td>");
						out.write("<td>" + infowifi.getLat() + "</td>");
						out.write("<td>" + infowifi.getLnt() + "</td>");
						out.write("<td>" + infowifi.getWork_dttm() + "</td>");
						out.write("</tr>");
					}
				%>
			</tr>
		</tbody>
	</table>

</body>
</html>