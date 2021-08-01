<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="myPackage.*" %>
<%@ page import="java.io.*" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>sign</title>
</head>
<body>
	<h4> privateKey를 이용하여 전자서명 생성 </h4>
	----------------------------------------------------------
	<%
		MyKeyPair keyPair = new MyKeyPair();
		DigitSign digit = new DigitSign();
		
		String publicKeyFilename = request.getParameter("publicKeyFilename");
		String privateKeyFilename = request.getParameter("privateKeyFilename");
		String plainTextname = request.getParameter("plainTextname");
		String sigFilename = request.getParameter("sigFilename");
		
		//전자서명 생성
		byte[] sig = digit.sign(plainTextname, privateKeyFilename);				

		//전자서명 생성한 내용(sig)을 sigFilename에 저장
		keyPair.saveSign(sig, sigFilename);	
	
	%>
	<form action = "verify.jsp" method = "post">
		<h3>검증할 PlainText 파일 선택</h3>
		<input type="file" name = "verifyPlainText" required>
		<br>	
		<h3>검증에 사용할 전자서명 파일 경로 선택</h3>
		<input type="file" name = "verifySigFile" required>
		<br>	
		<h3>검증에 사용할 publicKey 파일 경로 선택</h3>
		<input type="file" name = "verifyPublicKeyFile" required>
		<br>
		<input type = "hidden" name = "publicKeyFilename" value = "<%=publicKeyFilename %>">
		<input type = "hidden" name = "privateKeyFilename" value = "<%=privateKeyFilename %>">
		<input type = "hidden" name = "plainTextname" value = "<%=plainTextname %>">
		<input type = "hidden" name = "sigFilename" value = "<%=sigFilename %>">
		<br>
		<button type="submit">전자서명 검증하기</button>
	</form>
</body>
</html>