package com.efreipicturestudio.ui.fragment.common;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efreipicturestudio.R;

/**
 * Created by tlanquetin on 06/12/15.
 * Fragment mère de tout les fragments
 */
public class EPSFragment extends Fragment {




    protected Context myContext;


    /**
     * Inflates the {@link android.view.View} which will be displayed by this {@link android.support.v4.app.Fragment}, from the app's
     * resources.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }

    /**
     * This is called after the {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)} has finished.
     * Here we can pick out the {@link android.view.View}s we need to configure from the content view.
     *
     * @param view View created in {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext = context;
    }

    @Override
    public void  onDetach() {
        super.onDetach();
        myContext = null;
    }

    //region Navigation

    /**
     * Fonction pour avoir un id unique pour chaque fragment (utile pour les id dans la stack de fragment de navigation)
     * @return Un identifiant unique
     */
    public String getUniqueId() {
        return this.getClass().getName() + " " + hashCode();
    }

    //endregion Navigation
}
