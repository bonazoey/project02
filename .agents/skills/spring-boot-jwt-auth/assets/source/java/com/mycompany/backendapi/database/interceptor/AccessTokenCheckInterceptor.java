package com.mycompany.backendapi.database.interceptor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.backendapi.database.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AccessTokenCheckInterceptor implements HandlerInterceptor {
	@Autowired
	private JwtService jwtService;
	
	
	// 전처리 메소드
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("나 전처리하고 있어!!!");
		
		// 컨트롤의 엔드포인트로 요청할때만 HandlerMethod 강제 타입 변환을 할 수 있음
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			// 엔드포인트에 @AccessTokenCheck가 붙어 있는지 확인
			AccessTokenCheck atc = handlerMethod.getMethodAnnotation(AccessTokenCheck.class);
			
			// @AccessTokenCheck가 붙어있지 않을 경우
			if(atc == null) {
				// 컨트롤러 메소드를 실행
				return true;
			} 
			// @AccessTokenCheck가 붙어있을 경우
			else {		
				// AccessToken을 얻자
				String accessToken = null;
				
				//Axios를 통해서 요청 헤더에 Authorization으로 전달되었을 경우
				String authorization = request.getHeader("Authorization");
				if(authorization != null) {
					accessToken = authorization.substring(7);
				}
				
				////////////////////////////////////////////////////////
				if(accessToken == null) {
					//<img> 태그에서 Get 방식으로 전달되었을 경우
					accessToken = request.getParameter("accessToken");
				}
				////////////////////////////////////////////////////////
				
				// AccessToken을 얻었을 경우
				if(accessToken != null) {
					// 유효성 검사
					boolean isValid = jwtService.validateJwt(accessToken);
					// 유효한 경우
					if(isValid) {
						// 요청한 사용자의 mid를 요청 정보로 추가하기
						Map<String, String> map = jwtService.getClaims(accessToken);
						String mid = map.get("mid");
						request.setAttribute("mid", mid);
						// 컨트롤러 메소드를 실행
						return true;
					} 
					// 유효하지 않았을 경우
					else {
						// 컨트로러의 메소드를 실행하면 안됨
						// 바로 에러 응답을 생성
						response.sendError(HttpServletResponse.SC_FORBIDDEN, "AccessToken이 유효하지 않습니다.");
						return false;
					}
				} 
				// AccessToken을 얻지 못했을 경우
				else {
					// 컨트로러의 메소드를 실행하면 안됨
					// 바로 에러 응답을 생성
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "AccessToken이 없습니다.");
					return false;
				}
			}
		} 
		
		// 컨트롤의 엔드포인트가 아니라, 정적 리소스를 요청할 경우도 있음(http://localhost/images/photo1.jpg)
		else {
			// 정적 리소스 다운로드
			return true;
		}
	}
	
	// 후처리 메소드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info("나 후처리하고 있어!!!");
	}
}
