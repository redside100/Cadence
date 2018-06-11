package me.andrewpeng.cadence.buttons;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;

import java.util.ArrayList;

import me.andrewpeng.cadence.core.PlayerData;
import me.andrewpeng.cadence.music.Conductor;
import me.andrewpeng.cadence.objects.Button;


public class EraseDataButton extends Button {
    /**
     * Creates a button that will manage the judge difficulty
     * @param bitmap The image that will have the properties to change the difficulty
     * @param x x location
     * @param y y location
     * @param alpha Transparency of the image
     * @param context Context required to create alert dialogs
     */

    private Context context;
    public EraseDataButton(Bitmap bitmap, int x, int y, int alpha, Context context){
        super(bitmap, x, y, alpha);
        this.context = context;
    }
    @Override
    public void trigger(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Erase Data")
                .setMessage("Are you sure you want to erase all stored data? This includes your best grades, level, and experience.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PlayerData.reset();
                        PlayerData.saveAll();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
