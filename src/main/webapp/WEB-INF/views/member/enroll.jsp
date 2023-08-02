<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원가입</title>
		<link rel="stylesheet" href="/resources/css/member/main.css">
	</head>
	<body>
		<h1>회원가입</h1>
		<h3>회원정보를 입력하세요.</h3>
		<!-- <form action="/member/register.do" method="get"> -->
		<form action="/member/register.do" method="post">
			<fieldset>
				<legend>회원가입</legend>
				<ul>
					<li>
						<label for="member-id">아이디</label>
						<input type="text" name="member-id" id="member-id">
					</li>
					<li>
						<label for="member-pwd">비밀번호</label>
						<input type="password" name="member-pwd" id="member-pwd">
					</li>
					<li>
						<label for="member-name">이름</label>
						<input type="text" name="member-name" id="member-name">
					</li>
					<li>
						<label for="member-age">나이</label>
						<input type="text" name="member-age" id="member-age">
					</li>
					<li>
						<label for="member-gender">성별</label>
						<input type="radio" name="member-gender" value="M">남
						<input type="radio" name="member-gender" value="F">여
					</li>
					<li>
						<label for="member-email">이메일</label>
						<input type="text" name="member-email" id="member-email">
					</li>
					<li>
						<label for="member-phone">전화번호</label>
						<input type="text" name="member-phone" id="member-phone">
					</li>
					<li>
						<label for="member-address">주소</label>
						<input type="text" name="member-address" id="member-address">
					</li>
					<li>
						<label for="member-hobby">취미</label>
						<input type="text" name="member-hobby" id="member-hobby">
					</li>
				</ul>
			</fieldset>
			<input type="submit" value="가입하기">
			<input type="reset" value="초기화">
		</form>
	</body>
</html>