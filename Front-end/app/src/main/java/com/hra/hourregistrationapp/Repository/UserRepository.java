package com.hra.hourregistrationapp.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.User;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.Persistence.UserDao;
import com.hra.hourregistrationapp.Retrofit.RetrofitClient;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private RetrofitClient retrofitClient ;
    private User user;
    private UserDao userDao;


    public UserRepository(Application application)  {
        LocalDatabase localDatabase = LocalDatabase.getInstance(application.getApplicationContext());
        userDao = localDatabase.userDao();
        try {
            user = new UserAsyncTask(userDao, 1).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        this.retrofitClient = RetrofitClient.getInstance();
    }

    public void sendToken(String idToken, RetrofitResponseListener retrofitResponseListener){
        retrofitClient.getLoginService().verifyToken(idToken).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    user = response.body();
                    new UserAsyncTask(userDao, 2).execute(user);
                    retrofitResponseListener.onSuccess();
                }
                else{
                    retrofitResponseListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                retrofitResponseListener.onFailure();
            }
        });
    }

    public void AddCompanytoUser(User user, Company company, RetrofitResponseListener retrofitResponseListener){
        retrofitClient.getLoginService().createUser(user.getId(), company.getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    retrofitResponseListener.onSuccess();
                }else {
                    retrofitResponseListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                retrofitResponseListener.onFailure();
            }
        });
    }

    public User getUser() {
        User user2;
        try {
            user2 = new UserAsyncTask(userDao, 1).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            user2 = null;
        }
        return user2;
    }

    public void deleteAll(){
        new UserAsyncTask(userDao, 3).execute();
    }

    private static class UserAsyncTask extends AsyncTask<User, Void, User> {
        private UserDao userDao;
        private int taskCode;

        private static final int TASK_GET = 1;
        private static final int TASK_INSERT = 2;
        private static final int TASK_DELETEALL = 3;


        UserAsyncTask(UserDao userDao, int taskCode) {
            this.userDao = userDao;
            this.taskCode = taskCode;
        }

        @Override
        protected User doInBackground(User... users) {
            switch (taskCode){
                case TASK_GET:
                    return userDao.getAll();
                case TASK_INSERT:
                    userDao.insert(users[0]);
                    return null;
                case TASK_DELETEALL:
                    userDao.deleteAllFromTable();
            }
            return null;
        }
    }
}
