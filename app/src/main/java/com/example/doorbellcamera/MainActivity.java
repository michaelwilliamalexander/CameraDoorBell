package com.example.doorbellcamera;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.doorbellcamera.Notification.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mReferences;
    private ArrayList<Photo> photos;
    private StaggeredGridLayoutManager gridLayoutManager;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseMessaging.getInstance().subscribeToTopic("news");

        recyclerView = findViewById(R.id.recyclerView);
        gridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        photos = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mReferences = FirebaseDatabase.getInstance().getReference().child(mAuth.getUid());
        mReferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                photos.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Photo photo = dataSnapshot1.getValue(Photo.class);
                    photo.setTime(dataSnapshot1.getKey());
                    photos.add(photo);
                }
                adapter = new RecyclerViewAdapter(MainActivity.this,photos);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Ops....Something Wrong",Toast.LENGTH_SHORT).show();
            }
        });
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        token = task.getResult().getToken();
                        updateToken(token);
                    }
                });
    }

    private void updateToken(String refresh){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
        Token mToken =  new Token(token);
        ref.child(mAuth.getUid()).setValue(mToken);
        System.out.println(mToken.getToken());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            mAuth.signOut();
            Intent logout = new Intent(MainActivity.this, LoginActivity.class);
            logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(logout);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
