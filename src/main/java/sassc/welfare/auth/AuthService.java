package sassc.welfare.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sassc.welfare.global.JwtProvider;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public AuthResponse signUp(SignUpRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }

        String hash = encoder.encode(req.password());
        User user = User.builder()
                .email(req.email().toLowerCase())
                .passwordHash(hash)
                .name(req.name())
                .build();

        userRepository.save(user);

        String token = jwtProvider.generateToken(user.getId(), user.getEmail());
        return new AuthResponse(token, "Bearer", user.getId(), user.getEmail(), user.getName());
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.email());
        if (user == null || !encoder.matches(req.password(), user.getPasswordHash())) {
            throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtProvider.generateToken(user.getId(), user.getEmail());
        return new AuthResponse(token, "Bearer", user.getId(), user.getEmail(), user.getName());
    }
}
