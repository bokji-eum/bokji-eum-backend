package sassc.welfare.global;

import java.io.Serializable;

public record UserPrincipal(Long id, String email, String name) implements Serializable {}
