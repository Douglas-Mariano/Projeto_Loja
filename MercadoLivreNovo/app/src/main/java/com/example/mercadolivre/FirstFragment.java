package com.example.mercadolivre;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mercadolivre.adater.CustomListAdapter;
import com.example.mercadolivre.app.AppController;
import com.example.mercadolivre.databinding.FragmentFirstBinding;
import com.example.mercadolivre.model.Oferta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private static String url = "https://api.mercadolibre.com/sites/MLB/search?q=";
    private ProgressDialog pDialog;
    private List<Oferta> ofertaList = new ArrayList<Oferta>();
    private CustomListAdapter adapter;
    private EditText edtValor;
    private Button btnBuscar;
    private ListView listView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) view.findViewById(R.id.list);
        edtValor = (EditText) view.findViewById(R.id.edtValor);
        btnBuscar = (Button) view.findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getData();
            }
        });

        adapter = new CustomListAdapter(this.getActivity(), ofertaList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).setSelectedOferta((Oferta) parent.getItemAtPosition(position));
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    private void getData() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        ofertaList.clear();
        JsonObjectRequest ofertaReq = new JsonObjectRequest(0, url + edtValor.getText(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hidePDialog();
                        try {
                            JSONArray jr = response.getJSONArray("results");
                            // Parsing json
                            for (int i = 0; i < jr.length(); i++) {


                                JSONObject obj = jr.getJSONObject(i);
                                Oferta oferta = new Oferta();
                                oferta.setCodOferta(obj.getString("id"));
                                oferta.setTitle(obj.getString("title"));
                                oferta.setThumbnailUrl(obj.getString("thumbnail"));
                                oferta.setPrice(((Number) obj.get("price")).doubleValue());
                                oferta.setQuant(obj.getInt("available_quantity"));

                                ArrayList<String> condition = new ArrayList<String>();
                                condition.add(obj.getString("condition"));
                                oferta.setCondition(condition);
                                ofertaList.add(oferta);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
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