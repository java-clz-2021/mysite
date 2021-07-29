<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

		<!-- header(로고 로그인버튼)  nav(메인 상단메뉴) -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		

		<div id="container" class="clearfix">
		
			<!-- aside (게시판 왼쪽메뉴) -->
			<c:import url="/WEB-INF/views/includes/asideBoard.jsp"></c:import>


			<div id="content">

				<div id="content-head">
					<h3>게시판</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>게시판</li>
							<li class="last">일반게시판</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->
	
				<div id="board">
					<div id="list">
						<form action="${pageContext.request.contextPath }/board" method="get">
							<div class="form-group text-right">
								<input type="text" name="keyword" value="">
								<button type="submit" id=btn_search>검색</button>
							</div>
							<input type="hidden" name="action" value="list">
						</form>
						<table >
							<thead>
								<tr>
									<th>글번호</th>
									<th>번호(pk)</th>
									<th>제목</th>
									<th>글쓴이</th>
									<th>조회수</th>
									<th>작성일</th>
									<th>관리</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${boardList }" var="boardVo" varStatus="status">
									<tr>
										<td>${totalCount-status.index}</td>
										<td>${boardVo.no}</td>
										<td class="text-left"><a href="${pageContext.request.contextPath }/board?action=read&no=${boardVo.no }">${boardVo.title }</a></td>
										<td>${boardVo.userName }</td>
										<td>${boardVo.hit }</td>
										<td>${boardVo.regDate }</td>
									
										<c:if test="${authUser.no == boardVo.userNo }">	
											<td><a href="${pageContext.request.contextPath }/board?action=delete&no=${boardVo.no }">[삭제]</a></td>
										</c:if>
										<c:if test="${authUser.no != boardVo.userNo }">	
											<td></td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
			
						<div id="paging">
							<ul>
								<li><a href="">◀</a></li>
								<li><a href="">1</a></li>
								<li><a href="">2</a></li>
								<li><a href="">3</a></li>
								<li><a href="">4</a></li>
								<li class="active"><a href="">5</a></li>
								<li><a href="">6</a></li>
								<li><a href="">7</a></li>
								<li><a href="">8</a></li>
								<li><a href="">9</a></li>
								<li><a href="">10</a></li>
								<li><a href="">▶</a></li>
							</ul>
							
							
							<div class="clear"></div>
						</div>
						
						<c:if test="${not empty authUser}">	
							<a id="btn_write" href="/mysite/board?action=writeForm">글쓰기</a>
						</c:if>
					</div>
					<!-- //list -->
				</div>
				<!-- //board -->
			</div>
			<!-- //content  -->

		</div>
		<!-- //container  -->
		
		<!-- 푸터 -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
		
	</div>
	<!-- //wrap -->

</body>

</html>
