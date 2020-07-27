package eth.craig.alert0x;

import eth.craig.alert0x.service.AlertSpecRegistrar;
import eth.craig.alert0x.spec.AlertSpecSource;
import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AlertSpecBootstrapper {

    private AlertSpecRegistrar alertSpecRegistrar;
    private List<AlertSpecSource> alertSpecSources;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initAlertSpecs();
    }

    private void initAlertSpecs() {
        alertSpecSources.forEach(source -> alertSpecRegistrar.register(source.getAlertSpecs()));
    }
}
