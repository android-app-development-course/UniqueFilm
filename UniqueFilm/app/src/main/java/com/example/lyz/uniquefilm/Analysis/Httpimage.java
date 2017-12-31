package com.example.lyz.uniquefilm.Analysis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by lyz on 17-12-28.
 */

public class Httpimage extends AsyncTask {

    private String murl;
    private ImageView mImageView;
    private TableRow mTableRow;
    private boolean which;


    public Httpimage(String url,ImageView imageView){
        this.murl=url;
        this.mImageView=imageView;
        which=false;
    }

    public Httpimage(String url, ImageView imageView, TableRow tableRow){
        this.murl=url;
        this.mImageView=imageView;
        this.mTableRow=tableRow;
        which=true;
    }



    @Override
    protected Object doInBackground(Object[] params) {
        try{
            URL url=new URL(murl);
            InputStream in=url.openStream();
            Bitmap bitmap= BitmapFactory.decodeStream(in);
            in.close();
            return bitmap;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        if(o!=null)
          Log.i("bitmap","true");
        else
          Log.i("bitmap","false");
        mImageView.setImageBitmap((Bitmap)o);
        if(which){
            //Palette palette=Palette.from((Bitmap)o).generate();
            //int defaultcolor=Color.BLUE;
            //int color=palette.getMutedColor(defaultcolor);
            Palette.from((Bitmap)o).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    Palette.Swatch vibrant=palette.getMutedSwatch();
                    if(vibrant==null){
                        for(Palette.Swatch swatch:palette.getSwatches()){
                            vibrant=swatch;
                            break;
                        }
                    }
                    int rbg=vibrant.getRgb();
                    mTableRow.setBackgroundColor(rbg);
                }
            });

        }

    }
}
