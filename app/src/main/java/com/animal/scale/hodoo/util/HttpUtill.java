package com.animal.scale.hodoo.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtill {

    public static String lineEnd = "\r\n";
    public static String twoHyphens = "--";
    public static String boundary = "*****";

    public static Uri mImageCaptureUri;
    public static ImageView mPhotoImageView;
    public byte[] imgbyte;

    public static FileInputStream mFileInputStream = null;
    public static URL connectUrl = null;
    public static EditText mEdityEntry;
    public static ByteArrayInputStream mByteInputStream = null;


    public static String HttpFileUpdate(String url, PetBasicInfo basicInfo, ImageView profile) {
        String s = null;
        try {
            String filepath = String.valueOf(System.currentTimeMillis()) + ".jpg";
            connectUrl = new URL(url);
            ByteArrayInputStream mByteInputStream;
            //이미지 셋팅
            Bitmap photo = ((BitmapDrawable) profile.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
            byte[] defaultImage = byteArray.toByteArray();
            mByteInputStream = new ByteArrayInputStream(defaultImage);

            // open connection
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data; charset=utf-8; boundary=" + boundary);
            // write data
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            //BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(dos, "UTF-8"));

            //ID
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"id\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(String.valueOf(basicInfo.getId()));
            dos.writeBytes(lineEnd);
            // 성별
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"sex\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(basicInfo.getSex());
            dos.writeBytes(lineEnd);
            // 개이름
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"petName\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.write(basicInfo.getPetName().getBytes("utf-8"));
            dos.writeBytes(lineEnd);
            // 펫 종류
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"petType\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(String.valueOf(basicInfo.getPetType()));
            dos.writeBytes(lineEnd);
            // 선택된 BFI
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"selectedBfi\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(basicInfo.getSelectedBfi());
            dos.writeBytes(lineEnd);
            // 개 품종
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"petBreed\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.write(basicInfo.getPetBreed().getBytes("utf-8"));
            dos.writeBytes(lineEnd);
            //생일
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"birthday\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(basicInfo.getBirthday());
            dos.writeBytes(lineEnd);
            //중성화 여부
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"neutralization\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(basicInfo.getNeutralization());
            dos.writeBytes(lineEnd);
            //FILE
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"profile\";filename=\"" + filepath + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            int bytesAvailable = mByteInputStream.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            byte[] buffer = new byte[bufferSize];
            int bytesRead = mByteInputStream.read(buffer, 0, bufferSize);

            Log.d("TestServerResponseIn", "image byte is " + bytesRead);

            // read image
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = mByteInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = mByteInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            Log.e("TestServerResponseIn", "File is written");
            mByteInputStream.close();
            dos.flush(); // finish upload...

            // get response
            int ch;
            InputStream is = conn.getInputStream();
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            s = b.toString().trim();
            Log.e("TestServerResponseIn", "result = " + s);
            // mEdityEntry.setText(s);
            dos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static Pet HttpFileRegist(String url, String groupCode, PetBasicInfo basicInfo, ImageView profile) {
        String jsonResult = null;
        Pet pet = null;
        try {
            String filepath = String.valueOf(System.currentTimeMillis()) + ".jpg";
            connectUrl = new URL(url);
            ByteArrayInputStream mByteInputStream;
            //이미지 셋팅
            Bitmap photo = ((BitmapDrawable) profile.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
            byte[] defaultImage = byteArray.toByteArray();
            mByteInputStream = new ByteArrayInputStream(defaultImage);

            // open connection
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data; charset=utf-8; boundary=" + boundary);
            // write data
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            //BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(dos, "UTF-8"));
            // 그룹아이디
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"groupCode\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(groupCode);
            dos.writeBytes(lineEnd);
            // 아이디
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"id\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(String.valueOf(basicInfo.getId()));
            dos.writeBytes(lineEnd);
            // 성별
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"sex\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(basicInfo.getSex());
            dos.writeBytes(lineEnd);
            // 개이름
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"petName\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.write(basicInfo.getPetName().getBytes("utf-8"));
            dos.writeBytes(lineEnd);
            // 펫 종류
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"petType\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(String.valueOf(basicInfo.getPetType()));
            dos.writeBytes(lineEnd);
            // 선택된 BFI
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"selectedBfi\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(basicInfo.getSelectedBfi());
            dos.writeBytes(lineEnd);
            // 개 품종
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"petBreed\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.write(basicInfo.getPetBreed().getBytes("utf-8"));
            dos.writeBytes(lineEnd);
            // 생일
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"birthday\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(basicInfo.getBirthday());
            dos.writeBytes(lineEnd);
            // 중성화여부
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"neutralization\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(basicInfo.getNeutralization());
            dos.writeBytes(lineEnd);
            //FILE
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"profile\";filename=\"" + filepath + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            int bytesAvailable = mByteInputStream.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            byte[] buffer = new byte[bufferSize];
            int bytesRead = mByteInputStream.read(buffer, 0, bufferSize);

            Log.d("TestServerResponseIn", "image byte is " + bytesRead);

            // read image
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = mByteInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = mByteInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            Log.e("TestServerResponseIn", "File is written");
            mByteInputStream.close();
            dos.flush(); // finish upload...

            // get response
            int ch;
            InputStream is = conn.getInputStream();
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            jsonResult = b.toString().trim();
            Gson gson = new Gson();
            ResultMessageGroup resultMessageGroup = gson.fromJson(jsonResult, ResultMessageGroup.class);
            Log.e("HJLEE", "resultMessageGroup = " + resultMessageGroup);
            pet = gson.fromJson(resultMessageGroup.getDomain().toString(), Pet.class);
            Log.e("HJLEE", "pet = " + pet);
            // mEdityEntry.setText(jsonResult);
            dos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pet;
    }
}
