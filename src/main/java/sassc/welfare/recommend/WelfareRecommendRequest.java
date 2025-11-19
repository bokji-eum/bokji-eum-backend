package sassc.welfare.recommend;

public record WelfareRecommendRequest(
        String category,
        Integer age,
        String gender,
        String household_type,
        Integer child_count,
        String disability,
        String income_level,
        String employment,
        String pregnant,
        String chronic_disease,
        String region
) {}
