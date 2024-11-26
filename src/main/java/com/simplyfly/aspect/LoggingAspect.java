package com.simplyfly.aspect;
import org.apache.logging.log4j.LogManager;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


import org.apache.logging.log4j.core.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	Logger log = (Logger) LogManager.getRootLogger();

//    @Before("execution(* com.hexaware.jpasampleproject.service.ProductServiceImpl.addproduct*.*(..))")
//    public void logMethodCall() {
//        System.out.println("Method called!");
//    }
	@Pointcut("execution(* com.simplyfly.services.UserServiceImpl.registerUser(..))")
	public void registerUserPointcut() {};
	//@Before(pointcut="execution(" com.hexaware.jpasampleproject.service.ProductServiceImpl.addproduct(..))")
	/*@Before("createUserPointcut()")     //point-cut expression
    public void logBeforeV1(JoinPoint joinPoint) {
		System.out.println("Method Called");
	}*/

	@Before("registerUserPointcut()") // point-cut expression
	public void logBeforeV1(JoinPoint joinPoint) {

		System.out.println("registerUserAspect.logBeforeregisterUser() : " + joinPoint.getSignature().getName());
	}

	@AfterThrowing(pointcut = "execution(* com.simplyfly.services.UserServiceImpl.findByUsername(..))", throwing = "error")
	public void throwingAdvice(JoinPoint joinPoint, Throwable error) {
		log.info("Method Signature: " + joinPoint.getSignature());
		log.error("ResourceNotFoundException: " + error.getMessage());
		log.error(error.getStackTrace());
	}
}