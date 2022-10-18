package proj.base;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class App extends Application<AppConfig> {

    @Override
    public void run(AppConfig configuration, Environment environment) throws Exception {

        environment.healthChecks().register("AppHealthCheck", new AppHealthCheck());
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(GuiceBundle.builder()
                .enableAutoConfig(getClass().getPackage().getName())
                .modules(new AppModule())
                .build());

        bootstrap.addBundle(new SwaggerBundle<AppConfig>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AppConfig configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

}
