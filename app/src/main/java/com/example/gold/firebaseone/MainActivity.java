package com.example.gold.firebaseone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etContent;
    Button btnPost;

    ListView listView;
    ListAdapter listAdapter;

    List<Bbs> datas = new ArrayList<>();

    //파이어베이스는 레퍼런스 단위로 동작한다.
    //디비 닫을 필요 없음.
    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference bbsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        bbsRef = database.getReference("bbs");

        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ListAdapter(datas, this);
        listView.setAdapter(listAdapter);



        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        btnPost = (Button) findViewById(R.id.btnPost);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();

                //bbs레퍼런스(테이블)에 키를 생성
                //push()에서 fb에 데이터를 넣고 fb서 만들어진 키를 getkey로 가져온다
                String key = bbsRef.push().getKey();

                //2.입력될 키,값 세트 (레코드)를 생성
                Map<String, Object> postValues = new HashMap<>();
                postValues.put("title", title);
                postValues.put("content", content);


//                Map<String, Object> childUpdates = new HashMap<>();
//                childUpdates.put(key, postValues);

                //3. 생성된 데이터(레코드)를 데이터베이스에 입력
//                bbsRef.updateChildren(childUpdates);
                DatabaseReference keyRef = bbsRef.child(key);
                keyRef.setValue(postValues);




            }
        });
        //bbsRef.setValue("Hello, World!");

        bbsRef.addValueEventListener(postListener);
    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            //Post post = dataSnapshot.getValue(Post.class);
            // ...
            Log.w("MainActivity","data count : " + dataSnapshot.getChildrenCount());

            datas.clear();

            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                String key = snapshot.getKey();
                Bbs bbs = snapshot.getValue(Bbs.class); //object단위로 넘어옴
                bbs.key = key;

                datas.add(bbs);
            }
            listAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };



}
