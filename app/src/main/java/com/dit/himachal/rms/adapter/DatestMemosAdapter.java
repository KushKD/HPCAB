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
import com.dit.himachal.rms.modal.Dates;

import java.util.ArrayList;
import java.util.List;

public class DatestMemosAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Dates> news;

    private Filter planetFilter;
    private List<Dates> origUserList;

    ImageLoader il = new ImageLoader(context);
    String param_ = null;


    /**
     * @param context
     * @param objects
     */
    public DatestMemosAdapter(Context context, List<Dates> objects, String param) {

        this.context = context;
        this.news = objects;
        this.origUserList = objects;
        this.param_ =param;
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
    public Dates getItem(int position) {
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

        Dates u = news.get(position);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView state_dept = (TextView) view.findViewById(R.id.state_dept);
        TextView central_dept = (TextView) view.findViewById(R.id.central_dept);
        ImageView imageView1 = (ImageView)view.findViewById(R.id.imageView1);

        if(param_.equalsIgnoreCase("Forwarded")){
            imageView1.setImageDrawable(context.getResources().getDrawable(R.drawable.forward_memos));
            state_dept.setVisibility(View.VISIBLE);
        }else if(param_.equalsIgnoreCase("Backwarded")){
            imageView1.setImageDrawable(context.getResources().getDrawable(R.drawable.sent_back_memos));
            state_dept.setVisibility(View.GONE);
        }else if(param_.equalsIgnoreCase("Cabinet_Decisions")){
            imageView1.setImageDrawable(context.getResources().getDrawable(R.drawable.sent_back_memos));
            state_dept.setVisibility(View.VISIBLE);
        }

        else{
            imageView1.setImageDrawable(context.getResources().getDrawable(R.drawable.cabinet_memos));
            state_dept.setVisibility(View.VISIBLE);
        }





        name.setText(u.getDepartmentName());
        state_dept.setText("Total Number :-  "+u.getTotalCabinetMemos());
        central_dept.setText(u.getMeetingDate());
//        if(u.getStateDept().isEmpty() || u.getStateDept().equalsIgnoreCase("null")){
//            state_dept.setVisibility(View.GONE);
//        }else{
//            state_dept.setText(u.getStateDept());
//        }
//        if(u.getCentralDept().isEmpty() || u.getCentralDept().equalsIgnoreCase("null")){
//            central_dept.setVisibility(View.GONE);
//        }else{
//            central_dept.setText(u.getCentralDept());
//        }



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
            planetFilter = new DatestMemosAdapter.PlanetFilter();

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
                List<Dates> nPlanetList = new ArrayList<>();

                for (Dates p : news) {
                    if (p.getDepartmentName().toUpperCase().contains(constraint.toString().toUpperCase())){
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
                news = (List<Dates>) results.values;
                notifyDataSetChanged();
            }

        }

    }

}