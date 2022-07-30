package pl.sda.arppl4.spring.security.config.jwt;

/**
 * @author Paweł Recław, AmeN
 * @project arppl4_spring_security
 * @created 30.07.2022
 */
public interface SecurityConstants {
    String APPLICATION_NAME = "sPRINGsECURITY";
    String APPLICATION_KEY = "ArPpL4SeCrEt";
    String HEADER_AUTH = "Authorization";           // Standard
    String HEADER_AUTH_BEARER = "Bearer:";          // Standard
    String HEADER_EXPIRATION = "Expires_at";

    String HEADER_ROLES = "App_roles";
    String ROLES_SEPARATOR = ",";
}
