package com.webapp.ecommerce_spring_boot.config;


import com.webapp.ecommerce_spring_boot.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] restrictedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        // Allow HTTP methods for Product with full CRUD support
        enableHttpMethods(Product.class, config);

        // Restrict ProductCategory, Country, and State
        disableHttpMethods(ProductCategory.class, config, restrictedActions);
        disableHttpMethods(Country.class, config, restrictedActions);
        disableHttpMethods(State.class, config, restrictedActions);

        // call an internal helper method
        exposeIds(config);

        // Enable CORS for frontend (http://localhost:4200)
        cors.addMapping("/api/**") // Allow all API endpoints
                .allowedOrigins("http://localhost:4200") // Allow frontend requests
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }



    private void disableHttpMethods(Class<?> theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void enableHttpMethods(Class<?> theClass, RepositoryRestConfiguration config) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.enable(HttpMethod.values()))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.enable(HttpMethod.values()));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        List<Class<?>> entityClasses = new ArrayList<>();

        for (EntityType<?> entityType : entities) {
            entityClasses.add(entityType.getJavaType());
        }

        Class<?>[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}