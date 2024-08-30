package admin.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logoimage")
public class LogoImageServlet extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 이미지 파일 경로
		String yogologoPath = getServletContext().getRealPath("/WEB-INF/views/admin/yogologo.png");

		// 이미지 파일을 읽어서 스트림으로 전송
		try (FileInputStream fis = new FileInputStream(new File(yogologoPath))) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				response.getOutputStream().write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			// 에러 처리
			e.printStackTrace();
		}

	}
}
