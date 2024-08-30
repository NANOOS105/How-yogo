package member.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class fileUploadUtil {

	public static String getTodayDateString() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	public static void createUploadDirectory(String todayDate) {
		// 우선 운영 체제에 따른 경로 구분자를 가져옵니다.
		String fileSeparator = System.getProperty("file.separator");

		// 폴더 경로를 절대 경로로 지정합니다.
		String uploadPath = "C:" + fileSeparator + "lookImg" + fileSeparator + "profileImg" + fileSeparator + todayDate;

		// 폴더를 생성합니다.
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
	}

	/**
	 * 유저가 등록한 파일의 파일명(originalFileName)에서 확장자만을 추출하고, UUID라는 랜덤 문자열과 확장자를 이어 붙여서
	 * 새로운 파일이름을 생성 파일명의 중복을 피하기 위해 사용 예) 84003cd6-39f3-4b2e-89a1-ff14898d1599.jpg
	 * (UUID 랜덤 문자열.확장자)
	 * 
	 * @param originalFileName 유저가 전달한 파일명
	 * @return 랜덤 문자열.확장자
	 */
	public static String getUniqueFileName(String originalFileName) {
		// 사용자의 파일명에서 확장자만을 추출(.jpg, .png 등등)
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		// UUID 메서드를 통해 랜덤 문자열을 만들고, 확장자를 이어 붙인다.
		String newFileName = UUID.randomUUID().toString() + extension;
		// 위 예시와 같이 긴 파일명을 리턴
		return newFileName;
	}

}
