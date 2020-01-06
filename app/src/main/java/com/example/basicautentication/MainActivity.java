package com.example.basicautentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.basicautentication.Model.Profesor;
import com.example.basicautentication.api.WebServiceApi;
import com.example.basicautentication.api.WebServiceBA;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;

public class MainActivity extends AppCompatActivity {

    private MessageDigest md;
    private Button btnPublico;
    private Button btnUser;
    private Button btnAdmin;
    private Button btnAdminWithHeader;
    private Button btnUserWithHeaderandHash;
    private Button btnUserWithHeaderandHashUserPassword;
    private EditText txtUserName;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();

    }

    private void setUpView() {
       btnPublico = findViewById(R.id.btnPublico);
       btnAdmin = findViewById( R.id.btnAdmin);
       btnUser= findViewById(R.id.btnUser);
       btnAdminWithHeader= findViewById(R.id.btnAdminWithHeader);
       btnUserWithHeaderandHash= findViewById(R.id.btnUserWithHeaderandHash);
       btnUserWithHeaderandHashUserPassword= findViewById(R.id.btnUserWithHeaderandHashUserPassword);

       txtUserName=findViewById(R.id.txtUserName);
       txtPassword=findViewById(R.id.txtPassword);

       btnPublico.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               solicitudSinAutenticar();

           }
       });

       btnAdmin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               solicitudAutenticadoAdmin();
           }
       });
       btnUser.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               solicitudAutenticadoUser();
           }
       });
       btnAdminWithHeader.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               solicitudAutenticadoAdminWithHeader();
           }
       });
        btnUserWithHeaderandHash.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               solicitudAutenticadoUserHash();
           }
       });
        btnUserWithHeaderandHashUserPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitudAutenticadoUserWithHeaderandHashUserPassword();
            }
        });
    }

    private void solicitudAutenticadoUserWithHeaderandHashUserPassword() {
        String user = txtUserName.getText().toString().trim();
        String pass= txtPassword.getText().toString().trim();
        String passwordHash = encrypPassword(pass);
        String userPassword= user+":"+passwordHash;
        String authHeader = "Basic "+Base64.encodeToString(userPassword.getBytes(),Base64.NO_WRAP);


        Call<List<Profesor>> call= WebServiceBA.getInstance().createService(WebServiceApi.class).listAllProfesorUser(authHeader);
        call.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
                if(response.code()==200){
                    for(int i = 0;i<response.body().size();i++){
                        Log.d("TAG1","Nombre Profesor: "+response.body().get(i).getName()+"Salario: "+response.body().get(i).getSalary()+
                                " ID: "+response.body().get(i).getId());
                    }
                }else if(response.code()==204){
                    Log.d("TAG1", "No hay profesores ");
                }else if(response.code()==403){
                    Log.d("TAG1","Forbidden: No tienes permiso para el recurso");
                }else if(response.code()==401){
                    Log.d("TAG1","No existe ese usuario");
                }else {
                    Log.d("TAG1","Error no definido ");
                }
            }

            @Override
            public void onFailure(Call<List<Profesor>> call, Throwable t) {
                Log.d("TAG1",t.getMessage());
            }
        });
    }

    private void solicitudAutenticadoUserHash() {
        String passwordHash = encrypPassword("user");
        Log.d("TAG1", passwordHash);
        String userPassword="user:"+passwordHash;
        String authHeader= "Basic "+ Base64.encodeToString(("user:"+passwordHash).getBytes(),Base64.NO_WRAP);
        Call<List<Profesor>> call= WebServiceBA.getInstance().createService(WebServiceApi.class).listAllProfesorUser(authHeader);
        call.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
                if(response.code()==200){
                    for(int i = 0;i<response.body().size();i++){
                        Log.d("TAG1","Nombre Profesor: "+response.body().get(i).getName()+"Salario: "+response.body().get(i).getSalary()+
                                " ID: "+response.body().get(i).getId());
                    }
                }else if(response.code()==204){
                    Log.d("TAG1", "No hay profesores ");
                }else if(response.code()==403){
                    Log.d("TAG1","Forbidden: No tienes permiso para el recurso");
                }else if(response.code()==401){
                    Log.d("TAG1","No existe ese usuario");
                }else {
                    Log.d("TAG1","Error no definido ");
                }
            }

            @Override
            public void onFailure(Call<List<Profesor>> call, Throwable t) {
                Log.d("TAG1",t.getMessage());
            }
        });


    }

    private void solicitudAutenticadoAdminWithHeader() {
        String authHeader ="Basic "+ Base64.encodeToString(("alberto:alberto").getBytes(),Base64.NO_WRAP);
        Call<List<Profesor>> call= WebServiceBA.getInstance().createService(WebServiceApi.class).listAllProfesorAdmin(authHeader);
        call.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
                if(response.code()==200){
                    for(int i = 0;i<response.body().size();i++){
                        Log.d("TAG1","Nombre Profesor: "+response.body().get(i).getName()+"Salario: "+response.body().get(i).getSalary()+
                                " ID: "+response.body().get(i).getId());
                    }
                }else if(response.code()==204){
                    Log.d("TAG1", "No hay profesores ");
                }else if(response.code()==403){
                    Log.d("TAG1","Forbidden: No tienes permiso para el recurso");
                }else if(response.code()==401){
                    Log.d("TAG1","No existe ese usuario");
                }else {
                    Log.d("TAG1","Error no definido ");
                }
            }

            @Override
            public void onFailure(Call<List<Profesor>> call, Throwable t) {
                Log.d("TAG1",t.getMessage());
            }
        });






    }

    private void solicitudAutenticadoUser() {
        Call<List<Profesor>> call= WebServiceBA.getInstance().createService(WebServiceApi.class).listAllProfesorUser();
        call.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
                if(response.code()==200){
                    for(int i = 0;i<response.body().size();i++){
                        Log.d("TAG1","Nombre Profesor: "+response.body().get(i).getName()+"Salario: "+response.body().get(i).getSalary()+
                                " ID: "+response.body().get(i).getId());
                    }
                }else if(response.code()==204){
                    Log.d("TAG1", "No hay profesores ");
                }else if(response.code()==403){
                    Log.d("TAG1","Forbidden: No tienes permiso para el recurso");
                }else if(response.code()==401){
                    Log.d("TAG1","No existe ese usuario");
                }else {
                    Log.d("TAG1","Error no definido ");
                }
            }

            @Override
            public void onFailure(Call<List<Profesor>> call, Throwable t) {
                Log.d("TAG1",t.getMessage());
            }
        });




    }

    private void solicitudAutenticadoAdmin() {
        Call<List<Profesor>> call= WebServiceBA.getInstance().createService(WebServiceApi.class).listAllProfesorAdmin();
        call.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
                if(response.code()==200){
                    for(int i = 0;i<response.body().size();i++){
                        Log.d("TAG1","Nombre Profesor: "+response.body().get(i).getName()+"Salario: "+response.body().get(i).getSalary()+
                                " ID: "+response.body().get(i).getId());
                    }
                }else if(response.code()==204){
                    Log.d("TAG1", "No hay profesores ");
                }else if(response.code()==403){
                    Log.d("TAG1","Forbidden: No tienes permiso para el recurso");
                }else if(response.code()==401){
                Log.d("TAG1","No existe ese usuario");
                }else {
                    Log.d("TAG1","Error no definido ");
                }
            }

            @Override
            public void onFailure(Call<List<Profesor>> call, Throwable t) {
                Log.d("TAG1",t.getMessage());
            }
        });

    }

    private void solicitudSinAutenticar() {
      Call<List<Profesor>> call= WebServiceBA.getInstance().createService(WebServiceApi.class).listAllProfesorPublic();
      System.out.println("D".getBytes());
      call.enqueue(new Callback<List<Profesor>>() {
          @Override
          public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
              if(response.code()==200){
                 for(int i = 0;i<response.body().size();i++){
                     Log.d("TAG1","Nombre Profesor: "+response.body().get(i).getName()+"Salario: "+response.body().get(i).getSalary()+
                             " ID: "+response.body().get(i).getId());
                 }
              }else if(response.code()==204){
                  Log.d("TAG1", "No hay profesores ");
              }
          }

          @Override
          public void onFailure(Call<List<Profesor>> call, Throwable t) {

          }
      });
    }

    private  String encrypPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger bigInteger = new BigInteger(1,messageDigest);
            String hashPassword = bigInteger.toString(16);
            while (hashPassword.length()<32){
                hashPassword= "0"+hashPassword;
            }
             return hashPassword;


        }catch (NoSuchAlgorithmException e){
           throw  new RuntimeException(e);
        }

    }
}
