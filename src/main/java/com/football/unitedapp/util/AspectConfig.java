package com.football.unitedapp.util;

import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.football.unitedapp")
public class AspectConfig {

    @Autowired
    private MeterRegistry meterRegistry;

    private static final Logger logger = Logger.getLogger(AspectConfig.class.getName());

    @Aspect
    @Component
    public class ControllerLoggingAdvice {
        @Before("execution(* com.football.unitedapp.team.*Controller.*Player(*))")
        public void logController4(JoinPoint joinPoint) {
            Object[] args = joinPoint.getArgs();
            Object arg = args[0];

            logger.info("Logging controller  " + joinPoint.toString() + "  with arg " + arg);

        }
    }

    @Aspect
    @Component
    public class ControllerMetricAdvice {
        @Before("execution(* com.football.unitedapp.team.*Controller.*(*))")
        public void logController3(JoinPoint joinPoint) {
            meterRegistry.counter("Aspect-Metric-Searches-Count",
                    "Controller Function", joinPoint.toString())
                    .increment();

            logger.info("Calling controller metrics for " + joinPoint.toShortString());
        }

    }

    @Aspect
    @Component
    public class PlayerCountAdvice {
        @Before("execution(* com.football.unitedapp.team.*Controller.*getPlayer(*))")
        public void logController(JoinPoint joinPoint) {
            Object[] args = joinPoint.getArgs();
            Object playerId = args[0];
            meterRegistry.counter("players.searched.on",
                    "playerId", playerId.toString(),
                    "Team", "Manchester United")
                    .increment();

        }

        @Aspect
        @Component
        public class ControllerLoggingAllAdvice {
           // @Before("execution(* com.football.unitedapp.*Controller.*(..))")
            @Before("execution(public * com.football.unitedapp.team.*Controller.get*(..))")
            public void logController2(JoinPoint joinPoint) {
              //  Object[] args = joinPoint.getArgs();
              //  Object arg = args[0];

                logger.info("Logging controller  " + joinPoint.toString() + "  with no arg ");

            }
        }
    }

}