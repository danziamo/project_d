package com.mirsoft.easyfix.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mirsoft.easyfix.R;
import com.mirsoft.easyfix.Settings;
import com.mirsoft.easyfix.models.User;
import com.mirsoft.easyfix.models.UserSpecialty;
import com.mirsoft.easyfix.networking.RestClient;
import com.mirsoft.easyfix.networking.api.UserApi;
import com.mirsoft.easyfix.utils.Singleton;
import com.mirsoft.easyfix.views.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyMasterHistoryCommentFragment extends Fragment {

    View view;

    private Integer[] list = new Integer[]{2};

    private int userId;
    private String userPassword;
    private User mUser;

    private ProgressBar progressBar;
    private LinearLayout llProfileInfoContent;

    private EditText etLastName;
    private EditText etFirstName;
    private RatingBar ratingBar;
    private TextView tvFeedbacks;
    private EditText etPhone;
    private EditText etProfession;
    private EditText etLicense;
    private EditText etExperiance;

    private Button btnComment;
    private ImageView ivProfileInfo;

    Singleton singleton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_master_history_comment, container, false);
        singleton = Singleton.getInstance(getActivity());

        progressBar = (ProgressBar) view.findViewById(R.id.history_progress_bar);
        llProfileInfoContent = (LinearLayout) view.findViewById(R.id.linearLayout_history);

        etLastName = (EditText) view.findViewById(R.id.et_last_name);
        etFirstName = (EditText) view.findViewById(R.id.et_first_name);
        etPhone = (EditText) view.findViewById(R.id.et_phone);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        tvFeedbacks = (TextView) view.findViewById(R.id.tvFeedbacks);

        etProfession = (EditText) view.findViewById(R.id.et_profession);
        etLicense = (EditText) view.findViewById(R.id.et_license);
        etExperiance = (EditText) view.findViewById(R.id.et_experience);
        ivProfileInfo = (ImageView) view.findViewById(R.id.profile_photo);
        btnComment = (Button)view.findViewById(R.id.btnCommet);


        Settings settings = new Settings(getActivity());
        userId = settings.getUserId();
        userPassword = settings.getPassword();

        UserApi api = RestClient.createService(UserApi.class);

        api.getById(userId, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                mUser = user;
                updateCommonViews(user);
                getSpecialtyOptions();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), "Error:loading master information", Toast.LENGTH_SHORT);
            }
        });

//        api.getSpecialties(singleton.clientSelectedOrder.getContractor().getId(), new Callback<ArrayList<UserSpecialty>>() {
//            @Override
//            public void success(ArrayList<UserSpecialty> userSpecialties, Response response) {
//                updateView(userSpecialties);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Toast.makeText(getActivity(), "Error:loading master information", Toast.LENGTH_SHORT);
//                error.printStackTrace();
//            }
//        });




        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .customView(R.layout.comment_custom_layout, false)
                        .positiveText(R.string.submit)
                        .negativeText(R.string.btn_cancel)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                Toast.makeText(getActivity(),"Коммент отправлен",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNegative(dialog);
                            }
                        })
                        .show();
            }
        });



        return view;
    }

    private void getSpecialtyOptions(){
        RestClient.getUserService(false).getSpecialties(1, new Callback<ArrayList<UserSpecialty>>() {
            @Override
            public void success(ArrayList<UserSpecialty> userSpecialties, Response response) {
                updateView(userSpecialties);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), "Error:loading master information", Toast.LENGTH_SHORT);
                error.printStackTrace();
            }
        });
    }

    private void updateCommonViews(User user){
        progressBar.setVisibility(View.GONE);
        llProfileInfoContent.setVisibility(View.VISIBLE);
        etLastName.setText(user.getLastName());
        etFirstName.setText(user.getFirstName());
        ratingBar.setRating(user.getRating());
        tvFeedbacks.setText(getResources().getQuantityString(R.plurals.feedback_count, user.getReviewsCount(), user.getReviewsCount()));
        etPhone.setText(user.getPhone());

        RoundedTransformation transformation = new RoundedTransformation(10, 5);
        Picasso.with(getActivity())
                .load("https://scontent.xx.fbcdn.net/hphotos-xtf1/v/t1.0-9/10464122_669886999770868_7199669825191714119_n.jpg?oh=3d8b1edf292f4fef440b870a243a864e&oe=565BAFD9")
                .resize(150, 150)
                .centerCrop()
                .placeholder(R.drawable.no_avatar)
                .error(R.drawable.no_avatar)
                .transform(transformation)
                .into(ivProfileInfo);

    }

    private void updateView(ArrayList<UserSpecialty> specialties){
        for(int i = 0; i < specialties.size();i++){
            if(singleton.clientSelectedOrder.getSpecialty().getId() == specialties.get(i).getId()){
                for(int j = 0; j < singleton.specialtyList.size();j++){
                    if(specialties.get(i).getId() == singleton.specialtyList.get(j).getId())
                        etProfession.setText(singleton.specialtyList.get(j).getName());
                }
                if(specialties.get(i).isCertified()) {
                    etLicense.setText("Лицензия");
                    etLicense.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                }
                else {
                    etLicense.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.false_lic, 0);
                    etLicense.setText("Лицензия");
                }
                etExperiance.setText("6 лет");
            }
        }
    }

}