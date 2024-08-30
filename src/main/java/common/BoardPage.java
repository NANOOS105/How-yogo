package common;

public class BoardPage {
	public static String pagingStr(int totalCount, int pageSize, int blockPage, int pageNum, String reqUrl,
			String sortBy) {
		String pagingStr = "";

		// 단계 3 : 전체 페이지 수 계산
		int totalPages = (int) (Math.ceil(((double) totalCount / pageSize)));

		// 단계 4 : '이전 페이지 블록 바로가기' 출력
		int pageTemp = (((pageNum - 1) / blockPage) * blockPage) + 1;
		if (pageTemp != 1) {
			pagingStr += "<a href='" + reqUrl + "?pageNum=1&sort=" + sortBy + "'>[첫 페이지]</a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + (pageTemp - 1) + "&sort=" + sortBy + "'>[이전 블록]</a>";
		}

		// 단계 5 : 각 페이지 번호 출력
		int blockCount = 1;
		while (blockCount <= blockPage && pageTemp <= totalPages) {
			if (pageTemp == pageNum) {
				// 현재 페이지도 링크 걸음
				pagingStr += "&nbsp;<a href='" + reqUrl + "?pageNum=" + pageTemp + "&sort=" + sortBy + "'>" + pageTemp
						+ "</a>&nbsp;";
			} else {
				pagingStr += "&nbsp;<a href='" + reqUrl + "?pageNum=" + pageTemp + "&sort=" + sortBy + "'>" + pageTemp
						+ "</a>&nbsp;";
			}
			pageTemp++;
			blockCount++;
		}

		// 단계 6 : '다음 페이지 블록 바로가기' 출력
		if (pageTemp <= totalPages) {
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + pageTemp + "&sort=" + sortBy + "'>[다음 블록]</a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + totalPages + "&sort=" + sortBy + "'>[마지막 페이지]</a>";
		}

		return pagingStr;
	}
}
