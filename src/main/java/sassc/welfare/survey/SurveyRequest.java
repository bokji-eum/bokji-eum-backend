package sassc.welfare.survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyRequest {

    private String category;

    // 공통
    private Integer age;
    private String gender;
    private String household_type;
    private Integer child_count;
    private String disability;
    private String income_level;
    private String region;

    // 생계 / 취업 / 보건 / 청년 등 카테고리별
    private String employment;
    private String pregnant;
    private String chronic_disease;
}
