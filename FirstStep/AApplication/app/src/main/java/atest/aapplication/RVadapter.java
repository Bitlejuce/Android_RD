package atest.aapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import atest.aapplication.pojo.Link;


public class RVadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Link> items;
    private MainActivity activity;

    public RVadapter(MainActivity activity) {
        this.activity = activity;
        items = activity.getLinkList();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Link item = items.get(position);
        itemViewHolder.link.setText(item.getLink());
        itemViewHolder.timeAndDate.setText(String.valueOf(item.getDateMills()));

        //checking status to set background colour
        if (item.getStatus() == Link.STATUS_LOADED) {
            //TODO
        }
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView link;
        TextView timeAndDate;

        public ItemViewHolder(View itemView) {
            super(itemView);
            link = itemView.findViewById(R.id.link_textview);
            timeAndDate = itemView.findViewById(R.id.time_date_textview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                }
            });

        }
    }
}
