package com.shoppinglist.rdproject.shoppinglist.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.shoppinglist.rdproject.shoppinglist.DBHelper;
import com.shoppinglist.rdproject.shoppinglist.DataListHolder;
import com.shoppinglist.rdproject.shoppinglist.MainScreen;
import com.shoppinglist.rdproject.shoppinglist.Product;
import com.shoppinglist.rdproject.shoppinglist.R;
import com.shoppinglist.rdproject.shoppinglist.dialogs.ModifyItemDialog;

import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int AD_TYPE = 101;
    private static final int CONTENT_TYPE = 202;
    private List<Product> product;
    private int layoutId;
    private Activity context;
    private DataListHolder dataListHolder;
    public List<Product> getProductList() {
        return product;
    }

    public RVAdapter(Activity context, List<Product> product, int layoutId, DataListHolder dataListHolder){

        this.product = product;
        this.layoutId = layoutId;
        this.context = context;
        this.dataListHolder = dataListHolder;
    }
    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AD_TYPE){
            View  v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_banner_layout, parent, false);
            return new AdsViewHolder(v);
        } else {

            CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

            switch (layoutId) {
                case R.id.lis_to_do: {
                    v.setCardBackgroundColor(context.getResources().getColor(R.color.cardBackground));
                    return new ProductViewHolder(v);
                }
                case R.id.list_done: {
                    v.setCardBackgroundColor(context.getResources().getColor(R.color.cardview_dark_background));
                }
            }
            return new ProductViewHolder(v);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        switch (holder.getItemViewType()) {
            case AD_TYPE:
                AdsViewHolder adsViewHolder = (AdsViewHolder) holder;
                AdRequest request = new AdRequest.Builder().addTestDevice("3361271BC77BB2DAE797507DFCF9F45A").build();
                adsViewHolder.mAdView.loadAd(request);
                break;

            case CONTENT_TYPE:
                ProductViewHolder productViewHolder = (ProductViewHolder) holder;
                String name = product.get(i).getName();
                productViewHolder.productName.setText(name);
                productViewHolder.productQty.setText(product.get(i).getQuantity());
                productViewHolder.productPhoto.setText(name.substring(0, 1).toUpperCase());
            }
        }

    @Override
    public int getItemViewType(int position)
    {
        if (MainScreen.isAdsfree || MainScreen.isAdsfreeForNow) return CONTENT_TYPE;

        if (position == 5) {
            return AD_TYPE;
        }
        return CONTENT_TYPE;
    }

    @Override
    public int getItemCount() {
//        if (product.size()>5 && !MainScreen.isAdsfree){
//            return product.size() + 1;
//        }
        return product.size();
    }

//    private int getRealPosition(int position) {
//        if (MainScreen.isAdsfree) {
//            return position;
//        } else {
//            if (getItemViewType(position) == CONTENT_TYPE && position > 5){
//            return position -1;
//        }
//            return position;
//        }
//    }

    public class AdsViewHolder extends RecyclerView.ViewHolder {
        AdView mAdView;
        public AdsViewHolder(View itemView) {
            super(itemView);
            mAdView = (AdView)itemView.findViewById(R.id.adView);
        }

    }
    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AdapterView.OnLongClickListener {
        View cv;
        TextView productName;
        TextView productQty;
        TextView productPhoto;
        ProductViewHolder(View itemView) {
            super(itemView);
            cv = itemView; //(CardView)itemView.findViewById(R.id.item);
            productName = (TextView)itemView.findViewById(R.id.product_name);
            productQty = (TextView)itemView.findViewById(R.id.quantity);
            productPhoto = (TextView)itemView.findViewById(R.id.pic_of_the_product);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {
                RecyclerView listFrom;
                RecyclerView listTo;
                int position = getAdapterPosition();
                Product product;

                if (layoutId == R.id.lis_to_do) {
                    listFrom = (RecyclerView) view.getRootView().findViewById(R.id.lis_to_do);   // testing
                    listTo = (RecyclerView) view.getRootView().findViewById(R.id.list_done);   // testing
                } else {
                    listTo = (RecyclerView) view.getRootView().findViewById(R.id.lis_to_do);   // testing
                    listFrom = (RecyclerView) view.getRootView().findViewById(R.id.list_done);   // testing
                }

                RVAdapter adapterFrom = (RVAdapter) listFrom.getAdapter();
                RVAdapter adapterTo = (RVAdapter) listTo.getAdapter();

                product = adapterFrom.getProductList().get(position);
                if (product.getStatus() == DBHelper.STATUS_TODO) {
                    product.setStatus(DBHelper.STATUS_DONE);
                }else {
                    product.setStatus(DBHelper.STATUS_TODO);
                }
                List<Product> listWhereAddProduct = adapterTo.getProductList();
                List<Product> listWhereRemoveProduct = adapterFrom.getProductList();

                    listWhereAddProduct.add(0, product);
                    adapterTo.notifyDataSetChanged();

                    listWhereRemoveProduct.remove(position);
                    adapterFrom.notifyDataSetChanged();


                dataListHolder.update(product);


                if (layoutId == R.id.lis_to_do) {
                    Snackbar.make(view, "Well done, you just got  " + (product.getName() + "!"), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            } catch (Exception e) {
                // ignore right now
            }
        }
//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            menu.setHeaderTitle(R.string.select_option);
//            menu.add(0, RENAME_ITEM, 0, R.string.rename);//groupId, itemId, order, title
//            menu.add(0, DELETE_ITEM, 1, R.string.delete);
//        }
//

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();

            ModifyItemDialog modifyItemDialog = new ModifyItemDialog();
            modifyItemDialog.setItemToModify( product, position);
            modifyItemDialog.show(context.getFragmentManager(), "Modify item");
            return true;
        }
    }
}