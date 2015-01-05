package com.ruchi.hibernate.model.DAO;

import java.sql.Timestamp;

import javax.persistence.*;

/**
 * Created by Thamayanthy on 12/22/2014.
 */

@Entity
@Table(name = "foods", uniqueConstraints = {@UniqueConstraint(columnNames = {"food_id"})})
public class FoodDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "food_id", nullable = false, unique = true, length = 50)
    private Timestamp food_id;

    @Column(name = "food_name", nullable = false, unique = false, length = 50)
    private String food_name;

    @Column(name = "food_type", nullable = true, unique = false, length = 20)
    private String food_type;

    public Timestamp getFood_id() {
        return food_id;
    }

    public void setFood_id(Timestamp food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }
}
