package com.ril.digitalwardrobeAI.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ril.digitalwardrobeAI.Model.Product;
import com.ril.digitalwardrobeAI.ProductInterface;
import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.View.Activity.BuySellActivity;
import com.ril.digitalwardrobeAI.View.Adapter.ProductAdapter;

import java.util.ArrayList;
import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;

import static com.ril.digitalwardrobeAI.Constants.WARDROBE_TYPE;
import static com.ril.digitalwardrobeAI.Constants.FILTER;
import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
import static com.ril.digitalwardrobeAI.Constants.catalogComplementaryProducts;
import static com.ril.digitalwardrobeAI.Constants.complementaryProducts;
import static com.ril.digitalwardrobeAI.Constants.croppedImage;
import static com.ril.digitalwardrobeAI.Constants.restResponse;
import static com.ril.digitalwardrobeAI.Constants.setAdapterViewPager;

public class ComplimentaryFragment extends Fragment implements ProductInterface {
    AutoScrollViewPager viewPager;
    LinearLayout dotsLayout;
    TextView noComp,compText,switchText;
    ImageView productImage,cameraScan,store,wardrobe;
    TextView colorDesc;
    ImageButton skirtFab,trouserFab,filter;
    boolean isFABOpen=false;
    ArrayList<Product> products,baseProduct;
    RecyclerView recyclerView;
    ProductAdapter mAdapter;
    Switch switchState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        View view= inflater.inflate(R.layout.layout_comp, container, false);
        container.removeAllViews();
        viewPager = (AutoScrollViewPager) view.findViewById(R.id.viewPager);
        dotsLayout = (LinearLayout) view.findViewById(R.id.layout_dots);

