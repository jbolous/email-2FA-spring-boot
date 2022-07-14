package com.behl.salamanca.security.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.behl.salamanca.security.CustomUserDetailService;
import com.behl.salamanca.security.constant.ApiPathExclusion;
import com.behl.salamanca.security.filter.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService customUserDetialService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetialService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                //.antMatchers("/","/aboutus").permitAll()  //dashboard , Aboutus page will be permitted to all user
                .antMatchers("/admin/**").hasAnyRole("ADMIN") //Only admin user can login
                .antMatchers("/user/**").hasAnyRole("USER") //Only normal user can login
                .antMatchers(List.of(ApiPathExclusion.values()).stream().map(apiPath -> apiPath.getPath())
                        .toArray(String[]::new))
                .permitAll().anyRequest().authenticated().and()

                .formLogin()
                .loginPage("/login")  //Loginform all can access ..
                .defaultSuccessUrl("/dashboard")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

      /*  http.csrf().disable().authorizeRequests()
                .antMatchers("/","/aboutus").permitAll()  //dashboard , Aboutus page will be permit to all user
                .antMatchers("/admin/**").hasAnyRole("ADMIN") //Only admin user can login
                .antMatchers("/user/**").hasAnyRole("USER") //Only normal user can login
                .anyRequest().authenticated() //Rest of all request need authentication
                .and()
                .formLogin()
                .loginPage("/login")  //Loginform all can access ..
                .defaultSuccessUrl("/dashboard")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .permitAll()*/
                /*.and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)*/;
    }

}