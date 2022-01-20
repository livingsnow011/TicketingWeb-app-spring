package ticket.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ticket.dto.ShowInfoResponseDto;
import ticket.service.ShowAndSeatService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/test")
public class TestController {
    private final ShowAndSeatService showAndSeatService;

    @GetMapping("/show/movie")
    public String movieInfo(Model model){
        List<ShowInfoResponseDto> shows = showAndSeatService.findAllShowInfo();
        model.addAttribute("subject","영화 목록");
        model.addAttribute("shows",shows);
        return "test/show";
    }

    @GetMapping("/show/play")
    public String playInfo(Model model){
        List<ShowInfoResponseDto> shows = showAndSeatService.findAllShowInfo();
        model.addAttribute("subject","연극 목록");
        model.addAttribute("shows",shows);
        return "test/show";
    }

    @GetMapping("/show/concert")
    public String concertInfo(Model model){
        List<ShowInfoResponseDto> shows = showAndSeatService.findAllShowInfo();
        model.addAttribute("subject","콘서트 목록");
        model.addAttribute("shows",shows);
        return "test/show";
    }

}
