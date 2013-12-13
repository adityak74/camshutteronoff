package com.adityak74.camerashutteroffon;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import com.adityak74.camerashutteroff.R;
import com.stericson.RootTools.RootTools;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button camshutteroff,camshutteron,camfocusoff,camfocuson;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		camfocusoff = (Button) findViewById(R.id.cam_focus_off);
		camfocuson = (Button) findViewById(R.id.cam_focus_on);
		camshutteroff = (Button) findViewById(R.id.cam_shutter_off);
		camshutteron = (Button) findViewById(R.id.cam_shutter_on);
		
		
		if(RootTools.isAccessGiven())
		{
		try
		{
		   RootTools.remount("/system", "rw");
	       File cam_shutter = new File("/system/media/audio/ui/camera_click.ogg");
	       File focus_shutter = new File("/system/media/audio/ui/camera_focus.ogg");
	       File cam_shutter_bak = new File("/system/media/audio/ui/camera_click.bak");
	       File focus_shutter_bak = new File("/system/media/audio/ui/camera_focus.bak");
	       
	       if(cam_shutter.exists() && focus_shutter.exists())
	       {
	    	   Toast.makeText(getApplicationContext(), "Ogg Files exist", Toast.LENGTH_SHORT).show();
	    	   camfocusoff.setClickable(true);
	    	   camshutteroff.setClickable(true);
	    	   camfocuson.setClickable(false);
	    	   camshutteron.setClickable(false);
	       }
	       else if(cam_shutter_bak.exists() && focus_shutter_bak.exists())
	       {
	    	   Toast.makeText(getApplicationContext(), "Bak Files exist", Toast.LENGTH_SHORT).show();
	    	   camfocusoff.setClickable(false);
	    	   camshutteroff.setClickable(false);
	    	   camfocuson.setClickable(true);
	    	   camshutteron.setClickable(true);
	       }
	       else if(cam_shutter_bak.exists() || focus_shutter_bak.exists())
	       {
	    	   Toast.makeText(getApplicationContext(), "(one)Bak File exist", Toast.LENGTH_SHORT).show();
	    	   if(cam_shutter_bak.exists())
	    	   {
	    		   camfocusoff.setClickable(true);
		    	   camshutteroff.setClickable(false);
		    	   camfocuson.setClickable(false);
		    	   camshutteron.setClickable(true);
	    	   }
	    	   if(focus_shutter_bak.exists())
	    	   {
	    		   camfocusoff.setClickable(true);
		    	   camshutteroff.setClickable(true);
		    	   camfocuson.setClickable(true);
		    	   camshutteron.setClickable(false);
	    	   }
	       }
	       
	       else
	       {
	    	   Toast.makeText(getApplicationContext(), "Files does not exist", Toast.LENGTH_SHORT).show();
	       }
	       RootTools.remount("/system", "ro");
		}
		catch(Exception ex)
		{
			Toast.makeText(getApplicationContext(), "Error checking for files", Toast.LENGTH_SHORT).show();
		}
		}
		else
		{
			AlertDialog.Builder alertdb = new AlertDialog.Builder(this);
			alertdb.setIcon(R.drawable.error_icon);
			alertdb.setTitle("Oops No Root Accesss");
			alertdb.setMessage("Looks like your phone is not rooted or your superuser app refused permission.Please Root your phone/accept superuser permissions before you can run this application.");
			alertdb.setCancelable(false);
			alertdb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					MainActivity.this.finish();
					
				}
			});
			AlertDialog alertDg = alertdb.create();
			alertDg.show();
		}
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void clickshutteroff(View v) 
	{
		try
		{
		Process process = Runtime.getRuntime().exec("su");
		DataOutputStream out = new DataOutputStream(process.getOutputStream());
		out.writeBytes("mount -o remount,rw -t ext4 /dev/block/platform/msm_sdcc.3/by-num/p17 /system\n");
		out.writeBytes("mv /system/media/audio/ui/camera_click.ogg /system/media/audio/ui/camera_click.bak\n");
		out.writeBytes("mount -o remount,ro -t ext4 /dev/block/platform/msm_sdcc.3/by-num/p17 /system\n");
		out.writeBytes("exit\n");  
		out.flush();
		process.waitFor();
		Toast.makeText(getApplicationContext(), "Camera Shutter Off", Toast.LENGTH_LONG).show();
		}
		catch(IOException ex)
		{
			Toast.makeText( getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
			Toast.makeText( getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
		}
		
	}
	public void clickshutteron(View v) 
	{
		
		try
		{
			Process process = Runtime.getRuntime().exec("su");
			DataOutputStream out = new DataOutputStream(process.getOutputStream());
			out.writeBytes("mount -o remount,rw -t ext4 /dev/block/platform/msm_sdcc.3/by-num/p17 /system\n");
			out.writeBytes("mv /system/media/audio/ui/camera_click.bak /system/media/audio/ui/camera_click.ogg\n");
			out.writeBytes("mount -o remount,ro -t ext4 /dev/block/platform/msm_sdcc.3/by-num/p17 /system\n");
			out.writeBytes("exit\n");  
			out.flush();
			process.waitFor();
			Toast.makeText(getApplicationContext(), "Camera Shutter ON", Toast.LENGTH_LONG).show();
		}
		catch(IOException ex)
		{
			Toast.makeText( getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
			Toast.makeText( getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
		}
		
	}
	public void focusshutteroff(View v) 
	{
		
		try
		{
			Process process = Runtime.getRuntime().exec("su");
			DataOutputStream out = new DataOutputStream(process.getOutputStream());
			out.writeBytes("mount -o remount,rw -t ext4 /dev/block/platform/msm_sdcc.3/by-num/p17 /system\n");
			out.writeBytes("mv /system/media/audio/ui/camera_focus.ogg /system/media/audio/ui/camera_focus.bak\n");
			out.writeBytes("mount -o remount,ro -t ext4 /dev/block/platform/msm_sdcc.3/by-num/p17 /system\n");
			out.writeBytes("exit\n");  
			out.flush();
			process.waitFor();
			Toast.makeText(getApplicationContext(), "Focus Shutter Off", Toast.LENGTH_LONG).show();
		}
		catch(IOException ex)
		{
			Toast.makeText( getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
			Toast.makeText( getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
		}
		
	}
	public void focusshutteron(View v) 
	{
		
		try
		{
			Process process = Runtime.getRuntime().exec("su");
			DataOutputStream out = new DataOutputStream(process.getOutputStream());
			out.writeBytes("mount -o remount,rw -t ext4 /dev/block/platform/msm_sdcc.3/by-num/p17 /system\n");
			out.writeBytes("mv /system/media/audio/ui/camera_focus.bak /system/media/audio/ui/camera_focus.ogg\n");
			out.writeBytes("mount -o remount,ro -t ext4 /dev/block/platform/msm_sdcc.3/by-num/p17 /system\n");
			out.writeBytes("exit\n");  
			out.flush();
			process.waitFor();
			Toast.makeText(getApplicationContext(), "Focus Shutter ON", Toast.LENGTH_LONG).show();
		}
		catch(IOException ex)
		{
			Toast.makeText( getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
			Toast.makeText( getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
		}
		
	}

	
	
	public void showaboutapp(MenuItem v)
	{
		AlertDialog.Builder alertdb = new AlertDialog.Builder(this);
		
		alertdb.setTitle("About:");
		alertdb.setMessage("This is a simple app to shut the camera shutter and focus sounds on/off for Micromax Canvas Doodle A111.");
		alertdb.setCancelable(false);
		alertdb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				dialog.dismiss();
			}
		});
		AlertDialog alertDg = alertdb.create();
		alertDg.show();
	}
}