        noComp=view.findViewById(R.id.noComp);
        compText=view.findViewById(R.id.compText);
        colorDesc=view.findViewById(R.id.colorDesc);
        productImage=view.findViewById(R.id.productImage);
        cameraScan=view.findViewById(R.id.cameraScan);
        switchState=view.findViewById(R.id.switchState);
        switchText=view.findViewById(R.id.switchText);
        store=view.findViewById(R.id.store);
        wardrobe=view.findViewById(R.id.wardrobe);
        colorDesc.setText(restResponse.getObject().getTextSuggestion());
       // Picasso.get().load(getImageUri(getContext(),croppedImage)).resize(500,500).onlyScaleDown().centerInside().transform(new CircleTransform()).into(productImage);
        productImage.setImageBitmap(croppedImage);
        filter = (ImageButton) view.findViewById(R.id.filter);
        skirtFab = (ImageButton) view.findViewById(R.id.skirt);
        trouserFab = (ImageButton) view.findViewById(R.id.trouser);
        recyclerView=view.findViewById(R.id.recycler);
        products=new ArrayList<>();
        mAdapter = new ProductAdapter(getContext(), products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setPi(this);
        if(restResponse.getObject().getMsdTags().getCategory().equals("Tops")){
            getProductByType(FILTER);
        }
        else if(restResponse.getObject().getMsdTags().getCategory().equals("Skirts")||restResponse.getObject().getMsdTags().getCategory().equals("Pants")){
            skirtFab.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.top_float));
            trouserFab.setVisibility(View.GONE);
            getProductByType(FILTER);
        }

        setSwitchState();






        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });




        cameraScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), BuySellActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

        skirtFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFABMenu();
                if(restResponse.getObject().getMsdTags().getCategory().equals("Tops")) {
                    FILTER="Skirts";
                    getProductByType(FILTER);
                }
                else if(restResponse.getObject().getMsdTags().getCategory().equals("Pants")||restResponse.getObject().getMsdTags().getCategory().equals("Skirts")){
                    FILTER="Tops";
                    getProductByType(FILTER);

                }
            }
        });
        trouserFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFABMenu();
                FILTER="Pants";
                getProductByType(FILTER);
            }
        });


        switchState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    WARDROBE_TYPE="wardrobe";
                    setSwitchState();
                    if (restResponse.getObject().getMsdTags().getCategory().equals("Tops")) {
                        getProductByType("Skirts");
                    } else if (restResponse.getObject().getMsdTags().getCategory().equals("Skirts") || restResponse.getObject().getMsdTags().getCategory().equals("Pants")) {
                        skirtFab.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.top_float));
                        trouserFab.setVisibility(View.GONE);
                        getProductByType("Tops");
                    }
                    if(products.size()!=0) {
                        mAdapter.setProducts(products, WARDROBE_TYPE);
                        mAdapter.notifyDataSetChanged();
                        if (products != null)
                            setAdapterViewPager(viewPager, getActivity(), dotsLayout, products, WARDROBE_TYPE);
                    }

                } else {
                    WARDROBE_TYPE="catalog";
                    setSwitchState();
                    if (restResponse.getObject().getMsdTags().getCategory().equals("Tops")) {
                        getProductByType("Skirts");
                    } else if (restResponse.getObject().getMsdTags().getCategory().equals("Skirts") || restResponse.getObject().getMsdTags().getCategory().equals("Pants")) {
                        skirtFab.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.top_float));
                        trouserFab.setVisibility(View.GONE);
                        getProductByType("Tops");
                    }
                    if (products.size() != 0) {
                        // The toggle is disabled
                        mAdapter.setProducts(products, WARDROBE_TYPE);
                        mAdapter.notifyDataSetChanged();
                        if (products != null)
                            setAdapterViewPager(viewPager, getActivity(), dotsLayout, products, WARDROBE_TYPE);
                    }
                }

            }
        });
        return  view;
    }

    private void setSwitchState() {

        if(WARDROBE_TYPE.equals("catalog")){
            wardrobe.setImageResource(R.drawable.wardrobe_inactive);
            store.setImageResource(R.drawable.store_active);
            switchText.setText("From Store");
            switchState.setChecked(false);
        }
        else{
            switchText.setText("My Wardrobe");
            wardrobe.setImageResource(R.drawable.wardrobe_active);
            store.setImageResource(R.drawable.store_inactive);
            switchState.setChecked(true);
        }
    }

    private void showFABMenu(){
        isFABOpen=true;
        skirtFab.setVisibility(View.VISIBLE);
        if(restResponse.getObject().getMsdTags().getCategory().equals("Tops"))
            trouserFab.setVisibility(View.VISIBLE);
        skirtFab.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        trouserFab.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        filter.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.close));

    }

    private void closeFABMenu(){
        isFABOpen=false;
        skirtFab.setVisibility(View.GONE);
        trouserFab.setVisibility(View.GONE);
        filter.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter));

    }

    @Override
    public void getPositon(int position) {
        viewPager.setCurrentItem(position);
    }

    ArrayList<Product> getProductByType(String type){
        products=new ArrayList<>();
        baseProduct=new ArrayList<>();
        if(WARDROBE_TYPE.equals("wardrobe"))
            baseProduct = complementaryProducts;

        else if(WARDROBE_TYPE.equals("catalog"))
            baseProduct=catalogComplementaryProducts;
        Log.d(LOGTAG," SIZE " + baseProduct.size());

        for(int i=0;i<baseProduct.size();i++) {
            if(baseProduct.get(i).getTags().getCategory().equals(type)) {
                products.add(baseProduct.get(i));
                Log.d(LOGTAG," TYPE IN " + baseProduct.get(i).getTags().getCategory());
            }

        }
        Log.d(LOGTAG," SIZE  of prod" + products.size());

        if(products!=null&&products.size()!=0) {
            setAdapterViewPager(viewPager, getActivity(), dotsLayout, products,WARDROBE_TYPE);
            Log.d(LOGTAG,"SWITCH MODE "+WARDROBE_TYPE);
            mAdapter.setProducts(products,WARDROBE_TYPE);
            mAdapter.notifyDataSetChanged();


        }
        if(baseProduct.size()==0) {
            noComp.setVisibility(View.VISIBLE);
            compText.setVisibility(View.GONE);
            filter.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            dotsLayout.setVisibility(View.GONE);


        }
        else{
            noComp.setVisibility(View.GONE);
            compText.setVisibility(View.VISIBLE);
            filter.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            dotsLayout.setVisibility(View.VISIBLE);


        }


        return  products;

    }
}
