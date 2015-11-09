package kokeroulis.gr.swipeformore;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public interface SwipeForMoreListener<VH extends RecyclerView.ViewHolder> {
    int SwipeFMType = 9999;
    List<String> swipedItems = new ArrayList<>();

    VH onCreateSwipeableViewHolder(ViewGroup parent);

    Class<VH> getViewHolderType();
}
