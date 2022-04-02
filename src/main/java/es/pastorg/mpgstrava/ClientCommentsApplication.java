package es.pastorg.mpgstrava;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Server;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = {"es.pastorg.mpgstrava","es.pastorg","es.pastorg.mpgstrava.repository"
		,"es.pastorg.mpgstrava.background"})
public class ClientCommentsApplication {
	@Value(value = "${spring.datasource.url}")
	private String DATABASE_CONNECTION;

	@Value(value = "${spring.datasource.username}")
	private String DB_USERNAME;

	@Value(value = "${spring.datasource.password}")
	private String DB_PWD;

	public static void main(String[] args) {
		SpringApplication.run(ClientCommentsApplication.class, args);
	}
	@Bean
	public Docket activityApi(){
		Server testServer = new Server("test", "http://mpg4ras01:32209/strava/", "for testing"
				, Collections.emptyList(), Collections.emptyList());
		return new Docket(DocumentationType.OAS_30)
				.servers(testServer)
				.groupName("strava-comments")
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/strava/**"))
				.build().apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo(){
		return new ApiInfoBuilder().title("MadAstur")
				.version("0.1").description("Learning new Java again")
				.contact(new Contact("Marcos Pastor","http://www.madastur.com", "marcos@pastorg.es"))
				.license("OPEN the Doc Index.")
				.licenseUrl("https://api.madastur.com/Doc")
				.build();
	}

	@Bean
	@Scope("prototype")
	public Logger produceLogger(InjectionPoint injectionPoint) {
		Class<?> classOnWired = injectionPoint.getMember().getDeclaringClass();
		return LoggerFactory.getLogger(classOnWired);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		//dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		//dataSource.setUrl("jdbc:postgresql://mpgubu18:32685/stravadb");
		dataSource.setUrl(DATABASE_CONNECTION);
		dataSource.setUsername(DB_USERNAME);
		dataSource.setPassword(DB_PWD);

		return dataSource;
	}
}
