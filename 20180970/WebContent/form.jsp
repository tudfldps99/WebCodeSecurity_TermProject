<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="myPackage.*" %>
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>form</title>
</head>
<body>
	<form action = "result.jsp" method = "post">
		<h3>publicKey 저장할 파일 경로 입력</h3>
		<input name = "publicKeyFilename" type="text" value = "C:\web\">
		<br>
		<h3>privateKey 저장할 파일 경로 입력</h3>
		<input name = "privateKeyFilename" type="text" value = "C:\web\">
		<br>
		<h3>plainText 파일 선택</h3>
		<input name = "plainTextname" type="file">
		<br><br>	
		<h3>전자서명을 저장할 파일 경로 입력</h3>
		<input name = "sigFilename" type="text" value = "C:\web\">
		<br><br>	
		<button type = "submit">비대칭키 생성하여 파일에 저장</button>
	</form>	
</body>
</html>