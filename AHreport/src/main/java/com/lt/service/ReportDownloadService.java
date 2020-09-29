package com.lt.service;

import com.lt.entity.vo.ReqVo;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/20 20:33
 */
public interface ReportDownloadService {

    void wordExport(HttpServletResponse httpServletResponse) throws IOException, TemplateException, InterruptedException;
}
