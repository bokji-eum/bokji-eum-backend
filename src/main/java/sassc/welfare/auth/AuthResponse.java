package sassc.welfare.auth;

public record AuthResponse(String accessToken, String tokenType, Long userId, String username, String name) {}
