package com.example.john.weinong;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.john.weinong.dbService.MyUser;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;
public class Fragment4 extends android.support.v4.app.Fragment implements OnClickListener {
    private View mView;
    private CircleImageView cImageView;
    private RelativeLayout pLayout;
    private RelativeLayout dLayout;
    private RelativeLayout sLayout;
    private RelativeLayout aLayout;
    private TextView textview_mine_nickName;
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


            mView = inflater.inflate(R.layout.fragment_mine, null, true);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
    }

    public void findViews() {
        cImageView = (CircleImageView) mView
                .findViewById(R.id.circleimageview_mine_photo);

        pLayout = (RelativeLayout) mView
                .findViewById(R.id.layout_mine_personal);
        pLayout.setOnClickListener(this);
        dLayout = (RelativeLayout) mView
                .findViewById(R.id.layout_mine_guanyuweinong);
        dLayout.setOnClickListener(this);
        sLayout = (RelativeLayout) mView.findViewById(R.id.layout_mine_call);
        sLayout.setOnClickListener(this);
        aLayout = (RelativeLayout) mView
                .findViewById(R.id.layout_mine_shezhi);
        aLayout.setOnClickListener(this);
        textview_mine_nickName=(TextView)mView.findViewById(R.id.textview_mine_nickName);
        if(Fragment1.user != null){
            // 允许用户使用应用
          textview_mine_nickName.setText(Fragment1.user.getUsername());
        }else{
            textview_mine_nickName.setText("点击登录");
            //缓存用户对象为空时， 可打开用户注册界面…
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.layout_mine_personal:
                if(Fragment1.user != null){
                    // 允许用户使用应用
                }else{
                    Intent intent2=new Intent();
                    intent2.setClass(getActivity(),DengluActivity.class);
                    startActivity(intent2);
                    //缓存用户对象为空时， 可打开用户注册界面…
                }


                break;
            case R.id.layout_mine_call:

                Intent sIntent = new Intent(Intent.ACTION_DIAL);
                sIntent.setData(Uri.parse("tel:15267540795"));
                startActivity(sIntent);
                break;

            case R.id.layout_mine_shezhi:
                Intent intent1=new Intent();
                intent1.setClass(getActivity(),ShezhiActivity.class);
                startActivity(intent1);
                break;
            case R.id.layout_mine_guanyuweinong:
                Intent intent=new Intent();
                intent.setClass(getActivity(),GuanyuweinongActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        Fragment1.user=BmobUser.getCurrentUser(MyUser.class);
        if(Fragment1.user != null){
            // 允许用户使用应用
            textview_mine_nickName.setText(Fragment1.user.getUsername());
            if(Fragment1.user.getPhoto()!=null) {
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.demon1).error(R.drawable.demon1);
                Glide.with(this)
                        .load(Fragment1.user.getPhoto().getFileUrl())
                        .apply(options)
                        .into(cImageView);


            }else {
                cImageView.setImageResource(R.drawable.demon1);
            }
        }else{
            textview_mine_nickName.setText("点击登录");
            cImageView.setImageResource(R.drawable.demon1);

        }
    }
}
