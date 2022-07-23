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
    private FirebaseDatabase db;
    public Userbase()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());
        setUsers();
    }

    public User getSpecificUser(String email) {
        User user = new User();
        for(User u: this.users) {
            if(u.isEqual(email)) {
                user = u;
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
    public void setUsers() {
        reference = FirebaseDatabase.getInstance().getReference("User");

        ArrayList<User> u = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    User user = dataSnapshot.getValue(User.class);
                    u.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("AAAAAAAAAAAAAAAAAAAA");
            }
        });

        this.users = u;
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