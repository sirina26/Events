package com.example.myapplication;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.myapplication.ZXing.IntentIntegrator;
import com.example.myapplication.ZXing.IntentResult;
import com.example.myapplication.adapter.ConfirmatedAdapter;
import com.example.myapplication.models.Invitation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private static final int REQUEST_CODE = 26;

    private ConfirmatedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //on recupere les vues de l'activity main pour pouvoir les utiliser dans l'applica
        inflateViews();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));// on dit au recycler de se presenter sous forme de liste si tu veux que ce soit en grille alors faudra utiliser new GridLayoutManager()
        recyclerView.setAdapter(adapter); // chaque item du recyclerview sera gerer par adapter
        databind(true);// on recupere les données a mette dans le recycler
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                databind(false);
            }
        }); // quand on va rafraichir en tirant vers le bas on rappel la methode qui recupere les données et les met dans le recycler

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Snackbar.make(view, "Scanner les codes des invités ?", Snackbar.LENGTH_LONG)
                        .setAction("Oui", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String[] perms = {
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                };

                                if (!EasyPermissions.hasPermissions(view.getContext(), perms)) {
                                    EasyPermissions.requestPermissions(MainActivity.this, view.getContext().getString(R.string.rationale_permission), REQUEST_CODE, perms);//quand on a pas la permission de prendre des photos ou de stocker le contenu du scan dans la memoire cache du telephone alors on demande les permissions
                                } else {
                                    scanCode();//c'est la methode qui permet de faire le scan. si l'utilisateur n'a pas l'appli il le redirigera pour le télecharger sur play store
                                }
                            }
                        }).show();
            }
        });//nous permet de faire le scan des code bar et qrcode
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                String content = result.getContents();
            }
        }
    }

    private void databind(boolean show) {
        if (show) {
            showProgress(true);
        }
        //Recuperer le données depuis le serveur ici

        List<Invitation> invitations = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            Invitation invitation = new Invitation();
            invitation.setName("Invité " + i);
            invitation.setPhone("+33 2 14 89 12 9" + i);
            invitation.setAvatar("Url de la photo " + i);
            invitations.add(invitation);
        } //on insere dans une liste d'invité juste 15 valeur pour notre test.
        //c'est ici qu'il faudra faire la requete vers la bd distante ou en locale pour recuperer les données

        adapter = new ConfirmatedAdapter(this, invitations);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
        integrator.initiateScan();
    }

    private void showProgress(final boolean show) {

        //on fais une petite animation quand on recupere les données apres avoir caché la liste view et afficher le progressbar. une fois l'animation fini et le données recuperer on refais l'inverse

        int animTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        /*recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        recyclerView.animate().setDuration(animTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });*/

        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBar.animate().setDuration(animTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBar.setVisibility(show ? View.VISIBLE : View.GONE );
            }
        });
    }

    private void inflateViews() {
        swipeRefreshLayout = findViewById(R.id.confirmated_swipe);
        recyclerView = findViewById(R.id.rv_confirmated);
        progressBar = findViewById(R.id.progressbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_present:
                //au click sur invité present dans le menu on ouvre la PrincipaleActivity
                startActivity(new Intent(MainActivity.this, PrincipaleActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
