package es.pastorg.mpgstrava;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class BasicSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
        .antMatchers("/strava").anonymous();
        http
                .authorizeRequests()
                .antMatchers("/eventPushes/**","/","/accessTokens/**","/athleteActivityEmails/**",
                        "/refreshTokens/**","/activityTypes/**","/profile/**")
                .authenticated()
                .and()
                .formLogin();


    }
}
