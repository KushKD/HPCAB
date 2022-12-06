package com.dit.himachal.rms.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.dit.himachal.rms.R;
import com.dit.himachal.rms.lazyloader.ImageLoader;
import com.dit.himachal.rms.modal.ListAnnexures;

import java.util.ArrayList;
import java.util.List;

public class CabinetAnnextureMemosAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<ListAnnexures> news;

    private Filter planetFilter;
    private List<ListAnnexures> origUserList;

    ImageLoader il = new ImageLoader(context);



    /**
     * @param context
     * @param objects
     */
    public CabinetAnnextureMemosAdapter(Context context, List<ListAnnexures> objects) {

        this.context = context;
        this.news = objects;
        this.origUserList = objects;
    }


    /**
     * @return
     */
    @Override
    public int getCount() {
        return news.size();
    }


    /**
     * @param position
     * @return
     */
    @Override
    public ListAnnexures getItem(int position) {
        return news.get(position);
    }

    /**
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cabinet_memo_annexture_list, parent, false);

        ListAnnexures u = news.get(position);
        TextView name = view.findViewById(R.id.name);
        TextView state_dept = view.findViewById(R.id.state_dept);
        TextView central_dept = view.findViewById(R.id.central_dept);
        ImageView imageView1 = view.findViewById(R.id.imageView1);


        central_dept.setVisibility(View.GONE);
        state_dept.setVisibility(View.GONE);


        name.setText(u.getAnnexureID());



        return view;
    }

    /**
     *
     */
    public void resetData() {
        news = origUserList;
    }

    @Override
    public Filter getFilter() {
        if (planetFilter == null)
            planetFilter = new CabinetAnnextureMemosAdapter.PlanetFilter();

        return planetFilter;
    }

    /**
     *
     */
    private class PlanetFilter  extends Filter {



        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origUserList;
                results.count = origUserList.size();
            }
            else {
                // We perform filtering operation
                List<ListAnnexures> nPlanetList = new ArrayList<>();

                for (ListAnnexures p : news) {
                    if (p.getAnnexureName().toUpperCase().contains(constraint.toString().toUpperCase())){
                        nPlanetList.add(p);
                    }//else{
//                        nPlanetList.remove(p);
//                    }

                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }


        /**
         * @param constraint
         * @param results
         */
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                news = (List<ListAnnexures>) results.values;
                notifyDataSetChanged();
            }

        }

    }

}