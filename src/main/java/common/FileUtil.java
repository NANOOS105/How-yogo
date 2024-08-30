package common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class FileUtil {
	// 파일 업로드(multipart/form-data 요청) 처리
	public static Map<String, Map<String, String>> uploadFile(HttpServletRequest req, String saveDirectory)
			throws ServletException, IOException {
		try {
			// 파일 업로드
			Collection<Part> parts = req.getParts();
			Map<String, Map<String, String>> fileNames = new LinkedHashMap<>();
			for (Part part : parts) {
				if (part.getName().startsWith("mainOName") || part.getName().startsWith("inOName")
						|| part.getName().startsWith("imgOName")) {
					String partHeader = part.getHeader("content-disposition");
					String[] phArr = partHeader.split("filename=");
					String originalFileName = phArr[1].trim().replace("\"", "");
					if (!originalFileName.isEmpty()) {
						String newFileName = renameFile(saveDirectory, originalFileName,
								fileNames.values().stream().map(m -> m.get("SName")).collect(Collectors.toList()));
						part.write(saveDirectory + File.separator + newFileName);

						Map<String, String> fileInfo = new HashMap<>();
						fileInfo.put("OName", originalFileName);
						fileInfo.put("SName", newFileName);
						fileNames.put(part.getName(), fileInfo);
					}
				}
			}
			return fileNames;
		} catch (Exception e) {
			// 업로드 실패
			e.printStackTrace();
			return null;
		}
	}

	// 파일 저장명 설정
	public static String renameFile(String sDirectory, String originalFileName, Collection<String> existingNames) {
		String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
		String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
		String newFileName = originalFileName.replace(ext, "") + "_" + now + ext;

		// 파일명 중복 체크
		int count = 1;
		while (existingNames.contains(newFileName)) {
			newFileName = originalFileName.replace(ext, "") + "_" + now + "(" + count + ")" + ext;
			count++;
		}

		File newFile = new File(sDirectory + File.separator + newFileName);
		return newFileName;
	}

	// 지정한 위치의 파일을 삭제합니다.
	public static void deleteFile(HttpServletRequest req, String directory, String filename) {
		String sDirectory = req.getServletContext().getRealPath(directory);
		File file = new File(sDirectory + File.separator + filename);
		if (file.exists()) {
			file.delete();
		}
	}
}