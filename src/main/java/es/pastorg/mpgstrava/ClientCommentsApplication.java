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

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = {"es.pastorg.mpgstrava","es.pastorg","es.pastorg.mpgstrava.repository"
		,"es.pastorg.mpgstrava.repository"})
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
//	@Bean(name="entityManagerFactory")
//	public LocalSessionFactoryBean sessionFactory() {
//		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//		sessionFactory.setDataSource(dataSource());
//		//List<Resource> mappingFiles = searchMappingFiles();
//		//sessionFactory.setMappingLocations(mappingFiles.toArray(new Resource[mappingFiles.size()]));
//		return sessionFactory;
//	}
}
