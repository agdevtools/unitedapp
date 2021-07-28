package com.football.unitedapp;

import com.football.unitedapp.repository.TeamRepository;
import com.football.unitedapp.team.TeamServiceImpl;
import com.football.unitedapp.util.AspectConfig;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.Tag;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Configuration
@Import(AspectConfig.class)
public class AppConfig {

    @Bean
    public TeamServiceImpl teamServiceImpl(TeamRepository teamRepository) {
        return new TeamServiceImpl(teamRepository);
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("squid.rmq.cloudamqp.com");
        connectionFactory.setUri("amqps://cvofxaso:bh2MdB29UtcNXf7b2rwEB4gXGbmho9fV@squid.rmq.cloudamqp.com/cvofxaso");
        return connectionFactory;
    }

    @Bean
    public WebMvcTagsProvider webMvcTagsProvider() {
        return new WebMvcTagsProvider() {
            @Override
            public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler, Throwable exception) {
                ArrayList<Tag> tags = new ArrayList<>();

                tags.add(new ImmutableTag("club", "man united"));
                tags.add(new ImmutableTag("method", request.getMethod()));
                tags.add(new ImmutableTag("endpoint", getRequest_mapping(request)));
                tags.add(new ImmutableTag("status", String.valueOf(response.getStatus())));

                return tags;
            }

            private String getRequest_mapping(HttpServletRequest request) {
                String reqMapping = (String) request.getAttribute("bestMatchingPattern");
                return reqMapping == null ? "no route" : reqMapping;
            }

            @Override
            public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler) {
                return new ArrayList<>();
            }

        };

    }

    @Configuration
    public class ConFigureCORS {

        @Bean
        public FilterRegistrationBean corsFilter() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.addAllowedOrigin("http://localhost:7777");
            config.addAllowedOrigin("http://localhost:3000");
            config.addAllowedOrigin("https://unitedappfrontend.herokuapp.com");
            config.addAllowedOrigin("http://www.unitedapp.com:7777");
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            source.registerCorsConfiguration("/**", config);
            FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
            bean.setOrder(0);
            return bean;
        }
    }

}

