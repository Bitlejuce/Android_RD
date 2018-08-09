package rd.declarationtest;

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

import java.util.List;

import rd.declarationtest.pojo.Item;

public class RVadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Item> items;
    private MainActivity activity;
    private List<Item> favoriteList;

    public RVadapter(MainActivity activity) {
        this.activity = activity;
        items = activity.getListToShow();
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
        Item item = items.get(position);
        itemViewHolder.firstSecondName.setText(item.getFirstname());
        itemViewHolder.patronymic.setText(item.getLastname());
        itemViewHolder.positionName.setText(item.getPosition());

        //checking if current person is in the favorite list to handle favorite icon
        if (favoriteList.contains(item)) {
            itemViewHolder.favoriteIcon.setImageResource(android.R.drawable.btn_star_big_on);
            itemViewHolder.notes.setVisibility(View.VISIBLE);

            //checking if current person HAS COMMENT in the favorite list to set right comment icon
            int indexOfItemInFavoriteList = favoriteList.indexOf(item);
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
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView firstSecondName;
        TextView patronymic;
        TextView positionName;
        ImageView favoriteIcon;
        ImageView pdfLink;
        ImageView notes;

        public ItemViewHolder(View itemView) {
            super(itemView);
            firstSecondName = itemView.findViewById(R.id.first_second_name);
            patronymic = itemView.findViewById(R.id.patronymic);
            positionName = itemView.findViewById(R.id.position_name);
            favoriteIcon = itemView.findViewById(R.id.empty_star);
            pdfLink = itemView.findViewById(R.id.pdf_link_pic);
            notes = itemView.findViewById(R.id.notes);

            favoriteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item clickedItem = items.get(getAdapterPosition());

                    if (favoriteList.contains(clickedItem)){
                        clickedItem.setFavorite(Item.NOT_FAVORITE_ITEM);
                        favoriteList.remove(clickedItem);
                        activity.getrVadapter().notifyDataSetChanged();
                    }else {
                        clickedItem.setFavorite(Item.FAVORITE_ITEM);
                        favoriteList.add(clickedItem);
                        activity.getrVadapter().notifyDataSetChanged();
                    }
                }
            });
            pdfLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item clickedItem = items.get(getAdapterPosition());
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(clickedItem.getLinkPDF()));
                    activity.startActivity(intent);
                }
            });
            notes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item clickedItem = items.get(getAdapterPosition());
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
