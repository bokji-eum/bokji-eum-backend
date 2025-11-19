package sassc.welfare.survey;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String category;

    @Column(columnDefinition = "json")
    private String answersJson;

    private LocalDateTime createdAt;

    @PrePersist
    void initTime() {
        this.createdAt = LocalDateTime.now();
    }
}
