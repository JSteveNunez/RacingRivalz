package com.artoos.racing.utils.typeface;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

/**
 * The manager of roboto typefaces.
 *
 * @author e.shishkin
 */
public class TypefaceManager
{

    public final static int AVENIR_ROMAN  = 0;
    public final static int AVENIR_BOLD_ITALIC   = 1;

    public final static int DEFAULT = AVENIR_ROMAN;

    /**
     * Array of created typefaces for later reused.
     */
    private final static SparseArray<Typeface> mTypefaces = new SparseArray<Typeface>(2);

    /**
     * Obtain typeface.
     *
     * @param context       The Context the widget is running in, through which it can access the current theme, resources, etc.
     * @param typefaceValue The value of "typeface" attribute
     * @return specify {@link android.graphics.Typeface}
     * @throws IllegalArgumentException if unknown `typeface` attribute value.
     */
    public static Typeface obtainTypeface(Context context, int typefaceValue) throws IllegalArgumentException
    {
        Typeface typeface = mTypefaces.get(typefaceValue);
        if (typeface == null) {
            typeface = createTypeface(context, typefaceValue);
            mTypefaces.put(typefaceValue, typeface);
        }
        return typeface;
    }

    /**
     * Create typeface from assets.
     *
     * @param context       The Context the widget is running in, through which it can
     *                      access the current theme, resources, etc.
     * @param typefaceValue The value of "typeface" attribute
     * @return {@link android.graphics.Typeface}
     * @throws IllegalArgumentException if unknown `typeface` attribute value.
     */
    private static Typeface createTypeface(Context context, int typefaceValue) throws IllegalArgumentException
    {
        Typeface typeface;
        switch (typefaceValue) {
            case AVENIR_ROMAN:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir.ttc");
                break;
            case AVENIR_BOLD_ITALIC:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir.ttc");
                break;
            default:
                throw new IllegalArgumentException("Unknown `typeface` attribute value " + typefaceValue);
        }
        return typeface;
    }

}
