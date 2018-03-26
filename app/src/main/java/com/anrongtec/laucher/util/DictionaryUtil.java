package com.anrongtec.laucher.util;

import java.util.ArrayList;

/**
 * @author huiliu
 * @date 2018/1/10
 * @email liu594545591@126.com
 * @introduce
 */
public class DictionaryUtil {

    private static final int CROWN_LEVEL = 64;
    private static final int SUN_LEVEL = 16;

    /**
     * 在线等级
     *
     * @param onlineTime
     * @return
     */
    public static ArrayList<Integer> getLevel(String onlineTime) {
        int crown = 0;
        int sun = 0;
        int moon = 0;
        int star = 0;
        ArrayList<Integer> list = new ArrayList<>();
        int time = Integer.parseInt(onlineTime)/10;
        if (time > CROWN_LEVEL) {
            //皇冠数量
            crown = time / CROWN_LEVEL;
            int crownRemain = time % CROWN_LEVEL;
            //太阳数量
            sun = crownRemain / 16;
            int sunRemain = crownRemain % 16;
            //月亮数量
            moon = sunRemain / 4;
            //星星数量
            star = sunRemain % 4;
        } else if (SUN_LEVEL < time && time < CROWN_LEVEL) {
            //太阳数量
            sun = time / 16;
            int sunRemain = time % 16;
            //月亮数量
            moon = sunRemain / 4;
            //星星数量
            star = sunRemain % 4;
        } else if (time > 0 && time < SUN_LEVEL) {
            //月亮数量
            moon = time / 4;
            //星星数量
            star = time % 4;
        }
        list.add(star);
        list.add(moon);
        list.add(sun);
        list.add(crown);
        return list;
    }


    /**
     * 勋章等级
     *
     * @param onlineTime
     * @return
     */
    public static int getHonor(String onlineTime) {
        int time = Integer.parseInt(onlineTime);
        return (time / 10) + 1;
    }
}
