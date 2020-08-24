package me.kakao.pay.common;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class CommonAspect {
	private static final Logger logger = LogManager.getLogger(CommonAspect.class);

	@Around("execution(* me.kakao.pay.luck.LuckController.*(..))")
	public Object logging(ProceedingJoinPoint jp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		logger.info("Url : {}", request.getRequestURI());
		logger.info("Parameters : " + Arrays.toString(jp.getArgs()));

		Object result = jp.proceed();
		
		@SuppressWarnings("rawtypes")
		ResponseEntity responseEntity = (ResponseEntity) result;
		logger.info("Result ({}) : {}", responseEntity.getStatusCode(), responseEntity.getBody());

		return result;
	}

}