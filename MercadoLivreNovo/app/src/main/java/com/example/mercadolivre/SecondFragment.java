package com.example.mercadolivre;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mercadolivre.app.AppController;
import com.example.mercadolivre.databinding.FragmentSecondBinding;
import com.example.mercadolivre.model.Oferta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private static final String url = "https://api.mercadolibre.com/items/";
    private ProgressDialog pDialog;
    private TextView texto;
    private Object JSONException;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Oferta o = ((MainActivity) getActivity()).getSelectedOferta();

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) view.findViewById(R.id.thumbnail);
        thumbNail.setImageUrl(o.getThumbnailUrl(), imageLoader);
        ((TextView) view.findViewById(R.id.title)).setText(o.getTitle());
        ((TextView) view.findViewById(R.id.price)).setText(String.valueOf(o.getPrice()));
        ((TextView) view.findViewById(R.id.quant)).setText(String.valueOf(o.getQuant()));
     //   ((TextView) view.findViewById(R.id.estado)).setText(o.getCodOferta());
        texto = ((TextView) view.findViewById(R.id.texto));
        texto.setMovementMethod(new ScrollingMovementMethod());
        getData(o.getCodOferta());

    }

    private void getData(String codOferta) {

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        JsonObjectRequest ofertaReq = new JsonObjectRequest(0,
                url + codOferta,
                null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {

                        hidePDialog();
                        texto.setText(response.toString());

                        try {

                            texto.setText(response.getString("seller_id"));

                            JSONArray jr = response.getJSONArray("results");
                            // Parsing json
                            for (int i = 0; i < jr.length(); i++) {

                            }
                        } catch (org.json.JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(ofertaReq);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}