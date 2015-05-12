package jp.geekschool.web;

import jp.geekschool.web.filter.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * register filter that encoding in UTF-8.
     *
     * @return {@link org.springframework.boot.context.embedded.FilterRegistrationBean}
     */
    @Bean
    public FilterRegistrationBean encodingFilterRegistrationBean() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(encodingFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    /**
     * register authentication filter.
     *
     * @return {@link org.springframework.boot.context.embedded.FilterRegistrationBean}
     */
    @Bean
    public FilterRegistrationBean authFilterRegistrationBean() {
        AuthFilter authFilter = new AuthFilter();

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(authFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }

}
