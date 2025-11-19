package sassc.welfare.recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/welfare")
@RequiredArgsConstructor
public class WelfareController {

    private final WelfareService welfareService;

    @PostMapping("/recommend")
    public ResponseEntity<WelfareRecommendResponse> recommend(
            @RequestBody WelfareRecommendRequest request
    ) {
        WelfareRecommendResponse response = welfareService.requestRecommendation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(
                java.util.Map.of(
                        "status", "UP",
                        "service", "Python Backend"
                )
        );
    }
}
