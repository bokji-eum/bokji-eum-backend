package sassc.welfare.stt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/stt")
@RequiredArgsConstructor
public class SttController {

    private final SttService sttService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SttResponse transcribe(
            @RequestPart("file") MultipartFile file,
            @RequestPart(value = "language", required = false) String language
    ) {
        return sttService.transcribe(file, language);
    }
}
