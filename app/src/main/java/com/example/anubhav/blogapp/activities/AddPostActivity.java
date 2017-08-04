package com.example.anubhav.blogapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anubhav.blogapp.R;
import com.example.anubhav.blogapp.model.Blog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {

    private static final int GALLERY_CODE = 100;
    private RecyclerView recyclerView;
    private EditText edtTitle, edtDescp;
    private ImageView imgPost;
    private Button btnPost;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ProgressDialog progressDialog;
    private Uri imageUri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        imgPost = (ImageView) findViewById(R.id.imgPost);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtDescp = (EditText) findViewById(R.id.edtDescp);
        btnPost = (Button) findViewById(R.id.btnPost);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        reference = FirebaseDatabase.getInstance().getReference().child("Blogs");

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });

        imgPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), GALLERY_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            if (imageUri != null) {
                imgPost.setImageURI(imageUri);
            }
            imgPost.setImageURI(imageUri);
        }
    }


    private void startPosting() {
        progressDialog.setMessage(getString(R.string.posting_message));
        progressDialog.show();

        final String title = edtTitle.getText().toString().trim();
        final String description = edtDescp.getText().toString().trim();

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)
                && imageUri != null) {
            StorageReference filePath = storageReference
                    .child("Blog_Images").child(imageUri.getLastPathSegment());

            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri currentImageUri = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost = reference.push();

                    Map<String, String> dataToSave = new HashMap<>();
                    dataToSave.put("title", title);
                    dataToSave.put("description", description);
                    dataToSave.put("image", currentImageUri.toString());
                    dataToSave.put("timeStamp", String.valueOf(java.lang.System.currentTimeMillis()));
                    dataToSave.put("userId", firebaseUser.getUid());

                    newPost.setValue(dataToSave);

                    progressDialog.dismiss();

                    startActivity(new Intent(AddPostActivity.this, PostListActivity.class));
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
    }
}
