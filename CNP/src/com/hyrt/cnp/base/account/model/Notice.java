package com.hyrt.cnp.base.account.model;


import com.hyrt.cnp.base.account.utils.StringUtils;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GYH on 14-1-3.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notice implements Serializable {

    private int annource_id;
    private String title;
    private String content;
    private int posttime;
    private String renname;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notice notice = (Notice) o;

        if (annource_id != notice.annource_id) return false;
        if (posttime != notice.posttime) return false;
        if (content != null ? !content.equals(notice.content) : notice.content != null)
            return false;
        if (renname != null ? !renname.equals(notice.renname) : notice.renname != null)
            return false;
        if (title != null ? !title.equals(notice.title) : notice.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = annource_id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + posttime;
        result = 31 * result + (renname != null ? renname.hashCode() : 0);
        return result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public int getAnnource_id() {
        return annource_id;
    }

    public void setAnnource_id(int annource_id) {
        this.annource_id = annource_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContetn() {
        return content;
    }

    public void setContetn(String content) {
        this.content = content;
    }

    public String getPosttime2(){
        try {
            return StringUtils.millTimeToNormalTime2(posttime+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getPosttime() {
        return posttime;
    }

    public void setPosttime(int posttime) {
        this.posttime = posttime;
    }

    public String getRenname() {
        return renname;
    }

    public void setRenname(String renname) {
        this.renname = renname;
    }

    public static class Model2 extends Base{
        private static final long serialVersionUID = -1;
        private Notice data;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            Model2 model2 = (Model2) o;

            if (data != null ? !data.equals(model2.data) : model2.data != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (data != null ? data.hashCode() : 0);
            return result;
        }

        public Notice getData() {
            return data;
        }

        public void setData(Notice data) {
            this.data = data;
        }
    }

    public static class Model extends Base{
        private static final long serialVersionUID = -1;
        private ArrayList<Notice> data;
        private String more;

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public ArrayList<Notice> getData() {
            return data;
        }

        public void setData(ArrayList<Notice> data) {
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            Model model = (Model) o;

            if (data != null ? !data.equals(model.data) : model.data != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (data != null ? data.hashCode() : 0);
            return result;
        }
    }

    public static class Model3 extends Base{
        private static final long serialVersionUID = -1;
        private String data;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            Model3 model3 = (Model3) o;

            if (data != null ? !data.equals(model3.data) : model3.data != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (data != null ? data.hashCode() : 0);
            return result;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
