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
import android.support.v7.widget.helper.ItemTouchHelper;

public final class SwipeForMore {

    public SwipeForMore() {
        throw new RuntimeException("No direct instances");
    }

    public static SwipeForMore.Builder with(RecyclerView rv, Class<?> viewHolderClass) {
        return new Builder(rv, viewHolderClass);
    }

    public static class Builder {
        RecyclerView mRv;
        Class<?> mViewHolderClass;

        public Builder(RecyclerView rv, Class<?> viewHolderClass) {
            mRv = rv;
            mViewHolderClass = viewHolderClass;
            bind();
        }

        public void bind() {
            if (!(mRv.getAdapter() instanceof WrapperAdapter)) {
                WrapperAdapter adapter = new WrapperAdapter(mRv.getAdapter());
                mRv.setAdapter(adapter);

                SwipeForMoreCallback callback = new SwipeForMoreCallback(adapter, mViewHolderClass);
                ItemTouchHelper helper = new ItemTouchHelper(callback);
                helper.attachToRecyclerView(mRv);
            }
        }

        public void unBind() {
            if ((mRv.getAdapter() instanceof WrapperAdapter)) {
                WrapperAdapter adapter = (WrapperAdapter) mRv.getAdapter();
                mRv.setAdapter(adapter.getWrappedAdapter());
            }
        }
    }
}
