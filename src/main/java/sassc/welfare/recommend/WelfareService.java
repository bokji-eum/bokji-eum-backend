package sassc.welfare.recommend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Slf4j
@RequiredArgsConstructor
public class WelfareService {

    private final WebClient webClient;

    public WelfareRecommendResponse requestRecommendation(WelfareRecommendRequest req) {
        try {
            log.info("파이썬 전송 확인: {}", req);

            return webClient.post()
                    .uri("http://localhost:8000/api/welfare/recommend")
                    .bodyValue(req)
                    .retrieve()
                    .bodyToMono(WelfareRecommendResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("파이썬 에러: status={}, body={}",
                    e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw e;
        } catch (Exception e) {
            log.error("파이썬 실패", e);
            throw e;
        }
    }
}
