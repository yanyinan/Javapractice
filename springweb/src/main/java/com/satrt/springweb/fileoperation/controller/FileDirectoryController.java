package com.satrt.springweb.fileoperation.controller;

import com.satrt.springweb.core.constant.Constant;
import com.satrt.springweb.core.model.entity.FileEntity;
import com.satrt.springweb.core.model.entity.UserEntity;
import com.satrt.springweb.exception.service.ServiceException;
import com.satrt.springweb.exception.sql.SqlServiceException;
import com.satrt.springweb.fileoperation.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/26 20:56
 */
@Controller
@RequestMapping("/fileOperate")
public class FileDirectoryController {
    private FileService fileService;
    FileDirectoryController(FileService fileService){
        this.fileService = fileService;
    }
    @RequestMapping(value = "/fileDirectory", method = RequestMethod.GET)
    public String fileList(Model model) throws SqlServiceException {
        // 查询用户列表
        List<FileEntity> filelist = fileService.fileDirectory();
        // 存到域中
        model.addAttribute("fileList", filelist);
        return "backgrounder/fileOperation/fileDirectory";
    }
    @RequestMapping(value = "/fileAdd", method = RequestMethod.GET)
    public String fileAdd(Model model, Integer id) throws SqlServiceException {
        // 查询文件信息
        FileEntity byId = fileService.getById(id);
        // 存到域中
        model.addAttribute("file", byId);
        return "backgrounder/fileOperation/fileAdd";
    }
    @RequestMapping(value = "/fileAdd", method = RequestMethod.POST)
    public ModelAndView upload(@RequestParam("img") MultipartFile file, @SessionAttribute(Constant.LOGIN_USER) UserEntity user) {
        ModelAndView mv = new ModelAndView("redirect:/backgrounder/fileOperation/fileDirectory");
        if (!file.isEmpty()){
            try {
                fileService.save(file, user.getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SqlServiceException e) {
                throw new RuntimeException(e);
            }
        }
        return mv;
    }

}
