package eth.craig.alert0x.spec;

import eth.craig.alert0x.exception.ScriptParseException;
import eth.craig.alert0x.model.alert.Alert;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GroovyAlertSpecSource implements AlertSpecSource {

    private ResourcePatternResolver resourcePatternResolver;

    public List<AlertSpec> getAlertSpecs() {

        try {
            final Resource[] resources = resourcePatternResolver.getResources("classpath:scripts/*.groovy");

            return Arrays.asList(resources)
                    .stream()
                    .map(this::scriptResourceToAlertSpec)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ScriptParseException("There was an error when loading groovy scripts", e);
        }
    }

    private AlertSpec scriptResourceToAlertSpec(Resource resource) {
        try {
            final GroovyShell groovyShell = new GroovyShell();

            final Script script = groovyShell.parse(new InputStreamReader(resource.getInputStream()));
            final Object when =  script.invokeMethod("when", null);

            if (!(when instanceof Criterion)) {
                //TODO throw
            }

            final List<Alert> alerts = (List<Alert>) script.invokeMethod("sendAlerts", null);

            return AlertSpec
                    .builder()
                    .id(UUID.randomUUID().toString())
                    .criterion((Criterion) when)
                    .alerts(alerts)
                    .build();
        } catch (IOException e) {
            throw new ScriptParseException("There was an error when executing a groovy script", e);
        }
    }
}
