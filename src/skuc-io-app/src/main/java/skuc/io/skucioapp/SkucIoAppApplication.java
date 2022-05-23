package skuc.io.skucioapp;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.builder.KieScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SkucIoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkucIoAppApplication.class, args);
	}

	@Bean
	public KieContainer kieContainer() {
	 KieServices ks = KieServices.Factory.get();
	 KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("skuc.io","skuc-io-kjar", "0.0.1-SNAPSHOT"));
	 KieScanner kScanner = ks.newKieScanner(kContainer);
	 kScanner.start(10_000);

	 return kContainer;
	}
	

}
