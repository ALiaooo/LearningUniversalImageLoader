package com.aliao.learninguil;

/**
 * Created by 丽双 on 2015/7/9.
 */
public class Constants {

    public static final String[] IMAGES = new String[] {
            "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383264_3954.jpg"/*,
            "http://img.my.csdn.net/uploads/201407/26/1406383264_4787.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383264_8243.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383248_3693.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383243_5120.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383242_3127.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383242_9576.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383242_1721.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383219_5806.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383214_7794.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383213_4418.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383213_3557.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383210_8779.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383172_4577.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383166_3407.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383166_2224.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383166_7301.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383165_7197.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383150_8410.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383131_3736.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383130_5094.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383130_7393.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383129_8813.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383100_3554.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383093_7894.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383092_2432.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383092_3071.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383091_3119.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383059_6589.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383059_8814.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383059_2237.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383058_4330.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383038_3602.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382942_3079.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382942_8125.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382942_4881.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382941_4559.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382941_3845.jpg"
            "http://img.my.csdn.net/uploads/201407/26/1406382924_8955.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382923_2141.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382923_8437.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382922_6166.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382922_4843.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382905_5804.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382904_3362.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382904_2312.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382904_4960.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382900_2418.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382881_4490.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382881_5935.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382880_3865.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382880_4662.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382879_2553.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382862_5375.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382862_1748.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382861_7618.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382861_8606.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382861_8949.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382841_9821.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382840_6603.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382840_2405.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382840_6354.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382839_5779.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382810_7578.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382810_2436.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382809_3883.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382809_6269.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382808_4179.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382790_8326.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382789_7174.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382789_5170.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382789_4118.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382788_9532.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382767_3184.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382767_4772.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382766_4924.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382766_5762.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382765_7341.jpg",

            // 大图
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155156565264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155157815264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155159018264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155200315264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155201409264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155202393264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155203440264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155204518264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155205971264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155207628264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155208737264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155454628264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101104175135957123.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155453284264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155451565264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155449987264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155448174264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155445596264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155443628264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155442503264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155439190264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155437956264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155436721264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155434143264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155432393264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155431112264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155429003264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155427643264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155425831264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155424721264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155423549264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155420518264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155415096264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155410987264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155406753264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155404628264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155403393264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155402003264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155358643264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155357346264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155355534264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155354112264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155351690264.jpg",
            "http://car0.autoimg.cn/upload/spec/5742/w_20101014155350456264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105110950264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105112622264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105114107264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105109200264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105107357264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105105607264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105103903264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105101372264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105059622264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105056685264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105054919264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105052716264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105051060264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105049169264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105046919264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105043435264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105041747264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024105038403264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092500482264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092458950264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092457513264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092455966264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092454669264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092453075264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092451763264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092450450264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092448872264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092447388264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092445513264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092443919264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092442216264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092440591264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092438872264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092437200264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092435528264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092433810264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092432122264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092430294264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092428607264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092426857264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092425075264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092423216264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092421388264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092419638264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092417935264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092415794264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092414075264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092412372264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092410341264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092408591264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092406888264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092404763264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092402982264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092401200264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092359357264.jpg",
            "http://car0.autoimg.cn/upload/spec/10691/w_20111024092356966264.jpg",*/
    };


}
