package com.myexample.unitycallandroidutility;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;//for use UnityPlayer.UnitySendMessage
import com.unity3d.player.UnityPlayerActivity;//for MainActiviy extends UnityPlayerActivity

public class MainActivity extends UnityPlayerActivity
{
	protected PopupWindow mPopupWindow;  
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
      //setContentView(R.layout.activity_main);//unity dont need it!
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
	  //getMenuInflater().inflate(R.menu.main, menu);
	  //return true;
        //參數1:群組id, 參數2:itemId, 參數3:item順序, 參數4:item名稱
		menu.add(0, 0, 0, "EasyMode");
		menu.add(0, 1, 1, "HardMode");
		menu.add(0, 1, 2, "Quit");
		return super.onCreateOptionsMenu(menu);		
	}
	//無效果.unity似乎不會呼叫android的menu...
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId())
		{
	    	case 0 : UnityPlayer.UnitySendMessage( "Main Camera", "AndroidCallUnityFunction", "EASY" ); break;
	    	case 1 : UnityPlayer.UnitySendMessage( "Main Camera", "AndroidCallUnityFunction", "HARD" ); break;
	    	case 2 : finish() ; break;
	    	default: return false;
		}
		return true;
	}
	//unity call android toast
	public void showToast(final String sText) 
    {
    	runOnUiThread(new Runnable() 
    	{
    		@Override
    		public void run() 
    		{
    			Toast.makeText(MainActivity.this, sText , Toast.LENGTH_SHORT).show();
    		}
    	});
   	}
	//unity call android 兩個按鈕的AlertDialog
	public void showAlertDialog(final String sTitle , final String sMessage , final String sLeftButton , final String sRightButton)
    {
	    	runOnUiThread(new Runnable() 
	    	{
	    		public void run() 
	    		{
	    			AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
	    			.setTitle(sTitle)
	    			.setMessage(sMessage)
	    			.setPositiveButton(sLeftButton, new OnClickListener()
	    	    	{			
	    				@Override
	    				public void onClick(DialogInterface dialog, int which) 
	    				{
	    					//Android send msg to Unity, "掛載此腳本的物件名(AlertDialogOnClick.cs在Unity3D裡是放在攝影機上...)", "函式名稱", "參數" 
	    					UnityPlayer.UnitySendMessage( "Main Camera", "AlertDialogOnClick", "POSITIVE" );
	    					dialog.dismiss() ;				
	    				}
	    			})
	    			.setNegativeButton(sRightButton, new OnClickListener() 
	    			{			
	    				@Override
	    				public void onClick(DialogInterface dialog, int which) 
	    				{
	    					UnityPlayer.UnitySendMessage( "Main Camera", "AlertDialogOnClick", "NEGATIVE" );
	    					dialog.dismiss() ;				
	    				}
	    			})	    			
	    			.create() ;
	    			//
	    			dialog.show() ;
	    			dialog.setCanceledOnTouchOutside(false) ;
	    		}
	    	});
    }
	//unity call android 一個按鈕的AlertDialog
	public void showAlertDialog(final String sTitle , final String sMessage , final String sButtonTips)
    {
		runOnUiThread(new Runnable() 
    	{
    		public void run() 
    		{
    			new AlertDialog.Builder(MainActivity.this)
    			.setTitle(sTitle)
    			.setMessage(sMessage)
    			.setPositiveButton(sButtonTips, new OnClickListener()
    	    	{			
    				@Override
    				public void onClick(DialogInterface dialog, int which) 
    				{
    					//Android send msg to Unity, "掛載腳本的Unity3D的物件名", "函式名稱", "參數" 
    					UnityPlayer.UnitySendMessage( "Main Camera", "AlertDialogOnClick", "POSITIVE" );
    					dialog.dismiss() ;				
    				}
    			})
    			.setCancelable(false)
    			.create()
    			.show() ;
    		}
    	});
    }
	//unity call android 三個按鈕的AlertDialog		
	public void showAlertDialog(final String sTitle , final String sMessage , final String sLeftButton , final String sMiddleButton , final String sRightButton)
    {		
	   	runOnUiThread(new Runnable() 
    	{
    		public void run() 
    		{
    			new AlertDialog.Builder(MainActivity.this)
    			.setTitle(sTitle)
    			.setMessage(sMessage)
    			.setPositiveButton(sLeftButton, new OnClickListener()
    	    	{			
    				@Override
    				public void onClick(DialogInterface dialog, int which) 
    				{
    					//Android send msg to Unity, "掛載腳本的Unity3D的物件名", "函式名稱", "參數" 
    					UnityPlayer.UnitySendMessage( "Main Camera", "AlertDialogOnClick", "POSITIVE" );
    					dialog.dismiss() ;				
    				}
    			})
    			.setNegativeButton(sRightButton, new OnClickListener() 
    			{			
    				@Override
    				public void onClick(DialogInterface dialog, int which) 
    				{
    					UnityPlayer.UnitySendMessage( "Main Camera", "AlertDialogOnClick", "NEGATIVE" );
    					dialog.dismiss() ;				
    				}
    			})
    			.setNeutralButton(sMiddleButton, new OnClickListener() 
    			{			
    				@Override
    				public void onClick(DialogInterface dialog, int which) 
    				{
    					UnityPlayer.UnitySendMessage( "Main Camera", "AlertDialogOnClick", "NEUTRAL" );
    					dialog.dismiss() ;				
    				}
    			})
    			.setCancelable(false)
    			.create()
    			.show() ;    			
    		}
    	});
    }
	//android quit function		 
	public void exit()
	{
		finish() ;
	}	
	//unity call android PopuptWindow(使用彈出式視窗取代原本menu)
	public void showPopupWindow(int iWidth,int iHeight,final String sUpButton , final String sMiddleButton , final String sBottomButton)
	{
		if (null != mPopupWindow) 
		{  
			mPopupWindow.dismiss();//已開過就關了它
			return;  
		} 
		else
		{  
			LinearLayout pScreenView = (LinearLayout)findViewById(R.id.LinearLayoutScreen);
			//沒開過就開了它
			LayoutInflater layoutInflater = LayoutInflater.from(this);  
			View pwView = layoutInflater.inflate(R.layout.popup_window, null);  
			Button pButton1 = (Button) pwView.findViewById(R.id.button01);  
			Button pButton2 = (Button) pwView.findViewById(R.id.button02);
			Button pButton3 = (Button) pwView.findViewById(R.id.button03);
			pButton1.setOnClickListener(new View.OnClickListener()
			{	
				@Override
				public void onClick(View v)
				{
					UnityPlayer.UnitySendMessage( "Main Camera", "PopupWindowOnClick", "NEUTRAL" );
	        		mPopupWindow.dismiss();					
				}
			});			
			pButton2.setOnClickListener(new View.OnClickListener()
			{
	        	public void onClick(View v)
	        	{
	        		UnityPlayer.UnitySendMessage( "Main Camera", "PopupWindowOnClick", "NEUTRAL" );
	        		mPopupWindow.dismiss();	
	        	}
			});
			pButton3.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					UnityPlayer.UnitySendMessage( "Main Camera", "PopupWindowOnClick", "NEUTRAL" );
					mPopupWindow.dismiss();	
				}
			});
			pButton1.setText(sUpButton);
			pButton2.setText(sMiddleButton);
			pButton3.setText(sBottomButton);
			mPopupWindow = new PopupWindow(pwView, iWidth , iHeight );  
			mPopupWindow.setOutsideTouchable(true);//當使用者按超出 PopupWindow 的範圍時， 事件能夠放PopupWindow外的 View 所接收，並且PopupWindow會被關上
			mPopupWindow.setFocusable(true) ;//設定Focus為true，讓Android知道應該要將焦點擺在PopupWindow上。不然執行PopupWindow內的程式碼，可運作，但點擊時沒有反應，也就是沒有變色；
			//
			mPopupWindow.showAtLocation(pScreenView, Gravity.BOTTOM, 0, 0);
		}  
	}
}
