package ticket.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
public class FileController {

    @GetMapping("/img/**")
    public ResponseEntity<byte[]> getFile(HttpServletRequest request) throws Exception{
        String uri = request.getRequestURI();
        byte[] file = getFileHandler(uri);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(file, headers, HttpStatus.OK);
        return entity;
    }

    // 파일을 byte[]로 반환하는 메소드
    public byte[] getFileHandler(String fileURI) throws Exception{
        File filePath = new File(new File("src/main/resources/static" + fileURI).getAbsolutePath());
        byte[] file = FileUtils.readFileToByteArray(filePath);

        return file;
    }
}
