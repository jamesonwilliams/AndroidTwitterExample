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
        cb.setOAuthConsumerKey(mContext.getString(R.string.twitter_oauth_key));
        cb.setOAuthConsumerSecret(mContext.getString(R.string.twitter_oauth_secret));
        return new TwitterFactory(cb.build()).getInstance();
    }

    private void showTweetsAbout(String queryString) {
        List<Tweet> tweets = new ArrayList<Tweet>();
        ArrayList<String> tweetTexts = new ArrayList<String>();

        try {
            tweets = mTwitter.search(new Query(queryString)).getTweets();

            for (Tweet t : tweets) {
                tweetTexts.add(t.getText() + "\n\n");
            }
        } catch (Exception e) {
            tweetTexts.add("Twitter query failed: " + e.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, tweetTexts);
        mListView.setAdapter(adapter);
    }
}

