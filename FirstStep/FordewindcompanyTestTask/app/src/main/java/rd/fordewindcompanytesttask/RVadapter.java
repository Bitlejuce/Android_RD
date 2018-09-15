package rd.fordewindcompanytesttask;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import rd.fordewindcompanytesttask.pojo.User;


public class RVadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<User> users;
    private MainActivity activity;
    private List<User> favoriteList;

    public RVadapter(MainActivity activity) {
        this.activity = activity;
        users = activity.getListToShow();
        favoriteList = activity.getFavoriteList();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.person_card, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
        User user = users.get(position);
        itemViewHolder.login.setText(user.getLogin());
        itemViewHolder.url.setText(user.getHtmlUrl());
        Glide.with(activity).load(user.getAvatarUrl()).into(itemViewHolder.pickLink);


        //checking if current person is in the favorite list to handle favorite icon
        if (favoriteList.contains(user)) {
            itemViewHolder.favoriteIcon.setImageResource(android.R.drawable.btn_star_big_on);
            itemViewHolder.notes.setVisibility(View.VISIBLE);

            //checking if current person HAS COMMENT in the favorite list to set right comment icon
            int indexOfItemInFavoriteList = favoriteList.indexOf(user);
            if (favoriteList.get(indexOfItemInFavoriteList).getComment().isEmpty()) {
                itemViewHolder.notes.setImageResource(R.drawable.note_grey);
            }else {
                itemViewHolder.notes.setImageResource(R.drawable.note);
            }
        }else {
            itemViewHolder.favoriteIcon.setImageResource(android.R.drawable.btn_star);
            itemViewHolder.notes.setImageResource(R.drawable.note_grey);
            itemViewHolder.notes.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView login;
        TextView url;
        ImageView pickLink;

        ImageView favoriteIcon;
        ImageView notes;

        public ItemViewHolder(View itemView) {
            super(itemView);
            login = itemView.findViewById(R.id.login);
            url = itemView.findViewById(R.id.profile_link);
            favoriteIcon = itemView.findViewById(R.id.empty_star);
            pickLink = itemView.findViewById(R.id.link_pic);
            notes = itemView.findViewById(R.id.notes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User clickedItem = users.get(getAdapterPosition());
                    activity.showFollowers(clickedItem.getLogin());
                }
            });

            favoriteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User clickedItem = users.get(getAdapterPosition());

                    if (favoriteList.contains(clickedItem)){
                        clickedItem.setFavorite(User.NOT_FAVORITE_ITEM);
                        favoriteList.remove(clickedItem);
                        activity.getrVadapter().notifyDataSetChanged();
                    }else {
                        clickedItem.setFavorite(User.FAVORITE_ITEM);
                        favoriteList.add(clickedItem);
                        activity.getrVadapter().notifyDataSetChanged();
                    }
                }
            });
            pickLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User clickedItem = users.get(getAdapterPosition());
                    activity.showFollowers(clickedItem.getLogin());
                }
            });
            notes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User clickedItem = users.get(getAdapterPosition());
                    int indexOfItemInFavoriteList = favoriteList.indexOf(clickedItem);
                    String comment = favoriteList.get(indexOfItemInFavoriteList).getComment();

                    AddCommentDialog addCommentDialog = new AddCommentDialog();
                    addCommentDialog.setComment(comment);
                    addCommentDialog.setItemPosition(indexOfItemInFavoriteList);
                    addCommentDialog.show(activity.getFragmentManager(), "AddCommentDialog");
                }
            });

        }
    }
}
