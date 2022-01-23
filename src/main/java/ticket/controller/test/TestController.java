package ticket.controller.test;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ticket.dto.LogResponseDto;
import ticket.dto.SeatResponseDto;
import ticket.dto.ShowInfoResponseDto;
import ticket.dto.ResponseUser;
import ticket.dto.UserDTO;
import ticket.service.LogService;
import ticket.service.ShowAndSeatService;
import ticket.service.UserService;

import javax.servlet.http.Cookie;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class TestController {
    private final ShowAndSeatService showAndSeatService;
    private final UserService userService;
    private final LogService logService;

    @GetMapping("/show/movie")
    public String movieInfo(Model model) {
        List<ShowInfoResponseDto> shows = showAndSeatService.findAllShowInfo();

        shows.removeIf(n -> (!n.getClassification().equals("movie")));
        Collections.reverse(shows);

        model.addAttribute("subject", "MOVIE");
        model.addAttribute("shows", shows);
        model.addAttribute("size", shows.size());

        return "show";
    }

    @GetMapping("/show/play")
    public String playInfo(Model model) {
        List<ShowInfoResponseDto> shows = showAndSeatService.findAllShowInfo();

        shows.removeIf(n -> (!n.getClassification().equals("play")));
        Collections.reverse(shows);

        model.addAttribute("subject", "PLAY");
        model.addAttribute("shows", shows);
        model.addAttribute("size", shows.size());

        return "show";
    }

    @GetMapping("/show/concert")
    public String concertInfo(Model model) {
        List<ShowInfoResponseDto> shows = showAndSeatService.findAllShowInfo();

        shows.removeIf(n -> (!n.getClassification().equals("concert")));
        Collections.reverse(shows);

        model.addAttribute("subject", "CONCERT");
        model.addAttribute("shows", shows);
        model.addAttribute("size", shows.size());

        return "show";
    }

    @GetMapping("/ticketing")
    public String ticketInfo(@RequestParam String showId, Model model) {
        long id = Long.parseLong(showId);
        ShowInfoResponseDto show = showAndSeatService.findShowInfoById(id);
        List<SeatResponseDto> seats = showAndSeatService.findSeatByShowInfoId(id);

        List<String> gradeList = new ArrayList<>();
        for (SeatResponseDto seat : seats) {
            gradeList.add(seat.getGrade());
        }
        //grade리스트만
        List<String> distinctGradeList = gradeList.stream().distinct().collect(Collectors.toList());


        model.addAttribute("gradeList", distinctGradeList);
        model.addAttribute("show", show);
        model.addAttribute("seats", seats);
        return "reservation";
    }

    @GetMapping("/show/register")
    public String registerShow(@CookieValue(name = "userId", required = false) Cookie cookie) throws Exception {
        if (ObjectUtils.isEmpty(cookie)) {
            throw new NullPointerException();
        }

        UserDTO user = userService.getUsersByUserId(Long.parseLong(cookie.getValue()));
        if (user.getRole().equalsIgnoreCase("admin")) {
            return "registerShow";
        } else {
            throw new Exception("Forbidden");
        }
    }

    @GetMapping("/mypage")
    public String getMyPage(@CookieValue(name = "userId", required = false) Cookie cookie, Model model) throws Exception{
        if (ObjectUtils.isEmpty(cookie)) {
            throw new NullPointerException();
        }

        UserDTO userDTO = userService.getUsersByUserId(Long.parseLong(cookie.getValue()));
        ResponseUser user = new ModelMapper().map(userDTO, ResponseUser.class);
        List<LogResponseDto> logs = logService.findByUserId(user.getUserId())
                .stream().map(ticketingLog -> new LogResponseDto(ticketingLog)).collect(Collectors.toList());

        model.addAttribute("user", user);
        model.addAttribute("logs", logs);

        return "myPage";
    }
}
