package ticket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ticket.constant.Classification;
import ticket.dto.ShowFormDto;
import ticket.dto.ShowGetDto;
import ticket.service.ShowService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @GetMapping(value = "/admin/show/new")
    public String showForm(Model model){
        model.addAttribute("showFormDto", new ShowFormDto());
        return "/show/showForm";
    }

    //너무 많은 param, map 을 써보는 것도 고려
    @PostMapping(value = "/admin/show/new")
    public String showNew(@Valid ShowFormDto showFormDto, BindingResult bindingResult, Model model,
                          @RequestParam("showImgFile") List<MultipartFile> showImgFileList, @RequestParam("showDate")List<String> showDateTimeList,
                          @RequestParam("seatGrade")List<String> seatGradeList,@RequestParam("seatCount")List<Integer> seatCountList,@RequestParam("price")List<Integer>priceList){

        if(bindingResult.hasErrors()){
            return "show/showForm";
        }

        if(showImgFileList.get(0).isEmpty()&&showFormDto.getId()==null){
            model.addAttribute("errorMessage", "첫번째 공연 이미지는 필수 입니다.");
            return "show/showForm";
        }else if(showDateTimeList.size() == 0){
            model.addAttribute("errorMessage", "공연 날짜는 필수 입니다.");
            return "show/showForm";
        }else if((seatCountList.size()==0 || seatGradeList.size()==0)||seatCountList.contains(null)){
            model.addAttribute("errorMessage", "공연 좌석 양식을 준수해주세요.");
            return "show/showForm";
        }

        try{
            showService.saveShow(showFormDto,showDateTimeList ,showImgFileList,seatGradeList,seatCountList,priceList);
        }catch (Exception e){
            log.error("공연 등록 중 에러 발생 : {}", e.getMessage());
            model.addAttribute("errorMessage", "공연 등록 중 에러 발생");
            return "show/showForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/admin/show/{showId}")
    public String showDtl(@PathVariable("showId") Long showId, Model model){

        try{
            ShowFormDto showFormDto = showService.getShowDtl(showId);
            model.addAttribute("showFormDto", showFormDto);
        } catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 공연입니다.");
            model.addAttribute("showFormDto", new ShowFormDto());
        }

        return "show/showForm";
    }

    @PostMapping(value = "/admin/show/{showId}")
    public String updateShow(@Valid ShowFormDto showFormDto, BindingResult bindingResult, Model model,
                             @RequestParam("showImgFile") List<MultipartFile> showImgFileList, @RequestParam("showDate")List<String> showDateTimeList,
                             @RequestParam("seatGrade")List<String> seatGradeList, @RequestParam("seatCount")List<Integer> seatCountList,
                             @RequestParam("price")List<Integer>priceList){

        if(bindingResult.hasErrors())
            return "show/showForm";

//        if(showImgFileList.get(0).isEmpty()&&showFormDto.getId()==null){
//            model.addAttribute("errorMessage", "첫번째 공연 이미지는 필수 입력값입니다.");
//            return "show/showForm";
//        }

        try{
            showService.updateShow(showFormDto, showImgFileList, showDateTimeList, seatGradeList, seatCountList, priceList);
        } catch (Exception e){
            System.out.println(e.getMessage());
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "show/showForm";
        }

        return "redirect:/";
    }

    @DeleteMapping(value = "/admin/show/{showId}")
    public @ResponseBody ResponseEntity deleteShow(@PathVariable("showId") Long showId){

        try {
            showService.deleteShow(showId);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(showId,HttpStatus.OK);
    }

    @GetMapping(value = {"/show/{showClassification}","/show/{showClassification}/{page}"})
    public String showGet(@PathVariable String showClassification,@PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,9);
        Classification classification = Classification.valueOf(showClassification.toUpperCase());
        Page<ShowGetDto> shows =showService.findShowByClassification(classification, pageable);

        model.addAttribute("total", shows.getTotalElements());
        model.addAttribute("classification", classification);
        model.addAttribute("shows",shows);
        model.addAttribute("maxPage", 5);

        return "show/showGet";
    }

    @GetMapping(value = {"/admin/show/management","/admin/show/management/{page}"})
    public String manageShow(@PathVariable("page") Optional<Integer> page,Model model){
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,9);
        Page<ShowGetDto> shows = showService.findAllShow(pageable);

        model.addAttribute("total", shows.getTotalElements());
        model.addAttribute("shows", shows);
        model.addAttribute("maxPage", 15);

        return "show/showManage";
    }

    @GetMapping(value="/show/detail/{showId}")
    public String show(@PathVariable("showId") Long showId,Model model){
        ShowFormDto showFormDto = showService.getShowDtl(showId);
        model.addAttribute("show", showFormDto);
        return "show/showDtl";
    }
}
