package com.myweb.util.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.user.model.UserVO;

//게시글 수정, 삭제에 대한 필터

@WebFilter({"/board/modify.board","/board/update.board","/board/delete.board"})
public class BoardFilter2 implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");//한글 인코딩
		
		HttpServletRequest req=  (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		//1.등록화면에서는 작성자를  id값으로 고정,
		//2.각 요청으로 id가 parameter로 전달되는지 확인
		
		
		
		HttpSession session = req.getSession();
		UserVO user = (UserVO)session.getAttribute("user");
		
		if(user==null){
			res.sendRedirect("/TestWeb/user/login.user");
			return;
		}
		
		String id = user.getId();
		String writer = request.getParameter("writer");
		
	
		
		if(writer == null|| !id.equals(writer) ) { //로그인을 하지않았거나 글작성 당사자가 아닌경우 Boardwriter.jsp에서 (<input type="text" name="writer" value = "${sessionScope.user.id}") 이렇게 적었음
			
			res.setContentType("text/html; charset = UTF-8");
			PrintWriter out=res.getWriter();
			out.println("<script>");
			out.println("alert('로그인이 필요한 서비스 입니다');");
			out.println("location.href = '/TestWeb/board/list.board';");
			out.println("</script>");
			return;//글 수정 삭제시 로그인한 당사자가 작성한 글이아니면 넘어갈수 없게  return으로 메ㅐ서드 종료
			
		}		
		
		//3.write 화면에서 작성자를 ID값으로 고정
		//4.modify.board , update,board , delete.board 요청으로 넘어갈때 writer를 담아서 보내주도록 처리
		
		chain.doFilter(request, response);//컨트롤러의 실행
		
		
	}

}
