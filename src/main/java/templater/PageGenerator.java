package templater;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private static final String HTML_DIR = "site";

    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    private PageGenerator(){
        cfg = new Configuration(Configuration.VERSION_2_3_27);
    }

    public static PageGenerator instance(){
        if(pageGenerator == null){
            pageGenerator = new PageGenerator();
        }
        return pageGenerator;
    }

    public String getPage(String fileName, Map<String, Object> data){
        Writer stream = new StringWriter();
        try{
            TemplateLoader templateLoader = new FileTemplateLoader(new File(HTML_DIR + File.separator));
            cfg.setTemplateLoader(templateLoader);
            Template template = cfg.getTemplate(fileName);
            template.process(data, stream);
        }catch (IOException | TemplateException exc){
            exc.printStackTrace();
        }
        return stream.toString();
    }

}
