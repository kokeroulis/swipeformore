/*
 * Copyright (C) 2015 Antonis Tsiapaliokas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kokeroulis.gr.swipeformore;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SwipeForMoreCallback extends ItemTouchHelper.SimpleCallback {
    private final WrapperAdapter mAdapter;
    private final Class<?> mVHType;

    public SwipeForMoreCallback(WrapperAdapter adapter, Class<?> VHType) {
        super(0,  ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;

        if (mAdapter.getWrappedAdapter() instanceof SwipeForMoreViewHolder) {
            mVHType =VHType;
        } else {
            throw new RuntimeException(adapter.toString() + " must implement SwipeForMoreViewHolder");
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
        final boolean valueExists = mAdapter.swipedItems().contains(posValue);

        if (valueExists && direction == ItemTouchHelper.RIGHT) {
            mAdapter.removeSwipeableItem(posValue);
            mAdapter.notifyItemChanged(pos);
        } else if (!valueExists && direction == ItemTouchHelper.LEFT) {
            mAdapter.addSwipeableItem(posValue);
            mAdapter.notifyItemChanged(pos);
        }
    }
}
