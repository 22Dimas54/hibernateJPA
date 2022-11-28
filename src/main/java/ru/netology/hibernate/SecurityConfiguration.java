package ru.netology.hibernate;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("read").password("{noop}read").authorities("read")
                .and()
                .withUser("write").password("{noop}write").authorities("write");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .and()
                .authorizeRequests().antMatchers("/hello").permitAll()
                .and()
                .authorizeRequests().antMatchers("/persons/create", "/persons/update", "/persons/delete").hasAuthority("write")
                .and()
                .authorizeRequests().antMatchers("/persons/by-city", "/persons/readAll",
                "/persons/find-city", "/persons/find-age", "/persons/find-name-surname").hasAuthority("read")
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }
}
