package eth.craig.alert0x.spec;

import eth.craig.alert0x.exception.ScriptParseException;
import eth.craig.alert0x.model.alert.Alert;
import eth.craig.alert0x.service.BlockchainService;
import groovy.lang.GroovyShell;
import groovy.lang.MissingMethodException;
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

    private BlockchainService blockchainService;

    public List<AlertSpec> getAlertSpecs() {

        try {
            final Resource[] resources = resourcePatternResolver.getResources("classpath:scripts/*.groovy");

            return Arrays.asList(resources)
                    .stream()
                    .map(this::scriptResourceToAlertSpec)
                    .filter(spec -> spec != null)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ScriptParseException("There was an error when loading groovy scripts", e);
        }
    }

    private AlertSpec scriptResourceToAlertSpec(Resource resource) {
        try {
            final GroovyShell groovyShell = new GroovyShell();

            final Script script = groovyShell.parse(new InputStreamReader(resource.getInputStream()));

            if (isDisabled(script)) {
                return null;
            }

            final Object when =  script.invokeMethod("when", null);

            if (!(when instanceof Criterion)) {
                //TODO throw
            }

            final List<Alert> alerts = (List<Alert>) script.invokeMethod("sendAlerts", null);

            final Criterion criterion = (Criterion) when;

            if (criterion instanceof BlockchainInteractingCriterion) {
                ((BlockchainInteractingCriterion)criterion).setBlockchainService(blockchainService);
            }

            return AlertSpec
                    .builder()
                    .id(UUID.randomUUID().toString())
                    .criterion(criterion)
                    .alerts(alerts)
                    .build();
        } catch (IOException e) {
            throw new ScriptParseException("There was an error when executing a groovy script", e);
        }
    }

    private boolean isDisabled(Script script) {
        try {
            return (boolean) script.invokeMethod("isDisabled", null);

        } catch (MissingMethodException mme) {
            return false;
        }
    }
}
