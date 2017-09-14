package com.example.davidlira.permisos;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == Constants.REQUEST_PERMISSION_CAMERA) {
            Log.e("MyLog", requestCode + "");
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //YA se aceptó el permiso

                Log.e("MyLOg", grantResults[0] + "");
                btnCLick();
                return;
            }

            AlertDialog.Builder variableBuilder = new AlertDialog.Builder(this);
            variableBuilder.setTitle("Negastes el permisto de la camara").setMessage("Esta app no sirve sin ese permiso").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //volver a preguntar si acepta los permisos
                    Toast.makeText(MainActivity.this, "No puedes salir de esta actividad mi chavo", Toast.LENGTH_SHORT).show();
                }
            }).show();

//

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
                Log.e("MyLog", "2 if");

                ActivityCompat.requestPermissions(MainActivity.this, permissions, Constants.REQUEST_PERMISSION_CAMERA);

            } else {
                Log.e("MyLog", "2 else");
                //TODO Indicar al usuario de qudebe de ir a configuraciones y activar el permiso manualmente

            }

        }

    }

    private void camaraPermission() {
        String[] permission = new String[]{Manifest.permission.CAMERA};
        if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
            Log.e("MyLog", "Dentro del if");
            ActivityCompat.requestPermissions(MainActivity.this, permission, 300);

        }
    }

    @OnClick(R.id.btn_camara)
    public void btnCLick() {

        int permissionStatus = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, Constants.REQUEST_CODE_FOTO);
            }

        } else {
            Log.e("MyLog", "else");
            camaraPermission();

        }
    }


}