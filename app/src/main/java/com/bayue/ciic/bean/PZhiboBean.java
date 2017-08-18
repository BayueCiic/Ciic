package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class PZhiboBean {


    /**
     * data : [{"room_id":"10512914","cid":"0efb6f57c77749f0921057203de93906","video_img":"http://zz.bayuenet.com/Uploads/direct/2017-08-09/598ab84a1bc43.png","room_name":"s20170809151717_TENslyJqsgEmgEOejz","author":"1","click_count":"","push_url":"rtmp://p128ad5a5.live.126.net/live/0efb6f57c77749f0921057203de93906?wsSecret=34d5787624137fc943e205834ad0bc8b&wsTime=1502263036","pull_url":"http://flv128ad5a5.live.126.net/live/0efb6f57c77749f0921057203de93906.flv?netease=flv128ad5a5.live.126.net","h5_pull_url":"http://pullhls128ad5a5.live.126.net/live/0efb6f57c77749f0921057203de93906/playlist.m3u8","add_time":"1502263037","status":"1","enterprise_id":"3","label":"","show_id":",,1","title":"你是不是傻","direct":"","announcement":"","activity_id":"","is_show":"1","author_name":"陆文龙","enterprise_short_name":""},{"room_id":"10503748","cid":"dc267abb8a7b49cfb697baecf324fc44","video_img":"","room_name":"s20170808182543_rHnuxEdpgWDLgSfEBE","author":"12","click_count":"","push_url":"rtmp://p128ad5a5.live.126.net/live/dc267abb8a7b49cfb697baecf324fc44?wsSecret=cecca90e11a1dcfcc5abcfd95ab4b459&wsTime=1502187942","pull_url":"http://flv128ad5a5.live.126.net/live/dc267abb8a7b49cfb697baecf324fc44.flv?netease=flv128ad5a5.live.126.net","h5_pull_url":"http://pullhls128ad5a5.live.126.net/live/dc267abb8a7b49cfb697baecf324fc44/playlist.m3u8","add_time":"1502187943","status":"1","enterprise_id":"","label":"5","show_id":",0,12","title":"Earls direct","direct":"","announcement":"","activity_id":"","is_show":"1","author_name":"11231","enterprise_short_name":""},{"room_id":"10488719","cid":"e530da98e93d43fe894f6a4d55bbe2f5","video_img":"http://zz.bayuenet.com/Uploads/direct/2017-08-09/598ad66b76326.png","room_name":"s20170808093018_qSzwdIfpGEtGJhZBJs","author":"14","click_count":"","push_url":"rtmp://p128ad5a5.live.126.net/live/e530da98e93d43fe894f6a4d55bbe2f5?wsSecret=d854a39c516786e88d7575bc104b3996&wsTime=1502155818","pull_url":"http://flv128ad5a5.live.126.net/live/e530da98e93d43fe894f6a4d55bbe2f5.flv?netease=flv128ad5a5.live.126.net","h5_pull_url":"http://pullhls128ad5a5.live.126.net/live/e530da98e93d43fe894f6a4d55bbe2f5/playlist.m3u8","add_time":"1502155818","status":"1","enterprise_id":"14","label":"4","show_id":",,14","title":"第一个隆冬强","direct":"","announcement":"","activity_id":"","is_show":"1","author_name":"亚洲龙","enterprise_short_name":"亚洲龙"},{"room_id":"10472711","cid":"9b6a96052aef4524b8ced7df31a3358d","video_img":"http://zz.bayuenet.com/Uploads/direct/2017-08-09/598ad735151ef.png","room_name":"s20170807161300_BfiKhgTSxhPeVwlIPz","author":"13","click_count":"","push_url":"rtmp://p128ad5a5.live.126.net/live/9b6a96052aef4524b8ced7df31a3358d?wsSecret=e9b23c736a31da2889a6f722900f7729&amp;wsTime=1502093580","pull_url":"http://flv128ad5a5.live.126.net/live/9b6a96052aef4524b8ced7df31a3358d.flv?netease=flv128ad5a5.live.126.net","h5_pull_url":"http://pullhls128ad5a5.live.126.net/live/9b6a96052aef4524b8ced7df31a3358d/playlist.m3u8","add_time":"1502093643","status":"1","enterprise_id":"13","label":"4","show_id":",0,13","title":"魔攻","direct":"","announcement":"","activity_id":"","is_show":"1","author_name":"捌跃科技有限公司","enterprise_short_name":"捌跃"}]
     * code : 200
     * count : 4
     */
    private String msg;
    private int code;
    private String count;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * room_id : 10512914
         * cid : 0efb6f57c77749f0921057203de93906
         * video_img : http://zz.bayuenet.com/Uploads/direct/2017-08-09/598ab84a1bc43.png
         * room_name : s20170809151717_TENslyJqsgEmgEOejz
         * author : 1
         * click_count :
         * push_url : rtmp://p128ad5a5.live.126.net/live/0efb6f57c77749f0921057203de93906?wsSecret=34d5787624137fc943e205834ad0bc8b&wsTime=1502263036
         * pull_url : http://flv128ad5a5.live.126.net/live/0efb6f57c77749f0921057203de93906.flv?netease=flv128ad5a5.live.126.net
         * h5_pull_url : http://pullhls128ad5a5.live.126.net/live/0efb6f57c77749f0921057203de93906/playlist.m3u8
         * add_time : 1502263037
         * status : 1
         * enterprise_id : 3
         * label :
         * show_id : ,,1
         * title : 你是不是傻
         * direct :
         * announcement :
         * activity_id :
         * is_show : 1
         * author_name : 陆文龙
         * enterprise_short_name :
         */

        private String room_id;
        private String cid;
        private String video_img;
        private String room_name;
        private String author;
        private String click_count;
        private String push_url;
        private String pull_url;
        private String h5_pull_url;
        private String add_time;
        private String status;
        private String enterprise_id;
        private String label;
        private String show_id;
        private String title;
        private String direct;
        private String announcement;
        private String activity_id;
        private String is_show;
        private String author_name;
        private String enterprise_short_name;

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getVideo_img() {
            return video_img;
        }

        public void setVideo_img(String video_img) {
            this.video_img = video_img;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getClick_count() {
            return click_count;
        }

        public void setClick_count(String click_count) {
            this.click_count = click_count;
        }

        public String getPush_url() {
            return push_url;
        }

        public void setPush_url(String push_url) {
            this.push_url = push_url;
        }

        public String getPull_url() {
            return pull_url;
        }

        public void setPull_url(String pull_url) {
            this.pull_url = pull_url;
        }

        public String getH5_pull_url() {
            return h5_pull_url;
        }

        public void setH5_pull_url(String h5_pull_url) {
            this.h5_pull_url = h5_pull_url;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(String enterprise_id) {
            this.enterprise_id = enterprise_id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getShow_id() {
            return show_id;
        }

        public void setShow_id(String show_id) {
            this.show_id = show_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDirect() {
            return direct;
        }

        public void setDirect(String direct) {
            this.direct = direct;
        }

        public String getAnnouncement() {
            return announcement;
        }

        public void setAnnouncement(String announcement) {
            this.announcement = announcement;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getEnterprise_short_name() {
            return enterprise_short_name;
        }

        public void setEnterprise_short_name(String enterprise_short_name) {
            this.enterprise_short_name = enterprise_short_name;
        }
    }
}
