package com.example.root.libapp_v1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.ListView.FreshListView;
import com.example.root.libapp_v1.ListView.ApkEntry;
import com.example.root.libapp_v1.ListView.MyAdapter;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.ListView.FreshListView.IReflashListener;

/**
 * author : Yixin Luo
 * Date : 2015-3-26.
 * usage : 实现社交圈模块的展示实现社交圈模块的展示
 */
public class SecondFragment extends FatherFragment implements IReflashListener {
   // public String initContent() {
    //    return "second!";
    //}
    private ArrayList<ApkEntry> apk_list; //将要展示的信息集合队列
    private MyAdapter adapter;  //新建一个适配器接口
    private FreshListView listview; //新建一个支持下拉刷新的listview
    private HeadBar headBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   {
       View view = inflater.inflate(R.layout.fragment2, null);
       listview = (FreshListView) view.findViewById(R.id.listview);
       headBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
       headBar.setTitleText("书友会");
       setData();
       showList(apk_list);
       return view;
   }
 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setData();
        showList(apk_list);
    }*/

    private void showList(ArrayList<ApkEntry> apk_list) {
        if (adapter == null) {
            listview.setInterface(this);
            adapter = new MyAdapter(this.getActivity(), apk_list);
            listview.setAdapter(adapter);
        } else {
            adapter.onDateChange(apk_list);
        }
    }



    private void setData() {
        apk_list = new ArrayList<ApkEntry>();
        for (int i = 0; i < 10; i++) {
            ApkEntry entity = new ApkEntry();
            entity.setName("蝙蝠侠");
            entity.setDes("《正义曙光》将加快了与其他DC角色共同的宇宙。2014年4月，证实了继《正义曙光》之后的下一个项目就是《正义联盟》电影.");
            entity.setInfo("布鲁斯维恩");
            apk_list.add(entity);
        }
    }

    private void setReflashData() {
        for (int i = 0; i < 2; i++) {
            ApkEntry entity = new ApkEntry();
            entity.setName("蝙蝠侠");
            entity.setDes("《正义曙光》将加快了与其他DC角色共同的宇宙。2014年4月，证实了继《正义曙光》之后的下一个项目就是《正义联盟》电影.");
            entity.setInfo("布鲁斯维恩(已刷新)");
            apk_list.add(0,entity);
        }
    }
    private void setLoadData() {
        for (int i = 0; i < 2; i++) {
            ApkEntry entity = new ApkEntry();
            entity.setName("蝙蝠侠");
            entity.setDes("《正义曙光》将加快了与其他DC角色共同的宇宙。2014年4月，证实了继《正义曙光》之后的下一个项目就是《正义联盟》电影.");
            entity.setInfo("布鲁斯维恩(onload)");
            //apk_list.add(0,);
            apk_list.add(entity);
        }

    }
    public void onReflash() {
        // TODO Auto-generated method stub\
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取最新数据
                setReflashData();
                //通知界面显示
                showList(apk_list);
                //通知listview 刷新数据完毕；
                listview.reflashComplete();
            }
        }, 2000);

    }

    @Override
    public void onLoad() {
        //get more data, and notice ListView to refresh view
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取最新数据
                setLoadData();
                //通知界面显示
                showList(apk_list);
                //通知listview 刷新数据完毕；
                listview.loadComplete();
            }
        }, 2000);
    }
}
