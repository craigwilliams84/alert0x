package eth.craig.alert0x.template;

import java.util.Map;

public interface TemplateEngine {

    String generate(String templateLocator, Map<String, String> properties);

    String generateFromString(String templateName, String templateString, Map<String, String> properties);
}
