package android.com.keiladaily.activity;

import android.com.keiladaily.R;
import android.com.keiladaily.other.JasonData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemActivity extends AppCompatActivity {

    String imageItem, categoryItem, titleItem, dateItem, contentItem;
    TextView categoryItemView, titleItemView, dateItemView, contentItemView;
    ImageView imageItemView;
    LinearLayout linearLayoutContentValue;
    private String TAG = MainActivity.class.getSimpleName();
    int getItemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        if(intent.getStringExtra("getId") != null) {
            getItemId = Integer.parseInt(intent.getStringExtra("getId"));

            categoryItemView = (TextView) findViewById(R.id.categoryItems);
            titleItemView = (TextView) findViewById(R.id.titleItems);
            dateItemView = (TextView) findViewById(R.id.dateItems);
            //contentItemView = (TextView) findViewById(R.id.contentItems);
            imageItemView = (ImageView) findViewById(R.id.imageItems);
            linearLayoutContentValue = (LinearLayout) findViewById(R.id.linearLayoutContentValue);
            new GetJasons().execute();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            Picasso.with( getApplicationContext()).load(imageItem.replaceAll(" ", "%20")).into(imageItemView);

            //contentItemView.setText(contentItem);
            String[] separateContentItem = contentItem.split("@@");
            for(int i=0;i<separateContentItem.length;i++){
                linearLayoutContentValue.setOrientation(LinearLayout.VERTICAL);
                if(separateContentItem[i].contains("http://")){
                    ImageView imageItemContent = new ImageView(ItemActivity.this);
                    imageItemContent.setPadding(10,0,10,0);
                    Picasso.with( getApplicationContext()).load(separateContentItem[i].replaceAll(" ", "%20")).into(imageItemContent);

                    linearLayoutContentValue.addView(imageItemContent);
                }
                else {
                    if(i != 0) {
                        TextView textViewContent = new TextView(ItemActivity.this);
                        textViewContent.setPadding(10, 0, 10, 0);
                        if (i == separateContentItem.length - 1)
                            textViewContent.setPadding(10, 0, 10, 60);
                        textViewContent.setTextSize(25);
                        textViewContent.setText(separateContentItem[i]);
                        linearLayoutContentValue.addView(textViewContent);
                    }
                }
            }

        }
        private void bindItem(){
            // Jason Data
            String url = getString(R.string.url) + "ContentAll/ShowContentAmountWithSplitById/" + getItemId;
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
                    ItemActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText( ItemActivity.this,
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                ItemActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ItemActivity.this,
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}
