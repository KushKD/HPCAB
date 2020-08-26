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


    /**
     * @param context
     * @param objects
     */
    public CabinetMemosAdapter(Context context, List<CabinetMemoPojo> objects) {

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
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView state_dept = (TextView) view.findViewById(R.id.state_dept);
        TextView central_dept = (TextView) view.findViewById(R.id.central_dept);
        ImageView imageView1 = (ImageView)view.findViewById(R.id.imageView1);


//
//        if(u.getLogo().isEmpty()||u.getLogo()==null){
//
//
//            String fnm = "uttarakhand";
//            String PACKAGE_NAME = context.getApplicationContext().getPackageName();
//            int imgId = this.context.getApplicationContext().getResources().getIdentifier(PACKAGE_NAME+":drawable/"+fnm , null, null);
//            System.out.println("IMG ID :: "+imgId);
//            System.out.println("PACKAGE_NAME :: "+PACKAGE_NAME);
//
//            imageView1.setImageBitmap(BitmapFactory.decodeResource(context.getApplicationContext().getResources(),imgId));
//        }else{
//            //load icon from url
//            Log.e("Icon Server",u.getLogo());
//            il.DisplayImage(u.getLogo(), imageView1, null,null, false);
//        }











        name.setText(u.getSubject());
        state_dept.setText(u.getAgendaItemType());
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
                    if (p.getFileNo().toUpperCase().contains(constraint.toString().toUpperCase())){
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