package eth.craig.alert0x.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;

@Component
public class FreemarkerTemplateEngine implements TemplateEngine {

    private Configuration freeMarkerConfiguration;

    @Autowired
    public FreemarkerTemplateEngine(Configuration freeMarkerConfiguration) {
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

    @Override
    public String generate(String templateLocator, Map<String, String> properties) {
        final StringBuffer content = new StringBuffer();

        try {
            content.append(FreeMarkerTemplateUtils
                    .processTemplateIntoString(freeMarkerConfiguration.getTemplate(
                            templateLocator),
                            properties));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public String generateFromString(String templateName, String templateString, Map<String, String> properties) {
        final StringBuffer content = new StringBuffer();

        try {
            content.append(FreeMarkerTemplateUtils
                    .processTemplateIntoString(new Template(templateName, templateString, freeMarkerConfiguration),
                            properties));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
