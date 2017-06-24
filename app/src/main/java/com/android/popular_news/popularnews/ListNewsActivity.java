package com.android.popular_news.popularnews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.popular_news.popularnews.data.remote.ResponseModel;
import com.android.popular_news.popularnews.data.remote.Result;
import com.android.popular_news.popularnews.model.*;

import com.android.popular_news.popularnews.model.Article;
import com.android.popular_news.popularnews.model.NYTService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.*;

public class ListNewsActivity extends AppCompatActivity {
    final String nyt_api_key = "b26f537258304cbd8e78503c5690c7f4";
    private Adapter mAdapter;
    private NYTService service;

    /**
     * bottom navigation panel listener
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_most_emailed:
                    handleResponse(service.getMostEmailedNews(nyt_api_key));
                    return true;
                case R.id.navigation_most_shared:
                    handleResponse(service.getMostSharedNews(nyt_api_key));
                    return true;
                case R.id.navigation_most_viewed:
                    handleResponse(service.getMostViewedNews(nyt_api_key));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(0).setChecked(true);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new Adapter(new Adapter.Callback() {
            @Override
            public void show(Article news) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);

        service = ApiUtils.getNYTService();
        handleResponse(service.getMostViewedNews(nyt_api_key));
    }

    /**
     * handle request response
     * @param call
     */
    public void handleResponse(Call call){
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel articles = response.body();

                    List<Article> parsed_articles = getArticles(articles.getResults());

                    setRecyclerData(parsed_articles);

                } else {
                    Toast.makeText(ListNewsActivity.this, "An error occured", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ListNewsActivity.this, "No internet connection", Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }

    private void setRecyclerData(List<Article> parsed_articles) {
        mAdapter.setData(parsed_articles);
        mAdapter.notifyDataSetChanged();
    }

    private List<Article> getArticles(List<Result> results) {
        ArrayList<Article> articles = new ArrayList<>();
        for (Result res : results) {
            articles.add(new Article(res.getTitle(), res.getAbstract(), res.getUrl(), res
                    .getPublishedDate(), res.getByline()));
        }
        return articles;
    }


    /**
     * Recycler View Adapter
     */
    private static class Adapter extends RecyclerView.Adapter {
        List<Article> mData;
        Callback mCallback;

        public Adapter(Callback callBack) {
            mCallback = callBack;
        }

        public interface Callback {
            void show(Article news);
        }

        public void setData(List<Article> data) {
            mData = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .single_news_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((ViewHolder) holder).bind(mData.get(position), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.show(mData.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData != null ? mData.size() : 0;
        }

        /**
         * viewholder
         */
        static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView mTitle;
            private final TextView mAbstract;
            private final TextView publishdate;
            private final TextView author;

            ViewHolder(View itemView) {
                super(itemView);

                //  Cache all the views we will need when binding the model
                mTitle = (TextView) itemView.findViewById(R.id.news_title);
                mAbstract = (TextView) itemView.findViewById(R.id.news_abstract);
                publishdate = (TextView) itemView.findViewById(R.id.publishdate);
                author = (TextView) itemView.findViewById(R.id.author);

            }

            void bind(Article article, View.OnClickListener onClickListener) {
                //  The views are cached, just set the data
                mTitle.setText(article.getTitle());
                mAbstract.setText(article.getAbstract_section());
                publishdate.setText(article.getPublishedDate());
                author.setText(article.getAuthor());
                itemView.setOnClickListener(onClickListener);
            }
        }
    }
}
