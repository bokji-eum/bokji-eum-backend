package sassc.welfare.auth;

public record UserResponse (
    Long userId,
    String username,
    String name
) {
        public static UserResponse from(User user) {
            return new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getName()
            );
        }
}
