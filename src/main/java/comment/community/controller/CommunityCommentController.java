package comment.community.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.community.dao.CoCommentDAO;
import comment.community.dto.CoCommentDTO;
import common.DBConnPool;

@WebServlet("/ComComment")
public class CommunityCommentController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 댓글 등록 처리
			String action = request.getParameter("action");
			if (action != null && action.equals("insert")) {
				CoCommentDTO dto = new CoCommentDTO();
				dto.setC_num(request.getParameter("c_num"));
				dto.setNickname(request.getParameter("nickname"));
				dto.setCo_content(request.getParameter("co_content"));
				String co_parentId = request.getParameter("co_parentId");
				if (co_parentId != null && !co_parentId.isEmpty()) {
					dto.setCo_parentId(Integer.parseInt(co_parentId));
				}

				CoCommentDAO dao = new CoCommentDAO();
				dao.insertComment(dto);
			}

			// 댓글 삭제 처리
			if (action != null && action.equals("delete")) {
				int comId = Integer.parseInt(request.getParameter("comId"));
				CoCommentDAO dao = new CoCommentDAO();
				dao.deleteComment(comId);
			}

			// 댓글 수정 처리
			if (action != null && action.equals("update")) {
				int comId = Integer.parseInt(request.getParameter("comId"));
				String co_content = request.getParameter("co_content");

				CoCommentDTO dto = new CoCommentDTO();
				dto.setComId(comId);
				dto.setCo_content(co_content);

				CoCommentDAO dao = new CoCommentDAO();
				// 수정 처리
				dao.updateComment(dto);
			}
			// 다시 해당 게시글로 Redirect
			response.sendRedirect("view.do?c_num=" + request.getParameter("c_num"));
			return;
		} catch (Exception e) {
			System.out.println("댓글 작성/삭제 실패");
			e.printStackTrace();
		}
	}
}
