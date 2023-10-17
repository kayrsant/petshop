package br.csi.petshop.infra.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AutenticacaoFilter autenticacaoFilter;
    public SecurityConfig(AutenticacaoFilter filtro){
        this.autenticacaoFilter = filtro;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(crsf-> crsf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->
                        auth.requestMatchers(HttpMethod.POST,"/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/usuario/registrar").permitAll()
                                .requestMatchers(HttpMethod.GET,"/usuario").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/usuario/{id}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/usuario/{id}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/usuario/{id}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/cliente").hasAnyAuthority("ADMIN", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.POST, "/cliente/cadastrar").hasAnyAuthority("ADMIN", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.DELETE, "/cliente/{id}").hasAnyAuthority("ADMIN", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.PUT, "/cliente/{id}").hasAnyAuthority("ADMIN", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.GET, "/pet").hasAnyAuthority("ADMIN", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.POST, "/pet/cadastrar").hasAnyAuthority("ADMIN", "CLIENTE", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.PUT, "/pet/{id}").hasAnyAuthority("ADMIN", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.DELETE, "/pet/{id}").hasAnyAuthority("ADMIN", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.GET, "/produto").hasAnyAuthority("ADMIN", "CLIENTE", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.POST, "/produto/cadastrar").hasAnyAuthority("ADMIN", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.PUT, "/produto/{id}").hasAnyAuthority("ADMIN", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.DELETE, "/produto/{id}").hasAnyAuthority("ADMIN", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.GET, "/servico").hasAnyAuthority("ADMIN", "CLIENTE", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.POST, "/servico/cadastrar").hasAnyAuthority("ADMIN", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.DELETE, "/servico/{id}").hasAnyAuthority("ADMIN", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.PUT, "/servico/{id}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/funcionario").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/funcionario/cadastrar").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/funcionario/{id}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/funcionario/{id}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/agendamento").hasAnyAuthority("ADMIN", "FUNCIONARIO")
                                .requestMatchers(HttpMethod.POST, "/agendamento/criar").hasAnyAuthority("ADMIN", "FUNCIONARIO", "CLIENTE")
                                .requestMatchers(HttpMethod.DELETE, "/agendamento/{id}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/agendamento/{id}").hasAuthority("ADMIN")
                                .anyRequest().authenticated())
                .addFilterBefore(this.autenticacaoFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(acessoNegadoEntryPoint()))

                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception{
        return configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationEntryPoint acessoNegadoEntryPoint() {
        return (request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado. Você não tem permissão para acessar este recurso.");
        };
    }

}
