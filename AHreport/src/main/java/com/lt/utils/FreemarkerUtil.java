package com.lt.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * FreeMarker模板方法类，将数据动态的拼接到template中
 *
 * @author teng.li
 * @version 1.0
 * @date 2020/8/20 19:33
 */
@Component
public class FreemarkerUtil {

    //Linux
    private static String path;

    @Value(value = "${reportPath.freeMarker.path}")
    public void setPath(String path) {
        FreemarkerUtil.path = path;
    }

    public static String generateString(String templateFileName, String templateDirectory, Map<String, Object> datas)
            throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);

        // 设置默认编码
        configuration.setDefaultEncoding("UTF-8");

        // 设置模板所在文件夹
        configuration.setDirectoryForTemplateLoading(new File(path + templateDirectory));

        // 生成模板对象
        Template template = configuration.getTemplate(templateFileName);

        // 将datas写入模板并返回
        try (StringWriter stringWriter = new StringWriter()) {
            template.process(datas, stringWriter);
            stringWriter.flush();
            return stringWriter.getBuffer().toString();
        }
    }
}
