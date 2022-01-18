package ticket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticket.dto.SeatResponseDto;
import ticket.dto.SeatSaveRequestDto;
import ticket.dto.ShowInfoResponseDto;
import ticket.dto.ShowInfoSaveRequestDto;
import ticket.entity.Seat;
import ticket.entity.ShowInfo;
import ticket.repository.SeatRepository;
import ticket.repository.ShowInfoRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShowAndSeatService {
    private final ShowInfoRepository showInfoRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public String saveShowAndSeat(ShowInfoSaveRequestDto dto){
        ShowInfo newShow = ShowInfo.builder()
                .name(dto.getName())
                .classification(dto.getClassification())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .build();

        showInfoRepository.save(newShow);

        for (SeatSaveRequestDto seatDto : dto.getSeatSaveRequestDtoList()) {
            Seat newSeat = Seat.builder()
                    .showInfo(newShow)
                    .grade(seatDto.getGrade())
                    .price(seatDto.getPrice())
                    .totalSeat(seatDto.getTotalSeat())
                    .build();

            seatRepository.save(newSeat);
        }

        return "Post show info and seat successfully";
    }

    @Transactional
    public void deleteShowAndSeat(Long id) {
        showInfoRepository.delete(showInfoRepository.getById(id));
    }

    @Transactional
    public ShowInfoResponseDto findShowInfoById(Long id) {
        ShowInfo showInfo = showInfoRepository.getById(id);
        ShowInfoResponseDto responseDto = transferToResponse(showInfo);

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

    public ShowInfoResponseDto transferToResponse(ShowInfo showInfo) {
        ShowInfoResponseDto responseDto = new ShowInfoResponseDto(showInfo);

        List<SeatResponseDto> seatList = new ArrayList<>();

        showInfo.getSeatList().forEach((seat -> seatList.add(new SeatResponseDto(seat))));

        responseDto.setSeatList(seatList);

        return responseDto;
    }

    public SeatResponseDto transferToResponse(Seat seat) {
        SeatResponseDto responseDto = new SeatResponseDto(seat);

        ShowInfoResponseDto showInfoResponseDto = transferToResponse(seat.getShowInfo());

        responseDto.setShowInfo(showInfoResponseDto);

        return responseDto;
    }
}
