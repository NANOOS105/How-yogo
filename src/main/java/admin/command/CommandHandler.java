package admin.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//모든 컨트롤러는 반드시 interface CommandHandler를 구현해야만 한다.
//따라서 모든 컨트롤러는 추상메서드 process()를 반드시 오버라이딩해야만 한다.
//강제성을 가지게함으로 인해서 ControllerUsingURI에서 설정한 내용이 적용되게 한다.
public interface CommandHandler {

	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
