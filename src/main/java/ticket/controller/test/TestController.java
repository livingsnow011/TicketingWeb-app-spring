package ticket.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ticket.dto.SeatResponseDto;
import ticket.dto.ShowInfoResponseDto;
import ticket.service.ShowAndSeatService;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/test")
public class TestController {
    private final ShowAndSeatService showAndSeatService;

    @GetMapping("/show/movie")
    public String movieInfo(Model model){
        List<ShowInfoResponseDto> shows = showAndSeatService.findAllShowInfo();

        shows.removeIf(n -> (!n.getClassification().equals("movie")));
        Collections.reverse(shows);

        model.addAttribute("subject","MOVIE");
        model.addAttribute("shows",shows);
        model.addAttribute("size",shows.size());

        return "show";
    }

    @GetMapping("/show/play")
    public String playInfo(Model model){
        List<ShowInfoResponseDto> shows = showAndSeatService.findAllShowInfo();

        shows.removeIf(n -> (!n.getClassification().equals("play")));
        Collections.reverse(shows);

        model.addAttribute("subject","PLAY");
        model.addAttribute("shows",shows);
        model.addAttribute("size",shows.size());

        return "show";
    }

    @GetMapping("/show/concert")
    public String concertInfo(Model model){
        List<ShowInfoResponseDto> shows = showAndSeatService.findAllShowInfo();

        shows.removeIf(n -> (!n.getClassification().equals("concert")));
        Collections.reverse(shows);

        model.addAttribute("subject","CONCERT");
        model.addAttribute("shows",shows);
        model.addAttribute("size",shows.size());

        return "show";
    }

    @GetMapping("/ticketing")
    public String ticketInfo(@RequestParam String showId, Model model){
        long id = Long.parseLong(showId);
        ShowInfoResponseDto show = showAndSeatService.findShowInfoById(id);
        List<SeatResponseDto> seats =showAndSeatService.findSeatByShowInfoId(id);

        List<String> gradeList = new ArrayList<>();
        for (SeatResponseDto seat : seats) {
                        gradeList.add(seat.getGrade());
        }
        //grade리스트만
        List<String> distinctGradeList = gradeList.stream().distinct().collect(Collectors.toList());


        model.addAttribute("gradeList",distinctGradeList);
        model.addAttribute("show",show);
        model.addAttribute("seats",seats);
        return "reservation";
    }
}