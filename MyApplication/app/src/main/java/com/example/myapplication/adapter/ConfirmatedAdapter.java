package com.example.myapplication.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.models.Invitation;

import java.util.List;

public class ConfirmatedAdapter extends RecyclerView.Adapter<ConfirmatedAdapter.ViewHolder> {

    private Context context;
    private List<Invitation> invitations;

    public ConfirmatedAdapter(Context context,List<Invitation> invitationList){
        this.context = context;
        invitations = invitationList;
    }//le constructeur : on a besoin du context pour savoir dans quel activité afficher les items

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_confirmated_invitation,viewGroup,false);
        return new ViewHolder(view);// on recupere la vue qu'une seule fois pour eviter un gachi des ressource du phone
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Invitation invitationConfirmated = invitations.get(i);
        viewHolder.display(invitationConfirmated);//on aurait pu faire directement les setText ici mais dans le cas ou on voudra gerer le clic sur les cellule vaut mieux le faire dans le viewholder
        setAnimation(viewHolder.mItemView);//on fait une animation de 1seconde sur la cellule
    }//pour chaque cellule comment afficher

    private void setAnimation(View toAnimate){
        Animation animation = AnimationUtils.loadAnimation(toAnimate.getContext(),android.R.anim.fade_in);
        animation.setDuration(1000);
        toAnimate.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return invitations.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView mItemView;
        Invitation current;
        TextView name;
        TextView phone;
        ImageView avatar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView.findViewById(R.id.item_view);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            avatar = itemView.findViewById(R.id.avatar);

            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Click cellule", Toast.LENGTH_SHORT).show();
                }
            });
        }

        void display(Invitation invitation){
            current = invitation;
            name.setText(invitation.getName());
            phone.setText(invitation.getPhone());
            //utiliser picasso ou glide pour la photo

        }
    }
}
