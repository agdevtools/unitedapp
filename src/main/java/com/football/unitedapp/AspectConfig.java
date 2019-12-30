package com.football.unitedapp;

import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.football.unitedapp")
public class AspectConfig {

    @Autowired
    private MeterRegistry meterRegistry;

    @Aspect
    @Component
    public class ControllerLoggingAdvice {
        @Before("execution(* com.football.unitedapp.*Controller.*Player(*))")
        public void logController(JoinPoint joinPoint) {
            Object[] args = joinPoint.getArgs();
            Object arg = args[0];
            System.out.println("Logging controller  " + joinPoint.toString() + "  with arg " + arg);

        }
    }

    @Aspect
    @Component
    public class ControllerMetricAdvice {
        @Before("execution(* com.football.unitedapp.*Controller.*(*))")
        public void logController(JoinPoint joinPoint) {
            meterRegistry.counter("Aspect-Metric-Searches-Count",
                    "Controller Function", joinPoint.toString())
                    .increment();
            System.out.println("Calling controller metrics for " + joinPoint.toShortString());

        }

    }

    @Aspect
    @Component
    public class PlayerCountAdvice {
        @Before("execution(* com.football.unitedapp.*Controller.*getPlayer(*))")
        public void logController(JoinPoint joinPoint) {
            Object[] args = joinPoint.getArgs();
            Object playerId = args[0];
            meterRegistry.counter("players.searched.on",
                    "playerId", playerId.toString(),
                    "Team", "Manchester United")
                    .increment();

        }
    }

}