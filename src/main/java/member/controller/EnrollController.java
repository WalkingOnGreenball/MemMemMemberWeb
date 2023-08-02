package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class EnrollController
 */
@WebServlet("/member/register.do")
public class EnrollController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnrollController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/member/enroll.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String memberId = request.getParameter("member-id");
		String memberPwd = request.getParameter("member-pwd");
		String memberName = request.getParameter("member-name");
		int memberAge = Integer.parseInt(request.getParameter("member-age"));
		String memberGender = request.getParameter("member-gender");
		String memberEmail = request.getParameter("member-email");
		String memberAddress = request.getParameter("member-address");
		String memberPhone = request.getParameter("member-phone");
		String memberHobby = request.getParameter("member-hobby");
		
		Member member = new Member(memberId, memberPwd, memberName, memberAge, memberGender, memberEmail, memberPhone, memberAddress, memberHobby);
		
		// 서비스 호출
		MemberService service = new MemberService();
		
		// INSERT INTO MEMBER_TBL VALUES(?,?,?,?,?,?,?,?,?,DEFAULT,DEFAULT,DEFAULT);
		int result = service.insertMember(member);
		
		if(result > 0) {
			// 성공하면 성공 페이지로 이동 -> RequestDispatcher
			request.setAttribute("msg", "회원가입 성공했어요.");
			request.setAttribute("url", "/index.jsp");
			RequestDispatcher view = request.getRequestDispatcher("/member/serviceSuccess.jsp");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/member/serviceFail.jsp");
			view.forward(request, response);
		}
		
		
//		request.setAttribute("member-id", memberId);
//		request.setAttribute("member-pwd", memberPwd);
//		request.setAttribute("member-name", memberName);
//		request.setAttribute("member-age", memberAge);
//		request.setAttribute("member-gender", memberGender);
//		request.setAttribute("member-email", memberEmail);
//		request.setAttribute("member-address", memberAddress);
//		request.setAttribute("member-phone", memberPhone);
//		request.setAttribute("member-hobby", memberHobby);
//		
//		RequestDispatcher view = request.getRequestDispatcher("/member/memberResult.jsp");
//		view.forward(request, response);
	}

}
