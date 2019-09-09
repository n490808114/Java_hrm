package xyz.n490808114.train.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.n490808114.train.domain.Document;
import xyz.n490808114.train.dto.ListDto;
import xyz.n490808114.train.dto.TitleDto;
import xyz.n490808114.train.service.HrmService;
import xyz.n490808114.train.service.RequestParamCheck;
import xyz.n490808114.train.util.TableTitle;

import java.util.Map;

@RestController
@RequestMapping("/document")
@PreAuthorize("hasRole('ADMIN')")
public class DocumentController {
    @Autowired
    private HrmService hrmService;
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
}
