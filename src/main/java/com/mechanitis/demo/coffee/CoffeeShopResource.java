package com.mechanitis.demo.coffee;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;


@Path("/coffeeshop")
@Produces(MediaType.APPLICATION_JSON)
public class CoffeeShopResource {
    private Datastore datastore;

    public CoffeeShopResource(MongoClient mongoClient) {
        datastore = new Morphia().createDatastore(mongoClient, "Cafelito");
    }

    @Path("nearest/{latitude}/{longitude}")
    @GET
    public Object getNearest(@PathParam("latitude") double latitude ,
                        @PathParam("longitude") double longitude ) {
        return datastore.find(CoffeeShop.class)
                        .field("location")
                        .near(longitude, latitude, true)
                        .get();
    }

    //Path to new Service Using Google Places API
    @Path("gnearest/{latitude}/{longitude}")
    @GET
    public String getGoogleNearest(@PathParam("latitude") double latitude ,
                             @PathParam("longitude") double longitude ) throws IOException {

        final String GOOGLE_API_KEY = "AIzaSyBWhQLxfNxyiQr1kqF2Bo-A0Dg3ZqutgBw";
        String endpoint = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longitude + "&radius=500&name=coffee&key=" + GOOGLE_API_KEY;

        try {
            URL url = new URL(endpoint);

            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                result.append(line);
            }
            return result.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Path("order")
    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveOrder(Order order) {
        datastore.save(order);

        return Response.created(URI.create(order.getId())).entity(order).build();
    }
}
