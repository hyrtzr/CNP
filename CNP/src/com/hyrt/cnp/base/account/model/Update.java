package com.hyrt.cnp.base.account.model;

/**
 * Created by Zoe on 2014-05-21.
 * 升级
 */
public class Update extends Base{
    private String version;//最新版本号
    private String download;//apk下载地址

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Update update = (Update) o;

        if (download != null ? !download.equals(update.download) : update.download != null)
            return false;
        if (version != null ? !version.equals(update.version) : update.version != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (download != null ? download.hashCode() : 0);
        return result;
    }

    public static class Model extends Base {
        private static final long serialVersionUID = -1;
        private Update data;

        public Update getData() {
            return data;
        }

        public void setData(Update data) {
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Model model = (Model) o;

            if (data != null ? !data.equals(model.data) : model.data != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return data != null ? data.hashCode() : 0;
        }
    }
}
