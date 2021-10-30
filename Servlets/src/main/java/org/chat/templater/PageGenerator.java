package org.chat.templater;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class PageGenerator {
    private static final URL HTML_DIR = PageGenerator.class.getResource("/site");

    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    private PageGenerator() {
        cfg = new Configuration(Configuration.VERSION_2_3_27);
    }

    public static PageGenerator instance() {
        if (pageGenerator == null) {
            pageGenerator = new PageGenerator();
        }
        return pageGenerator;
    }

    public String getPage(String fileName, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            TemplateLoader templateLoader = new FileTemplateLoader(new File(HTML_DIR.toURI()));
            cfg.setTemplateLoader(templateLoader);
            Template template = cfg.getTemplate(fileName);
            template.process(data, stream);
        } catch (IOException | TemplateException | URISyntaxException exc) {
            exc.printStackTrace();
        }
        return stream.toString();
    }

}
