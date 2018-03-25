package com.admin.config.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.csrf().ignoringAntMatchers("/invoice/saveExcelInvoice")
	  	.and()
	  	.authorizeRequests()
	  	.accessDecisionManager(accessDecisionManager())
	  	.antMatchers("/invoice/saveExcelInvoice").anonymous()
	  	.antMatchers("/").authenticated()
	  	.antMatchers("/dashboard").fullyAuthenticated()
		.antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN","ADMIN")
		.antMatchers("/query", "/q").hasAnyRole("ROLE_ADMIN","ADMIN")
		.antMatchers("/product/**", "/batchintake/**", "/inventory**/**", "/promotion/**").hasAnyRole("PRODUCT_MGR", "ADMIN")
		.antMatchers("/invoice/**", "/expense/**", "/salarybonus/**", "/employee/**", "/cheque/**").hasAnyRole("DATA_ENTRY_USER", "ADMIN")
		
		.and().formLogin().loginPage("/login").permitAll()
		.usernameParameter("username").passwordParameter("password")
		.and().exceptionHandling().accessDeniedPage("/Access_Denied")
		.and().logout().invalidateHttpSession(true).logoutUrl("/logout").deleteCookies("JSESSIONID").permitAll()
		.and().sessionManagement().maximumSessions(1).expiredUrl("/login").and().invalidSessionUrl("/login");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Bean
	public AccessDecisionManager accessDecisionManager() {
	    List<AccessDecisionVoter<? extends Object>> decisionVoters 
	      = Arrays.asList(
	        new WebExpressionVoter(),
	    	new RoleVoter(),
	        new AuthenticatedVoter());
	    return new UnanimousBased(decisionVoters);
	}
}
