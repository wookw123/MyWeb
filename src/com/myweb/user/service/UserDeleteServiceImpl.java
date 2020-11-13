package com.myweb.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.user.model.UserDAO;
import com.myweb.user.model.UserVO;

public class UserDeleteServiceImpl implements UserService {

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		
		String pw = request.getParameter("pw");
		//아이디는 세션
		HttpSession session = request.getSession();
		UserVO vo = (UserVO)session.getAttribute("user");
		
		System.out.println(vo.toString());
		String id = vo.getId();
		
		//1.login메서드로 유효성 확인
		UserDAO dao = UserDAO.getInstance();
		UserVO result = dao.login(id, pw);//아이디 비밀번호 검증실패시  null 을 리턴하는 메서드
		
		if(result != null) {//비밀번호가 맞는경우 (회원 탈퇴 진행)
			dao.delete(id);
			session.invalidate();
			return 1;
		}else {
			return 0;
		}
		
		
		
	}
	
	
	

	
}
