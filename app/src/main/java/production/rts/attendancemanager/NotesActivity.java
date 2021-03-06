package production.rts.attendancemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class NotesActivity extends AppCompatActivity {

    Button saveBtn;
    EditText textBox;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        setContentView(R.layout.activity_notes);
        final String uid = muser.getUid();
        final DatabaseReference myRef = database.getReference("Notes").child(uid);

        textBox = findViewById(R.id.notesEditText);
        saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textBox.getText().toString().isEmpty()){
                    Toast.makeText(NotesActivity.this, "Please Enter Text To Save", Toast.LENGTH_SHORT).show();
                }else {
                    String id  = myRef.push().getKey();
                    myRef.child(id).setValue(textBox.getText().toString());
                    Intent gotoNotesViewer = new Intent(NotesActivity.this, NotesViewerActivity.class);
                    gotoNotesViewer.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(gotoNotesViewer);
                }
            }
        });
    }
}