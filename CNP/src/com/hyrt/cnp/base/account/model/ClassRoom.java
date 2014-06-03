package com.hyrt.cnp.base.account.model;


import com.hyrt.cnp.base.account.utils.FaceUtils;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GYH on 14-1-4.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassRoom implements Serializable {
    private int classroomID;
    private String roomname;
    private String grade;
    private String renname;
    private int user_id;
    private String nursery_id;
    private int kind;
    private ArrayList<String> original;
    private String rennames;
    private int renname_id;
    private String graduation;
    private String signature;

    public String getImagepath(){
        return FaceUtils.getClassRoomImage(classroomID,FaceUtils.FACE_BIG);
    }

    public static class Model extends Base{
        private static final long serialVersionUID = -1;

        private ArrayList<ClassRoom> data;

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

        public ArrayList<ClassRoom> getData() {
            return data;
        }

        public void setData(ArrayList<ClassRoom> data) {
            this.data = data;
        }
    }


    public static class Model2 extends Base{
        private static final long serialVersionUID = -1;

        private ClassRoom data;

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

        public ClassRoom getData() {
            return data;
        }

        public void setData(ClassRoom data) {
            this.data = data;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassRoom classRoom = (ClassRoom) o;

        if (classroomID != classRoom.classroomID) return false;
        if (kind != classRoom.kind) return false;
        if (renname_id != classRoom.renname_id) return false;
        if (user_id != classRoom.user_id) return false;
        if (grade != null ? !grade.equals(classRoom.grade) : classRoom.grade != null) return false;
        if (graduation != null ? !graduation.equals(classRoom.graduation) : classRoom.graduation != null)
            return false;
        if (nursery_id != null ? !nursery_id.equals(classRoom.nursery_id) : classRoom.nursery_id != null)
            return false;
        if (original != null ? !original.equals(classRoom.original) : classRoom.original != null)
            return false;
        if (renname != null ? !renname.equals(classRoom.renname) : classRoom.renname != null)
            return false;
        if (rennames != null ? !rennames.equals(classRoom.rennames) : classRoom.rennames != null)
            return false;
        if (roomname != null ? !roomname.equals(classRoom.roomname) : classRoom.roomname != null)
            return false;
        if (signature != null ? !signature.equals(classRoom.signature) : classRoom.signature != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = classroomID;
        result = 31 * result + (roomname != null ? roomname.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (renname != null ? renname.hashCode() : 0);
        result = 31 * result + user_id;
        result = 31 * result + (nursery_id != null ? nursery_id.hashCode() : 0);
        result = 31 * result + kind;
        result = 31 * result + (original != null ? original.hashCode() : 0);
        result = 31 * result + (rennames != null ? rennames.hashCode() : 0);
        result = 31 * result + renname_id;
        result = 31 * result + (graduation != null ? graduation.hashCode() : 0);
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        return result;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getClassroomID() {
        return classroomID;
    }

    public void setClassroomID(int classroomID) {
        this.classroomID = classroomID;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public String getNursery_id() {
        return nursery_id;
    }

    public void setNursery_id(String nursery_id) {
        this.nursery_id = nursery_id;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public ArrayList<String> getOriginal() {
        return original;
    }

    public void setOriginal(ArrayList<String> original) {
        this.original = original;
    }

    public String getRennames() {
        return rennames;
    }

    public void setRennames(String rennames) {
        this.rennames = rennames;
    }

    public int getRenname_id() {
        return renname_id;
    }

    public void setRenname_id(int renname_id) {
        this.renname_id = renname_id;
    }

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }
}
