package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/member/login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ? AND MEMBER_PW = ?
//		String memberId = request.getParameter("member-id");
//		String memberPw = request.getParameter("member-pw");
//		
//		Member member = new Member(memberId, memberPw);
////		Member member = new Member();	// 위와 같은 코드	// *아래서 말하는 코드*
////		member.setMemberId(memberId);
////		member.setMemberPw(memberPw);
//		
//		MemberService service = new MemberService();
//		Member mOne = service.selectCheckLogin(member);
////		Member mOne = service.selectCheckLogin(memberId, memberPw);	// 아까의 생략된 *위와 같은 코드*를 쓸 거면 이렇게
//		
//		if(mOne != null) {
//			HttpSession session = request.getSession();		// 로그인 정보 저장하기, 모든 곳에서 전역변수처럼 꺼내쓸 수 있음
//			session.setAttribute("memberId", mOne.getMemberId());		// 중요한 정보는 setAttribute하지 않음
//			session.setAttribute("memberName", mOne.getMemberName());
//			
//			// 로그인 성공!
//			request.setAttribute("msg", "로그인 성공!");
//			request.setAttribute("url", "/index.jsp");
//			RequestDispatcher view = request.getRequestDispatcher("/member/serviceSuccess.jsp");
//			view.forward(request, response);
//		} else {
//			// 로그인 실패!
//			request.setAttribute("msg", "로그인 실패!");
//			RequestDispatcher view = request.getRequestDispatcher("/member/serviceFailed.jsp");
//			view.forward(request, response);
//		}
		
		
		
		// SELECT COUNT(*) FROM MEMBER_TBL WHERE MEMBER_ID = ? AND MEMBER_PW = ?
		String memberId = request.getParameter("member-id");
		String memberPw = request.getParameter("member-pw");
		
		Member member = new Member(memberId, memberPw);
		
		MemberService service = new MemberService();
		int result = service.selectCheckLogin(member);
		
		if(result > 0) {
			HttpSession session = request.getSession();		// 로그인 정보 저장하기, 모든 곳에서 전역변수처럼 꺼내쓸 수 있음
			session.setAttribute("memberId", memberId);		// 중요한 정보는 setAttribute하지 않음
//			session.setAttribute("memberName", member.getMemberName());
			
			// 로그인 성공!
			request.setAttribute("msg", "로그인 성공!");
			request.setAttribute("url", "/index.jsp");
			RequestDispatcher view = request.getRequestDispatcher("/member/serviceSuccess.jsp");
			view.forward(request, response);
		} else {
			// 로그인 실패!
			request.setAttribute("msg", "로그인 실패!");
			RequestDispatcher view = request.getRequestDispatcher("/member/serviceFailed.jsp");
			view.forward(request, response);
		}
	}

}
