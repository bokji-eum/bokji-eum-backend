package sassc.welfare.stt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SttService {

    private final WebClient webClient;

    @Value("${stt.openai.url}")
    private String url;

    @Value("${stt.openai.model}")
    private String model;

    @Value("${stt.openai.api-key}")
    private String apiKey;

    public SttResponse transcribe(MultipartFile file, @Nullable String language) {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("audio file is empty");
        validateContentType(file.getContentType());

        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", file.getResource())
                .filename(file.getOriginalFilename() != null ? file.getOriginalFilename() : "audio");
        bodyBuilder.part("model", model);
        if (language != null && !language.isBlank()) bodyBuilder.part("language", language); // ko 권장

        Map<?,?> res = webClient.post()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        String text = res != null && res.get("text") != null ? res.get("text").toString() : "";
        return new SttResponse(text, null);
    }

    private void validateContentType(String ct) {
        if (ct == null) throw new IllegalArgumentException("missing content-type");
        if (!(ct.startsWith("audio/") || ct.equals("video/mp4"))) {
            throw new IllegalArgumentException("unsupported media type: " + ct);
        }
    }
}
