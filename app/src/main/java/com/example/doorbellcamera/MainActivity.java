package com.example.doorbellcamera;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.doorbellcamera.Notification.APIService;
import com.example.doorbellcamera.Notification.Client;
import com.example.doorbellcamera.Notification.Data;
import com.example.doorbellcamera.Notification.Sender;
import com.example.doorbellcamera.Notification.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mReferences;
    private ArrayList<Photo> photos;
    private StaggeredGridLayoutManager gridLayoutManager;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private String token;
    protected RequestQueue requestQueue;
    APIService apiService;
    private boolean notify = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final String message =  "Message";
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        user = FirebaseAuth.getInstance().getCurrentUser();

        apiService = Client.getRetrofit("https://fcm.googleapis.com/").create(APIService.class);
        recyclerView = findViewById(R.id.recyclerView);
        gridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        photos = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        final String mUid = mAuth.getUid();
        final String nama = user.getDisplayName();
        mReferences = FirebaseDatabase.getInstance().getReference().child(mAuth.getUid());
        //sendNotification(user.getUid(), "NAnada","KOntol");
        sendNotification(mUid,nama,message);
        mReferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                photos.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    final DataSnapshot snapshot = dataSnapshot1;
                    Photo photo = dataSnapshot1.getValue(Photo.class);
                    photo.setTime(dataSnapshot1.getKey());
                    photos.add(photo);
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }
                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            if (direction == ItemTouchHelper.LEFT) {
                                RecyclerViewAdapter.ViewHolder noteViewHolder = (RecyclerViewAdapter.ViewHolder) viewHolder;
                                handleDeleteItem(snapshot);
                            }
                        }
                    });
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                }
                updateToken(FirebaseInstanceId.getInstance().getToken());
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
    public void handleDeleteItem(DataSnapshot snapshot) {
        final DatabaseReference documentReference = snapshot.getRef();
        final Photo photo = snapshot.getValue(Photo.class);

        documentReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        Snackbar.make(recyclerView, "Item Deleted", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentReference.setValue(photo);
            }
        }).show();
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
        }else if(item.getItemId() == R.id.action_token){
            Intent getToken = new Intent(MainActivity.this, getToken.class);
            startActivity(getToken);

        }else if(item.getItemId() == R.id.action_token){
            Intent refresh = new Intent(MainActivity.this, MainActivity.class);
            refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(refresh);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendNotification(final String hisUid, final String name, final String message){
        System.out.println("INI BAGIAN KIRIM NOTIFNYA CUY");
        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = allTokens.orderByKey().equalTo(hisUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("6");
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Token token = ds.getValue(Token.class);
                    Data data = new Data(user.getUid(),R.drawable.boxtext, "meme", "michael", "sented");
                    Sender sender = new Sender(data, token.getToken());
                    System.out.println("1");
                    //fcm json
//                    apiService.sendNotification(sender)
//                            .enqueue((new Callback<Response>() {
//                                @Override
//                                public void onResponse(Call<Response> call, Response<Response> response) {
//
//                                }
//
//                                @Override
//                                public void onFailure(Call<Response> call, Throwable t) {
//
//                                }
//                            }));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("coeg");
            }
        });

    }
}
