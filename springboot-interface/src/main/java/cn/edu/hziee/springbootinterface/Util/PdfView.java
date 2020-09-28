package cn.edu.hziee.springbootinterface.Util;

import cn.edu.hziee.springbootinterface.Service.PdfExportService;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PdfView extends AbstractPdfView {
  //导出服务接口
    private PdfExportService pdfExportService=null;
    public PdfView(PdfExportService pdfExportService){
     this.pdfExportService=pdfExportService;
    }
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
                                    PdfWriter pdfWriter, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        pdfExportService.make(model,document,pdfWriter,request,response);
    }
}
