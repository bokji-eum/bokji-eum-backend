package sassc.welfare.survey;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sassc.welfare.auth.User;
import sassc.welfare.global.UserPrincipal;

import java.util.Map;

@RestController
@RequestMapping("/api/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping
    public ResponseEntity<Void> submitSurvey(
            @RequestBody SurveyRequest request,
            @AuthenticationPrincipal User user
    ) {
        Long userId = (user == null ? null : user.getId());
        surveyService.saveSurvey(userId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{category}/latest")
    public ResponseEntity<SurveyRequest> getLatest(
            @PathVariable String category,
            @AuthenticationPrincipal User user
    ) {
        if (user == null) return ResponseEntity.status(401).build();
        SurveyRequest survey = surveyService.getLatestSurvey(user.getId(), category);
        return ResponseEntity.ok(survey);
    }

}
