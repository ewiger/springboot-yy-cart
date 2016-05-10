package io.yycart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;


@Configuration
@ImportResource({ "spring-jpa-mysql.xml"})
public class DbConfig {

	@Bean
	public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {

		Resource sourceData = new ClassPathResource("data.json");

		Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
		// Set a custom ObjectMapper if Jackson customization is needed
		// factory.setObjectMapper(…);
		factory.setResources(new Resource[] { sourceData });
		return factory;
	}

}
