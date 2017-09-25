package au.com.realestate.hometime;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import au.com.realestate.hometime.Helper.Utils;
import au.com.realestate.hometime.models.Tram;

class TramAdapter extends RecyclerView.Adapter<TramAdapter.MyViewHolder> {

    Utils utils = Utils.getInstance();
    private List<Tram> data;

    public TramAdapter(List<Tram> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tram, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Tram tramObj = data.get(position);
        holder.day.setText(utils.simpleDate(tramObj.predictedArrival));
        holder.route.setText(tramObj.routeNo);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView day, route;

        public MyViewHolder(View view) {
            super(view);
            day = (TextView) view.findViewById(R.id.dateTextView);
            route = (TextView) view.findViewById(R.id.routeTextV);
        }
    }
}
