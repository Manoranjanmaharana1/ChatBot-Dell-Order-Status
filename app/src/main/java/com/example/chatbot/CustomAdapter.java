package com.example.chatbot;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class BotHandler extends RecyclerView.ViewHolder {

    private TextView text1;
    //private ImageView chatbot;
    public BotHandler(@NonNull View itemView) {
        super(itemView);
        text1 = itemView.findViewById(R.id.botChat);
        //chatbot = itemView.findViewById(R.id.chatbot);
    }

    public void setDetails(String text) {
        text1.setText(text);
    }
}

class UserHandler extends RecyclerView.ViewHolder {

    private TextView text1;
    public UserHandler(@NonNull View itemView) {
        super(itemView);

        text1 = itemView.findViewById(R.id.userChat);

    }

    public void setDetails(String text) {
        text1.setText(text);
    }
}

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


//    private static int CHAT_USER = 1;
//    private static int CHAT_BOT = 2;
    private List<Chat> chats;
    private Context context;

    public CustomAdapter(List<Chat> chats, Context context) {
        this.chats = chats;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_body, viewGroup, false);
        View v2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_body2, viewGroup, false);
        if (i == 1) return new UserHandler(v2);
        else return new BotHandler(v1);

       //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()){
            case 1 : {
                UserHandler userHandler = (UserHandler)viewHolder;
                Chat chat = chats.get(i);
                userHandler.setDetails(chat.getText());
                break;
            }
            case 2 : {
                BotHandler botHandler = (BotHandler)viewHolder;
                Chat chat = chats.get(i);
                botHandler.setDetails(chat.getText());
                break;
            }
            default: break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (chats.get(position).getUserType() == 1) return 1; //for user
        else return 2;                                        //for bot
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

}
