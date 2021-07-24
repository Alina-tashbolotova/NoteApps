package com.example.noteapps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.noteapps.utils.PreferencesHelper;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.noteapps.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    ImageView navUsername;
    SharedPreferences sharedPreferences;
    PreferencesHelper preferencesHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        preferencesHelper = new PreferencesHelper();
        preferencesHelper.init(MainActivity.this);
        if (!preferencesHelper.isShown()) {
            navController.navigate(R.id.onBoardFragment);
        }
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        binding.appBarMain.fab.setOnClickListener(view -> {
            navController.navigate(R.id.action_nav_home_to_formFragment);
        });
        destination(navController);
        imageView();
        saveImage();

    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                try {
                    final InputStream imageStream = getContentResolver().openInputStream(uri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    sharedPreferences.edit().putString("key", encodedImage).commit();
                    preferencesHelper.saveImage();
                    Glide.with(this)
                            .load(uri)
                            .circleCrop()
                            .into(navUsername);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });

    private void destination(NavController navController) {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (controller.getGraph().getStartDestination() == destination.getId()) {
                binding.appBarMain.fab.show();
            } else {
                binding.appBarMain.fab.hide();
            }
            if (destination.getId() == R.id.formFragment || destination.getId() == R.id.onBoardFragment) {
                binding.appBarMain.toolbar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void imageView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navUsername = (ImageView) headerView.findViewById(R.id.imageView);


        navUsername.setOnClickListener(v -> {
            mGetContent.launch("image/*");
        });

        navUsername.setOnLongClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(navUsername.getContext()).create();
            dialog.setTitle("Внимание!!!");
            dialog.setMessage("Вы точно хотите удалить?");
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    preferencesHelper.onSaveDefaultImage();
                    Glide.with(MainActivity.this)
                            .load(mGetContent)
                            .circleCrop()
                            .placeholder(R.drawable.ic_person)
                            .into(navUsername);
                }
            });
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Нет", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();
            return false;
        });


    }


    private void saveImage() {
        sharedPreferences = getSharedPreferences("", MODE_PRIVATE);
        if (preferencesHelper.isShownImage()) {
            if (!sharedPreferences.getString("key", "").equals("")) {
                byte[] decString = Base64.decode(sharedPreferences.getString("key", ""), Base64.DEFAULT);
                Bitmap decByte = BitmapFactory.decodeByteArray(decString, 0, decString.length);
                navUsername.setImageBitmap(decByte);
            }
        }

    }

}






