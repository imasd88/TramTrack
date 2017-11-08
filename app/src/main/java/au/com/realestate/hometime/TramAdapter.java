package au.com.realestate.hometime;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import au.com.realestate.hometime.helper.Utils;
import au.com.realestate.hometime.models.Tram;
import butterknife.BindView;
import butterknife.ButterKnife;

class TramAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Utils utils = Utils.getInstance();
    private List<Tram> data;
    private Context mContext;

    public TramAdapter(Context context, List<Tram> data) {
        this.data = data;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case Tram.HEADER_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_view, parent, false);
                return new HeaderViewHolder(view);
            case Tram.TRAM_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tram, parent, false);
                return new ChildViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Tram tramObj = data.get(position);

        if (tramObj != null) {
            switch (tramObj.getType()) {
                case Tram.HEADER_TYPE: {
                    ((HeaderViewHolder) holder).mDirection.setText(tramObj.getDirection());
                    ((HeaderViewHolder) holder).mDirection.setTextColor(tramObj.getDirection()
                            .equalsIgnoreCase("north")
                            ? ContextCompat.getColor(mContext, R.color.colorAccent)
                            : ContextCompat.getColor(mContext, R.color.colorTitle));

                }
                break;
                case Tram.TRAM_TYPE:
                    ((ChildViewHolder) holder).day.setText(utils.simpleDate(tramObj.predictedArrival));
                    ((ChildViewHolder) holder).route.setText(tramObj.routeNo);
                    break;
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (data != null) {
            Tram object = data.get(position);
            if (object != null) {
                return object.getType();
            }
        }
        return 0;
    }

    public static class ChildViewHolder extends RecyclerView.ViewHolder {
        //        public TextView day, route;
        @BindView(R.id.dateTextView)
        TextView day;
        @BindView(R.id.routeTextV)
        TextView route;

        public ChildViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDirection)
        TextView mDirection;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}



