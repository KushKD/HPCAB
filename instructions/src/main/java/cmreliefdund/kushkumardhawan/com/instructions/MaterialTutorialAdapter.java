package cmreliefdund.kushkumardhawan.com.instructions;

//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;



public class MaterialTutorialAdapter extends FragmentPagerAdapter {

    private List<MaterialTutorialFragment> fragments;

    public MaterialTutorialAdapter(FragmentManager fm, List<MaterialTutorialFragment> fragments) {
        super(fm);
        this.fragments = fragments;

    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }


    @Override
    public int getCount() {
        return this.fragments.size();
    }

}
