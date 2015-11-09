package kokeroulis.gr.swipeformore;

/*  Copyright (C) 2015 Antonis Tsiapaliokas

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    */


import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SwipeForMore extends ItemTouchHelper.SimpleCallback {
    private final RecyclerView.Adapter mAdapter;
    private final SwipeForMoreListener<?> mListener;
    private final Class<?> mVHType;

    public SwipeForMore(RecyclerView.Adapter adapter) {
        super(0,  ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;

        if (mAdapter instanceof SwipeForMoreListener) {
            mListener = (SwipeForMoreListener) mAdapter;
            mVHType = mListener.getViewHolderType();
        } else {
            throw new RuntimeException(adapter.toString() + " must implement SwipeForMoreListener");
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (mVHType.isInstance(viewHolder) && dX > 0) {
            viewHolder.itemView.setTranslationX(dX);
        } else if (!mVHType.isInstance(viewHolder) && dX < 0) {
            viewHolder.itemView.setTranslationX(dX);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        final int pos = viewHolder.getAdapterPosition();
        final String posValue = String.valueOf(pos);
        final boolean valueExists = mListener.swipedItems.contains(posValue);

        if (valueExists && direction == ItemTouchHelper.RIGHT) {
            mListener.swipedItems.remove(posValue);
            mAdapter.notifyItemChanged(pos);
        } else if (!valueExists && direction == ItemTouchHelper.LEFT) {
            mListener.swipedItems.add(posValue);
            mAdapter.notifyItemChanged(pos);
        }
    }
}
