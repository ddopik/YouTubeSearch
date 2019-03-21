package base.widgets;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by abdalla_maged on 10/4/2018.
 */
public abstract class PagingController {

    //    private val recyclerView: RecyclerView? = null
// The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int visibleThreshold = 9;
    private int firstVisibleItem = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;
    private int startingPageIndex = 0;
    private int currentPage = 0;
    private RecyclerView recyclerView;

    public PagingController(RecyclerView recyclerView) {
        this.recyclerView=recyclerView;
        initListener();
    }
    public PagingController(RecyclerView recyclerView,int visibleThreshold) {
        this.recyclerView=recyclerView;
        this.visibleThreshold=visibleThreshold;
        initListener();
    }

    private void initListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Scrolling up
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView
                        .getLayoutManager();
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (dy > 0) {

                    onScroll(firstVisibleItem, visibleItemCount, totalItemCount);
                } else {

                    // Scrolling down
                }
            }
        });
    }

    private void onScroll(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            //            onLoadMore(currentPage + 1, totalItemCount);
            getPagingControllerCallBack(currentPage + 1);
            loading = true;
        }
    }


    public abstract void getPagingControllerCallBack(int page);



}