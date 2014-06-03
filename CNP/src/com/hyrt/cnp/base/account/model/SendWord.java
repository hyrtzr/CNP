package com.hyrt.cnp.base.account.model;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by GYH on 14-1-4.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendWord {

    private int nursery_id;
    private String nName;
    private String message;
    private String leadId;
    private String email;
    private String renname;
    private int user_id;
    private String political;
    private String career;
    private String intro;
    private String skills;
    private String specialty;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SendWord sendWord = (SendWord) o;

        if (nursery_id != sendWord.nursery_id) return false;
        if (user_id != sendWord.user_id) return false;
        if (career != null ? !career.equals(sendWord.career) : sendWord.career != null)
            return false;
        if (email != null ? !email.equals(sendWord.email) : sendWord.email != null) return false;
        if (intro != null ? !intro.equals(sendWord.intro) : sendWord.intro != null) return false;
        if (leadId != null ? !leadId.equals(sendWord.leadId) : sendWord.leadId != null)
            return false;
        if (message != null ? !message.equals(sendWord.message) : sendWord.message != null)
            return false;
        if (nName != null ? !nName.equals(sendWord.nName) : sendWord.nName != null) return false;
        if (political != null ? !political.equals(sendWord.political) : sendWord.political != null)
            return false;
        if (renname != null ? !renname.equals(sendWord.renname) : sendWord.renname != null)
            return false;
        if (skills != null ? !skills.equals(sendWord.skills) : sendWord.skills != null)
            return false;
        if (specialty != null ? !specialty.equals(sendWord.specialty) : sendWord.specialty != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nursery_id;
        result = 31 * result + (nName != null ? nName.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (leadId != null ? leadId.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (renname != null ? renname.hashCode() : 0);
        result = 31 * result + user_id;
        result = 31 * result + (political != null ? political.hashCode() : 0);
        result = 31 * result + (career != null ? career.hashCode() : 0);
        result = 31 * result + (intro != null ? intro.hashCode() : 0);
        result = 31 * result + (skills != null ? skills.hashCode() : 0);
        result = 31 * result + (specialty != null ? specialty.hashCode() : 0);
        return result;
    }

    public int getNursery_id() {
        return nursery_id;
    }

    public void setNursery_id(int nursery_id) {
        this.nursery_id = nursery_id;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRenname() {
        return renname;
    }

    public void setRenname(String renname) {
        this.renname = renname;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public static class Model extends Base{
        private static final long serialVersionUID = -1;
        private SendWord data;

        public SendWord getData() {
            return data;
        }

        public void setData(SendWord data) {
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            Model model = (Model) o;

            if (data != null ? !data.equals(model.data) : model.data != null) return
                    false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (data != null ? data.hashCode() : 0);
            return result;
        }
    }
}
