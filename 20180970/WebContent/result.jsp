<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="myPackage.*" %>
<%@ page import="java.io.*" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>result</title>
</head>
<body>
	<h4> 비대칭 키 생성하여 파일에 저장 </h4>
	----------------------------------------------------------
	<%
		MyKeyPair keyPair = new MyKeyPair();
		DigitSign digit = new DigitSign();
		
		String publicKeyFilename = request.getParameter("publicKeyFilename");
		String privateKeyFilename = request.getParameter("privateKeyFilename");
		String plainTextname = request.getParameter("plainTextname");
		String sigFilename = request.getParameter("sigFilename");
		
		//비대칭 키 생성
		keyPair.createKeys();	
		
		//생성한 비대칭키를 파일에 저장
		keyPair.savePublicKey(keyPair.getPublicKey(), publicKeyFilename);
		keyPair.savePrivateKey(keyPair.getPrivateKey(), privateKeyFilename);
	%>
	
	<form action = "sign.jsp" method = "post">
		<h3>전자서명 생성에 사용할 privateKey가 저장된 파일 선택</h3>
		<input type="file" name = "privateKeyFilename" required>
		<br>
		<input type = "hidden" name = "publicKeyFilename" value = "<%=publicKeyFilename %>">
		<input type = "hidden" name = "plainTextname" value = "<%=plainTextname %>">
		<input type = "hidden" name = "sigFilename" value = "<%=sigFilename %>">
		<br>
		<button type="submit">전자서명 생성하기</button>
	</form>
</body>
</html>