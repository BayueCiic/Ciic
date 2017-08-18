package com.bayue.ciic.bean;

/**
 * Created by Administrator on 2017/8/2.
 */

public class PushBean {


    /**
     * data : {"room_id":10474812,"room_name":"s20170807174751_KjDMqzeZQUIlyTJSdI","cid":"4248c5a7763a4b078b0353110521b484","push_url":"rtmp://p128ad5a5.live.126.net/live/4248c5a7763a4b078b0353110521b484?wsSecret=33b0e153eb27881096d4281f2ae5fd34&wsTime=1502099277","h5_pull_url":"http://pullhls128ad5a5.live.126.net/live/4248c5a7763a4b078b0353110521b484/playlist.m3u8","pull_url":"http://flv128ad5a5.live.126.net/live/4248c5a7763a4b078b0353110521b484.flv?netease=flv128ad5a5.live.126.net","video_img":"","title":""}
     * code : 200
     * count : 0
     */
    private  String msg;
    private DataBean data;
    private int code;
    private String count;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public static class DataBean {
        /**
         * room_id : 10474812
         * room_name : s20170807174751_KjDMqzeZQUIlyTJSdI
         * cid : 4248c5a7763a4b078b0353110521b484
         * push_url : rtmp://p128ad5a5.live.126.net/live/4248c5a7763a4b078b0353110521b484?wsSecret=33b0e153eb27881096d4281f2ae5fd34&wsTime=1502099277
         * h5_pull_url : http://pullhls128ad5a5.live.126.net/live/4248c5a7763a4b078b0353110521b484/playlist.m3u8
         * pull_url : http://flv128ad5a5.live.126.net/live/4248c5a7763a4b078b0353110521b484.flv?netease=flv128ad5a5.live.126.net
         * video_img :
         * title :
         */

        private int room_id;
        private String room_name;
        private String cid;
        private String push_url;
        private String h5_pull_url;
        private String pull_url;
        private String video_img;
        private String title;

        public int getRoom_id() {
            return room_id;
        }

        public void setRoom_id(int room_id) {
            this.room_id = room_id;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getPush_url() {
            return push_url;
        }

        public void setPush_url(String push_url) {
            this.push_url = push_url;
        }

        public String getH5_pull_url() {
            return h5_pull_url;
        }

        public void setH5_pull_url(String h5_pull_url) {
            this.h5_pull_url = h5_pull_url;
        }

        public String getPull_url() {
            return pull_url;
        }

        public void setPull_url(String pull_url) {
            this.pull_url = pull_url;
        }

        public String getVideo_img() {
            return video_img;
        }

        public void setVideo_img(String video_img) {
            this.video_img = video_img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
