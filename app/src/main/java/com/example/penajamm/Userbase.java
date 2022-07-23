package com.example.penajamm;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Userbase
{
    private DatabaseReference databaseReference, reference;
    private ArrayList<User> users;
    private ArrayList<User> setUser;
    private FirebaseDatabase db;
    public Userbase()
    {
        setUsers();
        databaseReference = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());

    }

    public User getSpecificUser(String email) {
        for(User u: this.users) {
            if(u.getEmail().equals(email)){
                return u;
            }
        }

        for(User u: setUser){
            System.out.println(u.getRealname());
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }


    public void setUsers() {
        reference = FirebaseDatabase.getInstance().getReference("User");

        setUser = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    User user = dataSnapshot.getValue(User.class);
                    setUser.add(user);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        this.users = setUser;
        
    }


    public Task<Void> add(User emp)
    {
        return databaseReference.push().setValue(emp);
    }

    public Task<Void> update(String key, HashMap<String ,Object> hashMap)
    {
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public Task<Void> remove(String key)
    {
        return databaseReference.child(key).removeValue();
    }

    public Query get(String key)
    {
        if(key == null)
        {
            return databaseReference.orderByKey().limitToFirst(8);
        }
        return databaseReference.orderByKey().startAfter(key).limitToFirst(8);
    }

    public Query get()
    {
        return databaseReference;
    }

}