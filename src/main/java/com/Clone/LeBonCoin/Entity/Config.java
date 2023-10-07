package com.Clone.LeBonCoin.Entity;

import com.Clone.LeBonCoin.filter.AdminFilter;
import com.Clone.LeBonCoin.filter.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class Config {
    @Bean
    public AdminFilter adminFilter(){
        return new AdminFilter();
    }
    @Bean
    public UserFilter userFilter(){
        return new UserFilter();
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
    @Bean
    public FilterRegistrationBean<AdminFilter> adminAuth() {
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean();

        registrationBean.setFilter(adminFilter());
        registrationBean.addUrlPatterns("/admin/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean<UserFilter> userAuth() {
        FilterRegistrationBean<UserFilter> registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(userFilter());
        registrationBean.addUrlPatterns("/user/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
