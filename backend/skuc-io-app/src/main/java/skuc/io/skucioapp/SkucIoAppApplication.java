package skuc.io.skucioapp;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.modelmapper.ModelMapper;
import org.kie.api.builder.KieScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = { "skuc.io.skucioapp", "skuc.io.skuciocore" })
public class SkucIoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkucIoAppApplication.class, args);
	}

	@Bean
	public KieContainer kieContainer() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("skuc.io", "skuc-io-kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);

		return kContainer;
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}
