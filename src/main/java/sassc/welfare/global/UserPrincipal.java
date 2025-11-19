package sassc.welfare.global;

import java.io.Serializable;

public record UserPrincipal(Long id, String username, String name) implements Serializable {}
