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
import com.dit.himachal.rms.modal.AdvocateListPojo;

import java.util.ArrayList;
import java.util.List;

public class AdvocateListAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<AdvocateListPojo> news;

    private Filter planetFilter;
    private List<AdvocateListPojo> origUserList;

    ImageLoader il = new ImageLoader(context);
    String param_ = null;


    /**
     * @param context
     * @param objects
     */
    public AdvocateListAdapter(Context context, List<AdvocateListPojo> objects) {

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
    public AdvocateListPojo getItem(int position) {
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
        View view = inflater.inflate(R.layout.cabinet_memo_list, parent, false);

        AdvocateListPojo u = news.get(position);
        TextView name = view.findViewById(R.id.name);
        TextView state_dept = view.findViewById(R.id.state_dept);
        TextView central_dept = view.findViewById(R.id.central_dept);
        ImageView imageView1 = view.findViewById(R.id.imageView1);
        TextView number = view.findViewById(R.id.number);
        TextView details = view.findViewById(R.id.details);

            il.DisplaySquareImage(u.getPassportPhoto(),imageView1,null,null,false);

           // imageView1.setImageDrawable(context.getResources().getDrawable(R.drawable.judge));
            state_dept.setVisibility(View.GONE);
            number.setVisibility(View.GONE);

            name.setText(u.getAdvocateName());
            state_dept.setText( u.getBarCouncilName());
            central_dept.setText(u.getDateofRegistration());







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
            planetFilter = new AdvocateListAdapter.PlanetFilter();

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
                List<AdvocateListPojo> nPlanetList = new ArrayList<>();

                for (AdvocateListPojo p : news) {
                    if (p.getAdvocateName().toUpperCase().contains(constraint.toString().toUpperCase())){
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
                news = (List<AdvocateListPojo>) results.values;
                notifyDataSetChanged();
            }

        }

    }

}