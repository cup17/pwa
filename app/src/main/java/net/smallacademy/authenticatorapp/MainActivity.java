//package net.smallacademy.authenticatorapp;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import android.annotation.SuppressLint;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.squareup.picasso.Picasso;
//
//import javax.annotation.Nullable;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class MainActivity extends AppCompatActivity  {
//    private static final int GALLERY_INTENT_CODE = 1023;
//    TextView fullName, email, phone, verifyMsg;
//    FirebaseAuth fAuth;
//    FirebaseFirestore fStore;
//    String userId;
//    Button resendCode;
//    Button resetPassLocal;
//    ImageButton changeProfileImage;
//    FirebaseUser user;
//    CircleImageView profileImage;
//    StorageReference storageReference;
//
//
////    @SuppressLint("WrongViewCast")
//    @Override
//    protected void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_account); // <-- lu gabisa daftarin Fragment di activity,
//        //setContentView(R.layout.activity_main); // <-- harusnya diganti gini, tapi bakal error karena kode di file MainActivity.java ini banyak ngambil reference dari fragment_account.xml
//
//        phone = findViewById(R.id.profilePhone); // <-- ini adanya di fragment_account.xml bukan activity_main.xml
//        // -jadi pas lu ganti setcontentview diatas itu ke setContentView(R.layout.activity_main) bakal error
//        fullName = findViewById(R.id.profileName); // <-- ini adanya di fragment_account.xml bukan activity_main.xml -harusnya kode ini ada di AccountFragment.java
//        email = findViewById(R.id.profileEmail); // <-- ini adanya di fragment_account.xml bukan activity_main.xml -harusnya kode ini ada di AccountFragment.java
//
//        //Dan seterusnya ini harusnya ada di AccountFragment.java
//        //Kalau udah dipindah kode-kode disini ke AccountFragment.java, file ini (MainActivity.java) jadi gaberguna karena udah ada Activity_main.java yang gantiin ini yanga ada BNnya.
//        resetPassLocal = findViewById(R.id.resetPasswordLocal);
//
//        profileImage = findViewById(R.id.profileImage);
//        changeProfileImage = findViewById(R.id.changeProfile);
//
//
//        fAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();
//        storageReference = FirebaseStorage.getInstance().getReference();
//
//        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
//        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(profileImage);
//            }
//        });
//
//        resendCode = findViewById(R.id.resendCode);
//        verifyMsg = findViewById(R.id.verifyMsg);
//
//
//        userId = fAuth.getCurrentUser().getUid();
//        user = fAuth.getCurrentUser();
//
//        if (!user.isEmailVerified()) {
//            verifyMsg.setVisibility(View.VISIBLE);
//            resendCode.setVisibility(View.VISIBLE);
//
//            resendCode.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//
//                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(v.getContext(), "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("tag", "onFailure: Email not sent " + e.getMessage());
//                        }
//                    });
//                }
//            });
//        }
//
//
//        DocumentReference documentReference = fStore.collection("users").document(userId);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                if (documentSnapshot.exists()) {
//                    phone.setText(documentSnapshot.getString("phone"));
//                    fullName.setText(documentSnapshot.getString("fName"));
//                    email.setText(documentSnapshot.getString("email"));
//
//                } else {
//                    Log.d("tag", "onEvent: Document do not exists");
//                }
//            }
//        });
//
//
//        resetPassLocal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final EditText resetPassword = new EditText(v.getContext());
//
//                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
//                passwordResetDialog.setTitle("Reset Password ?");
//                passwordResetDialog.setMessage("Enter New Password > 6 Characters long.");
//                passwordResetDialog.setView(resetPassword);
//
//                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // extract the email and send reset link
//                        String newPassword = resetPassword.getText().toString();
//                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(MainActivity.this, "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(MainActivity.this, "Password Reset Failed.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                });
//
//                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // close
//                    }
//                });
//
//                passwordResetDialog.create().show();
//
//            }
//        });
//
//        changeProfileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // open gallery
//                Intent i = new Intent(v.getContext(), EditProfile.class);
//                i.putExtra("fullName", fullName.getText().toString());
//                i.putExtra("email", email.getText().toString());
//                i.putExtra("phone", phone.getText().toString());
//                startActivity(i);
////
//
//            }
//        });
//
//
//    }
//
//
//    public void logout(View view) {
//        FirebaseAuth.getInstance().signOut();//logout
//        startActivity(new Intent(getApplicationContext(), Login.class));
//        finish();
//    }
//
//
//
////
////        // kita set default nya Home Fragment
////        loadFragment(new HomeFragment());
////        // inisialisasi BottomNavigaionView
////        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);
////        // beri listener pada saat item/menu bottomnavigation terpilih
////        bottomNavigationView.setOnNavigationItemSelectedListener(this);
////    }
////
////    private boolean loadFragment(Fragment fragment){
////        if (fragment != null) {
////            getSupportFragmentManager().beginTransaction()
////                    .replace(R.id.fl_container, fragment)
////                    .commit();
////            return true;
////        }
////        return false;
////    }
////
////    @Override
////    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
////        Fragment fragment = null;
////        switch (menuItem.getItemId()){
////            case R.id.home_menu:
////                fragment = new HomeFragment();
////                break;
////            case R.id.berita_menu:
////                fragment = new BeritaFragment();
////                break;
////
////            case R.id.account_menu:
////                fragment = new AcountFragment();
////                break;
////        }
////        return loadFragment(fragment);
////    }
//    }
