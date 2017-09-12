package android.com.keiladaily.fragment;

import android.com.keiladaily.R;
import android.com.keiladaily.activity.ItemActivity;
import android.com.keiladaily.activity.MainActivity;
import android.com.keiladaily.other.CustomList;
import android.com.keiladaily.other.JasonData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment  implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    //List View Custom
    ListView simpleList,simpleListFootball,simpleListNoccMoeys,simpleListSport,simpleListLife,simpleListSportOnline;
    String list[] = new String[4], listMedia[] = new String[4], listPublicDate[] = new String[4], listIdItem[] = new String[4];
    String listFootball[] = new String[4], listMediaFootball[] = new String[4], listPublicDateFootball[] = new String[4], listFootballIdItem[] = new String[4];
    String listNoccMoeys[] = new String[4], listMediaNoccMoeys[] = new String[4], listPublicDateNoccMoeys[] = new String[4], listNoccIdItem[] = new String[4];
    String listSport[] = new String[4], listMediaSport[] = new String[4], listPublicDateSport[] = new String[4], listSportIdItem[] = new String[4];
    String listLife[] = new String[4], listMediaLife[] = new String[4], listPublicDateLife[] = new String[4], listLifeIdItem[] = new String[4];
    String listSportOnline[] = new String[4], listMediaSportOnline[] = new String[4], listPublicDateSportOnline[] = new String[4], listSportOnlineIdItem[] = new String[4];

    //Text View Category
    TextView footBallTV, noccTV, sportTV, lifeTV, sportDailyTV;

    //Block Image
    String footBallImage,footBallTitle, footBallDate, footBallId, noccImage, noccTitle, noccDate, noccId;
    String sportImage, sportTitle, sportDate, sportId, lifeImage, lifeTitle, lifeDate, lifeId, sportOnlineImage, sportOnlineTitle, sportOnlineDate, sportOnlineId;
    ImageView imageFootballView, imageNoccView, imageSportView, imageLifeView, imageSportOnlineView;
    TextView textFootballTitle, textNoccTitle, textSportTitle, textLifeTitle, textSportOnlineTitle;
    TextView textFootballDate, textNoccDate, textSportDate, textLifeDate, textSportOnlineDate;

    private String[] activityTitles;
    //Slider
    SliderLayout sliderLayout ;
    HashMap<String, String> HashMapForURL ;
    //Fragment
    private View myFragmentView;
    private String TAG = MainActivity.class.getSimpleName();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        // Scroll View
        final ScrollView scrollView = (ScrollView) myFragmentView.findViewById(R.id.scrollViewHome);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        //SlideShow
        sliderLayout = (SliderLayout) myFragmentView.findViewById(R.id.slider);
        //Category
        footBallTV = (TextView) myFragmentView.findViewById(R.id.football_id);
        footBallTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FootballFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(activityTitles[3]);
            }
        });
        noccTV = (TextView) myFragmentView.findViewById(R.id.NoccMoeys_id);
        noccTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NoccMoeysFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(activityTitles[4]);
            }
        });
        sportTV = (TextView) myFragmentView.findViewById(R.id.Sport_id);
        sportTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SportFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(activityTitles[5]);
            }
        });
        lifeTV = (TextView) myFragmentView.findViewById(R.id.Life_id);
        lifeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LifeFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(activityTitles[1]);
            }
        });
        //List View Custom
        simpleList = (ListView) myFragmentView.findViewById(R.id.simpleListView);
        simpleListFootball = (ListView) myFragmentView.findViewById(R.id.simpleListViewFootball);
        simpleListNoccMoeys = (ListView) myFragmentView.findViewById(R.id.simpleListViewNoccMoeys);
        simpleListSport = (ListView) myFragmentView.findViewById(R.id.simpleListViewSport);
        simpleListLife = (ListView) myFragmentView.findViewById(R.id.simpleListViewLife);
        simpleListSportOnline = (ListView) myFragmentView.findViewById(R.id.simpleListViewSportOnline);


        //Bloack Image
        textFootballTitle = (TextView) myFragmentView.findViewById(R.id.image_football_title);
        textFootballDate = (TextView) myFragmentView.findViewById(R.id.image_football_date);
        imageFootballView = (ImageView) myFragmentView.findViewById(R.id.image_football);

        textNoccTitle = (TextView) myFragmentView.findViewById(R.id.image_NoccMoeys_title);
        textNoccDate = (TextView) myFragmentView.findViewById(R.id.image_NoccMoeys_date);
        imageNoccView = (ImageView) myFragmentView.findViewById(R.id.image_NoccMoeys);
        textSportTitle = (TextView) myFragmentView.findViewById(R.id.image_Sport_title);
        textSportDate = (TextView) myFragmentView.findViewById(R.id.image_Sport_date);
        imageSportView = (ImageView) myFragmentView.findViewById(R.id.image_Sport);
        textLifeTitle = (TextView) myFragmentView.findViewById(R.id.image_Life_title);
        textLifeDate = (TextView) myFragmentView.findViewById(R.id.image_Life_date);
        imageLifeView = (ImageView) myFragmentView.findViewById(R.id.image_Life);
        textSportOnlineTitle = (TextView) myFragmentView.findViewById(R.id.image_SportOnline_title);
        textSportOnlineDate = (TextView) myFragmentView.findViewById(R.id.image_SportOnline_date);
        imageSportOnlineView = (ImageView) myFragmentView.findViewById(R.id.image_SportOnline);
        new GetJasons().execute();

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_UP);
            }
        });
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

            try {
                bindToSlideShow();
                bindToListFootball();
                bindToList();
                bindToListNoccMoeys();
                bindToListSport();
                bindToListLife();
                bindToListSportOnline();
                bindToBlockImageFootball();
                bindToBlockImageNocc();
                bindToBlockImageSport();
                bindToBlockImageLife();
                bindToBlockImageSportOnline();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try{
                showSlideShow();
                showList();
                showListFootball();
                showListNoccMoeys();
                showListSport();
                showListLife();
                showListSportOnline();
                showBlockImageFootball();
                showBlockImageNocc();
                showBlockImageSport();
                showBlockImageLife();
                showBlockImageSportOnline();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
        private void showList(){
            CustomList customAdapter = new CustomList(getContext(), list, listMedia, listPublicDate);
            simpleList.setAdapter(customAdapter);

            simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                    Fragment fragment = new ItemsDetailFragment();
//                    Bundle args = new Bundle();
//                    args.putInt("getItemId", Integer.parseInt(listIdItem[i]));
//                    fragment.setArguments(args);
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, fragment);
//                    fragmentTransaction.commitAllowingStateLoss();
                    Intent intent = new Intent(getActivity(), ItemActivity.class);
                    intent.putExtra("getId", listIdItem[i]);
                    startActivity(intent);
                }
            });
        }
        private void showSlideShow(){
            for(String name : HashMapForURL.keySet()){

                TextSliderView textSliderView = new TextSliderView(getContext());

                textSliderView
                        .description(name)
                        .image(HashMapForURL.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(HomeFragment.this);

                textSliderView.bundle(new Bundle());

                textSliderView.getBundle()
                        .putString("extra",name);

                sliderLayout.addSlider(textSliderView);
            }
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sliderLayout.setCustomAnimation(new DescriptionAnimation());
            sliderLayout.setDuration(3000);
            sliderLayout.addOnPageChangeListener(HomeFragment.this);
        }
        private void bindToSlideShow(){
            // Jason Data
            String url = getString(R.string.url) + "SlideShow";
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray getSlideShows = jsonObj.getJSONArray("SlideShow");

                    HashMapForURL = new HashMap<String, String>();


                    // looping through All Contacts
                    for (int i = 0; i < getSlideShows.length(); i++) {
                        JSONObject slideShow = getSlideShows.getJSONObject(i);
                        String id = slideShow.getString("slide_id");
                        String title = slideShow.getString("s_title");
                        String link = slideShow.getString("link");
                        String img = slideShow.getString("s_img");
                        String ordering = slideShow.getString("ordering");

                        HashMapForURL.put(title,img.replaceAll(" ", "%20"));
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
        }
        private void bindToList(){
            // Jason Data
            String url = getString(R.string.url) + "ContentAll/ShowContentAmount/4";
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray getContents = jsonObj.getJSONArray("Content");

                    // looping through All Contacts
                    for (int i = 0; i < getContents.length(); i++) {
                        JSONObject content = getContents.getJSONObject(i);
                        String id = content.getString("id");
                        String description = content.getString("description");
                        String cat_id = content.getString("cat_id");
                        String publish_date = content.getString("publish_date");
                        String media = content.getString("media");
                        list[i] = description;
                        listMedia[i] = media;
                        listPublicDate[i] = publish_date;
                        listIdItem[i] = id;

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
        }
        private void showListFootball(){
            CustomList customAdapter = new CustomList(getContext(), listFootball, listMediaFootball, listPublicDateFootball);
            simpleListFootball.setAdapter(customAdapter);
            simpleListFootball.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Fragment fragment = new ItemsDetailFragment();
//                    Bundle args = new Bundle();
//                    args.putInt("getItemId", Integer.parseInt(listFootballIdItem[i]));
//                    fragment.setArguments(args);
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, fragment);
//                    fragmentTransaction.commitAllowingStateLoss();
                    Intent intent = new Intent(getActivity(), ItemActivity.class);
                    intent.putExtra("getId", listFootballIdItem[i]);
                    startActivity(intent);
                }
            });
        }
        private void bindToListFootball(){
            // Jason Data
            String url = getString(R.string.url) + "ContentAll/ShowContentByCategory/2?amount=5";
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray getContents = jsonObj.getJSONArray("Content");
                    int j = 0;
                    // looping through All Contacts
                    for (int i = 0; i < getContents.length(); i++) {
                        if(i>0) {
                            JSONObject content = getContents.getJSONObject(i);
                            String id = content.getString("id");
                            String description = content.getString("description");
                            String cat_id = content.getString("cat_id");
                            String publish_date = content.getString("publish_date");
                            String media = content.getString("media");
                            listFootball[j] = description;
                            listMediaFootball[j] = media;
                            listPublicDateFootball[j] = publish_date;
                            listFootballIdItem[j] = id;
                            j++;
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
        }
        private void showListNoccMoeys(){
            CustomList customAdapter = new CustomList(getContext(), listNoccMoeys, listMediaNoccMoeys, listPublicDateNoccMoeys);
            simpleListNoccMoeys.setAdapter(customAdapter);
            simpleListNoccMoeys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Fragment fragment = new ItemsDetailFragment();
//                    Bundle args = new Bundle();
//                    args.putInt("getItemId", Integer.parseInt(listNoccIdItem[i]));
//                    fragment.setArguments(args);
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, fragment);
//                    fragmentTransaction.commitAllowingStateLoss();
                    Intent intent = new Intent(getActivity(), ItemActivity.class);
                    intent.putExtra("getId", listNoccIdItem[i]);
                    startActivity(intent);
                }
            });
        }
        private void bindToListNoccMoeys(){
            // Jason Data
            String url = getString(R.string.url) + "ContentAll/ShowContentByCategory/4?amount=5";
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray getContents = jsonObj.getJSONArray("Content");
                    int j = 0;
                    // looping through All Contacts
                    for (int i = 0; i < getContents.length(); i++) {
                        if(i>0) {
                            JSONObject content = getContents.getJSONObject(i);
                            String id = content.getString("id");
                            String description = content.getString("description");
                            String cat_id = content.getString("cat_id");
                            String publish_date = content.getString("publish_date");
                            String media = content.getString("media");
                            listNoccMoeys[j] = description;
                            listMediaNoccMoeys[j] = media;
                            listPublicDateNoccMoeys[j] = publish_date;
                            listNoccIdItem[j] = id;
                            j++;
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
        }
        private void showListSport(){
            CustomList customAdapter = new CustomList(getContext(), listSport, listMediaSport, listPublicDateSport);
            simpleListSport.setAdapter(customAdapter);
            simpleListSport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Fragment fragment = new ItemsDetailFragment();
//                    Bundle args = new Bundle();
//                    args.putInt("getItemId", Integer.parseInt(listSportIdItem[i]));
//                    fragment.setArguments(args);
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, fragment);
//                    fragmentTransaction.commitAllowingStateLoss();
                    Intent intent = new Intent(getActivity(), ItemActivity.class);
                    intent.putExtra("getId", listSportIdItem[i]);
                    startActivity(intent);
                }
            });
        }
        private void bindToListSport(){
            // Jason Data
            String url = getString(R.string.url) + "ContentAll/ShowContentByCategory/5?amount=5";
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray getContents = jsonObj.getJSONArray("Content");
                    int j = 0;
                    // looping through All Contacts
                    for (int i = 0; i < getContents.length(); i++) {
                        if(i>0) {
                            JSONObject content = getContents.getJSONObject(i);
                            String id = content.getString("id");
                            String description = content.getString("description");
                            String cat_id = content.getString("cat_id");
                            String publish_date = content.getString("publish_date");
                            String media = content.getString("media");
                            listSport[j] = description;
                            listMediaSport[j] = media;
                            listPublicDateSport[j] = publish_date;
                            listSportIdItem[j] = id;
                            j++;
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
        }
        private void showListLife(){
            CustomList customAdapter = new CustomList(getContext(), listLife, listMediaLife, listPublicDateLife);
            simpleListLife.setAdapter(customAdapter);
            simpleListLife.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Fragment fragment = new ItemsDetailFragment();
//                    Bundle args = new Bundle();
//                    args.putInt("getItemId", Integer.parseInt(listLifeIdItem[i]));
//                    fragment.setArguments(args);
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, fragment);
//                    fragmentTransaction.commitAllowingStateLoss();
                    Intent intent = new Intent(getActivity(), ItemActivity.class);
                    intent.putExtra("getId", listLifeIdItem[i]);
                    startActivity(intent);
                }
            });
        }
        private void bindToListLife(){
            // Jason Data
            String url = getString(R.string.url) + "ContentAll/ShowContentByCategory/6?amount=5";
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray getContents = jsonObj.getJSONArray("Content");
                    int j = 0;
                    // looping through All Contacts
                    for (int i = 0; i < getContents.length(); i++) {
                        if(i>0) {
                            JSONObject content = getContents.getJSONObject(i);
                            String id = content.getString("id");
                            String description = content.getString("description");
                            String cat_id = content.getString("cat_id");
                            String publish_date = content.getString("publish_date");
                            String media = content.getString("media");
                            listLife[j] = description;
                            listMediaLife[j] = media;
                            listPublicDateLife[j] = publish_date;
                            listLifeIdItem[j] = id;
                            j++;
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
        }
        private void showListSportOnline(){
            CustomList customAdapter = new CustomList(getContext(), listSportOnline, listMediaSportOnline, listPublicDateSportOnline);
            simpleListSportOnline.setAdapter(customAdapter);
            simpleListSportOnline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Fragment fragment = new ItemsDetailFragment();
//                    Bundle args = new Bundle();
//                    args.putInt("getItemId", Integer.parseInt(listSportOnlineIdItem[i]));
//                    fragment.setArguments(args);
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, fragment);
//                    fragmentTransaction.commitAllowingStateLoss();
                    Intent intent = new Intent(getActivity(), ItemActivity.class);
                    intent.putExtra("getId", listSportOnlineIdItem[i]);
                    startActivity(intent);
                }
            });
        }
        private void bindToListSportOnline(){
            // Jason Data
            String url = getString(R.string.url) + "ContentAll/ShowContentByCategory/8?amount=4";
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray getContents = jsonObj.getJSONArray("Content");
                    //int j=0;
                    // looping through All Contacts
                    for (int i = 0; i < getContents.length(); i++) {
                        JSONObject content = getContents.getJSONObject(i);
                        String id = content.getString("id");
                        String description = content.getString("description");
                        String cat_id = content.getString("cat_id");
                        String publish_date = content.getString("publish_date");
                        String media = content.getString("media");
                        listSportOnline[i] = description;
                        listMediaSportOnline[i] = media;
                        listPublicDateSportOnline[i] = publish_date;
                        listSportOnlineIdItem[i] = id;
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
        }
        private void showBlockImageFootball(){
            textFootballTitle.setText(footBallTitle);
            textFootballDate.setText(footBallDate);
            Picasso.with(getContext()).load(footBallImage.replaceAll(" ", "%20")).into(imageFootballView);
            lifeTV = (TextView) myFragmentView.findViewById(R.id.Life_id);
            imageFootballView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Fragment fragment = new ItemsDetailFragment();
//                    Bundle args = new Bundle();
//                    args.putInt("getItemId", Integer.parseInt(footBallId));
//                    fragment.setArguments(args);
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, fragment);
//                    fragmentTransaction.commitAllowingStateLoss();
                    Intent intent = new Intent(getActivity(), ItemActivity.class);
                    intent.putExtra("getId", footBallId);
                    startActivity(intent);
                }
            });
        }
        private void bindToBlockImageFootball(){
            // Jason Data Football
            String url = getString(R.string.url) + "ContentAll/ShowContentByCategory/2?amount=1";
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray getContents = jsonObj.getJSONArray("Content");
                    //int j=0;
                    // looping through All Contacts
                    for (int i = 0; i < getContents.length(); i++) {
                        //if(i>0) {
                        JSONObject content = getContents.getJSONObject(i);
                        String id = content.getString("id");
                        String description = content.getString("description");
                        String cat_id = content.getString("cat_id");
                        String publish_date = content.getString("publish_date");
                        String media = content.getString("media");
                        footBallTitle = description;
                        footBallDate = publish_date;
                        footBallImage = media;
                        footBallId = id;
                        //    j++;
                        //}
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
        }
        private void showBlockImageNocc(){
            textNoccTitle.setText(noccTitle);
            textNoccDate.setText(noccDate);
            Picasso.with(getContext()).load(noccImage.replaceAll(" ", "%20")).into(imageNoccView);
            imageNoccView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Fragment fragment = new ItemsDetailFragment();
//                    Bundle args = new Bundle();
//                    args.putInt("getItemId", Integer.parseInt(noccId));
//                    fragment.setArguments(args);
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, fragment);
//                    fragmentTransaction.commitAllowingStateLoss();
                    Intent intent = new Intent(getActivity(), ItemActivity.class);
                    intent.putExtra("getId", noccId);
                    startActivity(intent);
                }
            });
        }
        private void bindToBlockImageNocc(){
            // Jason Data Football
            String url = getString(R.string.url) + "ContentAll/ShowContentByCategory/4?amount=1";
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray getContents = jsonObj.getJSONArray("Content");
                    //int j=0;
                    // looping through All Contacts
                    for (int i = 0; i < getContents.length(); i++) {
                        //if(i>0) {
                        JSONObject content = getContents.getJSONObject(i);
                        String id = content.getString("id");
                        String description = content.getString("description");
                        String cat_id = content.getString("cat_id");
                        String publish_date = content.getString("publish_date");
                        String media = content.getString("media");
                        noccTitle = description;
                        noccDate = publish_date;
                        noccImage = media;
                        noccId = id;
                        //    j++;
                        //}
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
        }
        private void showBlockImageSport(){
            textSportTitle.setText(sportTitle);
            textSportDate.setText(sportDate);
            Picasso.with(getContext()).load(sportImage.replaceAll(" ", "%20")).into(imageSportView);
            imageSportView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Fragment fragment = new ItemsDetailFragment();
//                    Bundle args = new Bundle();
//                    args.putInt("getItemId", Integer.parseInt(sportId));
//                    fragment.setArguments(args);
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, fragment);
//                    fragmentTransaction.commitAllowingStateLoss();
                    Intent intent = new Intent(getActivity(), ItemActivity.class);
                    intent.putExtra("getId", sportId);
                    startActivity(intent);
                }
            });
        }
        private void bindToBlockImageSport(){
            // Jason Data Football
            String url = getString(R.string.url) + "ContentAll/ShowContentByCategory/5?amount=1";
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray getContents = jsonObj.getJSONArray("Content");
                    //int j=0;
                    // looping through All Contacts
                    for (int i = 0; i < getContents.length(); i++) {
                        //if(i>0) {
                        JSONObject content = getContents.getJSONObject(i);
                        String id = content.getString("id");
                        String description = content.getString("description");
                        String cat_id = content.getString("cat_id");
                        String publish_date = content.getString("publish_date");
                        String media = content.getString("media");
                        sportTitle = description;
                        sportDate = publish_date;
                        sportImage = media;
                        sportId = id;
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
        }
        private void showBlockImageLife(){
            textLifeTitle.setText(lifeTitle);
            textLifeDate.setText(lifeDate);
            Picasso.with(getContext()).load(lifeImage.replaceAll(" ", "%20")).into(imageLifeView);
            imageLifeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Fragment fragment = new ItemsDetailFragment();
//                    Bundle args = new Bundle();
//                    args.putInt("getItemId", Integer.parseInt(lifeId));
//                    fragment.setArguments(args);
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, fragment);
//                    fragmentTransaction.commitAllowingStateLoss();
                    Intent intent = new Intent(getActivity(), ItemActivity.class);
                    intent.putExtra("getId", lifeId);
                    startActivity(intent);
                }
            });
        }
        private void bindToBlockImageLife(){
            // Jason Data Football
            String url = getString(R.string.url) + "ContentAll/ShowContentByCategory/6?amount=1";
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray getContents = jsonObj.getJSONArray("Content");
                    //int j=0;
                    // looping through All Contacts
                    for (int i = 0; i < getContents.length(); i++) {
                        //if(i>0) {
                        JSONObject content = getContents.getJSONObject(i);
                        String id = content.getString("id");
                        String description = content.getString("description");
                        String cat_id = content.getString("cat_id");
                        String publish_date = content.getString("publish_date");
                        String media = content.getString("media");
                        lifeTitle = description;
                        lifeDate = publish_date;
                        lifeImage = media;
                        lifeId = id;
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
        }
        private void showBlockImageSportOnline(){
            textSportOnlineTitle.setText(sportOnlineTitle);
            textSportOnlineDate.setText(sportOnlineDate);
            Picasso.with(getContext()).load(sportOnlineImage.replaceAll(" ", "%20")).into(imageSportOnlineView);
            imageSportOnlineView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Fragment fragment = new ItemsDetailFragment();
//                    Bundle args = new Bundle();
//                    args.putInt("getItemId", Integer.parseInt(sportOnlineId));
//                    fragment.setArguments(args);
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, fragment);
//                    fragmentTransaction.commitAllowingStateLoss();
                    Intent intent = new Intent(getActivity(), ItemActivity.class);
                    intent.putExtra("getId", sportOnlineId);
                    startActivity(intent);
                }
            });
        }
        private void bindToBlockImageSportOnline(){
            // Jason Data Football
            String url = getString(R.string.url) + "ContentAll/ShowContentByCategory/8?amount=1";
            JasonData jason = new JasonData();
            String jsonStr = jason.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray getContents = jsonObj.getJSONArray("Content");
                    //int j=0;
                    // looping through All Contacts
                    for (int i = 0; i < getContents.length(); i++) {
                        //if(i>0) {
                        JSONObject content = getContents.getJSONObject(i);
                        String id = content.getString("id");
                        String description = content.getString("description");
                        String cat_id = content.getString("cat_id");
                        String publish_date = content.getString("publish_date");
                        String media = content.getString("media");
                        sportOnlineTitle = description;
                        sportOnlineDate = publish_date;
                        sportOnlineImage = media;
                        sportOnlineId = id;
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
        }
    }
    @Override
    public void onStop() {

        sliderLayout.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        Toast.makeText(getContext(),slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void AddImagesUrlOnline(){

        HashMapForURL = new HashMap<String, String>();

        HashMapForURL.put("កីឡា", "http://keiladaily.com/img/slide/thaerreal.jpg");
        HashMapForURL.put("បាល់ទាត់", "http://keiladaily.com/img/slide/cam-to-jordan-1.gif");
        HashMapForURL.put("ប្រដាល់", "http://keiladaily.com/img/slide/disable-best-athlet-2.gif");
        HashMapForURL.put("ជីវិតពិត", "http://keiladaily.com/img/slide/cam-to-jordan-1.gif");
        HashMapForURL.put("កីឡាដេលី", "http://keiladaily.com/img/thumbs/chess-academy-plan.gif");
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
