package kg.kloop.kloopapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private FeedParser parser;
    InputStream input;
    List<FeedParser.Entry> entries;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        parser = new FeedParser();

        new ListKloopTitlesTask().execute();

    }

    private class ListKloopTitlesTask extends AsyncTask<Integer, Integer, Integer> {
        protected Integer doInBackground(Integer... num) {
            try {
                input = new URL("http://kyrgyzmedia.com/feed/atom/").openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                entries = parser.parse(input);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Integer result) {

            Log.v("Check entries", entries.toString());

            adapter = new ArrayAdapter(MainActivity.this,
                                       R.layout.support_simple_spinner_dropdown_item,
                                       entries);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();;
        }


    }
}
