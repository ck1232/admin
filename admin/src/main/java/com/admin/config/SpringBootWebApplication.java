package com.admin.config;

import java.util.Properties;

import javax.sql.DataSource;

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
	@PropertySource(value = "file:/var/www/vhosts/ziumlight.com/config/application-${spring.profiles.active}.properties", ignoreResourceNotFound=true)
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
    public SessionFactory sessionFactory() throws Exception{
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
        prop.put("hibernate.enable_lazy_load_no_trans", "true");
        /*prop.put("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");*/
        return prop;
	}	
	
	@Bean(name = "dataSource", destroyMethod="")
	public DataSource dataSource() throws Exception{
		/*ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setJdbcUrl(url);
		cpds.setUser(user);
		cpds.setPassword(password);
		cpds.setDriverClass(driver);
		
		cpds.setInitialPoolSize(5);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(5);
		cpds.setMaxStatements(1000);
		
		return cpds;*/
		BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(password);
		return ds;
	}
	
	@Bean(name="transactionManager")
    public HibernateTransactionManager txManager() throws Exception{
		return new HibernateTransactionManager(sessionFactory());
    }
	
   /*@Bean
   public FilterRegistrationBean registerOpenEntityManagerInViewFilterBean() {
       FilterRegistrationBean registrationBean = new FilterRegistrationBean();
       OpenEntityManagerInViewFilter filter = new OpenEntityManagerInViewFilter();
       registrationBean.setFilter(filter);
       registrationBean.setOrder(5);
       return registrationBean;
   }*/
}
