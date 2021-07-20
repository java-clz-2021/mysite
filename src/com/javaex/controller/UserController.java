package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("[UserController]");

		//텍스트 인코딩
		request.setCharacterEncoding("UTF-8");
		
		//파라미터 가져오기(업무구분)
		String action = request.getParameter("action");
		System.out.println(action);
		
		
		if("joinForm".equals(action)) { //회원가입 폼
			System.out.println("[UserController.joinForm]");
			
			//회원가입폼 포워드
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
		}else if("join".equals(action)) { //회원가입
			System.out.println("[UserController.join]");
			
			//파라미터 꺼내기
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			//System.out.println(id+","+pw+","+name+","+gender);
			
			//vo만들기
			UserVo userVo = new UserVo(id, pw, name, gender);
			System.out.println(userVo);
			
			//*dao.insert(vo) --> db저장
			UserDao userDao = new UserDao();
			int count = userDao.userInsert(userVo);
			
			//포워드 
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
		}else if("loginForm".equals(action)) {
			System.out.println("[UserController.loginForm]");
			
			//로그인 폼 포워드
			WebUtil.forword(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		}else if("login".equals(action)) {
			System.out.println("[UserController.login]");
			
			//파라미터에 값 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("pw");
			
			//dao 회원정보 조회하기(세션 저장용)
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(id, password);
			
			if(userVo != null) {
				System.out.println("로그인 성공");
				//성공일때(아이디, 비번 일치했을때) 세션에 저장 
				HttpSession session = request.getSession();
				session.setAttribute("authUser", userVo);  // jsp에 데이터전달할때 비교  request.setAttibute();
				
				//리다이렉트 -메인페이지
				WebUtil.redirect(request, response, "/mysite/main");
				
			}else {
				System.out.println("로그인 실패");
				
				//리다이렉트 -로그인폼 페이지
				WebUtil.redirect(request, response, "/mysite/user?action=loginForm&result=fail");
			}
			
			
		}else if("logout".equals(action)) {
			System.out.println("[UserController.logout]");
			
			//세션의 있는 "authUSer"의 정보삭제
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite/main");
			
		}else if("modifyForm".equals(action)) {
			System.out.println("[UserController.modifyForm]");
			
			//로그인한 사용자의 회원정보를 보여줘야 한다. --> 세션에서 가져온다
			HttpSession session = request.getSession();
			UserVo authUser= (UserVo)session.getAttribute("authUser");
			int authUserNo = authUser.getNo();
			/*  //위의 코드 다른 표현
			HttpSession session = request.getSession();
			int authUserNo = ((UserVo)session.getAttribute("authUser")).getNo();
			*/
			
			//로그인한 사용자 회원정보 가져오기
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(authUserNo);
			
			//리퀘스트 세션 영역에 로그인한 사용자 회원정보 넣기
			request.setAttribute("userVo", userVo);
			
			//포원드 하기
			WebUtil.forword(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
			
		}else if("modify".equals(action)) {
			System.out.println("[UserController.modify]");
			
			HttpSession session = request.getSession();
			//vo만들기
			//세션에서 로그인한 사용자의 no를 가져온다.
			//나머지정보는 파라미터로 받는다.
			int no = ((UserVo)session.getAttribute("authUser")).getNo();
			String password = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
	
			UserVo userVo = new UserVo(no, password, name, gender);
			
			//db에 업데이트
			UserDao userDao = new UserDao();
			int count = userDao.userUpdate(userVo);
			
			//세션에 이름정보 업데이트(수정)
			((UserVo)session.getAttribute("authUser")).setName(name);
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite/main");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
