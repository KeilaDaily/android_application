package android.com.keiladaily.fragment;

import android.com.keiladaily.R;
import android.com.keiladaily.activity.ItemActivity;
import android.com.keiladaily.activity.MainActivity;
import android.com.keiladaily.listener.OnLoadMoreListener;
import android.com.keiladaily.model.User;
import android.com.keiladaily.other.JasonData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SportFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SportFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SportFragment newInstance(String param1, String param2) {
        SportFragment fragment = new SportFragment();
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

    private Toolbar mToolbar;
    private String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private List<User> mUsers = new ArrayList<>();
    private List<User> temUsers = new ArrayList<>();
    private UserAdapter mUserAdapter;
    String jsonStrAllContents = null;
    private View myFragmentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_boxing, container, false);

        mToolbar = (Toolbar) myFragmentView.findViewById(R.id.toolbar);


        mRecyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recycleView);
        mToolbar.setTitle("LoadMoreRecycleView");

        new GetJasons().execute();
        return myFragmentView;
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
                mUsers = bindToListFootball();
                jsonStrAllContents = getJasonArrayAllContect();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            showListFootball();
        }
        private void showListFootball(){
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mUserAdapter = new UserAdapter();
            mRecyclerView.setAdapter(mUserAdapter);
            mUserAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mUsers.add(null);
                    //mUserAdapter.notifyItemInserted(mUsers.size() - 1);
                    mRecyclerView.post(new Runnable() {
                        public void run() {
                            mUserAdapter.notifyItemInserted(mUsers.size() - 1);
                        }
                    });
                    //Load more data for reyclerview
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Remove loading item
                            mUsers.remove(mUsers.size() - 1);
                            mUserAdapter.notifyItemRemoved(mUsers.size());
                            int index = mUsers.size() * 2;
                            int end = index + 20;
                            boolean checkMoreValue = false;
                            try {
                                User user = new User();
                                JSONObject jsonObj = new JSONObject(jsonStrAllContents);
                                // Getting JSON Array node
                                JSONArray getContents = jsonObj.getJSONArray("Content");
                                // looping through All Contacts
                                for (int i = 0; i < getContents.length(); i++) {

                                    if(i>=index) {
                                        JSONObject content = getContents.getJSONObject(i);
                                        String id = content.getString("id");
                                        String description = content.getString("description");
                                        String cat_id = content.getString("cat_id");
                                        String publish_date = content.getString("publish_date");
                                        String media = content.getString("media");


                                        if(i%2 == 0) {
                                            user.setDate(publish_date);
                                            user.setContent(description);
                                            user.setImageUrl(media);
                                            user.setId(id);
                                        }
                                        else {
                                            user.setDate1(publish_date);
                                            user.setContent1(description);
                                            user.setImageUrl1(media);
                                            user.setId1(id);
                                            mUsers.add(user);
                                            user = new User();
                                        }
                                        checkMoreValue = true;
                                        //break;
                                    }
                                    if(i == end){
                                        break;
                                    }
                                }
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
                            if(checkMoreValue == true) {
                                mUserAdapter.notifyDataSetChanged();
                                mUserAdapter.setLoaded();
                            }
                        }
                    }, 500);
                }
            });
        }
        private List<User> bindToListFootball(){
            List<User> getListUser =  new ArrayList<>();
            // Jason Data
            String url = getString(R.string.url) + "ContentAll/ShowContentByCategory/5?amount=10";
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    User user = new User();
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray getContents = jsonObj.getJSONArray("Content");
                    int j = 0;
                    // looping through All Contacts
                    for (int i = 0; i < getContents.length(); i++) {
                        JSONObject content = getContents.getJSONObject(i);
                        String id = content.getString("id");
                        String description = content.getString("description");
                        String cat_id = content.getString("cat_id");
                        String publish_date = content.getString("publish_date");
                        String media = content.getString("media");


                        if(i%2 == 0) {
                            user.setDate(publish_date);
                            user.setContent(description);
                            user.setImageUrl(media);
                            user.setId(id);
                            if(i + 1 == getContents.length()){
                                getListUser.add(user);
                                user = new User();
                            }
                        }
                        else {
                            user.setDate1(publish_date);
                            user.setContent1(description);
                            user.setImageUrl1(media);
                            user.setId1(id);
                            getListUser.add(user);
                            user = new User();
                        }
                    }
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
            return getListUser;
        }
        private String getJasonArrayAllContect(){
            //List<User> getListUser =  new ArrayList<>();
            JSONArray  getContents = null;
            // Jason Data
            String url = getString(R.string.url) + "ContentAll/ShowContentByCategory/5";
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            return jsonStr;
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
    class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;

        private OnLoadMoreListener mOnLoadMoreListener;

        private boolean isLoading;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;

        public UserAdapter() {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            });
        }

        public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
            this.mOnLoadMoreListener = mOnLoadMoreListener;
        }

        @Override
        public int getItemViewType(int position) {
            return mUsers.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_user_item, parent, false);
                return new UserViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof UserViewHolder) {
                final User user = mUsers.get(position);
                UserViewHolder userViewHolder = (UserViewHolder) holder;
                try {
                    userViewHolder.tvDAte.setText(user.getDate());
                    userViewHolder.tvContent.setText(user.getContent());
                    Picasso.with(getContext()).load(user.getImageUrl().replaceAll(" ", "%20")).into(userViewHolder.ivImage);
                    userViewHolder.ivImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ItemActivity.class);
                            intent.putExtra("getId", user.getId());
                            startActivity(intent);
                        }
                    });
                    userViewHolder.tvDAte.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ItemActivity.class);
                            intent.putExtra("getId", user.getId());
                            startActivity(intent);
                        }
                    });
                    userViewHolder.tvContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ItemActivity.class);
                            intent.putExtra("getId", user.getId());
                            startActivity(intent);
                        }
                    });
                }
                catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                try {
                    userViewHolder.tvDAte1.setText(user.getDate1());
                    userViewHolder.tvContent1.setText(user.getContent1());
                    Picasso.with(getContext()).load(user.getImageUrl1().replaceAll(" ", "%20")).into(userViewHolder.ivImage1);

                    userViewHolder.ivImage1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ItemActivity.class);
                            intent.putExtra("getId", user.getId1());
                            startActivity(intent);
                        }
                    });
                    userViewHolder.tvDAte1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ItemActivity.class);
                            intent.putExtra("getId", user.getId1());
                            startActivity(intent);
                        }
                    });
                    userViewHolder.tvContent1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ItemActivity.class);
                            intent.putExtra("getId", user.getId1());
                            startActivity(intent);
                        }
                    });
                }
                catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

            } else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
        }

        @Override
        public int getItemCount() {
            return mUsers == null ? 0 : mUsers.size();
        }

        public void setLoaded() {
            isLoading = false;
        }
    }
    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDAte;
        public TextView tvContent;
        public ImageView ivImage;
        public TextView tvDAte1;
        public TextView tvContent1;
        public ImageView ivImage1;
        public UserViewHolder(View itemView) {
            super(itemView);
            tvDAte = (TextView) itemView.findViewById(R.id.tvDateLoading);
            tvContent = (TextView) itemView.findViewById(R.id.tvContentLoading);
            ivImage = (ImageView) itemView.findViewById(R.id.iconLoading);

            tvDAte1 = (TextView) itemView.findViewById(R.id.tvDateLoading1);
            tvContent1 = (TextView) itemView.findViewById(R.id.tvContentLoading1);
            ivImage1 = (ImageView) itemView.findViewById(R.id.iconLoading1);
        }
    }
}
