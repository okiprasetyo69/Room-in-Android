package com.iak.okiprasetyo.androidroomiak;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iak.okiprasetyo.androidroomiak.data.ConfigureTableContact;
import com.iak.okiprasetyo.androidroomiak.provider.ContenctProviderContact;

import butterknife.ButterKnife;
import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_contentprovider)
    RecyclerView recyclerContentprovider;

    private static final int LoaderKontak = 1;
    private RecycleAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerContentprovider.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecycleAdapter();
        recyclerContentprovider.setAdapter(recyclerAdapter);
        getSupportLoaderManager().initLoader(LoaderKontak, null, loaderCallBack);
    }

    //recycle adapter
    private class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
        private Cursor cursor;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (cursor.moveToPosition(position)) {
                holder.textRecycler.setText(
                        cursor.getString(cursor.getColumnIndexOrThrow(ConfigureTableContact.ColumnName)));
            }
        }

        @Override
        public int getItemCount() {
            return cursor == null ? 0 : cursor.getCount();
        }


        public void setRecycler(Cursor data) {
            cursor = data;
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textRecycler;

            public ViewHolder(ViewGroup parent) {
                super(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
                textRecycler = itemView.findViewById(android.R.id.text1);
            }
        }
    }

    //loader manager
    private LoaderManager.LoaderCallbacks<Cursor> loaderCallBack = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            switch (id) {
                case LoaderKontak:
                    return new CursorLoader(getApplicationContext(), ContenctProviderContact.URLContentProvider,
                            new String[]{ConfigureTableContact.ColumnName}, null, null, null);
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            switch (loader.getId()) {
                case LoaderKontak:
                    recyclerAdapter.setRecycler(data);
                    break;
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            switch (loader.getId()) {
                case LoaderKontak:
                    recyclerAdapter.setRecycler(null);
                    break;
            }
        }
    };
}
