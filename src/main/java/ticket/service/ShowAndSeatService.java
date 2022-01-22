package ticket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticket.dto.*;
import ticket.entity.Seat;
import ticket.entity.ShowDate;
import ticket.entity.ShowInfo;
import ticket.repository.SeatRepository;
import ticket.repository.ShowDateRepository;
import ticket.repository.ShowInfoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShowAndSeatService {
    private final ShowInfoRepository showInfoRepository;
    private final SeatRepository seatRepository;
    private final ShowDateRepository showDateRepository;

    @Transactional
    public String saveShowAndSeat(ShowInfoSaveRequestDto dto){
        ShowInfo newShow = ShowInfo.builder()
                .name(dto.getName())
                .classification(dto.getClassification())
                .description(dto.getDescription())
                .posterURI(dto.getPosterURI())
                .build();

        showInfoRepository.save(newShow);

        for (LocalDateTime time : dto.getStartDate()) {
            ShowDate newDate = ShowDate.builder()
                    .showInfoId(newShow.getId())
                    .showDate(time).build();

            showDateRepository.save(newDate);
        }

        for (SeatSaveRequestDto seatDto : dto.getSeatSaveRequestDtoList()) {
            // 좌석 정보를 생성할 때 newShow의 공연 시각별 좌석 정보를 생성함
            for (ShowDate showDate : showDateRepository.findByShowInfoId(newShow.getId())) {
                Seat newSeat = Seat.builder()
                        .showInfo(newShow)
                        .showDate(showDate.getShowDate())
                        .grade(seatDto.getGrade())
                        .price(seatDto.getPrice())
                        .totalSeat(seatDto.getTotalSeat())
                        .build();
                seatRepository.save(newSeat);
            }
        }

        return "Post show info and seat successfully";
    }

    @Transactional
    public String updateSeat(SeatUpdateRequestDto requestDto) {
        Seat seat = seatRepository.getById(requestDto.getId());

        seat.addCurrentCount(requestDto.getAddCount());

        return "update complete";
    }

    @Transactional
    public void deleteShowAndSeat(Long id) {
        showDateRepository.deleteAll(showDateRepository.findByShowInfoId(id));
        showInfoRepository.delete(showInfoRepository.getById(id));
    }

    @Transactional
    public ShowInfoResponseDto findShowInfoById(Long id) {
        ShowInfo showInfo = showInfoRepository.getById(id);
        ShowInfoResponseDto responseDto = transferToResponse(showInfo);
        List<LocalDateTime> date = showDateRepository.findByShowInfoId(id).stream().map(showDate -> showDate.getShowDate()).collect(Collectors.toList());

        responseDto.setShowDate(date);
        return responseDto;
    }

    @Transactional
    public List<SeatResponseDto> findSeatByShowInfoId(Long id) {
        List<Seat> seatList = seatRepository.findByShowInfoId(id);

        List<SeatResponseDto> responseDtoList = new ArrayList<>();

        seatList.forEach(seat -> responseDtoList.add(transferToResponse(seat)));

        return responseDtoList;
    }

    @Transactional
    public List<ShowInfoResponseDto> findAllShowInfo(){
        List<ShowInfo> showInfoList = showInfoRepository.findAll();

        List<ShowInfoResponseDto> dtoList = new ArrayList<>();

        showInfoList.forEach(showInfo -> dtoList.add(transferToResponse(showInfo)));

        return dtoList;
    }

    @Transactional
    public List<SeatResponseDto> findAllSeat(){
        List<Seat> seatList = seatRepository.findAll();

        List<SeatResponseDto> dtoList = new ArrayList<>();

        seatList.forEach((seat -> dtoList.add(transferToResponse(seat))));

        return dtoList;
    }

    @Transactional
    public SeatResponseDto findSeatById(Long id) {
        Seat seat = seatRepository.findById(id).orElseThrow(() -> new NullPointerException("Cannot find seat"));

        SeatResponseDto responseDto = transferToResponse(seat);

        return responseDto;
    }

    @Transactional
    public List<Seat> findLotteryTarget() {
        return seatRepository.findLotteryTarget();
    }

    @Transactional
    public List<Seat> findRefundTarget() {
        return seatRepository.findRefundTarget();
    }

    public ShowInfoResponseDto transferToResponse(ShowInfo showInfo) {
        return new ShowInfoResponseDto(showInfo);
    }

    public SeatResponseDto transferToResponse(Seat seat) {
        return new SeatResponseDto(seat);
    }
}
