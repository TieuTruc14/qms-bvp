package qms.bvp.security;

/**
 * Created by Admin on 5/11/2018.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@EnableTransactionManagement
//@ComponentScan(basePackageClasses = MyAccessDeniedHandler.class)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    //@EnableTransactionManagement thay the <tx:annotation-driven/> trong xml. giup load lazy fetch object hibernate
    //@EnableGlobalMethodSecurity(securedEnabled = true) giup su dung @Secured() tai method

//    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Cấu hình remember me, thời gian là 1296000 giây/15 ngay
        http.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(1296000);

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/reception/**", "/door/**","/area/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(myAuthenticationSuccessHandler)
//                .defaultSuccessUrl("/",true)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .permitAll()
                .and()
                .exceptionHandling()
                .and()
                .httpBasic();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new ManhUserDetailService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**","/bootstrap/**","/note/**");
    }

}