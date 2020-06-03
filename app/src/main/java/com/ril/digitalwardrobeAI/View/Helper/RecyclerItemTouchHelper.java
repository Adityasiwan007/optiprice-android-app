package com.ril.digitalwardrobeAI.View.Helper;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.ril.digitalwardrobeAI.View.Adapter.CartAdapter;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs,RecyclerItemTouchHelperListener listener) {
        super( dragDirs, swipeDirs );
        this.listener=listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    public void clearView(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder)
    {
        View foregroundView= ((CartAdapter.ViewHolder)viewHolder).viewForeground;
        getDefaultUIUtil().clearView( foregroundView );
    }


    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection( flags, layoutDirection );
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder!=null)
        {
            final View foregroundView= ((CartAdapter.ViewHolder)viewHolder).viewForeground;
            getDefaultUIUtil().onSelected( foregroundView );
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView= ((CartAdapter.ViewHolder)viewHolder).viewForeground;
        getDefaultUIUtil().onDraw( c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView= ((CartAdapter.ViewHolder)viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver( c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive );
    }
}
