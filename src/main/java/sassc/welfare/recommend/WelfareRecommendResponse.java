package sassc.welfare.recommend;

import java.util.List;

public record WelfareRecommendResponse(
        List<Recommendation> recommendations,
        Integer total_count,
        String message
) {

    public record Recommendation(
            String title,
            String target,
            String content,
            String method,
            String inquiry,
            Integer match_score,
            String category,
            Tags tags
    ) {}

    public record Tags(
            String age_group,
            String income_level,
            String region,
            String institution,
            String benefit_type,
            String application_method
    ) {}
}
