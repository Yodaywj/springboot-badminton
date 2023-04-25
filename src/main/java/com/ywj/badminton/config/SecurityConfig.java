//package com.ywj.badminton.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/stadium/**").authenticated() // 对 /api/secure/ 下的接口进行用户验证
//                .antMatchers("/**").permitAll() // 对 /api/public/ 下的接口不进行限制
//                .and()
//                .formLogin() // 使用表单登录
//                .and()
//                .httpBasic(); // 使用HTTP基本认证
//        return http.build();
//    }
//
//}