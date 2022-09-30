package com.vyle.TanKy.aop;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class LoggingAspect {

		private final Logger logger = LogManager.getLogger(LoggingAspect.class);
		
		@Autowired
		private ObjectMapper mapper;
		
		@Pointcut("execution(* com.vyle.TanKy.controller..*(..)))")
		public void controllerPointcut() {}
		
		@Pointcut("execution(* com.vyle.TanKy.repository..*(..)))")
		public void repositoryPointcut() {}
		
		// Performance log
		@Around("controllerPointcut() || repositoryPointcut()")
		public Object performanceControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
			
			MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
			String className = methodSignature.getDeclaringType().getSimpleName();
			String methodName = methodSignature.getName();
			
			final StopWatch stopWatch = new StopWatch();
			
			stopWatch.start();
			Object result = proceedingJoinPoint.proceed();
			stopWatch.stop();
			
			logger.info("Execution time of " + className +"."+ methodName+" :: " + stopWatch.getTotalTimeMillis() + " ms");
			
			return result;
			
		}
		
		//Parameter Log
		@Before("controllerPointcut() || repositoryPointcut()")
		public void logParameter(JoinPoint joinPoint) {
			
			MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
			String className = methodSignature.getDeclaringType().getSimpleName();
			String methodName = methodSignature.getName();
			
			
			CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
			String[] parameterNames = codeSignature.getParameterNames();
			
			if (parameterNames.length >0) {
				Map<String,Object> map = new HashMap<>();
				
				for(int i=0; i<parameterNames.length; i++) {
					map.put(parameterNames[i],joinPoint.getArgs()[i]);
				}
				
				try {
					logger.info(className+"."+methodName+" argruments: "+mapper.writeValueAsString(map));
				} catch (JsonProcessingException e) {
					logger.error("Error getting parameters' value", e);
				}
			}
		}
		
		// Return data log
		@AfterReturning(pointcut ="controllerPointcut()", returning = "result")
		public void logReturnData(JoinPoint joinPoint, Object result) {
			MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
			String className = methodSignature.getDeclaringType().getSimpleName();
			String methodName = methodSignature.getName();
			
			if (result !=null)
				try {
					logger.info(className+"."+methodName+" argruments: "+ mapper.writeValueAsString(result));
				} catch (JsonProcessingException e) {
					logger.error("Error getting parameters' value", e);
				}

		}
		
}
