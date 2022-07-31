package pl.sda.arppl4.spring.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.sda.arppl4.spring.security.config.jwt.AuthenticationFilter;
import pl.sda.arppl4.spring.security.config.jwt.AuthorizationFilter;
import pl.sda.arppl4.spring.security.service.ApplicationUserService;

/**
 * @author Paweł Recław, AmeN
 * @project arppl4_spring_security
 * @created 30.07.2022
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationUserService applicationUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // wyłącz csrf (cross site request forgery)
                .csrf().disable()
                // wyłącz cross origin resource sharing
                .cors().disable()
                // dalej konfigurujemy autoryzację requestów
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/test").permitAll()
                    .antMatchers("/api/public/**").permitAll()
                    .antMatchers("/api/private/**").authenticated()
                    .antMatchers("/api/test/authorized").hasRole("ADMIN")
                .anyRequest().authenticated()
                // i... (cofamy się do konfiguratora HttpSecurity, bo przed chwilą byliśmy w 'authorizeRequests()')
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager()))
                .addFilter(new AuthorizationFilter(authenticationManager()))
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic();
    }

    /**
     * Konfiguracja authentication providera - wskazujemy Spring Authentication Manager gdzie jest nasz authentication provider (ma być DAO).
     * Manager pochodzi ze spring'a. Provider pochodzi ze spring'a, ale my wskazujemy providerowi service który provider odpytuje w celu wyszukiwania użytkownika.
     * @param auth the {@link AuthenticationManagerBuilder} to use
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);                        // password encoder
        daoAuthenticationProvider.setUserDetailsService(applicationUserService);

        // ustaw providera w managerze
        auth.authenticationProvider(daoAuthenticationProvider);
    }

}
