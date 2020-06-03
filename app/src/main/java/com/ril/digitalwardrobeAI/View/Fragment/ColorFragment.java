package com.ril.digitalwardrobeAI.View.Fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.SweepGradientView;

import java.util.ArrayList;

import static com.ril.digitalwardrobeAI.Constants.croppedImage;
import static com.ril.digitalwardrobeAI.Constants.restResponse;
import static com.ril.digitalwardrobeAI.Constants.wardrobeItems;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {
    ArrayList<String> colorList;
    ImageView productImage,addToWardrobe,colorOfUploaded;
    TextView colorDesc;
    SweepGradientView sweepGradientView;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_color, container, false);
        productImage=view.findViewById(R.id.productImage);
        addToWardrobe=view.findViewById(R.id.addToWardrobe);
        colorOfUploaded=view.findViewById(R.id.colorOfUploaded);
        sweepGradientView= view.findViewById(R.id.sweepGradientView);
        colorDesc=view.findViewById(R.id.colorDesc);
        colorList=new ArrayList();
        if(restResponse!=null) {
            colorOfUploaded.getBackground().setColorFilter(Color.parseColor(restResponse.getObject().getMsdTags().getColorHex()), PorterDuff.Mode.SRC_ATOP);
            colorDesc.setText(restResponse.getObject().getTextSuggestion());
        }
        productImage.setImageBitmap(croppedImage);
        //get base colors from wardrobe
        if(wardrobeItems.size()>1&&restResponse.getObject().getSortedColors().size()>=1) {


            sweepGradientView.setVisibility(View.VISIBLE);
            colorOfUploaded.setVisibility(View.VISIBLE);
            sweepGradientView.setFragment(this);
            getColors();
            //create color pallete
            wardrobeColorPallete(sweepGradientView);
        }

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageDetails();
            }
        });
        return view;
    }

    private void wardrobeColorPallete(SweepGradientView v) {
        final int c[] = new int[colorList.size()];
        for(int i=0;i<colorList.size();i++)
            c[i] = Color.parseColor(colorList.get(i));
        v.setColors(c,Color.parseColor(restResponse.getObject().getMsdTags().getBaseHexColorOfUploadedItem()));
        double mx = v.cx + (v.r * Math.cos(Math.toRadians(45.0)));
        double my = v.cy + (v.r * Math.sin(Math.toRadians(45.0)));
    }

    public void setMarkerPosition(float angle){

        ConstraintLayout.LayoutParams layoutParams=  (ConstraintLayout.LayoutParams)colorOfUploaded.getLayoutParams();
        layoutParams.circleAngle = angle ;
        colorOfUploaded.setLayoutParams(layoutParams);
//
//        ConstraintSet constraintSet = new ConstraintSet();
//        ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(R.id.constraint);
//        constraintSet.clone(constraintLayout);
//        constraintSet.constrainCircle(colorOfUploaded.getId(), sweepGradientView.getId(),180,10);
//        //constraintSet.connect(colorOfUploaded.getId(), ConstraintSet.TOP, sweepGradientView.getId(), ConstraintSet.TOP, 210);
//        //constraintSet.connect(colorOfUploaded.getId(), ConstraintSet.LEFT,sweepGradientView.getId(),ConstraintSet.LEFT, 210);
//        constraintSet.applyTo(constraintLayout);
    }
    void getColors(){
        for(String sortedColor: restResponse.getObject().getSortedColors()){
            colorList.add(sortedColor);
        }

        if(colorList.size()==1){
            colorList.add(1,colorList.get(0));
        }
    }

    void getImageDetails(){
    ImageDetailsDialog imageDetailsDialog=new ImageDetailsDialog(getContext());
    imageDetailsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    imageDetailsDialog.show();


    }



}

