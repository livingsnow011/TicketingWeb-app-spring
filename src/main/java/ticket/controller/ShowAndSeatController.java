package ticket.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ticket.dto.SeatResponseDto;
import ticket.dto.ShowInfoResponseDto;
import ticket.dto.ShowInfoSaveRequestDto;
import ticket.service.ShowAndSeatService;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ShowAndSeatController {
    private final ShowAndSeatService showAndSeatService;

    // 공연 정보를 등록
    // 공연 포스터와 공연 정보를 동시에 받아오면 ShowInfoSaveRequestDto에 @RequestBody로 자동 매핑이 불가능해짐
    // @RequestPart로 MultipartFile과 json string을 받도록 변경
    @PostMapping("/api/v1/show")
    public ResponseEntity<String> save(@RequestPart("files") List<MultipartFile> files, @RequestParam("requestDto") String request) throws Exception {
        System.out.println("start");
        // ObjectMapper로 json string을 deserialize
        ObjectMapper mapper = new ObjectMapper();
        // JSR310 모듈이 deprecated라서 교체 필요
        mapper.registerModule(new JSR310Module());
        ShowInfoSaveRequestDto requestDto = mapper.readValue(request, new TypeReference<ShowInfoSaveRequestDto>() {
        });

        requestDto.setPosterURI(uploadFileHandler(files));

        return ResponseEntity.status(HttpStatus.CREATED).body(showAndSeatService.saveShowAndSeat(requestDto));
    }

    @DeleteMapping("/api/v1/show/{id}")
    public void delete(@PathVariable Long id) {
        showAndSeatService.deleteShowAndSeat(id);
    }

    @GetMapping("/api/v1/show/{id}")
    public ResponseEntity<ShowInfoResponseDto> findShowById(@PathVariable Long id) throws Exception{
        ShowInfoResponseDto responseDto = showAndSeatService.findShowInfoById(id);

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/api/v1/seat/{id}")
    public List<SeatResponseDto> findSeatById(@PathVariable Long id) {
        return showAndSeatService.findSeatByShowInfoId(id);
    }

    @GetMapping("/api/v1/show")
    public List<ShowInfoResponseDto> findAllShow() {
        List<ShowInfoResponseDto> responseDtoList = showAndSeatService.findAllShowInfo();

        return responseDtoList;
    }

    @GetMapping("/api/v1/seat")
    public List<SeatResponseDto> findAllSeat() {
        return showAndSeatService.findAllSeat();
    }

    public String uploadFileHandler(List<MultipartFile> multipartFileList) throws Exception{
        String path = "src/main/resources/static";
        StringBuilder fileURI = new StringBuilder();

        if (ObjectUtils.isEmpty(multipartFileList)) {
            return null;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MMdd");
        LocalDateTime now = LocalDateTime.now();
        String folder = "/img/poster/" + now.format(dtf);
        String storedDir = path + folder;
        File fileDir = new File(storedDir);
        File absDir = new File(fileDir.getAbsolutePath());

        if (!absDir.exists()) {
            absDir.mkdirs();
        }

        String originalFileExtension;

        for (MultipartFile file : multipartFileList) {
            if (!file.isEmpty()) {
                String contentType = file.getContentType();
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if (contentType.contains("image/jpeg")) {
                        originalFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    } else if (contentType.contains("image/gif")) {
                        originalFileExtension = ".gif";
                    } else if (contentType.contains("image/webp")) {
                        originalFileExtension = ".webp";
                    } else if (contentType.contains("text/plain")){
                        originalFileExtension = ".txt";
                    } else {
                        break;
                    }
                }

                String storedFileName = System.nanoTime() + originalFileExtension;

                fileURI.append(folder + "/" + storedFileName);

                fileDir = new File(storedDir + "/" + storedFileName);
                absDir = new File(fileDir.getAbsolutePath());
                file.transferTo(absDir);
            }
        }

        return fileURI.toString();
    }

        // 파일을 byte[]로 반환하는 메소드
//    public List<byte[]> getFileHandler(String fileURI) throws Exception{
//        List<byte[]> fileList = new ArrayList<>();
//        String[] pathList = fileURI.split("&");
//
//        for (String path : pathList) {
//            File filePath = new File(new File("src/main/resources/" + path).getAbsolutePath());
//            byte[] file = FileUtils.readFileToByteArray(filePath);
//
//            fileList.add(file);
//        }
//
//        return fileList;
//    }
}
