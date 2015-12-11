package com.mechanitis.demo.coffee;

import org.mongodb.morphia.annotations.Id;

/**
 * Created by Wayne.Segar on 12/11/2015.
 */
public class Order {

    private String drinker;
    private String size;
    private long coffeeShopId;
    private String[] selectedOptions;
    private DrinkType type;

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public String getDrinker() {
        return drinker;
    }

    public String getSize() {
        return size;
    }

    public long getCoffeeShopId() {
        return coffeeShopId;
    }

    public String[] getSelectedOptions() {
        return selectedOptions;
    }

    public DrinkType getType() {
        return type;
    }
}
