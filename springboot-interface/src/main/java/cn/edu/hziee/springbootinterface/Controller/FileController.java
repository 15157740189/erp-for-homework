package cn.edu.hziee.springbootinterface.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController {
    @GetMapping("/upload/page")
    public String uploadPage(){
        return "upload";
    }

    @PostMapping("/upload/request")
    @ResponseBody
    public Map<String,Object> uploadRequest(HttpServletRequest request){
        boolean flag=false;
        MultipartHttpServletRequest mreq=null;
        if (request instanceof  MultipartHttpServletRequest){
            mreq= (MultipartHttpServletRequest) request;
        }else {
            return dealResultMap(false,"上传失败");
        }
        MultipartFile multipartfile=mreq.getFile("file");
        String fileName=multipartfile.getOriginalFilename();
        File file=new File(fileName);
        try {
            multipartfile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
            return dealResultMap(false,"上传失败");
        }
        return dealResultMap(true,"上传成功");
    }
    @PostMapping ("/upload/multipart" )
    @ResponseBody
    //直接使用 MultipartFile对象获取上传的文件，进行操作。
    public Map<String, Object> uploadMultipartFile (MultipartFile file) {
        String fileName = file. getOriginalFilename() ;
        File dest =new File(fileName) ;
        try { file.transferTo(dest);
        }catch ( Exception e) {
            e.printStackTrace();
            return dealResultMap(false, "上传失败");
        }
        return dealResultMap(true,"上传成功");
        }

    @PostMapping ("/upload/part" )
    @ResponseBody
    //使用 Servlet 的 API，可以使用其 write 方法直接写入文件
    public Map<String, Object> uploadPart (Part file) {
        String fileName = file.getSubmittedFileName();
        try {
            file.write(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return dealResultMap(false, "上传失败");
        }
        return dealResultMap(true, "上传成功");
    }
            private Map<String,Object> dealResultMap(boolean success, String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put( "msg", msg);
        return result;
    }

}
