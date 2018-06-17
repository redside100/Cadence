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
     * Creates a button that will erase all user data
     * @param bitmap The image of the button
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
        super.trigger();
        // Create new dialog builder
        AlertDialog.Builder builder;
        // Check if the build version support material dialogs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            // If not, just have a normal native dialog
            builder = new AlertDialog.Builder(context);
        }
        // Create title, message, positive/negative button and their actions, and show the dialog
        builder.setTitle("Erase Data")
                .setMessage("Are you sure you want to erase all stored data? This includes your best grades, level, and experience.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Reset all data if confirmed
                        PlayerData.reset();
                        PlayerData.saveAll();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing if cancelled
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
