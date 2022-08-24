package ticket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import ticket.entity.ShowImg;
import ticket.repository.ShowImgRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ShowImgService {

    @Value("${showImgLocation}")
    private String showImgLocation;

    private final ShowImgRepository showImgRepository;

    private final FileService fileService;

    public void saveShowImg(ShowImg showImg, MultipartFile showImgFile) throws Exception{
        String oriImgName = showImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(showImgLocation, oriImgName, showImgFile.getBytes());
            imgUrl = "/images/show/" + imgName;
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
                fileService.deleteFile(showImgLocation+"/"+savedShowImg.getImgName());
            }

            String oriImgName = showImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(showImgLocation, oriImgName, showImgFile.getBytes());
            String imgUrl = "/images/show/" + imgName;

            savedShowImg.updateShowImg(oriImgName,imgName,imgUrl);
        }
    }

    public void deleteShowImgFile(Long showId) throws Exception{
        List<ShowImg> savedShowImgList = showImgRepository.findByShowId(showId);
        for(ShowImg savedShowImg : savedShowImgList){
            if(!StringUtils.isEmpty(savedShowImg.getImgName())){
                fileService.deleteFile(showImgLocation+"/"+savedShowImg.getImgName());
            }
        }

    }
}
