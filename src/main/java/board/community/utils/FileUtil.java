package board.community.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

@MultipartConfig
public class FileUtil {

	// 파일 업로드
	public static String uploadFile(HttpServletRequest request, String sDirectory)
			throws ServletException, IOException {
		// 여기서부터 파일 처리 로직 작성
		// Collection<Part> parts = request.getParts();
		// request 내장 객체의 getPart() 메소드로 file 타입으로 전송된 폼값을 받아 Part 객체에 저
		Part part = request.getPart("c_imgOName");
		// Part객체에서 "content-disposition"이라는 헤더 값을 읽어온다.
		// input태그의 name 속성과 파일명이 포함되어 있다.
		String partHeader = part.getHeader("content-disposition");

		// 출력 결과 확인: form-data; name="attachedFile"; filename="파일명.jpg"
		System.out.println("partHeader=" + partHeader);

		// 헤더 내용에서 파일명을 추출하기 위해 split()메서드로 분리한 후 더블쿼테이션을 제거
		String[] phArr = partHeader.split("filename=");
		String originalFileName = phArr[1].trim().replace("\"", "").trim();// 공백제거 추가

		// 파일명이 비어 있는 경우 처리
		if (originalFileName.isEmpty()) {
			return ""; // 빈 파일명 반환
		}

		// 파일명이 빈값이 아니라면 디렉토리에 파일을 저장.
		if (!originalFileName.isEmpty()) {
			part.write(sDirectory + File.separator + originalFileName);
		}

		return originalFileName;
	}

	// 파일명 변경
	public static String renameFile(String sDirectory, String fileName) {

		String ext = fileName.substring(fileName.lastIndexOf("."));
		// 현재날짜_시간 형식의 문자열을 생성한다
		String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
		String newFileName = now + ext;
		// 원본파일과 새로운 파일에 대한 File객체를 생성한 후 파일명을 변경한다.
		File oldFile = new File(sDirectory + File.separator + fileName);
		// 변경된 파일명을 반환한다.
		File newFile = new File(sDirectory + File.separator + newFileName);
		oldFile.renameTo(newFile);
		return newFileName;
	}

	// multiple 속성 추가로 2개 이상의 파일 업로드
	public static ArrayList<String> multipleFile(HttpServletRequest request, String sDirectory)
			throws ServletException, IOException {
		ArrayList<String> listFileName = new ArrayList<>();
		// getParts()메소드로 전송된 폼 값을 받는다.
		Collection<Part> parts = request.getParts();
		for (Part part : parts) {
			// 파일이 아니라면 업로드의 대상이 아니므로 무시
			if (!part.getName().equals("c_imgOName"))
				continue;

			// Part 객체의 헤더값 중 content-disposition 읽어온다.
			String partHeader = part.getHeader("content-disposition");
			System.out.println("partHeader=" + partHeader);
			// 헤더값에서 파일명 잘라내기
			String[] phArr = partHeader.split("filename=");
			String originalFileName = phArr[1].trim().replace("\"", "");
			// 파일명이 빈값이 아니라면 디렉토리에 파일을 저장.
			if (!originalFileName.isEmpty()) {
				part.write(sDirectory + File.separator + originalFileName);
			}
			// 원본 파일명을 ArrayList 컬렉션에 추가
			listFileName.add(originalFileName);
		}
		// 파일명이 담겨있는 컬렉션을 반환
		return listFileName;
	}

	// 지정한 위치의 파일을 삭제한다
	public static void deleteFile(HttpServletRequest req, String directory, String filename) {
		// 파일이 저장된 디렉토리의 물리적 경로 얻어오기
		String sDirectory = req.getServletContext().getRealPath(directory);
		// 경로와 파일명을 결합해서 파일 객체를 생성
		File file = new File(sDirectory + File.separator + filename);
		// 만약 경로에 파일이 존재하면 삭제한다.
		if (file.exists()) {
			file.delete();
		}
	}

}