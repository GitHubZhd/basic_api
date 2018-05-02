package com.toefl.basic.controller;

import com.toefl.basic.dto.Lrc;
import com.toefl.basic.utils.*;
import com.toefl.basic.utils.FileFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
public class HandleZipController {

    @RequestMapping(value = "/handle/zip", method = RequestMethod.POST)
    public void doHandle(@RequestParam(value = "file", required = false) MultipartFile file) {

        String filepath="D:/zhanghaidong/temp/";
        try {
            //1、保存zip文件
            File zipFile = saveFile(file, filepath + "upload/");

            //2、解析excel文件
            String targetFilePath = filepath + "audio/" + file.getOriginalFilename().split("\\.")[0] + File.separator;
            FileUtils.unzip(zipFile.getAbsolutePath(), targetFilePath);

            File targetFolder=new File(targetFilePath+file.getOriginalFilename().split("\\.")[0] + File.separator);
            File[]  excelArr=targetFolder.listFiles(new FileFilter(".xlsx"));
            if(excelArr!=null){
                System.out.println("-------"+excelArr.length);
                File excel=excelArr[0];
                ExcelUtil.excelImport(Lrc.class,excel.getAbsolutePath(),"test");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private File saveFile(MultipartFile file, String path) throws IOException {
        String fileName = UUIDUtils.create() + "aa.zip";
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        // 保存
        file.transferTo(targetFile);
        return targetFile;
    }
}
