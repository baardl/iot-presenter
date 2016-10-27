package io.baardl.presenter;

import org.constretto.ConstrettoBuilder;
import org.constretto.ConstrettoConfiguration;
import org.constretto.model.Resource;
import org.constretto.spring.javaconfig.BasicConstrettoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PresenterApplicationConfig extends BasicConstrettoConfiguration {

    @Override
    public ConstrettoConfiguration constrettoConfiguration() {
        return new ConstrettoBuilder()
                .createPropertiesStore()
                .addResource(Resource.create("classpath:iot-presenter.properties"))
                .addResource(Resource.create("file:./config-override/iot-presenter.properties"))
                .done()
                .getConfiguration();
    }

}
