package cl.inacap.authjwt.jwtauth.Jwt; // Declaración del paquete

import java.io.IOException; // Importando IOException para manejar excepciones de entrada/salida

import org.springframework.http.HttpHeaders; // Importando HttpHeaders para manejar encabezados HTTP
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Importando UsernamePasswordAuthenticationToken para la autenticación
import org.springframework.security.core.context.SecurityContextHolder; // Importando SecurityContextHolder para obtener el contexto de seguridad
import org.springframework.security.core.userdetails.UserDetails; // Importando UserDetails para obtener detalles del usuario
import org.springframework.security.core.userdetails.UserDetailsService; // Importando UserDetailsService para cargar detalles del usuario
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource; // Importando WebAuthenticationDetailsSource para construir detalles de autenticación web
import org.springframework.stereotype.Component; // Importando Component para marcar esta clase como un componente de Spring
import org.springframework.web.filter.OncePerRequestFilter; // Importando OncePerRequestFilter para filtrar solicitudes una vez por petición
import org.springframework.util.StringUtils; // Importando StringUtils para manejar cadenas de texto

import jakarta.servlet.FilterChain; // Importando FilterChain para manejar la cadena de filtros
import jakarta.servlet.ServletException; // Importando ServletException para manejar excepciones de servlets
import jakarta.servlet.http.HttpServletRequest; // Importando HttpServletRequest para manejar solicitudes HTTP
import jakarta.servlet.http.HttpServletResponse; // Importando HttpServletResponse para manejar respuestas HTTP
import lombok.RequiredArgsConstructor; // Importando RequiredArgsConstructor para generar un constructor con argumentos requeridos

@Component // Anotación para marcar esta clase como un componente de Spring
@RequiredArgsConstructor // Genera un constructor con argumentos requeridos (campos finales)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService; // Inyección de dependencia de JwtService
    private final UserDetailsService userDetailsService; // Inyección de dependencia de UserDetailsService

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request); // Obtener el token de la solicitud
        final String username;

        if (token == null) { // Si no hay token, continuar con la cadena de filtros
            filterChain.doFilter(request, response);
            return;
        }

        username = jwtService.getUsernameFromToken(token); // Obtener el nombre de usuario del token

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { // Si el nombre de usuario no es nulo y no hay autenticación en el contexto de seguridad
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Cargar los detalles del usuario

            if (jwtService.isTokenValid(token, userDetails)) { // Si el token es válido
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()); // Crear un token de autenticación

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Establecer los detalles de autenticación

                SecurityContextHolder.getContext().setAuthentication(authToken); // Establecer la autenticación en el contexto de seguridad
            }
        }

        filterChain.doFilter(request, response); // Continuar con la cadena de filtros
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION); // Obtener el encabezado de autorización

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) { // Si el encabezado tiene texto y comienza con "Bearer "
            return authHeader.substring(7); // Devolver el token sin el prefijo "Bearer "
        }
        return null; // Devolver nulo si no hay token
    }
}