<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<script>
	function KeyWordCheck() {
		if (document.search.keyWord.value == "") {
			alert("검색어를 입력하세요.");
		} else {
			document.search.submit();
		}
	}

</script>

<head>

<link href="css/7911bc0a5c62.css" rel="stylesheet">
<link href="css/7e63fa136b7d.css" rel="stylesheet">
<link href="css/facebook.css" rel="stylesheet">
<link href="css/floating.css" rel="stylesheet">
<link href="css/layout_nav.css" rel="stylesheet">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>

</head>
<body class="project-list">
<script>
	alert("이미 지원한 프로젝트 입니다.");
</script>
	이곳은 리스트 입니다 ㅋ
	<%
	String keyField = request.getParameter("keyField");
	String keyWord = request.getParameter("keyWord");
	

	if (keyField == null) {
		keyField = "pr_skill";
	}
	if (keyWord == null) {
		keyWord = "";
	}
%>
	<div class="page">
		<div class="content">
			<div class="content-header">
				<h3 class="header-text">프로젝트 찾기</h3>
				<div class="search search-right">
					<form action="/pfinal/pfinal.do" name="search" method="post">
						<div class="form-group">
							<select name="keyField">
								<option value="pr_skill" <%if (keyField.equals("pr_skill")) {%>
									selected="selected" <%}%>>기술</option>
								<option value="pr_subject"
									<%if (keyField.equals("pr_subject")) {%> selected="selected"
									<%}%>>프로젝트 명</option>
								<option value="pr_content"
									<%if (keyField.equals("pr_content")) {%> selected="selected"
									<%}%>>프로젝트 내용</option>
							</select> <input type="text" name="keyWord" value=<%=keyWord%>>

							<!-- <input type="button" value="찾기" onClick="KeyWordCheck()"> -->

							<button class="btn btn-default active" name="command"
								value="list" onclick="KeyWordCheck()">찾기</button>
						</div>
					</form>
					   <form method="post" action="/pfinal/pfinal.do">
	                       <button class="btn btn-link pl" name="command" value="prwrite" >글쓰기</button>
	                     </form>
				</div>
			</div>
			<div class="content-inner">
				<section class="project-unit"> 
					<c:forEach items="${dtoList}" var="dto">
					<div class="project-unit-heading">
						<h4 class="project-title">${dto.pr_subject}</h4>
						<c:set var="week" value="${dto.week_cha}" /> 
                     <c:set var="day" value="${dto.day_cha}" /> 
                     <c:if test="${week!=0&&day!=0&&day>0&&week>0}">  
                        <span class="label label-sm label-open">마감 ${dto.week_cha}주 ${dto.day_cha}일</span>
                     </c:if>
                     <c:if test="${week==0&&day!=0&&day>0}">  
                        <span class="label label-sm label-open">마감 ${dto.day_cha}일</span>
                     </c:if>
                     <c:if test="${day==0&&week!=0&&week>0}">  
                        <span class="label label-sm label-open">마감 ${dto.week_cha}주 </span>
                     </c:if>
                     <c:if test="${day==0&&week==0&&dto.pr_state eq 'X'}">  
                        <span class="label label-sm label-open">오늘마감</span>
                     </c:if>
                     <c:if test="${dto.pr_state eq 'Y'}">
								<span class="label label-sm label-open">마감입니다.</span>
						</c:if>
					</div>
					<div class="project-unit-body">
						<div class="project-unit-basic-info">
							<span><i class="fa fa-won"></i>예상금액 ${dto.pr_price}</span> <span><i
								class="fa fa-clock-o"></i>예상기간 10일</span> <span class="applied"><i
								class="fa fa-pencil"></i>총 ${dto.pr_cntman}명 지원</span> <span
								class="date-recruitment">등록일 ${dto.pr_start}</span>
						</div>
						<div class="project-unit-desc">
							<p>${dto.pr_content}</p>
						</div>
						<div class="project-unit-additional-info">
							<div class="project-skill-required">
								<span class="info-title">요구기술</span> 
								<c:forEach items="${dto.pr_skills}" var="skill">
									<span class="project-skill label-skill">${skill}</span>	
								</c:forEach>
							</div>
						</div>

						<form method="post" action="/pfinal/pfinal.do">
							<p>
								<input type="hidden" name="pr_id" value="${dto.pr_id}">
								<button class="btn btn-link pl" name="command" value="getboard">자세히보기</button>
							</p>
						</form>

					</div>
					<hr size="10">
				</c:forEach> </section>
			</div>
		</div>
	</div>
</body>
</html>

