package com.dit.himachal.rms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dit.himachal.rms.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;

    public SliderAdapter(Context context) {
        this.context = context;

    }

    @Override
    public SliderAdapter.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapter.SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapter.SliderAdapterVH viewHolder, int position) {
        viewHolder.textViewDescription.setText("Revenue Monitoring System V1.0");
        viewHolder.textViewDescription.setVisibility(View.VISIBLE);

        switch (position) {
            case 0:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.slider1)
                        .into(viewHolder.imageViewBackground);
                break;

            case 1:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.slider2)
                        .into(viewHolder.imageViewBackground);
                break;
//
//            case 2:
//                Glide.with(viewHolder.itemView)
//                        .load(R.drawable.apple_three)
//                        .into(viewHolder.imageViewBackground);
//                break;


        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            //textViewDescription.setText("eCabinet V2.0");
            this.itemView = itemView;
        }
    }
}