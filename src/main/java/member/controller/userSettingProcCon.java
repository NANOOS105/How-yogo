package member.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import common.DBConnPool;
import member.dao.UserDAO;
import member.dto.UserDTO;

@MultipartConfig
@WebServlet("/userSettingProcCon.do")
public class userSettingProcCon extends HttpServlet {

	private final String profilePath = "\\lookImg\\profileImg\\";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		DBConnPool con = new DBConnPool();
		Map<String, String> requestValues = new HashMap<>();

		HttpSession session = request.getSession(false);
		UserDTO bean = (UserDTO) session.getAttribute("bean");
		String id = bean.getmemberid(); // 세션에 저장된 id값
		String password = request.getParameter("password"); // 사용자가 입력한 password
		String originalPWD = request.getParameter("originalPWD"); // DB password
		String nickname = request.getParameter("nickname"); // 사용자가 입력한 nickname
		String imgSrc = null;
		String imgSname = null;
		System.out.println(originalPWD + password);

		try {

			String contentType = request.getContentType();

			if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
				Collection<Part> parts = request.getParts();

				for (Part part : parts) {
					System.out.printf("파라미터 명 : %s, contentType :  %s,  size : %d bytes \n", part.getName(),
							part.getContentType(), part.getSize());

					// input 태그의 타입이 file인 경우 if 실행
					if (part.getHeader("Content-Disposition").contains("filename=")) {

						String todayDate = fileUploadUtil.getTodayDateString();

						String uploadPath = profilePath + todayDate;
						System.out.println("저장하는 경로: " + uploadPath);

						fileUploadUtil.createUploadDirectory(todayDate);

						if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isBlank()) {
							String newFileName = fileUploadUtil.getUniqueFileName(part.getSubmittedFileName());
							System.out.println("사용자가 전달한 파일명 => " + part.getSubmittedFileName());

							// 파일명 생성 > 랜던값으로 준 이름 + 확장자
							imgSrc = uploadPath; // imgSrc
							imgSname = newFileName; // 수정한파일명 imgSimage

							if (part.getSize() > 0) {
								part.write("C:" + uploadPath + "\\" + newFileName);
								part.delete();
							}

						} else {
							imgSrc = bean.getimgSrc();
							imgSname = bean.getimgSname();
						}

					} else {
						String formValue = request.getParameter(part.getName());

					} // else끝
				} // for끝
			} // if끝

			if (password.equals(originalPWD)) {
				System.out.println(imgSrc);
				UserDAO udao = new UserDAO();
				udao.updateProfile(id, nickname, imgSrc, imgSname);
				System.out.println(nickname + "if문");
				bean = udao.getoneprofile(id);
				session.setAttribute("bean", bean);

				// 수정이 완료 되면 메시지 띄우면서 메인페이지로 이동
				request.setAttribute("msg", "수정이 완료되었습니다.");
				RequestDispatcher dis = request.getRequestDispatcher("/userSettingCon.do");
				dis.forward(request, response);
			} else {
				request.setAttribute("msg", "비밀번호를 확인해주세요");
				RequestDispatcher dis = request.getRequestDispatcher("/userSettingCon.do");
				dis.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("컨트롤러실패!!");
		} finally {

			// con.close();
		}

	}
}
