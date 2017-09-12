package android.com.keiladaily.fragment;

import android.com.keiladaily.R;
import android.com.keiladaily.activity.MainActivity;
import android.com.keiladaily.other.JasonData;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.defaultValue;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemsDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItemsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemsDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int getItemId;
    private OnFragmentInteractionListener mListener;
    private String TAG = MainActivity.class.getSimpleName();
    public ItemsDetailFragment() {
        // Required empty public constructor
    }
    String imageItem, categoryItem, titleItem, dateItem, contentItem;
    TextView categoryItemView, titleItemView, dateItemView, contentItemView;
    ImageView imageItemView;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemsDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemsDetailFragment newInstance(String param1, String param2) {
        ItemsDetailFragment fragment = new ItemsDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }

    private View myFragmentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_items_detail, container, false);
        //get ItemId
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            getItemId = bundle.getInt("getItemId", defaultValue);

            categoryItemView = (TextView) myFragmentView.findViewById(R.id.categoryItemsDetail);
            titleItemView = (TextView) myFragmentView.findViewById(R.id.titleItemsDetail);
            dateItemView = (TextView) myFragmentView.findViewById(R.id.dateItemsDetail);
            contentItemView = (TextView) myFragmentView.findViewById(R.id.contentItemsDetail);
            imageItemView = (ImageView) myFragmentView.findViewById(R.id.imageItemsDetail);

            new GetJasons().execute();
        }
        return  myFragmentView;
    }
    private class GetJasons extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Toast.makeText(JsonParsers.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
//
//        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try{
                bindItem();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            showItem();
        }
        private void showItem(){
            categoryItemView.setText(categoryItem);
            titleItemView.setText(titleItem);
            dateItemView.setText(dateItem);
            contentItemView.setText(contentItem);
            Picasso.with(getContext()).load(imageItem.replaceAll(" ", "%20")).into(imageItemView);
        }
        private void bindItem(){
            // Jason Data
            String url = "http://tostenh.com/ContentAll/ShowContentAmountById/" + getItemId;
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    //JSONArray getContents = jsonObj.getJSONArray("Content");
                    //int j=0;
                    // looping through All Contacts
                    //for (int i = 0; i < getContents.length(); i++) {
                        //if(i>0) {
                        JSONObject content = jsonObj.getJSONObject("Content");
                        if(content != null) {
                            String id = content.getString("id");
                            String description = content.getString("description");
                            String full_text = content.getString("full_text");
                            String text_title = content.getString("text_title");
                            String cat_title = content.getString("c_title");
                            String count = content.getString("count");
                            String publish_date = content.getString("publish_date");
                            String media = content.getString("media");

                            imageItem = media;
                            categoryItem = cat_title;
                            titleItem = text_title;
                            dateItem = publish_date;
                            contentItem = full_text;
                        }
                        //    j++;
                        //}

                    //}
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText( getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
