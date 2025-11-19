package sassc.welfare.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sassc.welfare.global.JwtProvider;
import sassc.welfare.global.UserPrincipal;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public AuthResponse signUp(SignUpRequest req) {
        if (userRepository.existsByUsername(req.username())) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }

        String hash = encoder.encode(req.password());
        User user = User.builder()
                .username(req.username().toLowerCase())
                .passwordHash(hash)
                .name(req.name())
                .build();

        userRepository.save(user);

        String token = jwtProvider.generateToken(user.getId(), user.getUsername());
        return new AuthResponse(token, "Bearer", user.getId(), user.getUsername(), user.getName());
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByUsername(req.username());
        if (user == null || !encoder.matches(req.password(), user.getPasswordHash())) {
            throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtProvider.generateToken(user.getId(), user.getUsername());
        return new AuthResponse(token, "Bearer", user.getId(), user.getUsername(), user.getName());
    }

    @Transactional(readOnly = true)
    public UserResponse getMe(UserPrincipal principal) {
        return new UserResponse(
                principal.id(),
                principal.username(),
                principal.name()
        );
    }

}
