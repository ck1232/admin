package com.admin.config;

import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
@SpringBootApplication
@ComponentScan(basePackages ={ "com.admin.*"})
@EntityScan("com.admin.to")
@EnableJpaRepositories("com.admin.dao")
@PropertySources({
	@PropertySource(value = "classpath:admin-dev-config.properties", ignoreResourceNotFound = false),
	@PropertySource(value = "file:C:\\Inetpub\\vhosts\\ziumlight.com\\Configuration\\application-${spring.profiles.active}.properties", ignoreResourceNotFound=true)
})
public class SpringBootWebApplication extends SpringBootServletInitializer{
	
	private static Class<SpringBootWebApplication> applicationClass = SpringBootWebApplication.class;
	
	@Value("${jdbc.admin.user}")
    private String user;
	
	@Value("${jdbc.admin.password}")
    private String password;
	
	@Value("${jdbc.url}")
    private String url;
	
	@Value("${jdbc.driver}")
	private String driver;
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
	
	
	@Bean
    public SessionFactory sessionFactory() {
            LocalSessionFactoryBuilder builder = 
		new LocalSessionFactoryBuilder(dataSource());
            builder.scanPackages("com.admin.to")
                  .addProperties(getHibernateProperties());

            return builder.buildSessionFactory();
    }
	
	private Properties getHibernateProperties() {
        Properties prop = new Properties();
        prop.put("hibernate.format_sql", "true");
        prop.put("hibernate.show_sql", "true");
        prop.put("hibernate.dialect", 
            "org.hibernate.dialect.MySQL5Dialect");
        prop.put("hibernate.ddl-auto", "validate");
        return prop;
	}	
	
	@Bean(name = "dataSource")
	public BasicDataSource dataSource() {
		
		BasicDataSource ds = new BasicDataSource();
	        ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(password);
		return ds;
	}
	
	@Bean(name="transactionManager")
    public HibernateTransactionManager txManager() {
            return new HibernateTransactionManager(sessionFactory());
    }
}
