package pl.piomin.services.product;

import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import org.xmlpull.v1.XmlPullParserException;
import pl.piomin.services.product.model.Product;
import pl.piomin.services.product.repository.ProductRepository;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class ProductApplication {

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ProductApplication.class).web(true).run(args);
	}

	@Bean
	ProductRepository repository() {
		ProductRepository repository = new ProductRepository();
		repository.add(new Product("Test1", 1000));
		repository.add(new Product("Test2", 1500));
		repository.add(new Product("Test3", 2000));
		repository.add(new Product("Test4", 3000));
		repository.add(new Product("Test5", 1300));
		repository.add(new Product("Test6", 2700));
		repository.add(new Product("Test7", 3500));
		repository.add(new Product("Test8", 1250));
		repository.add(new Product("Test9", 2450));
		repository.add(new Product("Test10", 800));
		return repository;
	}

	@Bean
	public Docket api() throws IOException, XmlPullParserException {
		MavenXpp3Reader reader = new MavenXpp3Reader();
//		Model model = reader.read(new FileReader("pom.xml"));
		ApiInfoBuilder builder = new ApiInfoBuilder()
				.title("Product Service Api Documentation")
				.description("Documentation automatically generated")
				.contact(new Contact("qith", "kawainfo.com", "qith@gmail.com"));
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("pl.piomin.services.product.controller"))
				.paths(PathSelectors.any()).build()
				.apiInfo(builder.build());
	}

	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration("validatorUrl", "list", "alpha", "schema",
				UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
	}

}
