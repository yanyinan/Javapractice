package com.satrt.springweb.fileoperation.controller;

import com.satrt.springweb.core.model.entity.FileEntity;
import com.satrt.springweb.core.model.entity.UserEntity;
import com.satrt.springweb.exception.sql.SqlServiceException;
import com.satrt.springweb.fileoperation.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/26 20:56
 */
@Controller
@RequestMapping("/fileoperate")
public class FileDirectoryController {
    private FileService fileService;
    FileDirectoryController(FileService fileService){
        this.fileService = fileService;
    }
    @RequestMapping(value = "/filedirectory", method = RequestMethod.GET)
    public String list(Model model) throws SqlServiceException {
        // 查询用户列表
        List<FileEntity> list = fileService.fileDirectory();
        // 存到域中
        model.addAttribute("filelist", list);
        return "backgrounder/useroperate/userdirectory";
    }

}
