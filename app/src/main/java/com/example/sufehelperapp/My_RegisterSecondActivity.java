package com.example.sufehelperapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class My_RegisterSecondActivity extends AppCompatActivity {

    private String myPhone;

    private user user;

    private RadioGroup rg;
    private RadioButton rb_Male;
    private RadioButton rb_Female;
    String sex;

    Connection con;
    ResultSet rs;

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private ImageView picture;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }


        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(My_RegisterSecondActivity.this, My_RegisterFirstActivity.class);
                startActivity(intent1);
            }

        });
        picture = (ImageView) findViewById(R.id.picture_upload);

        //从手机相册中选择
        Button chooseFromAlbum = (Button) findViewById(R.id.bottom_choose_from_album);
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(My_RegisterSecondActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(My_RegisterSecondActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                } else {
                    openAlbum();
                }
            }
        });

        //从My_RegisterFirstActivity获取user phone
        myPhone = getIntent().getStringExtra("user_phone");
        Log.d("RegisterSecondActivity",myPhone);

        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `user` WHERE `phonenumber` = '"+myPhone+"'");

            List<user> userList = new ArrayList<>();
            List list = DbUtils.populate(rs,user.class);
            for(int i=0; i<list.size(); i++){
                userList.add((user)list.get(i));
            }
            user = userList.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }

        }


        //点击“确认”按钮
        Button button2 = (Button) findViewById(R.id.button_8);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取用户名和密码的组件对象
                TextView nameView = findViewById(R.id.register2_name);
                TextView passwordView = findViewById(R.id.register2_password);

                //检查用户名和密码不得为空
                String name = nameView.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(My_RegisterSecondActivity.this, "用户名不得为空！", Toast.LENGTH_SHORT).show();
                }

                String password = passwordView.getText().toString();
                if(password.isEmpty()){
                    Toast.makeText(My_RegisterSecondActivity.this, "密码不得为空！", Toast.LENGTH_SHORT).show();
                }

                if(sex.isEmpty()){
                    Toast.makeText(My_RegisterSecondActivity.this, "性别不得为空！", Toast.LENGTH_SHORT).show();
                }

                try{
                    StrictMode.ThreadPolicy policy =
                            new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    con = DbUtils.getConn();
                    Statement st1 = con.createStatement();
                    rs= st1.executeQuery("SELECT * FROM `user` WHERE `myName` = '"+name+"'");

                    List list = DbUtils.populate(rs,user.class);

                    if (!list.isEmpty()) {
                        Toast.makeText(My_RegisterSecondActivity.this, "用户名已被注册！",
                                Toast.LENGTH_SHORT).show();
                    }



                if(list.isEmpty() && !name.isEmpty() && !password.isEmpty() && !sex.isEmpty()){

                    //TODO: 将sex存入当前用户
                    Statement st2 = con.createStatement();
                    st2.executeUpdate("UPDATE `user` SET `myName` = '"+name+"' WHERE `phonenumber` = '"+myPhone+"'");
                    st2.executeUpdate("UPDATE `user` SET `sex` = '"+sex+"' WHERE `phonenumber` = '"+myPhone+"'");
                    st2.executeUpdate("UPDATE `user` SET `password` = '"+password+"' WHERE `phonenumber` = '"+myPhone+"'");


                    Intent intent = new Intent(My_RegisterSecondActivity.this, My_RegisterThirdActivity.class);
                    intent.putExtra("user_phone", myPhone);

                    startActivity(intent);
                }

                    con.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        //获取sex的组件
        rg = (RadioGroup) findViewById(R.id.rg_sex);
        rb_Male = (RadioButton) findViewById(R.id.rb_Male);
        rb_Female = (RadioButton) findViewById(R.id.rb_FeMale);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                switch (id){
                    case R.id.rb_Male: sex = "男"; break;
                    case R.id.rb_FeMale: sex = "女"; break;
                }

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        switch (requestCode) {

            case CHOOSE_PHOTO:
                if(requestCode == RESULT_OK) {
                    //判断手机系统版本号
                    if(Build.VERSION.SDK_INT >= 19) {
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        //4.4以下系统使用这个方法处理图片
                        handldImageBeforeKitKat(data);
                    }
                }
                if (data != null)
                    startPhotoZoom(data.getData(), 100);

                break;
            case PHOTO_REQUEST_CUT:
                if (data != null)
                    setPicToView(data);//
                break;
            default:
                break;
        }
    }
    private Bitmap decodeUriBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
    //将进行剪裁后的图片显示到UI界面上
    private void setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
           /* Bitmap photo = bundle.getParcelable("data");
            byte[] myimages=img(photo);//
            user.setHeadshot(myimages);
            user.updateAll("phonenumber = ?",user.getPhonenumber());

            byte[] images=user.getHeadshot();
            Bitmap bitmap=BitmapFactory.decodeByteArray(images,0,images.length);
            Drawable drawable = new BitmapDrawable(bitmap);
            picture.setBackgroundDrawable(drawable);*/
            Bitmap photo = bundle.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            picture.setBackgroundDrawable(drawable);

        }
    }
    private void openAlbum() {
        /*Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
        intent2.setType("image/*");
        startActivityForResult(intent2,CHOOSE_PHOTO);//打开相册*/
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)) {
            //如果是document类型的Uri，则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri,null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);//根据图片路径显示图片

        /*
        //获取到图片
        Bitmap headShot= BitmapFactory.decodeFile(imagePath);
    //把图片转换字节流
        byte[] myimages=img(headShot);
    //保存
        user.setHeadshot(myimages);
        user.updateAll("phonenumber = ?",user.getPhonenumber());

    //获取图片
        byte[] images=user.getHeadshot();
        Log.d("msg",String.valueOf(images.length));
        Bitmap bitmap=BitmapFactory.decodeByteArray(images,0,images.length);
        ImageView img = (ImageView) findViewById(R.id.picture_upload);
        img.setImageBitmap(bitmap);*/


    }

    private byte[] img(Bitmap headShot) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = null;
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);//
        return baos.toByteArray();
    }

    private void handldImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath) {
        if(imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

}
