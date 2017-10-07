package com.example.matheus.arduinotemp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Matheus on 05/10/2017.
 */

public class TempService {

    private final String LTAG = getClass().getSimpleName();

    public static TempService instance;
    public static TempService getInstance(){if(instance == null){instance = new TempService();}return instance;}

    private double temperature;

    public void requisicaoTemperature(Context context,String url){
        Volley.newRequestQueue(context).add(new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(LTAG,"RESPOSTA SUCESSO!: " + response.toString());
                try {
                    TempService.this.temperature = parserJSONTemperature(response);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(LTAG,"RESPOSTA ERRO!: " + error.toString());
                TempService.this.temperature = 999;
            }
        }));
    }


    private double parserJSONTemperature(JSONObject json) throws JSONException {
        Log.i(LTAG,"PARSER JSONOBJECT: " + json.toString());
        return json.getDouble("temperatura");
    }


    public double getTemperature() {
        return temperature;
    }
}
