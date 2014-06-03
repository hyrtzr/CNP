package com.hyrt.cnp.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.hyrt.cnp.account.LoginActivity;
import com.hyrt.cnp.account.manager.UserInfoActivity;
import com.hyrt.cnp.account.manager.WelcomeActivity;
import com.hyrt.cnp.classroom.ui.ClassroomAlbumActivity;
import com.hyrt.cnp.classroom.ui.ClassroomBabayActivity;
import com.hyrt.cnp.classroom.ui.ClassroomIndexActivity;
import com.hyrt.cnp.classroom.ui.ClassroomRecipeInfoActivity;
import com.hyrt.cnp.classroom.ui.ClassroomphotoinfoActivity;
import com.hyrt.cnp.classroom.ui.ClassroomphotolistActivity;
import com.hyrt.cnp.dynamic.ui.AboutFriendActivity;
import com.hyrt.cnp.dynamic.ui.AddAlbumActivity;
import com.hyrt.cnp.dynamic.ui.AddPhotoDynamicActivity;
import com.hyrt.cnp.base.account.ui.AlbumBrowserActivity;
import com.hyrt.cnp.dynamic.ui.BabayIndexActivity;
import com.hyrt.cnp.dynamic.ui.BabayWordActivity;
import com.hyrt.cnp.dynamic.ui.ChangeAlbumActivity;
import com.hyrt.cnp.dynamic.ui.DynamicCommentActivity;
import com.hyrt.cnp.dynamic.ui.DynamicPhotoInfoActivity;
import com.hyrt.cnp.dynamic.ui.DynamicPhotoListActivity;
import com.hyrt.cnp.dynamic.ui.MyForwardListActivity;
import com.hyrt.cnp.dynamic.ui.SendDynamicActivity;
import com.hyrt.cnp.school.ui.ClassRoomListActivity;
import com.hyrt.cnp.school.ui.SchoolCityListActivity;
import com.hyrt.cnp.school.ui.SchoolIndexActivity;
import com.hyrt.cnp.school.ui.SchoolInfoActivity;
import com.hyrt.cnp.school.ui.SchoolListActivity;
import com.hyrt.cnp.school.ui.SchoolNoticeActivity;
import com.hyrt.cnp.school.ui.SchoolNoticeInfoActivity;
import com.hyrt.cnp.school.ui.SchoolPhotoActivity;
import com.hyrt.cnp.school.ui.SchoolRecipeActivity;
import com.hyrt.cnp.school.ui.SchoolRepiceInfoActivity;
import com.hyrt.cnp.school.ui.SchoolSearchActivity;
import com.hyrt.cnp.school.ui.SchoolSearchResultActivity;
import com.hyrt.cnp.school.ui.SendwordActivity;
import com.hyrt.cnp.school.ui.StarBabayActivity;
import com.hyrt.cnp.school.ui.StarTeacherActivity;
import com.hyrt.cnp.school.ui.StarTeacherInfoActivity;

/**
 * Created by GYH on 14-1-3.
 */
public class ActivityModule extends AbstractModule {
    @Override
    protected void configure() {

    }

    @Provides
    @Named("loginactivity")
    Class loginActivity(){
        return LoginActivity.class;
    }

    @Provides
    @Named("schoollistactivity")
    Class SchoolListActivity(){
        return SchoolListActivity.class;
    }

    @Provides
    @Named("schoolIndexActivity")
    Class SchoolIndexActivity(){
        return SchoolIndexActivity.class;
    }
    @Provides
    @Named("sendwordActivity")
    Class SendwordActivity(){
        return SendwordActivity.class;
    }

    @Provides
    @Named("schoolNoticeActivity")
    Class SchoolNoticeActivity(){
        return SchoolNoticeActivity.class;
    }

