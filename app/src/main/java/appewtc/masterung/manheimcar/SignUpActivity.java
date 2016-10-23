package appewtc.masterung.manheimcar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {

    //explicit
    private EditText nameEditText, userEditText, passwordEditText;
    private ImageView imageView;
    private Button button;
    private String nameString, userString, passString, imageString,
            imagePathString, imageNameString;
    private Uri uri;
    private boolean aBoolean = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        nameEditText = (EditText) findViewById(R.id.editText3);
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button3);

        //button Controller
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Value From Edit Text
                nameString = nameEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passString = passwordEditText.getText().toString().trim();

                //check space
                if (nameString.equals("") || userString.equals("") || passString.equals("") ) {
                    //have space
                    MyAlert myAlert = new MyAlert(SignUpActivity.this,
                            R.drawable.bird48, "มีช่องว่าง", "กรุณากรอกทุกช่อง");
                    myAlert.myDialog();
                } else if (aBoolean) {
                    // Non Choose Image
                    MyAlert myAlert = new MyAlert(SignUpActivity.this,
                            R.drawable.doremon48,
                            "ยังไม่ได้เลือกรูปภาพ",
                            "กรุณาเกลือกรูปภาพด้วยค่ะ");
                    myAlert.myDialog();
                } else {
                    // Choose Image Finish

                }

            }  //onClick
        });
            //Image Controller
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "โปรดเลือกรูปภาพ"), 1);

                }  //On Click
            });

    } //Main Method

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1)&&(resultCode == RESULT_OK)) {

            Log.d("23octV1", "Result OK");

            aBoolean = false;

            //Setup Image
            uri = data.getData();

            try {

                Bitmap bitmap = BitmapFactory
                        .decodeStream(getContentResolver()
                                .openInputStream(uri));
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }   //try
            //  Find Path and Name Image
            imagePathString = myFindPath(uri);
            Log.d("23octV1", "imagePathString ++>" + imagePathString);
        }   //If

        imageNameString = imagePathString.substring(imagePathString.lastIndexOf("/"));
        Log.d("23octV1", "imageString ==>" + imageNameString);

    }   //OnActivityResult

    private String myFindPath(Uri uri) {

        String result = null;
        String[] strings = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();
            int i = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            result = cursor.getString(i);

        } else {
            result = uri.getPath();
        }

        return result;
    }
}   //Main Class






