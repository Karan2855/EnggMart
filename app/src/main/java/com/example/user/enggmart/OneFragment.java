package com.example.user.enggmart;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class OneFragment extends Fragment {
    private RecyclerView mPostsListView;
    private PostsItemViewAdapter mPostsItemViewAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    public OneFragment() {
    }

    private GetPostsAsyncTask mGetPostsAsyncTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        mPostsListView = view.findViewById(R.id.posts_list_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String item = OneFragment.this.getArguments().getString(Constants.POST_ITEM);
        if (item != null) {
            mGetPostsAsyncTask = new GetPostsAsyncTask();
            mGetPostsAsyncTask.execute();
        }
    }

    private void setPosts(List<PostModel> postModels) {
        mLayoutManager =new LinearLayoutManager(getActivity());
        mPostsListView.setLayoutManager(mLayoutManager);

        mProductItemViewAdapter = new ProductItemViewAdapter(getActivity(), products);
        mProductsListView.setAdapter(mProductItemViewAdapter);
    }

    /**
     * AsyncTask to get products data & set in the list
     */
    private class GetPostsAsyncTask extends AsyncTask<Void, Void, List<PostModel>> {

        /**
         * used to get products data in a separate thread
         * @param voids
         * @return List<PostModels>
         */
        @Override
        protected List<PostModel> doInBackground(Void... voids) {
            String[] urls = ImageUrls.imageUrls;
            List<PostModel> posts = new ArrayList<>();

            for (int i = 0; i < 20; i++) {
                PostModel postModel = new PostModel();
                postModel.setUrl(urls[i]);
                product.setProductName("Product : " + i);
                product.setProductDescription("Product Descriptions");

                posts.add(postModel);
            }
            return posts;
        }

        /**
         * Set the adapter after getting the products
         * @param postModels
         */
        @Override
        protected void onPostExecute(List<PostModel> postModels) {
            super.onPostExecute(postModels);

            setPosts(postModels);
        }
    }



}
