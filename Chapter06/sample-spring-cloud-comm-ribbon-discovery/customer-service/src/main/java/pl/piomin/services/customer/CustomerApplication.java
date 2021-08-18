package pl.piomin.services.customer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import org.xmlpull.v1.XmlPullParserException;
import pl.piomin.services.customer.model.Customer;
import pl.piomin.services.customer.model.CustomerType;
import pl.piomin.services.customer.repository.CustomerRepository;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.Model;

import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class CustomerApplication {

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(CustomerApplication.class).web(true).run(args);
	}

	@Bean
	CustomerRepository repository() {
		CustomerRepository repository = new CustomerRepository();
		repository.add(new Customer("John Scott", CustomerType.NEW));
		repository.add(new Customer("Adam Smith", CustomerType.REGULAR));
		repository.add(new Customer("Jacob Ryan", CustomerType.VIP));
		return repository;
	}

	@Bean
	public Docket api() throws IOException, XmlPullParserException {
		MavenXpp3Reader reader = new MavenXpp3Reader();
//		Model model = reader.read(new FileReader("pom.xml"));
		ApiInfoBuilder builder = new ApiInfoBuilder()
				.title("Customer Service Api Documentation")
				.description("Documentation automatically generated")
				.contact(new Contact("qith", "kawainfo.com", "qith@gmail.com"));
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("pl.piomin.services.customer.controller"))
				.paths(PathSelectors.any()).build()
				.apiInfo(builder.build());
	}

	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration("validatorUrl", "list", "alpha", "schema",
				UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
	}
	
}
