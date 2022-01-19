package ticket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ticket.dto.*;
import ticket.entity.*;
import ticket.service.LogService;
import ticket.service.ShowAndSeatService;
import ticket.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class TicketingController {
    private final ShowAndSeatService showAndSeatService;
    private final UserServiceImpl userService;
    private final LogService logService;
    private final Lottery lottery;

    @PostMapping("/api/v1/ticketing")
    public ResponseEntity<String> makeReservation(@RequestBody TicketingSaveRequestDto requestDto) throws Exception{
        //TODO userId는 토큰에서 가져오기
        UserDTO user = userService.getUsersByUserId(requestDto.getUserId());
        SeatResponseDto seat = showAndSeatService.findSeatById(requestDto.getSeatId());

        //TODO 나중에 할인 기능 생기면 여기서 처리
        if (seat.getPrice() <= user.getCurrent_point()) {
            // TODO 포인트 차감
//            ModelMapper mapper = new ModelMapper();
//            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//            UserDTO userRequestDto = mapper.map(user, UserDTO.class);
//
//            userRequestDto.setCurrent_point(user.getCurrent_point() - seat.getPrice());
//
//            userService.saveUser(userRequestDto);

            TicketingLogSaveRequestDto logSaveRequestDto = TicketingLogSaveRequestDto.builder()
                    .userId(requestDto.getUserId()).seatId(requestDto.getSeatId()).build();

            logService.save(logSaveRequestDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잔여 포인트가 부족합니다");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("예매 성공");
    }


//    테스트용 크론탭
    @Scheduled(cron = "0/15 * * * * *")
//    실제 사용할 크론탭
//    @Scheduled(cron = "0 0 * * * *")
    public String draw() {
        // 공연 시작까지 얼마 안남은 공연 id를 받아온다
        // 몇 시간 전부터 추첨을 돌릴지는 ShowDate repo에 쿼리로 설정
        List<ShowDate> lotteryTargetList = showAndSeatService.findLotteryTarget();
        List<TicketingLog> logList;

        for (ShowDate showDate : lotteryTargetList) {
            // TODO 디버그용 출력 정리
            System.out.println("공연 시각 : " + showDate.getShowDate().toString());

            // 받아온 공연 정보가 갖고있는 좌석 정보마다 해당 좌석을 예매한 내역 중 예매에 성공하지 않은 내역을 logList 에 추가한다
            List<Seat> seatList = showAndSeatService.findLotterySeatByTimeAndShowInfoId(showDate.getShowDate(), showDate.getShowInfo().getId());
            for (Seat seat : seatList) {
                System.out.println("추첨 좌석 : " + seat.getShowInfo().getName() + " 등급 : " + seat.getGrade());
                logList = new ArrayList<>(logService.findLotteryTargetBySeatId(seat.getId()));

                // 저장된 내역과 seat의 좌석수를 가지고 추첨을 한다.
                if(logList.isEmpty()){
                    continue;
                }else{
                    lottery.doLottery(seat, logList);
                }
            }
        }
        return "good";
    }
}

// TODO 디버그용 출력 정리
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
            System.out.println("당첨 인덱스 : " + winIdx);
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
//        System.out.println(i + " 만큼 추첨이 진행되었습니다.");
        showAndSeatService.updateSeat(new SeatUpdateRequestDto(seat.getId(), i));
    }
}