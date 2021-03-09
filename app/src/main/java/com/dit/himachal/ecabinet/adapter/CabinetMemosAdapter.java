package com.dit.himachal.ecabinet.adapter;

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

import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.lazyloader.ImageLoader;
import com.dit.himachal.ecabinet.modal.CabinetMemoPojo;

import java.util.ArrayList;
import java.util.List;

public class CabinetMemosAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<CabinetMemoPojo> news;

    private Filter planetFilter;
    private List<CabinetMemoPojo> origUserList;

    ImageLoader il = new ImageLoader(context);
    String param_ = null;


    /**
     * @param context
     * @param objects
     */
    public CabinetMemosAdapter(Context context, List<CabinetMemoPojo> objects, String param) {

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
    public CabinetMemoPojo getItem(int position) {
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

        CabinetMemoPojo u = news.get(position);
        TextView name = view.findViewById(R.id.name);
        TextView state_dept = view.findViewById(R.id.state_dept);
        TextView central_dept = view.findViewById(R.id.central_dept);
        ImageView imageView1 = view.findViewById(R.id.imageView1);
        TextView number = view.findViewById(R.id.number);

        if (param_.equalsIgnoreCase("Forwarded")) {
            imageView1.setImageDrawable(context.getResources().getDrawable(R.drawable.forward_memos));
            state_dept.setVisibility(View.GONE);
            number.setVisibility(View.GONE);

            name.setText(u.getSubject());
            state_dept.setText("Agenda Item No:- " + u.getAgendaItemType());
            central_dept.setText(u.getDeptName());
        } else if (param_.equalsIgnoreCase("Backwarded")) {
            imageView1.setImageDrawable(context.getResources().getDrawable(R.drawable.sent_back_memos));
            state_dept.setVisibility(View.GONE);
            number.setVisibility(View.GONE);

            name.setText(u.getSubject());
            state_dept.setText("Agenda Item No:- " + u.getAgendaItemType());
            central_dept.setText(u.getDeptName());
        }else if(param_.equalsIgnoreCase("PlacedInCabinet")){
            imageView1.setImageDrawable(context.getResources().getDrawable(R.drawable.sent_back_memos));
            state_dept.setVisibility(View.VISIBLE);
            number.setVisibility(View.GONE);
            name.setText(u.getSubject());
            state_dept.setText("Date:- " +u.getMeetingdate() +", Item No:- "+u.getAgendaItemType());
          //  state_dept.setText("Item No:- "+u.getAgendaItemType());
            central_dept.setText(u.getDeptName());
        }
        else {
            imageView1.setImageDrawable(context.getResources().getDrawable(R.drawable.cabinet_memos));
            state_dept.setVisibility(View.VISIBLE);
            name.setText(u.getSubject());
            number.setVisibility(View.VISIBLE);
            number.setText("Item Number:- " + u.getAgendaItemNo());
            state_dept.setText("Item Type :- " + u.getAgendaItemType());
            central_dept.setText(u.getDeptName());
        }


          //  PlacedInCabinet






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
            planetFilter = new CabinetMemosAdapter.PlanetFilter();

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
                List<CabinetMemoPojo> nPlanetList = new ArrayList<>();

                for (CabinetMemoPojo p : news) {
                    if (p.getSubject().toUpperCase().contains(constraint.toString().toUpperCase())){
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
                news = (List<CabinetMemoPojo>) results.values;
                notifyDataSetChanged();
            }

        }

    }

}