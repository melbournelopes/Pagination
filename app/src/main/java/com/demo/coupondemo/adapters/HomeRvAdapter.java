package com.demo.coupondemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.coupondemo.CouponApplication;
import com.demo.coupondemo.R;
import com.demo.coupondemo.fragments.HomeFragment;
import com.demo.coupondemo.interfaces.OnLoadMoreListener;
import com.demo.coupondemo.models.ResponseModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final String TAG = HomeRvAdapter.class.getSimpleName();
    public static final int NO_DATA = 0;
    private static final int CONTENT_TYPE_ITEM = 1;
    private static final int TYPE_LOAD_MORE = 2;
    private static final int EXTRA_BOTTOM_ITEMS = 1;
    private List<ResponseModel.Response.Item> itemList;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isMoreDataAvailable = true;
    private HomeFragment homeFragment;

    public HomeRvAdapter(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
        itemList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contentView;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case CONTENT_TYPE_ITEM:
                contentView = inflater.inflate(R.layout.rv_item_row, parent, false);
                viewHolder = new ViewHolderItem(contentView);
                break;
            case TYPE_LOAD_MORE:
                contentView = inflater.inflate(R.layout.load_more_footer, parent, false);
                viewHolder = new LoadHolder(contentView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case CONTENT_TYPE_ITEM:
                ViewHolderItem viewHolderItem = (ViewHolderItem) viewHolder;
                viewHolderItem.setData(itemList.get(position));
                viewHolderItem.rlItemMain.setTag(position);
                viewHolderItem.rlItemMain.setOnClickListener(this);

                break;
            case TYPE_LOAD_MORE:
                if (position != 0) {
                    LoadHolder loadHolder = (LoadHolder) viewHolder;
                    if (isMoreDataAvailable) {
                        onLoadMoreListener.onLoadMore();
                        if (homeFragment.getIndex() != 0) {
                            loadHolder.progressBarBottom.setVisibility(View.VISIBLE);
                        } else {
                            loadHolder.progressBarBottom.setVisibility(View.GONE);
                        }
                    } else {
                        loadHolder.progressBarBottom.setVisibility(View.GONE);
                    }
                }
                break;
        }

    }

    @Override
    public void onClick(View v) {
        final int position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.rlItemMain:
                Toast.makeText(homeFragment.getActivity(), "" + itemList.get(position).getName(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void addHomeDataList(List<ResponseModel.Response.Item> homeItemList, boolean isMoreDataAvailable) {
        itemList.addAll(homeItemList);
        this.isMoreDataAvailable = isMoreDataAvailable;
        notifyDataSetChanged();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position < itemList.size() ? CONTENT_TYPE_ITEM : TYPE_LOAD_MORE;
    }

    @Override
    public int getItemCount() {
        if (itemList == null) {
            return NO_DATA;
        }
        return itemList.size() + EXTRA_BOTTOM_ITEMS;
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder {
        @BindView(R.id.rlItemMain) RelativeLayout rlItemMain;
        @BindView(R.id.lblTitle) TextView lblTitle;
        @BindView(R.id.imgIcon) ImageView imgIcon;

        private ViewHolderItem(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

        private void setData(final ResponseModel.Response.Item item) {
            lblTitle.setText(item.getName());

            if (!item.getIcon().equals("")) {
                Picasso.with(CouponApplication.getInstance())
                        .load(item.getIcon())
                        .into(imgIcon);
            }
        }
    }

    private static class LoadHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBarBottom;

        private LoadHolder(View itemView) {
            super(itemView);
            progressBarBottom = (ProgressBar) itemView.findViewById(R.id.progress_bar_bottom);
        }

    }

    public void isMoreDataAvailable() {

    }

}
