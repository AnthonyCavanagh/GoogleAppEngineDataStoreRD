package com.cav.googlecloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
public class GoogleCloudDataService {
    public static void main( String[] args ) {
    	SpringApplication.run(GoogleCloudDataService.class, args);
    }
    
    @Bean(name = "datastoreService")
    public DatastoreService datastoreService(){
		return DatastoreServiceFactory.getDatastoreService();
	}
}
