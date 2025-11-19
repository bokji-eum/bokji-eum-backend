package sassc.welfare.survey;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Survey findTop1ByUserIdAndCategoryOrderByCreatedAtDesc(
            Long userId,
            String category
    );
}
