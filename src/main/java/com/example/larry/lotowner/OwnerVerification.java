package com.example.larry.lotowner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OwnerVerification extends AppCompatActivity {

    EditText firstEditText;
    EditText secondEditText;
    User user=new User();
    Button submitButton;
    private FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=mDatabase.getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_verification);

        firstEditText=findViewById(R.id.firstEditText);
        secondEditText=findViewById(R.id.secondEditText);
        submitButton=findViewById(R.id.submitButton);

        //sets focus of editText to the next edittext when the first reaches its maximum allowable figure

        firstEditText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(firstEditText.getText().toString().length()==3)
                {
                    secondEditText.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


    }

    public void verification(View view){

        //checks if any of the code edittexts are empty

        if(TextUtils.isEmpty(firstEditText.getText())||
                TextUtils.isEmpty(secondEditText.getText())){
            Toast.makeText(getApplicationContext(),"Fill both fields",Toast.LENGTH_LONG).show();
        }else{

            //if none are empty ,concatenates the texts in both editTexts to form the verification code

            String code=firstEditText.getText().toString()
                    +"-"+secondEditText.getText().toString();

            //checks whether verification code has been assigned to a lot in order to obtain the lot_id

            databaseReference.child("Verification").child("Key Pairs").child(code).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                String lot_id=dataSnapshot.getValue().toString();
                                Log.i("vercode value",dataSnapshot.getValue().toString());


                                //this code sets the lot id to true so that the user doesnt need to verify each time he logins

                                databaseReference.child("Verification").child("Verified").child(lot_id)
                                        .setValue(true);

                                Intent intent=new Intent(OwnerVerification.this,Requests.class);
                                intent.putExtra("Lot_id",lot_id);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),
                                        "You couldn't be verified" +
                                                "Check verification code and try again",Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    }
            );

        }


    }
}
