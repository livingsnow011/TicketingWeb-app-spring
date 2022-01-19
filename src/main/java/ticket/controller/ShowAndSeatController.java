package ticket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticket.dto.SeatResponseDto;
import ticket.dto.ShowInfoResponseDto;
import ticket.dto.ShowInfoSaveRequestDto;
import ticket.service.ShowAndSeatService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ShowAndSeatController {
    private final ShowAndSeatService showAndSeatService;

    @PostMapping("/api/v1/show")
    public ResponseEntity<String> save(@RequestBody ShowInfoSaveRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(showAndSeatService.saveShowAndSeat(requestDto));
    }

    @DeleteMapping("/api/v1/show/{id}")
    public void delete(@PathVariable Long id) {
        showAndSeatService.deleteShowAndSeat(id);
    }

    @GetMapping("/api/v1/show/{id}")
    public ShowInfoResponseDto findShowById(@PathVariable Long id) {
        return showAndSeatService.findShowInfoById(id);
    }

    @GetMapping("/api/v1/seat/{id}")
    public List<SeatResponseDto> findSeatById(@PathVariable Long id) {
        return showAndSeatService.findSeatByShowInfoId(id);
    }

    @GetMapping("/api/v1/show")
    public List<ShowInfoResponseDto> findAllShow() {
        return showAndSeatService.findAllShowInfo();
    }

    @GetMapping("/api/v1/seat")
    public List<SeatResponseDto> findAllSeat() {
        return showAndSeatService.findAllSeat();
    }
}
