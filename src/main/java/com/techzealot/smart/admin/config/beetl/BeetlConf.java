package com.techzealot.smart.admin.config.beetl;

import org.beetl.core.resource.ClasspathResource;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class BeetlConf {

        @Value("${beetl.templatesPath}") String templatesPath;//模板跟目录 ，比如 "templates"
        @Value("${beetl.prefix}") String prefix="";
        @Value("${beetl.suffix}") String suffix="";
        @Value("${beetl.order}") int order=0;
        @Value("${beetl.config}") String beetlConfig;
        @Bean(initMethod = "init", name = "beetlConfig")
        public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
                BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
                try {
                        ClasspathResourceLoader cploder = new ClasspathResourceLoader(BeetlConf.class.getClassLoader(),templatesPath);
                        beetlGroupUtilConfiguration.setResourceLoader(cploder);
                        beetlGroupUtilConfiguration.setConfigFileResource(new ClassPathResource(beetlConfig));
                        return beetlGroupUtilConfiguration;
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }

        }

        @Bean(name = "beetlViewResolver")
        public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
                BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
                beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
                beetlSpringViewResolver.setPrefix(prefix);
                beetlSpringViewResolver.setSuffix(suffix);
                beetlSpringViewResolver.setOrder(order);
                beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
                return beetlSpringViewResolver;
        }

 }