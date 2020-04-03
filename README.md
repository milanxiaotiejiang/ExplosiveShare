## 分享一款android与unity交互的demo

#### 开发工具
android studio、Unity（建议Unity Hub）、Visual Studio 2017


unity
```
    AndroidJavaObject currentActivity;
    AndroidJavaClass unityPlayer;

    void Start()
    {
        unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
    }
    
```
unity中调用Android系统方法
```
        string msg = “xxx”;
        AndroidJavaClass toastClass = new AndroidJavaClass("android.widget.Toast");
        int arg3 = toastClass.GetStatic<int>("LENGTH_LONG");
        AndroidJavaObject toastObject = toastClass.CallStatic<AndroidJavaObject>("makeText", currentActivity, msg, arg3);
        toastObject.Call("show");
```

调用的方法运行在android主线程
```
        currentActivity.Call("runOnUiThread", new AndroidJavaRunnable(() =>
        {
            // 运行在Android主线程方法
        }));
```

unity调用android中普通方法
```
unity
    public void FuncNormal()
    {
        currentActivity.Call("showToast");
    }

android
    public void showToast() {
        Toast.makeText(this, "Unity掉用Android中的自定义方法", Toast.LENGTH_LONG).show();
    }	
```

unity调用android中静态方法
```
unity
    public void FuncStatic()
    {
        AndroidJavaClass androidJavaClass = new AndroidJavaClass("com.seabreezerobot.explosive.MainActivity");
        infoText.text = androidJavaClass.CallStatic<String>("linkWorld", "Unity调用Android插件静态方法");
    }
    
android
    public static String linkWorld(String str) {
        clickCount++;
        return "hello, 我是Android中静态方法\n" + str + "\n 您点击 " + clickCount + " 下";
    }
```

android调用unity的方法
```
android
        UnityPlayer.UnitySendMessage("Canvas", "AndroidFuncTest", "");

unity
        Image image = infoText.GetComponentInParent<Image>();
        image.color = UnityEngine.Random.ColorHSV();
```

普通扩展类测试
```
unity
        AndroidJavaObject androidJavaClass = new AndroidJavaObject("com.seabreezerobot.explosive.MyTest");
        infoText.text = androidJavaClass.Call<String>("linkWorld", "Unity调用Android中其他类的普通方法");
android

public class MyTest {
    private static long clickCount = 0;

    public String linkWorld(String str) {
        clickCount++;
        return "hello, 我是Android中其他类的方法\n" + str + "\n 您点击 " + clickCount + " 下";
    }
}
```