package ticket.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ticket.dto.ShowInfoResponseDto;
import ticket.service.ShowAndSeatService;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/test")
public class TestController {
    private final ShowAndSeatService showAndSeatService;

    @GetMapping("/show/movie")
    public String movieInfo(Model model){
        List<ShowInfoResponseDto> shows = showAndSeatService.findAllShowInfo();

        shows.removeIf(n -> (!n.getClassification().equals("movie")));

        model.addAttribute("subject","MOVIE");
        model.addAttribute("shows",shows);
        model.addAttribute("size",shows.size());

        return "show";
    }

    @GetMapping("/show/play")
    public String playInfo(Model model){
        List<ShowInfoResponseDto> shows = showAndSeatService.findAllShowInfo();

        shows.removeIf(n -> (!n.getClassification().equals("play")));

        model.addAttribute("subject","PLAY");
        model.addAttribute("shows",shows);
        model.addAttribute("size",shows.size());

        return "show";
    }

    @GetMapping("/show/concert")
    public String concertInfo(Model model){
        List<ShowInfoResponseDto> shows = showAndSeatService.findAllShowInfo();

        shows.removeIf(n -> (!n.getClassification().equals("concert")));

        model.addAttribute("subject","CONCERT");
        model.addAttribute("shows",shows);
        model.addAttribute("size",shows.size());

        return "show";
    }

}
