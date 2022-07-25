package com.example.penajamm;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class MemoryData {

    public static void saveData(String data, Context context){
        try{

            FileOutputStream fileOutputStream = context.openFileOutput("datata.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();

        }catch (IOException e){

            e.printStackTrace();

        }
    }

    public static String getData(Context context){
        String data = "";

        try{
            FileInputStream fileInputStream = context.openFileInput("datata.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }

            data = stringBuilder.toString();

        }catch (IOException e){
            e.printStackTrace();
        }

        return data;
    }
}
