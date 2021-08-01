<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="myPackage.*" %>
<%@ page import="java.io.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>verify</title>
</head>
<body>
	<h3> publicKey를 이용하여 전자서명 검증 </h3>
	----------------------------------------------------------
	
	<%
	MyKeyPair keyPair = new MyKeyPair();
	DigitSign digit = new DigitSign();
	
	String publicKeyFilename = request.getParameter("publicKeyFilename");
	String privateKeyFilename = request.getParameter("privateKeyFilename");
	String dataFilename = request.getParameter("dataFilename");
	String plainTextname = request.getParameter("plainTextname");
	String sigFilename = request.getParameter("sigFilename");
	
	String verifyPlainText = request.getParameter("verifyPlainText");
	String verifyPublicKeyFile = request.getParameter("verifyPublicKeyFile");
	String verifySigFile = request.getParameter("verifySigFile");

	//전자서명 검증
	boolean result = digit.verify(verifyPlainText, verifySigFile, verifyPublicKeyFile);	
	%>
	
	<h3>서명 검증 성공 여부 :</h3>
	<%
		if (result == true) {
	%>
			검증 성공
	<%	} else {%>
			검증 실패
			<br><br><input type="button" value="다시 검증하기" onClick="history.go(-1)">
	<% 		System.out.println("검증 실패");
		} %>
</body>
</html>