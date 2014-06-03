package net.oschina.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * A @{code ImageView} which change the opacity of the icon if disabled.
 */
public class TwoStateImageView extends ImageView {
    private static final int ENABLED_ALPHA = 255;
    private static final int DISABLED_ALPHA = (int) (255 * 0.4);
    private boolean mFilterEnabled = true;

    public TwoStateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TwoStateImageView(Context context) {
        this(context, null);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (mFilterEnabled) {
            if (enabled) {
                setAlpha(ENABLED_ALPHA);
            } else {
                setAlpha(DISABLED_ALPHA);
            }
        }
    }

    public void enableFilter(boolean enabled) {
        mFilterEnabled = enabled;
    }
}
