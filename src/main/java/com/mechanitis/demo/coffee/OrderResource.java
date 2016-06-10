package com.mechanitis.demo.coffee;



import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;


import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    private Datastore datastore;

    public OrderResource (MongoClient mongoClient){
        datastore = new Morphia().createDatastore(mongoClient, "Order");
    }
}
