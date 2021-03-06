package com.example.larry.lotowner;

import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Larry on 1/27/2018.
 */

public class Parking_Lot_Details {

    private String lot_id;
    private String phoneNumber;
    private String lotName;
    private String ownerName;
    private String capacity;
    private String email;
    private GeoFire location;
    private FirebaseDatabase mDatabase= FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;




    public Parking_Lot_Details(){

    }

    public Parking_Lot_Details(String lot_id, String lotName, String ownerName, String phoneNumber, String email, String capacity) {
        this.lot_id=lot_id;
        this.lotName = lotName;
        this.ownerName = ownerName;
        this.phoneNumber=phoneNumber;
        this.capacity = capacity;
        this.email=email;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public  GeoFire getLocation() {return location;}

    public  void setLocation(GeoFire mlocation) {location = mlocation;}

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getLot_id() {return lot_id;}

    public void setLot_id(String lot_id) {this.lot_id = lot_id;}


    //This method takes the coordinates saves it as a GeoFire


    public void geoLocation(Double latitude,Double longitude) {


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Coordinates");
       // ref.child("Parking Lots").child(lot_id).setValue(item);

        GeoFire geoFire = new GeoFire(ref);





        geoFire.setLocation(getLot_id(), new GeoLocation(latitude,longitude),
                new GeoFire.CompletionListener() {

                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        if (error != null) {
                            Log.i("Geolocation","There was an error saving the location to GeoFire: " + error);
                        } else {
                            Log.i("Geolocation","Location saved on server successfully!");
                        }
                    }
                });

        //Parking_Lot_Details location=new Parking_Lot_Details();
        setLocation(geoFire);


    }

}
