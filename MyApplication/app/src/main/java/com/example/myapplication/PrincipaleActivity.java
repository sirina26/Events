package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.adapter.ConfirmatedAdapter;
import com.example.myapplication.models.Invitation;

import java.util.ArrayList;
import java.util.List;

public class PrincipaleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    //faire un autre adapter pour gerer la liste des présents
    private ConfirmatedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        inflateViews();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                databind(false);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        databind(true);
    }

    private void showProgress(final boolean show) {

        int animTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        /*recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        recyclerView.animate().setDuration(animTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });*/

        /*progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBar.animate().setDuration(animTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });*/
    }

    private void inflateViews() {
        swipeRefreshLayout = findViewById(R.id.present_swipe);
        recyclerView = findViewById(R.id.present_rv);
        //progressBar = findViewById(R.id.progressbar);
    }

    private void databind(boolean show) {
        if (show) {
            showProgress(true);
        }
        //Recuperer le données depuis le serveur ici

        List<Invitation> invitations = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            Invitation invitation = new Invitation();
            invitation.setName("Invité " + i);
            invitation.setPhone("+33 2 14 89 12 9" + i);
            invitation.setAvatar("Url de la photo " + i);
            invitations.add(invitation);
        }

        adapter = new ConfirmatedAdapter(this, invitations);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.menu_principale, menu);
        return true;*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
