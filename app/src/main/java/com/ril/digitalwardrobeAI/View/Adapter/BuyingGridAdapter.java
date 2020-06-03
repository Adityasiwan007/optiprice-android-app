package com.ril.digitalwardrobeAI.View.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


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

public class BuyingGridAdapter extends BaseAdapter {
    Dialog popup;
    Context context;
    String img_url;
    LayoutInflater inflater;
    private String products_string = null;
    private ArrayList<MissingItembean> missingPro;
    private ArrayList<String> sold;

    public BuyingGridAdapter(Context c, ArrayList<MissingItembean> missingPro,ArrayList<String> sold)
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
        img_url=ROOT_URL+"buy/"+missingPro.get(position).getRawImages().get(0);
        Picasso.get().load( Constants.IMAGE_THUMBNAIL_URL_WARDROBE + missingPro.get(position).getRawImages().get(0)).into(imageView);
        for(int i=0;i<sold.size();i++)
        {
            if(sold.get( i ).equals( missingPro.get(position).getRawImages().get(0) ))
            {
                //cover.setVisibility( View.VISIBLE );
                imageView.setAlpha( .2f );
            }
        }

        return convertView;
    }
}
