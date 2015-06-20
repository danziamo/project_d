package com.mirsoft.easyfixmaster;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;

public class FixNavigationDrawer extends MaterialNavigationDrawer {
    private static final int SELECT_PICTURE = 1;
    @Override
    public void init(Bundle savedInstanceState) {

        // create and set the header
        View view = LayoutInflater.from(this).inflate(R.layout.custom_drawer,null);

        ImageButton btnImage = (ImageButton)view.findViewById(R.id.imageView);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FixNavigationDrawer.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        setDrawerHeaderCustom(view);

/*public class FixNavigationDrawer extends MaterialNavigationDrawer implements MaterialAccountListener {
    @Override
    public void init(Bundle bundle) {
        MaterialAccount account = new MaterialAccount(this.getResources(), "Title", "Subtitle", R.drawable.foto, R.color.material_deep_teal_500);
        this.addAccount(account);*/

        //this.setAccountListener(this);
        this.addSection(newSection("Уведомления", new MainActivityFragment()));
        this.addSection(newSection("История заказов", new MainActivityFragment()));
        this.addSection(newSection("Помощь", TestFragment.newInstance(null, null)));
        this.addSection(newSection("Рекомендовать программу", new MainActivityFragment()));
        this.addSection(newSection("О программе", new MainActivityFragment()));
        this.addSection(newSection("Правила", new MainActivityFragment()));
        this.addSection(newSection("Продлить подписку", new MainActivityFragment()));
        this.addBottomSection(newSection("Выход", R.mipmap.ic_launcher, new Intent(this, MainActivity.class)));

        /*this.addSection(newSection("Section 3", R.drawable.ic_mic_white_24dp, new FragmentButton()).setSectionColor(Color.parseColor("#9c27b0")));
        this.addSection(newSection("Section", R.drawable.ic_hotel_grey600_24dp, new FragmentButton()).setSectionColor(Color.parseColor("#03a9f4")));*/
    }
}
