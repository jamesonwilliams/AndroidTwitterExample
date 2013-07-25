package org.nosemaj.twitterexample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

public class TwitterActivity extends Activity {
    Context mContext;
    Twitter mTwitter;
    ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mListView = (ListView) findViewById(R.id.listview);
        mContext = getApplicationContext();
        mTwitter = getTwitter();
    }

    @Override
    public void onResume() {
        super.onResume();
        showTweetsAbout("Potatos");
    }

    private Twitter getTwitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(getString(R.string.consumer_key));
        cb.setOAuthConsumerSecret(getString(R.string.consumer_secret));
        cb.setOAuthAccessToken(getString(R.string.access_token));
        cb.setOAuthAccessTokenSecret(getString(R.string.access_token_secret));
        return new TwitterFactory(cb.build()).getInstance();
    }

    private void showTweetsAbout(String queryString) {
        List<Status> statuses = new ArrayList<Status>();
        ArrayList<String> statusTexts = new ArrayList<String>();

        try {
            statuses = mTwitter.search(new Query(queryString)).getTweets();

            for (Status s : statuses) {
                statusTexts.add(s.getText() + "\n\n");
            }
        } catch (Exception e) {
            statusTexts.add("Twitter query failed: " + e.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, statusTexts);
        mListView.setAdapter(adapter);
    }
}

