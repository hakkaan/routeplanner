package fi.tasis.routeplanner.routeplannerintegration;

import fi.tasis.routeplanner.routeplannerintegration.config.ObjectMapperFactory;
import fi.tasis.routeplanner.routeplannerintegration.service.StopMessageService;
import fi.tasis.routeplanner.routeplannermodel.model.StopMessageContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Slf4j
@SpringBootApplication(scanBasePackages = "fi.tasis.routeplanner")
public class RouteplannerIntegrationApplication {

	@Value("${custom.mqtt.topic}")
	private String mqttTopic;

	@Value("${custom.mqtt.host}")
	private String mqttHost;

	private final StopMessageService stopMessageService;

	public RouteplannerIntegrationApplication(StopMessageService stopMessageService) {
		this.stopMessageService = stopMessageService;
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(RouteplannerIntegrationApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}

	@Bean
	public MessageChannel mqttInputChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageProducer inbound() {
		MqttPahoMessageDrivenChannelAdapter adapter =
				new MqttPahoMessageDrivenChannelAdapter(mqttHost, RandomStringUtils.randomAlphanumeric(20), mqttTopic);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(1);
		adapter.setOutputChannel(mqttInputChannel());
		return adapter;
	}

	@Bean
	@Transformer(inputChannel = "mqttInputChannel", outputChannel = "handleChannel")
	public JsonToObjectTransformer jsonToObjectTransformer() {
		return new JsonToObjectTransformer(StopMessageContainer.class, ObjectMapperFactory.getMapper());
	}

	@Bean
	@ServiceActivator(inputChannel = "handleChannel")
	public MessageHandler handler() {
		return message -> stopMessageService.saveStopMessage((StopMessageContainer) message.getPayload());
	}

}
