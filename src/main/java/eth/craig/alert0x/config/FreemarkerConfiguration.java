package eth.craig.alert0x.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration("fmConfiguration")
public class FreemarkerConfiguration {

    @Bean
    public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactory() {
        final FreeMarkerConfigurationFactoryBean fmConfigFactoryBean = new FreeMarkerConfigurationFactoryBean();
        fmConfigFactoryBean.setTemplateLoaderPath("/templates/");
        return fmConfigFactoryBean;
    }
}
