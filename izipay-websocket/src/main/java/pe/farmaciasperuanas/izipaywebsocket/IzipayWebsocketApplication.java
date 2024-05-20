package pe.farmaciasperuanas.izipaywebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IzipayWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(IzipayWebsocketApplication.class, args);
	}

}
