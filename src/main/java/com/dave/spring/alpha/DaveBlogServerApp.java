package com.dave.spring.alpha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@ComponentScan({"com.dave.spring.alpha", "com.dave.spring.starter.user", "com.dave.spring.alpha.repo", "com.dave.spring.alpha.model"})
//@ComponentScan(basePackages = {"com.dave.spring.alpha", "com.dave.spring.alpha.user", "com.dave.spring.alpha.repo"})
//@EnableJpaRepositories(basePackages = "com.dave.spring.alpha.repo")
//@EnableJms
public class DaveBlogServerApp {

	public static void main(String[] args) {
		SpringApplication.run(DaveBlogServerApp.class, args);
	}
	
	/*
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
        return builder
            .withDataSource(dataSource)
            
            .packages("com.dave.spring.alpha.model") // Package where your JPA entities are located
            .persistenceUnit("yourPersistenceUnitName")
            .build();
    }
    */
}
