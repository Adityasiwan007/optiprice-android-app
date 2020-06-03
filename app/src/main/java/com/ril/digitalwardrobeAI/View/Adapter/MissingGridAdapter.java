package com.ril.digitalwardrobeAI.View.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ril.digitalwardrobeAI.Constants;
import com.ril.digitalwardrobeAI.Model.MissingItembean;
import com.ril.digitalwardrobeAI.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.ril.digitalwardrobeAI.Constants.ROOT_URL;

public class MissingGridAdapter extends BaseAdapter {
    Dialog popup;
    Context context;
    String img_url;
    LayoutInflater inflater;
    private String products_string = null;
    private ArrayList<MissingItembean> missingPro;
    private ArrayList<String> sold;

    public MissingGridAdapter(Context c, ArrayList<MissingItembean> missingPro,ArrayList<String> sold)
    {
        this.context = c;
        this.missingPro= missingPro;
        this.sold=sold;
    }
    @Override
    public int getCount() {
        return missingPro.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
        {
            inflater=(LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        }
        if(convertView==null)
        {
            convertView=inflater.inflate( R.layout.row_item,null );
        }
        ImageView imageView = convertView.findViewById( R.id.image_view );
        img_url=ROOT_URL+"sell/"+missingPro.get(position).getRawImages().get(0);
        Picasso.get().load( Constants.IMAGE_THUMBNAIL_URL_WARDROBE + missingPro.get(position).getRawImages().get(0)).into(imageView);
        for(int i=0;i<sold.size();i++)
        {
            if(sold.get( i ).equals( missingPro.get(position).getRawImages().get(0) ))
            {
                //cover.setVisibility( View.VISIBLE );
                imageView.setAlpha( .2f );
            }
        }

//        imageView.setImageBitmap(grayScaleImage(BitmapFactory.decodeResource( );
        //Picasso.get().load( img_url).into(imageView);
//        Glide.with( context )
//                .asBitmap()
//                .load(img_url )
//                .into(imageView);
//        imageView.setImageResource( img_url );

        return convertView;
    }
//    public static Bitmap grayScaleImage(Bitmap src) {
//        // constant factors
//        final double GS_RED = 0.299;
//        final double GS_GREEN = 0.587;
//        final double GS_BLUE = 0.114;
//
//        // create output bitmap
//        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
//        // pixel information
//        int A, R, G, B;
//        int pixel;
//
//        // get image size
//        int width = src.getWidth();
//        int height = src.getHeight();
//
//        // scan through every single pixel
//        for(int x = 0; x < width; ++x) {
//            for(int y = 0; y < height; ++y) {
//                // get one pixel color
//                pixel = src.getPixel(x, y);
//                // retrieve color of all channels
//                A = Color.alpha(pixel);
//                R = Color.red(pixel);
//                G = Color.green(pixel);
//                B = Color.blue(pixel);
//                // take conversion up to one single value
//                R = G = B = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
//                // set new pixel color to output bitmap
//                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
//            }
//        }
//
//        // return final image
//        return bmOut;
//    }
}
