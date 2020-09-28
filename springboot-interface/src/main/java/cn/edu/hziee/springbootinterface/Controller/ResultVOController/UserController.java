package cn.edu.hziee.springbootinterface.Controller.ResultVOController;

import cn.edu.hziee.springbootinterface.Entity.User;
import cn.edu.hziee.springbootinterface.Service.PdfExportService;
import cn.edu.hziee.springbootinterface.Util.PdfView;
import com.lowagie.text.*;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.validation.Valid;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

@PostMapping("/addUser")
    public String addUser(@RequestBody @Valid User user, BindingResult result){
    for (ObjectError error:result.getAllErrors()) {
        return error.getDefaultMessage();
    }
    return "success";
}
@GetMapping("/export/pdf")
    public ModelAndView exportPdf(String userName,String password){
    List<User> userList=new ArrayList<>();
    User user=new User();
    user.setId(1L);
    user.setAccount(userName);
    user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
    user.setEmail("暂无");
    userList.add(user);
    for (int i = 0; i <5 ; i++) {
        User newUser= new User();
        BeanUtils.copyProperties(user,newUser);
        userList.add(newUser);
    }
    ModelAndView mv=new ModelAndView();
    View view=new PdfView(exportService());
    mv.setView(view);
    mv.addObject("userList",userList);
    return mv;
}
    //导出Pdf自定义
    @SuppressWarnings("unchecked")
    private PdfExportService exportService() {
    return ((model, document, writer, request, response) -> {
        try {
            //A4张纸
            document.setPageSize(PageSize.A4);
            //标题
            document.addTitle("用户信息");
            //换行
            document.add(new Chunk("\n"));
            //表格,3列
            PdfPTable table=new PdfPTable(3);
            PdfPCell cell=null;
            Font f8=new Font();
            f8.setColor(Color.blue);
            f8.setStyle(Font.BOLD);
            cell=new PdfPCell(new Paragraph("id",f8));
            cell.setHorizontalAlignment(1);
            table.addCell(cell);
            cell= new PdfPCell (new Paragraph ("note", f8));
            cell.setHorizontalAlignment(1);
            table.addCell(cell);
            List<User> userList= (List<User>) model.get("userList");
            for (User user:userList) {
                document.add(new Chunk("\n"));
                cell= new PdfPCell(new Paragraph(user.getId() + ""));
                table.addCell(cell);
                cell= new PdfPCell (new Paragraph(user.getAccount()) );
                table.addCell (cell);
                String note= user.getPassword() ==null? "" : user.getPassword();
                cell= new PdfPCell(new Paragraph(note));
                table.addCell(cell) ;
            }
            document.add(table);
        }catch (DocumentException e){
            e.printStackTrace();
        }
    });
    }

}
