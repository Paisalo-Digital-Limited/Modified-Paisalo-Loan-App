package com.softeksol.paisalo.jlgsourcing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.entities.Manager;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiClient;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiInterface;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Morpho_Recharge_Entry extends AppCompatActivity {
Manager manager;
TextView creatorName,branchCodeName;

TextInputEditText edit_textdevSrNum;
Button btnRechargeDevId;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morpho_recharge_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Morpho Device Recharge");
        ApiInterface apiInterface= ApiClient.getClient(SEILIGL.NEW_SERVERAPI).create(ApiInterface.class);

        creatorName=findViewById(R.id.creatorName);
        edit_textdevSrNum=findViewById(R.id.edit_textdevSrNum);
        branchCodeName=findViewById(R.id.branchCodeName);
        btnRechargeDevId=findViewById(R.id.btnRechargeDevId);

        manager= SQLite.select().from(Manager.class).querySingle();
        creatorName.setText(manager.Creator);
        branchCodeName.setText(manager.FOCode);
        btnRechargeDevId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_textdevSrNum.getText().toString().trim().length()>1){
                    Call<JsonObject> call=apiInterface.sendDataForMorphoRecharge(getJsonObject());
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject jsonObject=response.body();
                            if (jsonObject.get("statusCode").getAsInt()==200){
                                Toast.makeText(Morpho_Recharge_Entry.this, ""+jsonObject.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(Morpho_Recharge_Entry.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("TAG", "onFailure: "+t.getMessage());
                            Toast.makeText(Morpho_Recharge_Entry.this, ""+t.getMessage()
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(Morpho_Recharge_Entry.this, "Please Enter Device Serial Number!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
      


    }

    private JsonObject getJsonObject() {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("Creator", manager.Creator);
        jsonObject.addProperty("GroupCode","");
        jsonObject.addProperty("CityCode", manager.FOCode);
        jsonObject.addProperty("DeviceSirNo",edit_textdevSrNum.getText().toString());
        return  jsonObject;
    }
}