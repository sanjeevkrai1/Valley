package com.valley.cws.config;

import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.XmlViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
@ComponentScan("com.valley.cws")
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:cws.properties")
public class Config extends WebMvcConfigurerAdapter {

	@Value("${oracle.db.driver}")
	private String oracleDbDriver;

	@Value("${oracle.db.url}")
	private String oracleDbUrl;

	@Value("${oracle.db.username}")
	private String oracleDbUserName;

	@Value("${oracle.db.password}")
	private String oracleDbPassword;

	@Value("${oracle.hibernate.dialect}")
	private String oracleDialect;

	@Value("${oracle.hibernate.hbm2ddl.auto}")
	private String oracleHbmddl;

	@Value("${oracle.hibernate.connection.datasource}")
	private String oracleDatasource;

	@Value("${mysql.db.driver}")
	private String mysqlDbDriver;

	@Value("${mysql.db.url}")
	private String mysqlDbUrl;

	@Value("${mysql.db.username}")
	private String mysqlDbUserName;

	@Value("${mysql.db.password}")
	private String mysqlDbPassword;

	@Value("${mysql.hibernate.dialect}")
	private String mysqlDialect;

	@Value("${mysql.hibernate.hbm2ddl.auto}")
	private String mysqlHbmddl;

	@Value("${message.basename}")
	private String baseName;

	@Value("${message.encoding}")
	private String encoding;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	public XmlViewResolver XmlViewResolver() {
		XmlViewResolver xmlViewResolver = new XmlViewResolver();
		return xmlViewResolver;
	}

	@Bean
	public InternalResourceViewResolver setupViewResolverForRedirect() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewNames(new String[] { "redirect*" });
		return resolver;
	}

	@Bean
	public TemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		templateResolver.setCacheable(false);
		templateResolver.setTemplateMode("HTML5");
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
		resource.setBasename(baseName);
		resource.setDefaultEncoding(encoding);
		return resource;
	}

	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory() {
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(getOracleDataSource());
		sessionBuilder.addProperties(getOracleHibernateProperties());

		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaAdData.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaAdShownHistory.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaBrowsingHistory.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaBuyingHistory.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaCategoryDetails.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaPolicyAdConfiguration.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaPolicyConfiguration.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaPolicyPeriod.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaSubcategoryDetails.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaUserProfile.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaLoginHistory.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaRunningAppHistory.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaDefaultAd.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaConfiguration.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaWalletPlan.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.CategoryUrlMapping.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.UserLogin.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaAppDataUsage.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaCampaignInfo.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.GroupMaster.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaBottomAds.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaUserInterest.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.WcaIpRanges.class);

		return sessionBuilder.buildSessionFactory();
	}

	@Bean(name = "mysqlSessionFactory")
	public SessionFactory getMysqlSessionFactory() {
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(getMysqlDataSource());
		sessionBuilder.addProperties(getMysqlHibernateProperties());

		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.mysql.Radcheck.class);
		sessionBuilder.addAnnotatedClass(com.valley.cws.entity.mysql.Radreply.class);

		return sessionBuilder.buildSessionFactory();
	}

	private Properties getOracleHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql",false);
		properties.put("hibernate.dialect", oracleDialect);
		properties.put("hibernate.hbm2ddl.auto", oracleHbmddl);
		return properties;
	}

	private Properties getMysqlHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", true);
		properties.put("hibernate.dialect", mysqlDialect);
		properties.put("hibernate.hbm2ddl.auto", mysqlHbmddl);
		return properties;
	}

	@Bean
	public BasicDataSource getOracleDataSource() {
		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setDriverClassName(oracleDbDriver);
		dataSource.setUrl(oracleDbUrl);
		dataSource.setUsername(oracleDbUserName);
		dataSource.setPassword(oracleDbPassword);

		return dataSource;
	}

	@Bean
	public BasicDataSource getMysqlDataSource() {
		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setDriverClassName(mysqlDbDriver);
		dataSource.setUrl(mysqlDbUrl);
		dataSource.setUsername(mysqlDbUserName);
		dataSource.setPassword(mysqlDbPassword);

		return dataSource;
	}

	@Bean(name="transactionManager")
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(getSessionFactory());
		return transactionManager;
	}

	@Bean(name="mysqlTransactionManager")
	public HibernateTransactionManager getMysqlTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(getMysqlSessionFactory());
		return transactionManager;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
