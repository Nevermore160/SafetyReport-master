package com.lt.controller;


import com.lt.service.ReportDownloadService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 安全报告日志下载
 *
 * @author teng.li
 * @version 1.0
 * @date 2020/8/20 17:55
 */
@RestController
@RequestMapping("/report")
@CrossOrigin
public class ReportDownloadController {

    @Autowired
    private ReportDownloadService reportDownloadService;

    @PostMapping("/download")
    public void downloadFile(HttpServletResponse response) {
        try {
            reportDownloadService.wordExport(response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
