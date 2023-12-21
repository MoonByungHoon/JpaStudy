package study.data.jpa.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
//SpringConfig에 @Bean으로 등록해도 되고 @Component를 적용해도 된다.
//@Component
public class TimeTraceAop {

  @Around("execution(* study.data.jpa..*(..))")
  public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    System.out.println("START : " + joinPoint.toString());

    try {
      return joinPoint.proceed();
    } finally {
      long finish = System.currentTimeMillis();
      long timeMs = finish - start;

      if (timeMs > 3) {
        System.out.println("경고! 처리 지언됨.");
      }
      System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
    }
  }
}
