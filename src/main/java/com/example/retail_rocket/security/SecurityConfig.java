package com.example.retail_rocket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //Tells that don't go with the default flow, use my config
public class SecurityConfig  {
    //SPring will have no idea or will provide a default one,
    // but we want to use it our object, thast we will cretae our own class - CustomUserDetailsService
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(request-> request
                        //dont check credntial for this api
                        .requestMatchers("api/users").hasAuthority("admin")
                        .requestMatchers("api/products/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.POST,"api/products").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"api/orders").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"api/orders/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/cart/**").hasAuthority("admin")

                        .requestMatchers("api/login","api/register")
                        .permitAll()

                        .anyRequest().authenticated())

                .formLogin(Customizer.withDefaults())//for postman from login will not work, wihtout writing the next line
                .httpBasic(Customizer.withDefaults())

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                //Now the statless sesson, means create new session id everytime
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

        //This will simply disable all security filters
        // return http.build();
    }
   /* @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1 = User
                .withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("user")
                .build();
        return new InMemoryUserDetailsManager(user1);
    }*/

    //Now we create our own authentication provider,
    @Bean
    public AuthenticationProvider authenticationProvider(){
        //for database we will use this, DaoAuthenticationProvider inherits
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //For password in plaintext
        //provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

        //For validating passord in hash
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    //Lets work for jwt token
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
