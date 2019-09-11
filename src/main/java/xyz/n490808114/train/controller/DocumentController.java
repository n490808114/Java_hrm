package xyz.n490808114.train.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.medsea.mimeutil.MimeUtil;

import xyz.n490808114.train.domain.Document;
import xyz.n490808114.train.domain.User;
import xyz.n490808114.train.dto.DetailDto;
import xyz.n490808114.train.dto.ListDto;
import xyz.n490808114.train.dto.SimpleDto;
import xyz.n490808114.train.dto.TitleDto;
import xyz.n490808114.train.service.HrmService;
import xyz.n490808114.train.service.RequestParamCheck;
import xyz.n490808114.train.util.TableTitle;
import xyz.n490808114.train.util.TrainConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;


@RestController
@RequestMapping("/document")
@PreAuthorize("hasRole('ADMIN')")
public class DocumentController {
    @Autowired
    private HrmService hrmService;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final static Log log = LogFactory.getLog(DocumentController.class);

    @GetMapping
    public ListDto<Document> getList(@RequestParam Map<String,String> param){
        RequestParamCheck.check(param);
        return hrmService.getDocumentList(param);
    }
    @GetMapping("/create")
    public TitleDto create(){
        return new TitleDto().setTitle("document").setDataTitle(TableTitle.DOCUMENT_CREATE_TITLE);
    }
    @GetMapping("/search")
    public TitleDto search(){
        return new TitleDto().setTitle("document").setDataTitle(TableTitle.DOCUMENT_SEARCH_TITLE);
    }
    @PostMapping
    public SimpleDto create(@ModelAttribute Document document,HttpServletRequest request){
        FileOutputStream fileOutputStream = null;
        try{
            Date date = new Date();
            String fileName = "documents/"+date.getTime()+ "#"+document.getFile().getOriginalFilename();
            fileOutputStream = new FileOutputStream(new File(fileName));
            fileOutputStream.write(document.getFile().getBytes());;
            fileOutputStream.close();
            document.setFileName(fileName);

            Set<ConstraintViolation<Document>> set = validator.validate(document);
            if(set.size() != 0){
                Map<String,String> error = new LinkedHashMap<>();
                for(ConstraintViolation<Document> constraintViolation : set){
                    error.put(constraintViolation.getPropertyPath().toString()
                    , constraintViolation.getMessage());
                }
                return new SimpleDto(403,error);
            }
            document.setUser((User)request.getAttribute(TrainConstants.USER_REQUEST));
            document.setCreateDate(date);
            hrmService.addDocument(document);
        }catch(FileNotFoundException ex){
            return new SimpleDto(403,"Exception");
        }catch(IOException ex){
            return new SimpleDto(403,"Exception");
        }
        return new SimpleDto(200,"done");
    }
    @GetMapping("/{id}")
    public DetailDto<Document> getDetail(@PathVariable int id){
        DetailDto<Document> dto;
        Document document = hrmService.findDocumentById(id);
        if(document == null){
            dto = new DetailDto<Document>().builder().code(403).title("document").message("找不到这个文档").build();
        }else{
            dto = new DetailDto<Document>().builder().code(200)
                                                    .title("document")
                                                    .message("获取成功")
                                                    .dataTitle(TableTitle.DOCUMENT_TITLE)
                                                    .data(document)
                                                    .build();
        }
        return dto;
    }
    @GetMapping(value = "/{id}/file")
    public ResponseEntity<Boolean> getFile(@PathVariable int id,HttpServletResponse response){
        Document document = hrmService.findDocumentById(id);
        if(document == null){
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        try{
            File file = new File(document.getFileName());
            MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
            Collection<?> mimeTypes = MimeUtil.getMimeTypes(file);

            response.setContentType(mimeTypes.toString());
            log.info(mimeTypes.toString());
            log.info(new MimetypesFileTypeMap().getContentType(file));
            fileInputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = fileInputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            outputStream.close();
            fileInputStream.close();

        }catch(IOException ex){
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(true, HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/{id}")
    public SimpleDto update(@PathVariable int id,@RequestParam Map<String,String> param,HttpServletRequest request){
        Document document = new Document();
        document.setTitle(param.get("title"));
        document.setRemark(param.get("remark"));
        Set<ConstraintViolation<Document>> set = validator.validate(document);
        if(set.size() != 0){
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Document> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            return new SimpleDto(404,error);
        }else{
            document.setUser((User)request.getAttribute(TrainConstants.USER_REQUEST));
            document.setCreateDate(new Date());
            document.setId(id);
            hrmService.modifyDocument(document);
            return new SimpleDto(200,"修改成功");
        }
    }
    @DeleteMapping("/{id}")
    public SimpleDto delete(@PathVariable int id){
        Document document = hrmService.findDocumentById(id);
        if(document ==null){
            return new SimpleDto(404,"找不到这个文档");
        }
        File file = new File(document.getFileName());
        if(file.exists() && file.isFile()){
            file.delete();
        }
        hrmService.removeDocument(id);
        return new SimpleDto(200,"删除成功");
    }
}
