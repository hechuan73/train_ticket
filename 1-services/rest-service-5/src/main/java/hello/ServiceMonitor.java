/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hello;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceMonitor {

	@AfterReturning("execution(* hello..*Controller.*(..))")
	public void logServiceAccess(JoinPoint joinPoint) {
		System.out.println("AfterReturning: " + joinPoint);
	}

	@After("execution(* hello..*Controller.*(..))")
	public void logServiceAfter(JoinPoint joinPoint) {
		System.out.println("After: " + joinPoint.toLongString());
		System.out.println("After: " + joinPoint.toShortString());
		System.out.println("After: " + joinPoint.getArgs()[0].toString());
		System.out.println("After: " + joinPoint.getKind());
		System.out.println("After: " + joinPoint.getClass());
		System.out.println("After: " + joinPoint.getSignature());
		System.out.println("After: " + joinPoint.getSourceLocation());
		System.out.println("After: " + joinPoint.getStaticPart());
		System.out.println("After: " + joinPoint.getTarget());
	}

}
