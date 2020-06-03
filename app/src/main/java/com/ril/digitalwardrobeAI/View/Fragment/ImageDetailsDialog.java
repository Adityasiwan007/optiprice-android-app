package com.ril.digitalwardrobeAI.View.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ril.digitalwardrobeAI.R;

import static com.ril.digitalwardrobeAI.Constants.restResponse;

public class ImageDetailsDialog extends Dialog {
    TextView category,occasion,sleeve,neckLine,occasionLabel,pattern;
    LinearLayout color,baseColor;
    public ImageDetailsDialog( Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_image_detail);
        category=findViewById(R.id.category);
        occasionLabel=findViewById(R.id.occasionLabel);
        occasion=findViewById(R.id.occasion);
        sleeve=findViewById(R.id.sleeve);
        neckLine=findViewById(R.id.neckline);
        color=findViewById(R.id.color);
        baseColor=findViewById(R.id.baseColor);
        pattern=findViewById(R.id.pattern);
        setData();
    }


    void setData(){

        category.setText(restResponse.getObject().getMsdTags().getCategory());
       /* if(restResponse.getObject().getMsdTags().getStyleOccasion()==null){
            occasionLabel.setVisibility(View.GONE);
            occasion.setVisibility(View.GONE);

        }*/
        pattern.setText(restResponse.getObject().getMsdTags().getPattern());
        occasion.setText(restResponse.getObject().getMsdTags().getStyleOccasion());
        sleeve.setText(restResponse.getObject().getMsdTags().getSleeveLength());
        neckLine.setText(restResponse.getObject().getMsdTags().getNeckline());
        color.setBackgroundColor(Color.parseColor(restResponse.getObject().getMsdTags().getColorHex()));
        baseColor.setBackgroundColor(Color.parseColor(restResponse.getObject().getMsdTags().getBaseHexColorOfUploadedItem()));


    }
}
