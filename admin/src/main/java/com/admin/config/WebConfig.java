package com.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@PropertySources({
	@PropertySource(value = "classpath:admin-dev-config.properties", ignoreResourceNotFound = false),
	@PropertySource(value = "file:/var/www/vhosts/ziumlight.com/config/application-${spring.profiles.active}.properties", ignoreResourceNotFound=true)
})
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Value("${image.folder}")
    private String imageFolderSource;
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/development/**").addResourceLocations("/development/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+imageFolderSource);
    }
	
	@Bean(name = "filterMultipartResolver")
	   public CommonsMultipartResolver filterMultipartResolver() {
	      CommonsMultipartResolver filterMultipartResolver = new CommonsMultipartResolver();
	      filterMultipartResolver.setDefaultEncoding("utf-8");
	      filterMultipartResolver.setMaxUploadSize(10485760);
	      return filterMultipartResolver;
	}
}
