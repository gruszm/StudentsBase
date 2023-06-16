package com.StudentsBase.StudentsBase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Spring MVC.
 *
 * @author Mariusz Gruszczynski, Mateusz Pysera
 * @version 1.0
 * @since JDK 17
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Creates a HiddenHttpMethodFilter Bean. This filter allows for HTTP methods
     * such as PUT and DELETE to be sent via a POST request by including a
     * '_method' parameter in the request body.
     *
     * @return the HiddenHttpMethodFilter bean
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
