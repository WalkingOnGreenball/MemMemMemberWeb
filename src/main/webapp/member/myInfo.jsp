<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>마이페이지</title>
        <link rel="stylesheet" href="/resources/css/member/main.css">
    </head>
    <body>
        <h1>마이페이지</h1>
        <form action="/member/update.do" method="post">
            <fieldset>
                <legend>회원 상세 정보</legend>
                <ul>
                    <li>
                        <label for="member-id">아이디</label>
                        <input type="text" name="member-id" id="member-id" value="${ member.memberId }"readonly>
                    </li>
                    <li>
                        <label for="member-pwd">비밀번호</label>
                        <input type="password" name="member-pwd" id="member-pwd">
                    </li>
                    <li>
                        <label for="member-name">이름</label>
                        <input type="text" name="member-name" id="member-name" value="${ member.memberName }"readonly>
                    </li>
                    <li>
                        <label for="member-age">나이</label>
                        <input type="text" name="member-age" id="member-age" value="${ member.memberAge }"readonly>
                    </li>
                    <li>
                        <label for="member-gender">성별</label>
<!--                         기존 성별 수정 코드의 오류를 막기위해 적어주는 태그 -->
                        <input type="hidden" name="member-gender" value="${ member.memberGender }">
                        <c:if test="${ member.memberGender eq 'M' }">남자</c:if>
                        <c:if test="${ member.memberGender eq 'F' }">여자</c:if>
<%--                         <input type="radio" name="member-gender" value="M" <c:if test="${ member.memberGender eq 'M' }">checked</c:if>>남 --%>
<%--                         <input type="radio" name="member-gender" value="F" <c:if test="${ member.memberGender eq 'F' }">checked</c:if>>여 --%>
                    </li>
                    <li>
                        <label for="member-email">이메일</label>
                        <input type="text" name="member-email" id="member-email" value="${ member.memberEmail }">
                    </li>
                    <li>
                        <label for="member-phone">전화번호</label>
                        <input type="text" name="member-phone" id="member-phone" value="${ member.memberPhone }">
                    </li>
                    <li>
                        <label for="member-address">주소</label>
                        <input type="text" name="member-address" id="member-address" value="${ member.memberAddress }">
                    </li>
                    <li>
                        <label for="member-hobby">취미</label>
                        <input type="text" name="member-hobby" id="member-hobby" value="${ member.memberHobby }">
                    </li>
                    <li>
                        <label for="member-date">취미</label>
                        <input type="text" name="member-date" id="member-date" value="${ member.memberDate }"readonly>
                    </li>
                </ul>
            </fieldset>
            <div>
                <button type = "submit">수정하기</button>
                <a href="javascript:void(0)" onclick="checkDelete();">탈퇴하기</a>
                <!-- <button>탈퇴하기</button> -->
            </div>
        </form>
        <script>
            // /member/delete.do?memberId=${ sessionScope.memberId }
            function checkDelete() {
                const memberId = '${ sessionScope.memberId }';
                if(confirm("탈퇴하시겠습니까?")) {
                    location.href = "/member/delete.do?memberId=" + memberId;
                }
            }
        </script>
    </body>
</html>