/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package  net.oschina.app.common;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.hardware.Camera;
import android.os.Build;
import android.provider.MediaStore.MediaColumns;
import android.view.View;

import java.lang.reflect.Field;

public class ApiHelper {
    public static interface VERSION_CODES {
        // These value are copied from Build.VERSION_CODES
        public static final int GINGERBREAD_MR1 = 10;
        public static final int HONEYCOMB = 11;
        public static final int HONEYCOMB_MR1 = 12;
        public static final int HONEYCOMB_MR2 = 13;
        public static final int ICE_CREAM_SANDWICH = 14;
        public static final int ICE_CREAM_SANDWICH_MR1 = 15;
        public static final int JELLY_BEAN = 16;
        public static final int JELLY_BEAN_MR1 = 17;
    }

    public static final boolean HAS_VIEW_PROPERTY_ANIMATOR =
            Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
    public static final boolean HAS_JELLY_BEAN_MR1 = Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1;
    public static final boolean HAS_JELLY_BEAN = HAS_JELLY_BEAN_MR1 ? true : Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
    public static final boolean HAS_ICE_CREAM_SANDWICH_MR1 = HAS_JELLY_BEAN ? true : Build.VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    public static final boolean HAS_ICE_CREAM_SANDWICH = HAS_ICE_CREAM_SANDWICH_MR1 ? true : Build.VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH;
    public static final boolean HAS_HONEY_COMB_MR2 = HAS_ICE_CREAM_SANDWICH ? true : Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR2;
    public static final boolean HAS_HONEY_COMB_MR1 = HAS_HONEY_COMB_MR2 ? true : Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
    public static final boolean HAS_HONEY_COMB = HAS_HONEY_COMB_MR1 ? true : Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;


}