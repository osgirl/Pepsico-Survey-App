package limited.it.planet.visicoolertracking.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Tarikul on 4/15/2018.
 */

public class FontCustomization {
    public Typeface TexgyreHerosRegular;
    public Typeface TexgyreHerosBold;
    Context mContext;
    public FontCustomization(Context context){
        this.mContext = context;
        this.TexgyreHerosRegular = Typeface.createFromAsset(context.getAssets(),"texgyreheros-regular.otf") ;
        this.TexgyreHerosBold = Typeface.createFromAsset(context.getAssets(),"texgyreheros-bold.otf") ;
    }

    public Typeface getTexgyreHerosRegular(){
        return TexgyreHerosRegular;
    }
    public Typeface getTexgyreHerosBold(){
        return TexgyreHerosBold;
    }

}
