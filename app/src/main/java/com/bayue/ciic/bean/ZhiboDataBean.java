package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 */

public class ZhiboDataBean {


    /**
     * data : {"label":[{"id":"7","name":"日常休闲","type":"3"}],"activity":[{"activity_id":"3","title":"这是活动吧"}],"company":[{"id":"1","username":"装修公司23551"}],"uids":[{"id":"1","username":"装修公司23551"}]}
     * code : 200
     * count : 0
     */
    private String msg;
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
        private List<LabelBean> label;
        private List<ActivityBean> activity;
        private List<CompanyBean> company;
        private List<UidsBean> uids;

        public List<LabelBean> getLabel() {
            return label;
        }

        public void setLabel(List<LabelBean> label) {
            this.label = label;
        }

        public List<ActivityBean> getActivity() {
            return activity;
        }

        public void setActivity(List<ActivityBean> activity) {
            this.activity = activity;
        }

        public List<CompanyBean> getCompany() {
            return company;
        }

        public void setCompany(List<CompanyBean> company) {
            this.company = company;
        }

        public List<UidsBean> getUids() {
            return uids;
        }

        public void setUids(List<UidsBean> uids) {
            this.uids = uids;
        }

        public static class LabelBean {
            /**
             * id : 7
             * name : 日常休闲
             * type : 3
             */

            private String id;
            private String name;
            private String type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class ActivityBean {
            /**
             * activity_id : 3
             * title : 这是活动吧
             */

            private String activity_id;
            private String title;

            public String getActivity_id() {
                return activity_id;
            }

            public void setActivity_id(String activity_id) {
                this.activity_id = activity_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class CompanyBean {
            /**
             * id : 1
             * username : 装修公司23551
             */

            private String id;
            private String username;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }

        public static class UidsBean {
            /**
             * id : 1
             * username : 装修公司23551
             */

            private String id;
            private String username;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