    @Provides
    @Named("schoolNoticeInfoActivity")
    Class SchoolNoticeInfoActivity(){
        return SchoolNoticeInfoActivity.class;
    }
    @Provides
    @Named("schoolRecipeActivity")
    Class SchoolRecipeActivity(){
        return SchoolRecipeActivity.class;
    }
    @Provides
    @Named("starTeacherActivity")
    Class StarTeacherActivity(){
        return StarTeacherActivity.class;
    }
    @Provides
    @Named("starTeacherInfoActivity")
    Class StarTeacherInfoActivity(){
        return StarTeacherInfoActivity.class;
    }
    @Provides
    @Named("starBabayActivity")
    Class StarBabayActivity(){
        return StarBabayActivity.class;
    }
    @Provides
    @Named("classRoomListActivity")
    Class ClassRoomListActivity(){
        return ClassRoomListActivity.class;
    }
    @Provides
    @Named("schoolPhotoActivity")
    Class SchoolPhotoActivity(){
        return SchoolPhotoActivity.class;
    }
    @Provides
    @Named("schoolRepiceInfoActivity")
    Class SchoolRepiceInfoActivity(){
        return SchoolRepiceInfoActivity.class;
    }
    @Provides
    @Named("schoolInfoActivity")
    Class SchoolInfoActivity(){
        return SchoolInfoActivity.class;
    }
    @Provides
    @Named("classroomIndexActivity")
    Class ClassroomIndexActivity(){
        return ClassroomIndexActivity.class;
    }
    @Provides
    @Named("classroomAlbumActivity")
    Class classroomAlbumActivity(){
        return ClassroomAlbumActivity.class;
    }
    @Provides
    @Named("classroomphotolistActivity")
    Class classroomphotolistActivity(){
        return ClassroomphotolistActivity.class;
    }
    @Provides
    @Named("classroomBabayActivity")
    Class classroomBabayActivity(){
        return ClassroomBabayActivity.class;
    }
    @Provides
    @Named("classroomRecipeInfoActivity")
    Class classroomRecipeInfoActivity(){
        return ClassroomRecipeInfoActivity.class;
    }
    @Provides
    @Named("babayIndexActivity")
    Class babayIndexActivity(){
        return BabayIndexActivity.class;
    }

    @Provides
    @Named("babayWordActivity")
    Class babayWordActivity(){
        return BabayWordActivity.class;
    }

    @Provides
    @Named("userInfoActivity")
    Class userInfoActivity(){
        return UserInfoActivity.class;
    }
    @Provides
    @Named("dynamicCommentActivity")
    Class dynamicCommentActivity(){
        return DynamicCommentActivity.class;
    }
    @Provides
    @Named("classroomphotoinfoActivity")
    Class classroomphotoinfoActivity(){
        return ClassroomphotoinfoActivity.class;
    }
    @Provides
    @Named("schoolSearchActivity")
    Class schoolSearchActivity() {return SchoolSearchActivity.class;}
    @Provides
    @Named("schoolSearchResultActivity")
    Class schoolSearchResultActivity() {return SchoolSearchResultActivity.class;}
    @Provides
    @Named("schoolCityListActivity")
    Class schoolCityListActivity() {return SchoolCityListActivity.class;}
    @Provides
    @Named("myForwardActivity")
    Class myForwardActivity() {return MyForwardListActivity.class;}
    @Provides
    @Named("addPhotoDynamicActivity")
    Class addPhotoDynamicActivity() {return AddPhotoDynamicActivity.class;}
    @Provides
    @Named("sendDynamicActivity")
    Class sendDynamicActivity() {return SendDynamicActivity.class;}
    @Provides
    @Named("aboutFriendActivity")
    Class aboutFriendActivity() {return AboutFriendActivity.class;}
    @Provides
    @Named("changeAlbumActivity")
    Class changeAlbumActivity() {return ChangeAlbumActivity.class;}
    @Provides
    @Named("addAlbumActivity")
    Class addAlbumActivity() {return AddAlbumActivity.class;}
    @Provides
    @Named("dynamicPhotoListActivity")
    Class dynamicPhotoListActivity() {return DynamicPhotoListActivity.class;}
    @Provides
    @Named("dynamicPhotoInfoActivity")
    Class dynamicPhotoInfoActivity() {return DynamicPhotoInfoActivity.class;}
    @Provides
    @Named("albumBrowserActivity")
    Class albumBrowserActivity() {return AlbumBrowserActivity.class;}
    @Provides
    @Named("welcomeActivity")
    Class welcomeActivity() {return WelcomeActivity.class;}
}
