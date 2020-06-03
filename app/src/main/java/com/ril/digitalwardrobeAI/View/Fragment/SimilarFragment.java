package com.ril.digitalwardrobeAI.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ril.digitalwardrobeAI.Constants;
import com.ril.digitalwardrobeAI.Model.Product;
import com.ril.digitalwardrobeAI.ProductInterface;
import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.View.Activity.BuySellActivity;
import com.ril.digitalwardrobeAI.View.Adapter.ProductAdapter;

import java.util.ArrayList;

import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;

import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
import static com.ril.digitalwardrobeAI.Constants.WARDROBE_TYPE;
import static com.ril.digitalwardrobeAI.Constants.catalogSimilarProducts;
import static com.ril.digitalwardrobeAI.Constants.croppedImage;
import static com.ril.digitalwardrobeAI.Constants.restResponse;
import static com.ril.digitalwardrobeAI.Constants.setAdapterViewPager;

public class SimilarFragment extends Fragment implements ProductInterface {
    AutoScrollViewPager viewPager;
    LinearLayout dotsLayout;
    TextView txtZeroCount,noSimilar,similarText;
    ImageView productImage,cameraScan,store,wardrobe;
    TextView colorDesc,switchText;
    Switch switchState;
    RecyclerView recyclerView;
    ProductAdapter mAdapter;
    ArrayList<Product> products;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        View view= inflater.inflate(R.layout.fragment_similar, container, false);
        container.removeAllViews();
        viewPager = (AutoScrollViewPager) view.findViewById(R.id.viewPager);
        dotsLayout = (LinearLayout) view.findViewById(R.id.layout_dots);
        txtZeroCount=view.findViewById(R.id.txtZeroCount);
        noSimilar=view.findViewById(R.id.noSimilar);
        colorDesc=view.findViewById(R.id.colorDesc);
        productImage=view.findViewById(R.id.productImage);
        cameraScan=view.findViewById(R.id.cameraScan);
        similarText=view.findViewById(R.id.similarText);
        switchState=view.findViewById(R.id.switchState);
        switchText=view.findViewById(R.id.switchText);
        store=view.findViewById(R.id.store);
        wardrobe=view.findViewById(R.id.wardrobe);
        recyclerView=view.findViewById(R.id.recycler);

        setInitialProducts();
        colorDesc.setText(restResponse.getObject().getTextSuggestion());
        //Picasso.get().load(getImageUri(getContext(),croppedImage)).resize(500,500).onlyScaleDown().centerInside().transform(new CircleTransform()).into(productImage);
        productImage.setImageBitmap(croppedImage);

        setSwitchState();


        LinearLayout swipeLeft = getActivity().findViewById(R.id.swipeLeft);
        swipeLeft.setVisibility(View.VISIBLE);
        if(products.size()==0) {
            noSimilar.setVisibility(View.VISIBLE);
            similarText.setVisibility(View.GONE);
        }
        else{
            setAdapterViewPager(viewPager, getActivity(), dotsLayout, products, WARDROBE_TYPE);

        }
        cameraScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), BuySellActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        switchState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Log.d(LOGTAG," INSIDE WARDROBE");
                    WARDROBE_TYPE="wardrobe";
                    setSwitchState();
                    products=Constants.products;
                    if(products.size()!=0) {
                        mAdapter.setProducts(Constants.products, WARDROBE_TYPE);
                        mAdapter.notifyDataSetChanged();
                        setAdapterViewPager(viewPager, getActivity(), dotsLayout, products, WARDROBE_TYPE);
                        setUiForWithItems();
                    }
                    else if(products.size()==0) {
                       setUiForNoItems();

                    }

                } else {
                    // The toggle is disabled
                    Log.d(LOGTAG," INSIDE STORE");
                    WARDROBE_TYPE="catalog";
                    setSwitchState();
                    if(catalogSimilarProducts.size()!=0) {
                        mAdapter.setProducts(catalogSimilarProducts, WARDROBE_TYPE);
                        mAdapter.notifyDataSetChanged();
                        products = catalogSimilarProducts;
                        setAdapterViewPager(viewPager, getActivity(), dotsLayout, products, WARDROBE_TYPE);
                        setUiForWithItems();

                    }
                    else if(catalogSimilarProducts.size()==0) {
                       setUiForNoItems();
                    }

                }

            }
        });
        return view;
    }

    private void setSwitchState() {

        if(WARDROBE_TYPE.equals("wardrobe")){
            switchText.setText("My Wardrobe");
            wardrobe.setImageResource(R.drawable.wardrobe_active);
            store.setImageResource(R.drawable.store_inactive);
            switchState.setChecked(true);
        }
        else{
            wardrobe.setImageResource(R.drawable.wardrobe_inactive);
            store.setImageResource(R.drawable.store_active);
            switchText.setText("From Store");
            switchState.setChecked(false);

        }
    }

    private void setInitialProducts() {
        if(WARDROBE_TYPE.equals("wardrobe"))
            products=Constants.products;
        else
            products= catalogSimilarProducts;

        mAdapter = new ProductAdapter(getContext(),products);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setProducts(products,WARDROBE_TYPE);

        mAdapter.setPi(this);

    }

    @Override
    public void getPositon(int position) {
        viewPager.setCurrentItem(position);
    }

    void setUiForNoItems(){
        noSimilar.setVisibility(View.VISIBLE);
        similarText.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

    }

    void setUiForWithItems(){
        noSimilar.setVisibility(View.GONE);
        similarText.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

    }
}
