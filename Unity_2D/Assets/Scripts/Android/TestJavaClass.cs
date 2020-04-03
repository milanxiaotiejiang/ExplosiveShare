using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
public class TestJavaClass : MonoBehaviour
{
    public static Text infoText;

    AndroidJavaObject currentActivity;
    AndroidJavaClass unityPlayer;
    // Use this for initialization
    void Start()
    {
        infoText = GameObject.Find("Canvas/Panel/Text").GetComponent<Text>();
        //获取UnityPlayer 
        unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        //获取当前激活的Activity
        currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");

    }

    // Update is called once per frame
    void Update()
    {

    }

    public void TestToast()
    {
        BtnClick("Unity调用Android中Toast");
    }

    public void TesRunnablet()
    {
        currentActivity.Call("runOnUiThread", new AndroidJavaRunnable(() =>
        {
            BtnClick("运行在Android主线程");
        }));
    }

    public void BtnClick(string str)
    {
        //创建Java类的映射 带着包名和类名
        AndroidJavaClass toastClass = new AndroidJavaClass("android.widget.Toast");

        string msg = str;
        //获取静态字段，泛型是字段的类型,参数是字段的名字
        int arg3 = toastClass.GetStatic<int>("LENGTH_LONG");


        //所有的java对象在Unity端都可以用AndroidJavaObject来接受
        //CallStatic 调用静态方法，泛型是返回值的类型，
        //参数1：方法名字
        //参数2：方法的参数
        AndroidJavaObject toastObject = toastClass.CallStatic<AndroidJavaObject>("makeText", currentActivity, msg, arg3);
        //调用java对象的普通方法
        toastObject.Call("show");
    }

    public void TestAndroidProxy()
    {
        AndroidJavaObject dateDialog = new AndroidJavaObject("android.app.DatePickerDialog", currentActivity,
            new DateSetListener(), DateTime.Now.Year, DateTime.Now.Month - 1, DateTime.Now.Day
            );
        dateDialog.Call("show");
    }

    //调用android插件普通方法
    public void FuncNormal()
    {
        currentActivity.Call("showToast");
    }

    //调用android插件静态方法
    public void FuncStatic()
    {
        AndroidJavaClass androidJavaClass = new AndroidJavaClass("com.seabreezerobot.explosive.MainActivity");
        infoText.text = androidJavaClass.CallStatic<String>("linkWorld", "Unity调用Android插件静态方法");
    }

    public void TestAndroidBtnClick()
    {
        //先调用Android中changeBg方法
        //在Android得changeBg方法中调用Unity的AndroidFuncTest方法
        currentActivity.Call("changeBg");
    }

    //Android端调用的方法
    public void AndroidFuncTest()
    {
        Image image = infoText.GetComponentInParent<Image>();
        image.color = UnityEngine.Random.ColorHSV();
    }

    public void TestNormalClass()
    {
        AndroidJavaObject androidJavaClass = new AndroidJavaObject("com.seabreezerobot.explosive.MyTest");
        infoText.text = androidJavaClass.Call<String>("linkWorld", "Unity调用Android中其他类的普通方法");
    }

    //实现Android里面的接口
    //1.C# 要有一个内部类,继承AndroidJavaProxy 来模式Android的接口
    //2.在该类的构造方法，调用父类的构造，传入所对应的接口的包名+类名+接口名  接口前面用$连接
    //3.实现该接口的方法
    class DateSetListener : AndroidJavaProxy
    {
        public DateSetListener() : base("android.app.DatePickerDialog$OnDateSetListener")
        {

        }
        void onDateSet(AndroidJavaObject view, int year, int month, int dayOfMonth)
        {
            TestJavaClass.infoText.text = year + "/" + (month - 1) + "/" + dayOfMonth;
        }
    }
}
