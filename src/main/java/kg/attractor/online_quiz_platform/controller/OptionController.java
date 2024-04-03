package kg.attractor.online_quiz_platform.controller;

import kg.attractor.online_quiz_platform.dto.OptionDto;
import kg.attractor.online_quiz_platform.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("options")
@RequiredArgsConstructor
public class OptionController {
    private final OptionService optionService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OptionDto>> getOptions() {
        return ResponseEntity.ok(optionService.getOptions());
    }
}
