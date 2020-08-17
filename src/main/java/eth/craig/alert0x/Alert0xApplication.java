package eth.craig.alert0x;

import net.consensys.eventeum.annotation.EnableEventeum;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude= {
        RabbitAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        MongoAutoConfiguration.class})
@EnableEventeum
@EnableFeignClients
public class Alert0xApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(Alert0xApplication.class)
                .properties("spring.config.additional-location:classpath:/eventeum.yml")
                .build().run(args);
    }
}
