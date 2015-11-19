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

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class WrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final RecyclerView.Adapter mMainAdapter;
    private List<String> mSwipedItems = new ArrayList<>();
    private static final int SWIPED_ITEM_VIEWTYPE = 9999; // just set someting very big

    public WrapperAdapter(RecyclerView.Adapter adapter) {
        mMainAdapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SWIPED_ITEM_VIEWTYPE) {
            return  ((SwipeForMoreViewHolder)mMainAdapter).onCreateSwipeableViewHolder(parent);
        } else {
            return mMainAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // our swipeable viewholder should handle the binding, not
        // our wrapper...
        if (!mSwipedItems.contains(String.valueOf(position))) {
            mMainAdapter.onBindViewHolder(holder, position);
        }
    }

    public void addSwipeableItem(String pos) {
        if (!mSwipedItems.contains(pos)) {
            mSwipedItems.add(pos);
        }
    }

    public void removeSwipeableItem(String pos) {
        if (mSwipedItems.contains(pos)) {
            mSwipedItems.remove(pos);
        }
    }

    public List<String> swipedItems() {
        return mSwipedItems;
    }

    @Override
    public int getItemViewType(int position) {
        if (mSwipedItems.contains(String.valueOf(position))) {
            return SWIPED_ITEM_VIEWTYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    public RecyclerView.Adapter getWrappedAdapter() {
        return mMainAdapter;
    }

    @Override
    public int getItemCount() {
        return mMainAdapter.getItemCount();
    }
}
