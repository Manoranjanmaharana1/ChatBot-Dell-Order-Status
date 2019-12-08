package com.example.chatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.smartreply.FirebaseSmartReply;
import com.google.firebase.ml.naturallanguage.smartreply.FirebaseTextMessage;
import com.google.firebase.ml.naturallanguage.smartreply.SmartReplySuggestion;
import com.google.firebase.ml.naturallanguage.smartreply.SmartReplySuggestionResult;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText sender;
    private ImageButton send;
    private String text;
    private List<Chat> chats;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<FirebaseTextMessage> conversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conversation = new ArrayList<>();
        sender = findViewById(R.id.sender);

        send = findViewById(R.id.send);
        recyclerView = findViewById(R.id.chatView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        chats = new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = sender.getText().toString().trim();
                if (text.length()>0){
                    conversation.add(FirebaseTextMessage.createForLocalUser(
                            text, System.currentTimeMillis()));

                    chats.add(new Chat(text,  1));
//                    CustomAdapter customAdapter = new CustomAdapter(chats,getApplicationContext());
//                    recyclerView.setAdapter(customAdapter);
                    Log.d("Chat",Integer.toString(chats.size()));
                    FirebaseSmartReply smartReply = FirebaseNaturalLanguage.getInstance().getSmartReply();
                    smartReply.suggestReplies(conversation)
                            .addOnSuccessListener(new OnSuccessListener<SmartReplySuggestionResult>() {
                                @Override
                                public void onSuccess(SmartReplySuggestionResult result) {
                                    if (result.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                                        Log.e("Chat","Not Supported!!");
                                        chats.add(new Chat("Sorry, I didn't get that!!",  2));
                                        CustomAdapter customAdapter1 = new CustomAdapter(chats,getApplicationContext());
                                        recyclerView.setAdapter(customAdapter1);
                                    } else if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
                                        List<String> responses = new ArrayList<>();
                                        for (SmartReplySuggestion suggestion : result.getSuggestions()) {
                                            responses.add(suggestion.getText());
                                        }

                                        chats.add(new Chat(responses.get(0),  2));
                                        CustomAdapter customAdapter1 = new CustomAdapter(chats,getApplicationContext());
                                        recyclerView.setAdapter(customAdapter1);

                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("Chat","Exception Occurred!!");
                                }
                            });

                }

            }
        });
    }
}
