package com.football.unitedapp.util;

import com.football.unitedapp.team.TeamRequest;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
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

//    @Component
//    @Aspect
//    public class PostRequestLogger {
//
//        @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
//        public void postAction() {
//        }
//
//        @After("postAction()")
//        public void logAction(JoinPoint joinPoint) {
//            logger.info("******** Starting Request Proxy************");
//
//            String payload = getPayload(joinPoint);
//            logger.info("Payload =  " + payload);
//
//            final String uri = "https://unitedappapi.herokuapp.com/team";
//
//            RestTemplate restTemplate = new RestTemplate();
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//            String[] parts = payload.split("\\{");
//            String part1 = parts[0]; // 004-
//            String part2 = parts[1]; // 034556
//            String part3 = "{" + part2;
//
//            TeamRequest teamRequest1 = new Gson().fromJson(part3, TeamRequest.class);
//            HttpEntity<TeamRequest> entity = new HttpEntity<>(teamRequest1, headers);
//
//
//            restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
//
//            logger.info("*******  Finishing REST CALL **********");
//        }
//
//        private String getPayload(JoinPoint joinPoint) {
//            CodeSignature signature = (CodeSignature) joinPoint.getSignature();
//            StringBuilder builder = new StringBuilder();
//            for (int i = 0; i < joinPoint.getArgs().length; i++) {
//                String parameterName = signature.getParameterNames()[i];
//                builder.append(parameterName);
//                builder.append(": ");
//                builder.append(joinPoint.getArgs()[i].toString());
//            }
//            return builder.toString();
//        }
//
//    }

}
