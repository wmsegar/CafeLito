package com.mechanitis.demo.coffee;

import com.mongodb.MongoClient;
import com.yammer.dropwizard.lifecycle.Managed;
import org.eclipse.jetty.util.component.LifeCycle;

/**
 * Created by Wayne.Segar on 12/11/2015.
 */
public class MongoClientManager implements Managed {
    private final MongoClient mongoClient;

    public MongoClientManager(final MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
        mongoClient.close();
    }
}
