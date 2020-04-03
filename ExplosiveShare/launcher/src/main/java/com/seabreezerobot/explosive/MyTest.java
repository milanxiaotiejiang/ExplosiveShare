package com.seabreezerobot.explosive;

/**
 * UserConfig: milan
 * Time: 2020/2/12 13:52
 * Des:
 */
public class MyTest {
    private static long clickCount = 0;

    public String linkWorld(String str) {
        clickCount++;
        return "hello, 我是Android中其他类的方法\n" + str + "\n 您点击 " + clickCount + " 下";
    }
}
