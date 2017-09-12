package android.com.keiladaily.other;

import android.com.keiladaily.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Star on 23-Mar-17.
 */

public class CustomList extends BaseAdapter {

    Context context;
    String countryList[];
    //int flags[];
    String imageList[];
    //LayoutInflater inflter;
    String publicDateList[];
    public CustomList(Context applicationContext, String[] countryList, String imageList[], String publicDateList[]) {
        this.context = applicationContext;
        this.countryList = countryList;
        this.imageList = imageList;
        this.publicDateList = publicDateList;
        //this.inflter = LayoutInflater.from(applicationContext);
    }

    @Override
    public int getCount() {
        return countryList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ViewGroup p = viewGroup;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_single, p, false);
        }
        TextView publicDate = (TextView) v.findViewById(R.id.textdate);
        TextView country = (TextView) v.findViewById(R.id.textView);
        ImageView icon = (ImageView) v.findViewById(R.id.icon);
        country.setText(countryList[i]);
        publicDate.setText(publicDateList[i]);

        Picasso.with(this.context).load(imageList[i].replaceAll(" ", "%20")).into(icon);
        //icon.setImageResource(flags[i]);
        return v;
    }
}