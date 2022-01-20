package ticket.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ticket.dto.*;
import ticket.entity.*;
import ticket.service.LogService;
import ticket.service.ShowAndSeatService;
import ticket.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TicketingController {
    private final ShowAndSeatService showAndSeatService;
    private final UserServiceImpl userService;
    private final LogService logService;
    private final Lottery lottery;

    @PostMapping("/api/v1/ticketing")
    public ResponseEntity<String> makeReservation(@RequestBody TicketingSaveRequestDto requestDto) throws Exception{
        UserDTO user = userService.getUsersByUserId(requestDto.getUserId());
        SeatResponseDto seat = showAndSeatService.findSeatById(requestDto.getSeatId());

        //끝난 공연은 예매 불가
        if(seat.getShowDate().isBefore(LocalDateTime.now())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 종료된 공연입니다");
        }

        //TODO 나중에 할인 기능 생기면 여기서 처리
        if (seat.getPrice() <= user.getCurrent_point()) {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            UserDTO userRequestDto = mapper.map(user, UserDTO.class);

            userRequestDto.setCurrent_point(user.getCurrent_point() - seat.getPrice());

            userService.changeUser(user.getUserId(), userRequestDto);

            TicketingLogSaveRequestDto logSaveRequestDto = TicketingLogSaveRequestDto.builder()
                    .userId(requestDto.getUserId()).seatId(requestDto.getSeatId()).build();

            logService.save(logSaveRequestDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잔여 포인트가 부족합니다");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("예매 성공");
    }

    @DeleteMapping("/api/v1/ticketing/{id}")
    public void refund(@PathVariable Long id) throws Exception{
        TicketingLog log = logService.findById(id);
        Seat seat = log.getSeat();
        UserDTO user = userService.getUsersByUserId(log.getUserId());

        // 삭제할 예매 내역의 유저에게 좌석 가격만큼 환불
        user.setCurrent_point(user.getCurrent_point() + seat.getPrice());
        userService.changeUser(log.getUserId(), user);

        // 삭제할 예매 내역이 당첨이었다면 좌석정보의 현재 카운트 -1
        if (log.isSuccess()) {
            seat.addCurrentCount(-1);
        }
        logService.delete(id);
    }


//    테스트용 크론탭
//    @Scheduled(cron = "0/15 * * * * *")
//    실제 사용할 크론탭
    @Scheduled(cron = "0 0 * * * *")
    public String draw() {
        // 공연 시작까지 얼마 안남은 공연 id를 받아온다
        // 몇 시간 전부터 추첨을 돌릴지는 Seat repo에 쿼리로 설정
        List<Seat> lotteryTargetList = showAndSeatService.findLotteryTarget();
        List<TicketingLog> logList;

        // 받아온 공연 정보가 갖고있는 좌석 정보마다 해당 좌석을 예매한 내역 중 예매에 성공하지 않은 내역을 logList 에 추가한다
        for (Seat seat : lotteryTargetList) {
            logList = new ArrayList<>(logService.findLotteryTargetBySeatId(seat.getId()));

            // 저장된 내역과 seat의 좌석수를 가지고 추첨을 한다.
            if(logList.isEmpty()){
                continue;
            }else{
                lottery.doLottery(seat, logList);
            }
        }
        return "good";
    }
}

// @Scheduled 와 @Transactional을 동시에 사용할 수가 없어서 클래스를 분리
@RequiredArgsConstructor
@Controller
class Lottery{
    private final LogService logService;
    private final ShowAndSeatService showAndSeatService;

    @Transactional
    public void doLottery(Seat seat, List<TicketingLog> logList) {
        // 추첨할 횟수
        int lotteryCount = seat.getTotalSeat() - seat.getCurrentCount();

        int scope = logList.size();
        boolean[] isAlreadyChosen = new boolean[scope];
        int i = 0;
        // i가 추첨 목표치보다 커지거나 추첨 대상 인원수보다 커지면 종료
        while (i < lotteryCount && i < scope) {
            int winIdx = (int)(Math.random() * scope);

            if (isAlreadyChosen[winIdx]) {
                System.out.println("이미 당첨된 티켓입니다 " + logList.get(winIdx).getId());
                continue;
            } else {
                TicketingLog log = logList.get(winIdx);
                logService.update(new TicketingLogUpdateRequestDto(log.getId(), true));
                isAlreadyChosen[winIdx] = true;
                i++;
            }
        }
        showAndSeatService.updateSeat(new SeatUpdateRequestDto(seat.getId(), i));
    }
}