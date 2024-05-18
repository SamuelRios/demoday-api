package com.demodayapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.demodayapi.resources.CorsFilter;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DemodayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemodayApplication.class, args);
	}

	  @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorsFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("CorsFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
