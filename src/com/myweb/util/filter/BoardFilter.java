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

//1.Filtere인터페이스를 상속받고, doFilter메서드를 오버라이딩합니다

//2필터를 등록하는 방법 @webfilter(어노테이션 - 기능을 가지고있는 주석) , @web.xml에 필터 설정
//@webFilter("/*") - 모든 요청에 대해 필터링 합니다
//@webFilter("*.board") - .board로 끝나는 모든 요청에대해 필터링 합니다
//글쓰기와 등록에 대해서만 필터링
//@WebFilter({"/board/write.board","/board/regist.board"}) 
public class BoardFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
	
		
		//ServletRequest는  HttpServletRequest의 부모타입.
		//형변환을 통해서 자식형태로 변환
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		//세션은 얻어서 권한 확인
		HttpSession session = req.getSession();
		UserVO user = (UserVO)session.getAttribute("user");
		
		if(user == null) { //세션이 존재하지 않는다는 것은 권한이 없음 (로그인을 하지않음, 회원이아님)
			//res.sendRedirect("list.board");
			res.setContentType("text/html; charset = UTF-8");
			PrintWriter out=res.getWriter();
			out.println("<script>");
			out.println("alert('로그인이 필요한 서비스 입니다');");
			out.println("location.href = '/TestWeb/user/login.user';");
			out.println("</script>");
			
			return;//로그인을 안했을경우 메소드를 종료하고 컨트롤러를 실행하지 않겠다
		}
		
		else
		
		chain.doFilter(request, response); //서블릿이나 , 연결되어있는 다른 필터를 실행시켜줘야합니다	
		
	}
	
	

}
