package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;


@WebServlet("/guest")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("[GuestbookController");
		
		//post일때 한글형식
		request.setCharacterEncoding("UTF-8");

		//action값 읽기 --> 업무구분
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("addList".equals(action)) {//리스트_등록폼
			System.out.println("[리스트_등록폼]");
			
			GuestbookDao guestbookDao = new GuestbookDao();
			List<GuestbookVo> guestbookList = guestbookDao.getGuestbookList();
			
			request.setAttribute("guestbookList", guestbookList);

			WebUtil.forword(request, response, "/WEB-INF/addList.jsp");
			
		}else if("add".equals(action)) {
			System.out.println("[등록]");
			
			//파라미터 꺼내기
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			
			//vo 만들기
			GuestbookVo guestbookVo = new GuestbookVo(name, password, content);
			
			//db에 저장하기
			GuestbookDao guestbookDao = new GuestbookDao();
			guestbookDao.guestbookInsert(guestbookVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "/guestbook2/gbc?action=addList");
		
		}else if("dForm".equals(action)) {
			System.out.println("[삭제폼]");
			
			//파라미터 꺼내기
			//파라미터의 id값이 jsp에 필요하므로 jsp에서 파라미터의 값을 꺼내서 사용

			//포워드
			WebUtil.forword(request, response, "/WEB-INF/deleteForm.jsp");
			
		}else if("delete".equals(action)) {
			System.out.println("[삭제]");
			
			//파라미터 꺼내기
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			
			//vo로 만들기
			GuestbookVo guestbookVo = new GuestbookVo();
			guestbookVo.setNo(no);
			guestbookVo.setPassword(password);
			
			//삭제하기
			GuestbookDao guestbookDao = new GuestbookDao();
			guestbookDao.guestbookDelete(guestbookVo);

			//리다이렉트
			WebUtil.redirect(request, response, "/guestbook2/gbc?action=addList");
		}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
