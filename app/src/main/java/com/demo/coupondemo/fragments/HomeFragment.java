package com.demo.coupondemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.coupondemo.CouponApplication;
import com.demo.coupondemo.R;
import com.demo.coupondemo.adapters.HomeRvAdapter;
import com.demo.coupondemo.interfaces.OnLoadMoreListener;
import com.demo.coupondemo.interfaces.WebApiInterface;
import com.demo.coupondemo.models.ResponseModel;
import com.demo.coupondemo.utils.NetworkUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    @BindView(R.id.rvMain) RecyclerView rvMain;
    private HomeRvAdapter homeRvAdapter;
    private int index = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (homeRvAdapter == null) {
            homeRvAdapter = new HomeRvAdapter(this);
        }

        homeRvAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (NetworkUtil.isNetworkAvailable(getActivity())) {
                    index = index + 1;
                    loadData("" + index);
                }else{
                    Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rvMain.setAdapter(homeRvAdapter);

        if (NetworkUtil.isNetworkAvailable(getActivity())) {
            loadData("" + index);
        }else{
            Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData(String pageNo) {
        WebApiInterface apiService = CouponApplication.getRetrofitInstance().create(WebApiInterface.class);
        Call<ResponseModel> call = apiService.getData("" + pageNo);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, final Response<ResponseModel> response) {
                List<ResponseModel.Response.Item> itemList = response.body().getResponse().getItemList();
                if (itemList != null) {
                    if (itemList.size() > 0) {
                        homeRvAdapter.addHomeDataList(itemList, true);
                    } else {
                        homeRvAdapter.addHomeDataList(itemList, false);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
            }
        });
    }

    public int getIndex() {
        return index;
    }
}
