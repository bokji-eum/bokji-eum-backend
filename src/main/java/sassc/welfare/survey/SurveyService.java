package sassc.welfare.survey;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final ObjectMapper objectMapper;

    public void saveSurvey(Long userId, SurveyRequest request) {
        String json = serialize(request);

        Survey survey = Survey.builder()
                .userId(userId)
                .category(request.getCategory())
                .answersJson(json)
                .build();

        surveyRepository.save(survey);
    }

    public SurveyRequest getLatestSurvey(Long userId, String category) {
        Survey survey = surveyRepository
                .findTop1ByUserIdAndCategoryOrderByCreatedAtDesc(userId, category);

        if (survey == null) {
            throw new RuntimeException("최근 설문 없음");
        }

        return deserialize(survey.getAnswersJson());
    }

    private String serialize(SurveyRequest req) {
        try {
            return objectMapper.writeValueAsString(req);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SurveyRequest deserialize(String json) {
        try {
            return objectMapper.readValue(json, SurveyRequest.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
