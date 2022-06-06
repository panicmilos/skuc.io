package skuc.io.skucioapp;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.modelmapper.ModelMapper;
import org.kie.api.builder.KieScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

import skuc.io.skucioapp.sockets.SocketsModule;
import skuc.io.skuciocore.bus.Bus;
import skuc.io.skuciocore.models.notifications.InformUserNotification;
import skuc.io.skuciocore.models.notifications.Notification;

@SpringBootApplication(scanBasePackages = { "skuc.io.skucioapp", "skuc.io.skuciocore" })
public class SkucIoAppApplication {

	@Autowired
	private Bus bus;

	@Value("${rt-server.host}")
	private String host;

	@Value("${rt-server.port}")
	private Integer port;


	public static void main(String[] args) {
		System.setProperty("org.apache.commons.logging.Log",
"org.apache.commons.logging.impl.NoOpLog");
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

	@Bean
	public SocketIOServer socketIOServer() {
		Configuration config = new Configuration();
		config.setHostname(host);
		config.setPort(port);
		return new SocketIOServer(config);
	}

	@Bean
	CommandLineRunner runner(SocketIOServer socketIOServer, SocketsModule socketsModule) {
		return args -> {
			socketIOServer.start();

			bus.register("InformUser", (Object param) -> {
				var informUserNotification = (InformUserNotification) param;

				socketsModule.brodcast(informUserNotification.getGroupId() + "/infos", informUserNotification);
				return null;
			});

			bus.register("ValueReceived", (Object param) -> {
				var notification = (Notification) param;

				socketsModule.brodcast(notification.getGroupId() + "/events", notification);
				return null;
			});

			bus.register("StatusReceived", (Object param) -> {
				var notification = (Notification) param;

				socketsModule.brodcast(notification.getGroupId() + "/events", notification);
				return null;
			});
			
		};
	}

}
