package com.entreprise.immobilier.config;

import com.entreprise.immobilier.security.JwtAuthenticationEntryPoint;
import com.entreprise.immobilier.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // pour utiliser @PreAuthorize
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 🔒 Désactivation CSRF (on est en JWT stateless)
                .csrf(csrf -> csrf.disable())

                // 🌐 Configuration CORS
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:5173")); // ton front React
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))

                // ⚠️ Gestion personnalisée des erreurs JWT
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))

                // 🧩 Pas de session (JWT → stateless)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 🚪 Définition des autorisations d’accès
                .authorizeHttpRequests(auth -> auth
                        // 🌍 Routes publiques (consultation sans connexion)
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/properties/**", // toutes les routes propriétés publiques
                                "/uploads/**",          // ⬅️ Autorise l’accès aux images
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // 👮‍♂️ Routes réservées aux administrateurs
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 👷‍♂️ Routes réservées aux agents ou admins
                        .requestMatchers("/api/agents/**").hasAnyRole("ADMIN", "AGENT")

                        // 🔐 Tout le reste nécessite un token JWT valide
                        .anyRequest().authenticated()
                )

                // 🔁 Ajout du filtre JWT avant l’authentification standard
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /** 🔑 Encodeur de mots de passe sécurisé */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** ⚙️ Gestionnaire d’authentification */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
