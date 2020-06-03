package com.ril.digitalwardrobeAI.View.Activity;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.support.constraint.ConstraintLayout;
        import android.support.v4.content.LocalBroadcastManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.GridView;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;
        import com.ril.digitalwardrobeAI.Model.MissingItembean;
        import com.ril.digitalwardrobeAI.Model.MissingItems;
        import com.ril.digitalwardrobeAI.Model.RestResponseBean;
        import com.ril.digitalwardrobeAI.Model.RestResponseBeanCart;
        import com.ril.digitalwardrobeAI.Network.Util;
        import com.ril.digitalwardrobeAI.R;
        import com.ril.digitalwardrobeAI.View.Adapter.BuyingGridAdapter;
        import com.ril.digitalwardrobeAI.View.Adapter.MissingAdapter;
        import com.ril.digitalwardrobeAI.View.Adapter.MissingGridAdapter;

        import org.json.JSONException;

        import java.util.ArrayList;
        import java.util.LinkedHashMap;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

        import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
        import static com.ril.digitalwardrobeAI.Constants.hearders;

public class BuyingGridActivity extends AppCompatActivity {

    ConstraintLayout organge;
    String sell_id;
    public static ArrayList<String> sold = new ArrayList<String>();
    public static ArrayList<String> sold1 = new ArrayList<String>();
    String jsonString;
    private TextView number;
    private ImageView back, cart;
    public GridView grid;
    ArrayList<String> colorList;
    public String[] cartIDs;
    LinkedHashMap<String, ArrayList<MissingItembean>> productMap;
    private static final String TAG = "MissingScreen";
    private MissingItembean selected_pro;
    public ArrayList<MissingItembean> missingPro;
    //public ArrayList<String> cartIDs=new ArrayList<String>();
    //String[] cartIDs;
    public ArrayList<MissingItembean> cart_item = new ArrayList<MissingItembean>();
    TextView add_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_buying_grid );
        Intent intent = getIntent();
        jsonString = intent.getStringExtra( "jsonObject" );
        sell_id = intent.getStringExtra( "status" );
        add_count = findViewById( R.id.pro_text );
        if (!sell_id.equals( "noAction" )) {
            sold.add( sell_id );
        }
        sold1 = removeDuplicates( sold );
        System.out.println( "AdityaS1 " + sold );
        System.out.println( "AdityaS2 " + sold1 );
        grid = findViewById( R.id.grid_view );

        try {
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void initRecyclerView() throws JSONException {
        missingPro = new Gson().fromJson( jsonString, new TypeToken<ArrayList<MissingItembean>>() {
        }.getType() );
        add_count.setText( "Total " + missingPro.size() + " Items" );

        BuyingGridAdapter adapter = new BuyingGridAdapter( this, missingPro, sold1 );
        grid.setAdapter( adapter );


        grid.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selected_pro = missingPro.get( position );
                Intent i = new Intent( BuyingGridActivity.this, BuyMarket.class );
                Gson gson = new Gson();
                String select = gson.toJson( selected_pro );
                i.putExtra( "selected", select );
                startActivity( i );

            }
        } );
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent( BuyingGridActivity.this, BuySellActivity.class );
        //Intent intent1=new Intent( BuyingGridActivity.this,LoginActivity.class );
        startActivity( intent1 );
        super.onBackPressed();
    }
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
}


