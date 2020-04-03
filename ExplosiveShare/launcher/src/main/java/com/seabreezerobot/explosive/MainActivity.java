package com.seabreezerobot.explosive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public class MainActivity extends UnityPlayerActivity {

    private static long clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflate = LayoutInflater.from(this);
        View view = inflate.inflate(R.layout.activity_main, null);
        Button btn1 = view.findViewById(R.id.btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBg();
            }
        });

        mFrameLayout.addView(view);

    }

    //*********************************************Unity方法*************************************************
    public void showToast() {
        Toast.makeText(this, "Unity掉用Android中的自定义方法", Toast.LENGTH_LONG).show();
    }

    public void changeBg() {
        //Android调用Unity中的方法，参数1：这个组件挂在哪个游戏物体上，参数2：方法名字，参数3：方法的参数
        UnityPlayer.UnitySendMessage("Canvas", "AndroidFuncTest", "");
    }

    public static String linkWorld(String str) {
        clickCount++;
        return "hello, 我是Android中静态方法\n" + str + "\n 您点击 " + clickCount + " 下";
    }

}
