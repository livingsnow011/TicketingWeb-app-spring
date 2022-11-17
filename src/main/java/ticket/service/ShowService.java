package ticket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ticket.constant.Classification;
import ticket.dto.*;
import ticket.entity.*;
import ticket.exception.DeleteBookedShowException;
import ticket.repository.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ShowService {

    private final ShowRepository showRepository;

    private final ShowDateRepository showDateRepository;
    private final ShowSeatRepository showSeatRepository;

    private final ShowImgRepository showImgRepository;
    private final ShowImgService showImgService;

    private final BookRepository bookRepository;

    @Transactional
    public Long saveShow(ShowFormDto showFormDto,List<String> showDateTimeList ,List<MultipartFile> showImgFileList,
                         List<String> seatGradeList, List<Integer> seatCountList, List<Integer> priceList) throws Exception{

        //공연 등록
        Show show = showFormDto.createShow();
        showRepository.save(show);
        log.info("공연 등록, 공연 이름 : {} ", show.getShowName());

        //공연 날짜 등록
        for (int i = 0; i < showDateTimeList.size(); i++) {
            if(showDateTimeList.get(i).isEmpty()) continue;

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(showDateTimeList.get(i), dateTimeFormatter);
            ShowDate showDate = ShowDate.builder().showDate(localDateTime).show(show).build();
            showDateRepository.save(showDate);
        }
        log.info("공연 {} 공연 날짜 {} 개 등록", show.getShowName(), showDateTimeList.size());

        //공연 좌석 등록
        for (int i = 0; i < seatGradeList.size(); i++) {
            if(seatGradeList.get(i).isEmpty()) continue;

            ShowSeat showSeat = ShowSeat.builder().
                    seatGrade(seatGradeList.get(i)).
                    seatCount(seatCountList.get(i)).
                    price(priceList.get(i)).
                    show(show).
                    build();
            showSeatRepository.save(showSeat);
        }
        log.info("공연 {} 공연 좌석 등급 {} 개 등록", show.getShowName(), seatGradeList.size());

        //공연 이미지 등록
        for (int i = 0; i < showImgFileList.size(); i++) {
            if(showImgFileList.get(i).isEmpty()) continue;

            ShowImg showImg = new ShowImg();
            showImg.setShow(show);
            if(i==0)
                showImg.setRepImgYn("Y");
            else
                showImg.setRepImgYn("N");

            log.info("showImgService 공연 등록 시작");
            showImgService.saveShowImg(showImg,showImgFileList.get(i));
        }
        log.info("공연 {} 공연 이미지 {} 개 등록", show.getShowName(), showImgFileList.size());

        return show.getId();
    }

    @Transactional(readOnly = true)
    public ShowFormDto getShowDtl(Long showId){
        List<ShowImg> showImgList = showImgRepository.findByShowIdOrderByIdAsc(showId);
        List<ShowImgDto> showImgDtoList = new ArrayList<>();

        List<ShowDate> showDateList = showDateRepository.findByShowIdOrderByShowDateAsc(showId);
        List<ShowDateDto> showDateDtoList = new ArrayList<>();

        List<ShowSeat> showSeatList = showSeatRepository.findByShowIdOrderByIdAsc(showId);
        List<ShowSeatDto> showSeatDtoList = new ArrayList<>();

        for(ShowDate showDate: showDateList){
            ShowDateDto showDateDto = ShowDateDto.of(showDate);
            showDateDtoList.add(showDateDto);
        }

        for(ShowSeat showSeat: showSeatList){
            ShowSeatDto showSeatDto = ShowSeatDto.of(showSeat);
            showSeatDtoList.add(showSeatDto);
        }

        for (ShowImg showImg : showImgList){
            ShowImgDto showImgDto = ShowImgDto.of(showImg);
            showImgDtoList.add(showImgDto);
        }

        Show show = showRepository.findById(showId)
                .orElseThrow(EntityNotFoundException::new);

        ShowFormDto showFormDto = ShowFormDto.of(show);
        showFormDto.setShowFormDto(showDateDtoList,showSeatDtoList,showImgDtoList);

        return showFormDto;
    }

    @Transactional
    public Long updateShow(ShowFormDto showFormDto,List<MultipartFile> showImgFileList,List<String> showDateTimeList,
                           List<String> seatGradeList, List<Integer> seatCountList, List<Integer> priceList) throws Exception{

        Show show = showRepository.findById(showFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        show.updateShow(showFormDto);

        List<Long> showDateIds = showFormDto.getShowDateIds();
        for (int i = 0; i < showDateTimeList.size(); i++) {
            ShowDate savedShowDate = showDateRepository.findById(showDateIds.get(i))
                    .orElseThrow(EntityNotFoundException::new);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(showDateTimeList.get(i), dateTimeFormatter);

            savedShowDate.updateShowDate(localDateTime);
        }

        List<Long> showSeatIds = showFormDto.getShowSeatIds();
        for (int i = 0; i < seatGradeList.size(); i++) {
            ShowSeat savedShowSeat = showSeatRepository.findById(showSeatIds.get(i))
                    .orElseThrow(EntityNotFoundException::new);

            savedShowSeat.updateShowSeat(seatGradeList.get(i),seatCountList.get(i),priceList.get(i));
        }

        List<Long> showImgIds = showFormDto.getShowImgIds();

        for (int i = 0; i < showImgFileList.size(); i++) {
            showImgService.updateShowImg(showImgIds.get(i),showImgFileList.get(i));
        }

        log.info("공연 {} 수정",show.getShowName());

        return show.getId();
    }

    @Transactional
    public Long deleteShow(Long showId) throws Exception {

        if(bookRepository.existsByShowId(showId)){
            throw new DeleteBookedShowException("이미 예매한 사용자가 있습니다");
        }

        Show savedShow = showRepository.findById(showId).orElseThrow(EntityNotFoundException::new);
        showDateRepository.deleteAllByShowIdInQuery(savedShow);
        showSeatRepository.deleteAllByShowIdInQuery(savedShow);
        showImgService.deleteShowImgFile(showId);
        showImgRepository.deleteAllByShowIdInQuery(savedShow);
        showRepository.deleteByIdInQuery(showId);

        log.info("공연 {} 삭제",savedShow.getShowName());

        return showId;
    }

    @Transactional(readOnly = true)
    public Page<ShowGetDto> findShowByClassification(Classification classification,Pageable pageable){
        Page<ShowGetDto> showList = showImgRepository.findByClassification(classification, pageable);
        return showList;
    }

    @Transactional(readOnly = true)
    public Page<ShowGetDto> findAllShow(Pageable pageable) {
        Page<ShowGetDto> showList = showImgRepository.findAllShow(pageable);
        return showList;
    }
}
