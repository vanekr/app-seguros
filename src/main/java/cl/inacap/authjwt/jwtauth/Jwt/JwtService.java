package cl.inacap.authjwt.jwtauth.Jwt; // Declaración del paquete

import java.security.Key; // Importando Key para manejar claves de seguridad
import java.util.Date; // Importando Date para manejar fechas
import java.util.HashMap; // Importando HashMap para manejar mapas de datos
import java.util.Map; // Importando Map para manejar mapas de datos
import java.util.function.Function; // Importando Function para manejar funciones

import org.springframework.security.core.userdetails.UserDetails; // Importando UserDetails para obtener detalles del usuario
import org.springframework.stereotype.Service; // Importando Service para marcar esta clase como un servicio de Spring

import io.jsonwebtoken.Claims; // Importando Claims para manejar reclamaciones JWT
import io.jsonwebtoken.Jwts; // Importando Jwts para manejar JWT
import io.jsonwebtoken.SignatureAlgorithm; // Importando SignatureAlgorithm para manejar algoritmos de firma
import io.jsonwebtoken.io.Decoders; // Importando Decoders para manejar decodificación
import io.jsonwebtoken.security.Keys; // Importando Keys para manejar claves de seguridad

@Service // Anotación para marcar esta clase como un servicio de Spring
public class JwtService {

    private static final String SECRET_KEY = "JHKEFSUYIR3478IHFJNL423R9YETGLNRWNLJ42UORIREGBNOH"; // Clave secreta para firmar el JWT

    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user); // Genera un token JWT sin reclamaciones adicionales
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims) // Establece las reclamaciones adicionales
                .setSubject(user.getUsername()) // Establece el sujeto del token (nombre de usuario)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Establece la fecha de emisión del token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Establece la fecha de expiración del token
                .signWith(getKey(), SignatureAlgorithm.HS256) // Firma el token con la clave secreta y el algoritmo HS256
                .compact(); // Compacta el token en una cadena
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Decodifica la clave secreta
        return Keys.hmacShaKeyFor(keyBytes); // Genera una clave HMAC a partir de los bytes de la clave secreta
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject); // Obtiene el nombre de usuario del token
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token); // Obtiene el nombre de usuario del token
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Verifica si el token es válido
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey()) // Establece la clave de firma
                .build()
                .parseClaimsJws(token) // Analiza el token JWT
                .getBody(); // Obtiene el cuerpo de las reclamaciones
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token); // Obtiene todas las reclamaciones del token
        return claimsResolver.apply(claims); // Aplica la función de resolución de reclamaciones
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration); // Obtiene la fecha de expiración del token
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date()); // Verifica si el token ha expirado
    }
}