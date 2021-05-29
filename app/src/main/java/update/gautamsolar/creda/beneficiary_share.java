package update.gautamsolar.creda;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gautamsolar.creda.R;

public class beneficiary_share extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary_share);
      /*  registerReceiver( new NetworkStateCheckerB(), new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION ) );
        btninst_capturevideo = findViewById(R.id.btninst_capturevideo);
        btninst_uploadvideo = findViewById(R.id.btninst_uploadvideo);
        process_status = findViewById(R.id.process_status);
        status_video = findViewById(R.id.status_video);
        db = new DatabaseHelper( this );
        Bundle bundle6 = getIntent().getExtras();
        reg_no = bundle6.getString( "reg_no" );


        btninst_uploadvideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isOnline()){
                    uploadVideo();


                }else{

                    saveNameToLocalStorageB( storage_video, reg_no, NAME_NOT_SYNCED_WITH_SERVERB);
                    Toast.makeText( beneficiary_share.this, "Saved Successfully Locally",
                            Toast.LENGTH_SHORT ).show();

                }



                uploadVideo();

            }
        });


        btninst_capturevideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureVideo();
                } else {
                    requestCameraPermission(MEDIA_TYPE_VIDEO);
                }
            }
        });

        loadNamesB();
        broadcastReceiverb = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //loading the names again
                loadNamesB();
            }
        };

        registerReceiver( broadcastReceiverb, new IntentFilter( DATA_SAVED_BROADCASTB ) );


    }


    private void saveNameToLocalStorageB(String photo1,  String reg_no , int status) {

        db.addNameB(photo1,reg_no, status);

    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
        return true;
    } else {
        return false;
    }
}

    private void loadNamesB() {


        Cursor cursor = db.getNames1( reg_no );
        if (cursor.moveToFirst()) {
            do {



              //  video_string_local = cursor.getString( cursor.getColumnIndex( DatabaseHelper.VIDEOPATH ) );
                Status_video = cursor.getInt( cursor.getColumnIndex( DatabaseHelper.VIDEO_STATUS ) );
                String Status_String1 = String.valueOf( Status_video );
                if (Status_String1.equals( "1" )) {

                    status_video.setText( "Complete" );
                } else if (Status_String1.equals( "0" )) {
                    status_video.setText( "Un Complete" );
                } else {
                    status_video.setText( "Pending" );

                }
            } while (cursor.moveToNext());
        }


    }


    private void captureVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_VIDEO);
        if (file != null) {
            storage_video = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        // set video quality
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30); // Duration in Seconds
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // Quality Low
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 5491520L); // 5MB

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file

        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri u = data.getData();


                Toast.makeText(this, storage_video
                        , Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    private void uploadVideo() {
        class UploadVideo extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                 progressDialog = new ProgressDialog(beneficiary_share.this, R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage( "Uploading..." );
                progressDialog.show();
                
            }


                @Override
                protected void onPostExecute (String s){
                    super.onPostExecute(s);
                    progressDialog.dismiss();
                    process_status.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                    process_status.setMovementMethod(LinkMovementMethod.getInstance());

                }

                @Override
                protected String doInBackground (Void...params){

                    Upload u = new Upload();
                    String msg = u.uploadVideo(storage_video);
                    return msg;
                }
            }

            UploadVideo uv = new UploadVideo();
        uv.execute();

        }



        private void requestCameraPermission(final int type) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_VIDEO) {
                                // capture picture

                                captureVideo();

                            } *//*else {
                                captureVideo1();
                            }*//*

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(beneficiary_share.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }*/

    }
}
