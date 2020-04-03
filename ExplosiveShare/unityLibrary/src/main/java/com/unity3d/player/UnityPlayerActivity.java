// GENERATED BY UNITY. REMOVE THIS COMMENT TO PREVENT OVERWRITING WHEN EXPORTING AGAIN
package com.unity3d.player;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.FrameLayout;

public abstract class UnityPlayerActivity extends Activity implements IUnityPlayerLifecycleEvents {
    protected FrameLayout mFrameLayout;
    protected UnityPlayer mUnityPlayer; // 不要更改此变量的名称；从本机代码引用

    // Override this in your custom UnityPlayerActivity to tweak the command line arguments passed to the Unity Android Player
    // The command line arguments are passed as a string, separated by spaces
    // UnityPlayerActivity calls this from 'onCreate'
    // Supported: -force-gles20, -force-gles30, -force-gles31, -force-gles31aep, -force-gles32, -force-gles, -force-vulkan
    // See https://docs.unity3d.com/Manual/CommandLineArguments.html
    // @param cmdLine the current command line arguments, may be null
    // @return the modified command line string or null
    protected String updateUnityCommandLineArguments(String cmdLine) {
        return cmdLine;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

//        String cmdLine = updateUnityCommandLineArguments(getIntent().getStringExtra("unity"));
//        getIntent().putExtra("unity", cmdLine);

        setContentView(R.layout.activity_unity_player);
        mFrameLayout = findViewById(R.id.unity_layout);

        mUnityPlayer = new UnityPlayer(this, this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(1080, 1920);
        layoutParams.gravity = Gravity.BOTTOM;
        mUnityPlayer.setLayoutParams(layoutParams);
        mFrameLayout.addView(mUnityPlayer);
//        mUnityPlayer.requestFocus();
    }

    // 当Unity玩家卸载移动任务到后台时
    @Override
    public void onUnityPlayerUnloaded() {
//        moveTaskToBack(true);
    }

    // 当Unity玩家退出杀戮过程时
    @Override
    public void onUnityPlayerQuitted() {
//        Process.killProcess(Process.myPid());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 为了支持深度链接，我们需要确保客户端能够访问上次发送的意图。
        // 客户机通过一个JNI api访问它，该api允许客户机在启动时获取意图集。
        // 要在启动后更新它，我们必须手动将意图替换为此处捕获的意图。
        setIntent(intent);
        mUnityPlayer.newIntent(intent);
    }

    // Quit Unity
    @Override
    protected void onDestroy() {
        mUnityPlayer.destroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUnityPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUnityPlayer.resume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL) {
            mUnityPlayer.lowMemory();
        }
    }

    // 这样可以确保布局正确。
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // 将焦点变化通知Unity。
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // 由于某些原因，ndk不支持multiple keyevent类型。
    // 通过重写dispatchKeyEvent（）强制事件注入。
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // 将未由（未聚焦）视图处理的任何事件直接传递到UnityPlayer
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }
}
