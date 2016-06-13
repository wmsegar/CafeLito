package com.mechanitis.demo.coffee;

import com.mongodb.MongoClient;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;

/**
 * Created by Wayne.Segar on 12/8/2015.
 */
public class CoffeeShopService extends Service<Configuration> {
    public static void main(String[] args) throws Exception {
        new CoffeeShopService().run(args);
    }
    @Override
    public void initialize(final Bootstrap<Configuration> bootstrap) {
        AssetsBundle bundle = new AssetsBundle("/html", "/");
        bootstrap.addBundle(bundle);

    }

    @Override
    public void run(final Configuration configuration, final Environment environment) throws Exception {
        MongoClient mongoClient = new MongoClient("10.140.117.15", 27017);
        environment.manage(new MongoClientManager(mongoClient));
        environment.addResource(new CoffeeShopResource(new MongoClient()));


    }
}
