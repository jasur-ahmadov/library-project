package az.egov.libraryproject.config;

import az.egov.libraryproject.jwt.JwtTokenVerifier;
import az.egov.libraryproject.jwt.JwtUsernamePasswordAuthenticationFilter;
import az.egov.libraryproject.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserService appUserService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, AppUserService appUserService, SecretKey secretKey, JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.appUserService = appUserService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // Disable CSRF (cross site request forgery)
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/login/**", "/api/registration/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated(); // authenticate everything else
        http.addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey));
        http.addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernamePasswordAuthenticationFilter.class);
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
////                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
//                .csrf().disable() // ease of use
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // doesn't store session ID on database
//                .and()
//                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
//                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
//                .antMatchers("/api/**").hasRole(STUDENT.name())
//                .anyRequest()
//                .authenticated().and();
////                .formLogin();
////                .and()
//////                .httpBasic();
////                .formLogin()
////                    .loginPage("/login").permitAll()
////                    .defaultSuccessUrl("/courses", true)
////                    .usernameParameter("username")
////                    .passwordParameter("password")
////                .and()
////                .rememberMe() // defaults to 2 weeks
////                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
////                    .key("something_very_secured")
////                    .rememberMeParameter("remember-me")
////                .and()
////                .logout()
////                    .logoutUrl("/logout")
////                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // because you disabled csrf protection, enable elesek POST gedecek avtomatik. Soldaki line-i silenden sonra
////                    .clearAuthentication(true)
////                    .invalidateHttpSession(true)
////                    .deleteCookies("JSESSIONID", "remember-me")
////                    .logoutSuccessUrl("/login");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }
}