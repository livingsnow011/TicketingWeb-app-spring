package ticket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import ticket.entity.ShowImg;
import ticket.repository.ShowImgRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ShowImgService {

    private final S3Uploader s3Uploader;

    @Value("${showImgLocation}")
    private String showImgLocation;

    private final ShowImgRepository showImgRepository;

    private final FileService fileService;

    public void saveShowImg(ShowImg showImg, MultipartFile showImgFile) throws Exception{
        String oriImgName = showImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        String[] imgNameAndUrl;
        log.info("saveShowImg 메서드 시작");
        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            try {
                log.info("try 구문 시작");
                imgNameAndUrl = s3Uploader.uploadFiles(showImgFile, "show");
                imgName = imgNameAndUrl[0];
                imgUrl = imgNameAndUrl[1];
            } catch (Exception e) {
                log.info("에러 캐치");
                System.out.println(e.getMessage());
            }
        }

        //공연 이미지 정보 저장
        showImg.updateShowImg(oriImgName, imgName, imgUrl);
        showImgRepository.save(showImg);
    }

    public void updateShowImg(Long showImgId, MultipartFile showImgFile)throws Exception{
        if(!showImgFile.isEmpty()){
            ShowImg savedShowImg = showImgRepository.findById(showImgId)
                    .orElseThrow(EntityNotFoundException::new);

            if(!StringUtils.isEmpty(savedShowImg.getImgName())){
                s3Uploader.deleteS3(savedShowImg.getImgName());
            }

            String oriImgName = showImgFile.getOriginalFilename();
            String imgName = "";
            String imgUrl = "";
            String[] imgNameAndUrl;

            try {
                imgNameAndUrl = s3Uploader.uploadFiles(showImgFile, "show");
                imgName = imgNameAndUrl[0];
                imgUrl = imgNameAndUrl[1];
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            savedShowImg.updateShowImg(oriImgName,imgName,imgUrl);
        }
    }

    public void deleteShowImgFile(Long showId) throws Exception{
        List<ShowImg> savedShowImgList = showImgRepository.findByShowId(showId);
        for(ShowImg savedShowImg : savedShowImgList){
            if(!StringUtils.isEmpty(savedShowImg.getImgName())){
                s3Uploader.deleteS3(savedShowImg.getImgName());
            }
        }

    }
}
