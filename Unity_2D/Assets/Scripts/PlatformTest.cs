using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class PlatformTest : MonoBehaviour
{
    public Text text;
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {

        /*
        //宏命令  预编译的
        //平台的判定  跟Build Setting里面的平台相关
        //if (true) {
        //    print("hello");
        //}
#if UNITY_ANDROID
        text.text = "Android";
#elif UNITY_IOS
        text.text = "IOS";
#else
        text.text = "Others";
#endif
*/
        //平台判定
        /*if (Application.platform == RuntimePlatform.Android)
        {
            text.text = "Android";
        }
        else if (Application.platform == RuntimePlatform.IPhonePlayer)
        {
            text.text = "IOS";
        }
        else {
            text.text = "Windows Editor";
        }

        print("是否是移动平台： "+Application.isMobilePlatform);
        print("是否是编辑器状态： " + Application.isEditor);
        */
        string content = "";
        content += "屏幕高度 ： " + Screen.height + "\n";
        content += "屏幕宽度 ： " + Screen.width + "\n";
        content += "屏幕分辨率 ： " + Screen.currentResolution + "\n";
        content += "屏幕DPI ： " + Screen.dpi + "\n";
        content += "是否全屏 ： " + Screen.fullScreen + "\n";

        content += "旋转屏幕（可以自己设置）： " + Screen.orientation + "\n";
        content += "屏幕休眠超时（仅支持移动设备）： " + Screen.sleepTimeout + "\n";

        content += "=====================================\n";

        content += "是否支持触摸： " + Input.touchSupported + "\n";
        content += "是否支持多点触摸： " + Input.multiTouchEnabled + "\n";
        //压力，角度
        content += "是否支持笔触： " + Input.stylusTouchSupported + "\n";
        content += "是否支持压感： " + Input.touchPressureSupported + "\n";
        content += "是否支持鼠标： " + Input.mousePresent + "\n";
        content += "任意键按下： " + Input.anyKey + "\n";

        content += "=====================================\n";
        content += "获取电子罗盘： " + Input.compass.rawVector + "\n";
        content += "获取陀螺仪： " + Input.gyro.gravity + "\n";
        content += "获取位置服务： " + Input.location.status + "\n";
        content += "获取设备的方向： " + Input.deviceOrientation + "\n";
        content += "获取系统语言： " + Application.systemLanguage + "\n";

        text.text = content;



    }
}
