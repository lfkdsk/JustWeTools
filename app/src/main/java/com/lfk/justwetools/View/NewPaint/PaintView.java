package com.lfk.justwetools.View.NewPaint;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lfk.justwetools.R;
import com.lfk.justwetools.View.NewPaint.Graph.DrawBase;
import com.lfk.justwetools.View.NewPaint.Graph.DrawCircle;
import com.lfk.justwetools.View.NewPaint.Graph.DrawLine;
import com.lfk.justwetools.View.NewPaint.Graph.DrawRect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class PaintView extends View {
    // drawing board
    private Bitmap mBitmap;
    // if you set a picture in you will use it
    private Bitmap mBitmapInit;
	private int mBitmapBackGround = R.drawable.whitbackground;
    private Canvas mCanvas;
//    private Path mPath;
    private Paint mBitmapPaint;
//    private Paint mEraserPaint;
//    private Paint mPaint;
    // width of screen
    private int width;
    // height of screen
    private int height;
    private Context context;
    // pass judgement on paint/eraser
    public static boolean IsPaint = true;
    // drawing x,y
//    private float mX, mY;
    // judge your fingers' tremble
    public static final float TOUCH_TOLERANCE = 4;
    // judge long pressed
    private static final long TOUCH_LONG_PRESSED = 500;
    public static boolean IsRecordPath = true;
    //    private PathNode pathNode;
    private boolean mIsLongPressed;
	private boolean IsShowing = false;
	private boolean IsFirstTime = true;
    private long Touch_Down_Time;
    private long Touch_Up_Time;
    private OnPathListener listener;
	private static final int CHOOSEPATH = 0;
	private static final int INDIVIDE = 1;
	private boolean ReDoOrUnDoFlag = false;
	private PathNode pathNode;
	private ArrayList<PathNode.Node> ReDoNodes = new ArrayList<>();

	private DrawBase drawBase = null;

	public PaintView(Context context,AttributeSet attrs) {
		super(context,attrs);
		this.context = context;
//        mPaint = new Paint();
//        mEraserPaint = new Paint();
//        Init_Paint(UserInfo.PaintColor,UserInfo.PaintWidth);
//        Init_Eraser(UserInfo.EraserWidth);
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        width = manager.getDefaultDisplay().getWidth();
        height = manager.getDefaultDisplay().getHeight();
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
//        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		drawBase = new DrawRect(mCanvas);
	}

    public PaintView(Context context) {
        super(context);
        this.context = context;
//        mPaint = new Paint();
//        mEraserPaint = new Paint();
//        Init_Paint(UserInfo.PaintColor, UserInfo.PaintWidth);
//        Init_Eraser(UserInfo.EraserWidth);
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        width = manager.getDefaultDisplay().getWidth();
        height = manager.getDefaultDisplay().getHeight();
//        mBitmap = BitmapFactory.decodeResource(context.getResources(), mBitmapBackGround).
//                copy(Bitmap.Config.ARGB_8888, false);
//        mBitmap = Bitmap.createScaledBitmap(mBitmap,width,height,false);
		mBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);


//        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    }

    // init paint
//    private void Init_Paint(int color ,int width){
//        mPaint.setAntiAlias(true);
//        mPaint.setDither(true);
//        mPaint.setColor(color);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        mPaint.setStrokeWidth(width);
//    }
//
//
//    // init eraser
//    private void Init_Eraser(int width){
//        mEraserPaint.setAntiAlias(true);
//        mEraserPaint.setDither(true);
//        mEraserPaint.setColor(0xFF000000);
//        mEraserPaint.setStrokeWidth(width);
//        mEraserPaint.setStyle(Paint.Style.STROKE);
//        mEraserPaint.setStrokeJoin(Paint.Join.ROUND);
//        mEraserPaint.setStrokeCap(Paint.Cap.SQUARE);
//        // The most important
//        mEraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//    }

//    // while size is changed
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//		if(IsPaint)
//        	Init_Paint(UserInfo.PaintColor, UserInfo.PaintWidth);
//		else
//			Init_Eraser(UserInfo.EraserWidth);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		drawBase.onDraw(canvas);
//        if(IsPaint)
//            canvas.drawPath(mPath, mPaint);
//        else
//            canvas.drawPath(mPath, mEraserPaint);

    }

//	private void Touch_Down(float x, float y) {
//		mPath.reset();
//		mPath.moveTo(x, y);
//		mX = x;
//		mY = y;
//		 if(IsRecordPath) {
//			 listener.addNodeToPath(x, y, MotionEvent.ACTION_DOWN, IsPaint);
//		 }
//	}
//
//
//	private void Touch_Move(float x, float y) {
//		float dx = Math.abs(x - mX);
//		float dy = Math.abs(y - mY);
//		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
//			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
//			mX = x;
//			mY = y;
//			if(IsRecordPath) {
//				listener.addNodeToPath(x, y, MotionEvent.ACTION_MOVE, IsPaint);
//			}
//		}
//	}
//	private void Touch_Up(Paint paint){
//		mPath.lineTo(mX, mY);
//		mCanvas.drawPath(mPath, paint);
//		mPath.reset();
//        if(IsRecordPath) {
//			listener.addNodeToPath(mX, mY, MotionEvent.ACTION_UP, IsPaint);
//		}
//	}


//	public void setColor(int color) {
//		showCustomToast("已选择颜色" + colorToHexString(color));
//		mPaint.setColor(color);
//	}
//
//
//	public void setPenWidth(int width) {
//		showCustomToast("设定笔粗为：" + width);
//		mPaint.setStrokeWidth(width);
//	}

    public void save(){
        mCanvas.save();
    }

	public void setIsPaint(boolean isPaint) {
		IsPaint = isPaint;
	}

	public void setOnPathListener(OnPathListener listener) {
		this.listener = listener;
	}

//	public void setmEraserPaint(int width){
//		showCustomToast("设定橡皮粗为："+width);
//		mEraserPaint.setStrokeWidth(width);
//	}

	public OnPathListener getListener() {
		return listener;
	}

	public void setIsRecordPath(boolean isRecordPath,PathNode pathNode) {
		this.pathNode = pathNode;
		IsRecordPath = isRecordPath;
	}

	public void setIsRecordPath(boolean isRecordPath) {
		IsRecordPath = isRecordPath;
	}
	public boolean isShowing() {
		return IsShowing;
	}


	private static String colorToHexString(int color) {
		return String.format("#%06X", 0xFFFFFFFF & color);
	}

	// switch eraser/paint
	public void Eraser(){
		showCustomToast("切换为橡皮");
		IsPaint = false;
//		Init_Eraser(UserInfo.EraserWidth);
	}

	public void Paint(){
		showCustomToast("切换为铅笔");
		IsPaint = true;
//		Init_Paint(UserInfo.PaintColor, UserInfo.PaintWidth);
	}

//	public Paint getmEraserPaint() {
//		return mEraserPaint;
//	}
//
//	public Paint getmPaint() {
//		return mPaint;
//	}

	/**
	 *  @author lfk_dsk@hotmail.com
	 *  clean the canvas
	 * */
	public void clean() {
        mBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
		mCanvas.setBitmap(mBitmap);
		try {
			Message msg = new Message();
			msg.obj = PaintView.this;
			msg.what = INDIVIDE;
			handler.sendMessage(msg);
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        IsFirstTime = true;
	}

	/**
	 *  @author lfk_dsk@hotmail.com
	 *  @param uri get the uri of a picture
	 * */
	public void setmBitmap(Uri uri){
		Log.e("图片路径", String.valueOf(uri));
		ContentResolver cr = context.getContentResolver();
		try {
			mBitmapInit = BitmapFactory.decodeStream(cr.openInputStream(uri));
			drawBitmapToCanvas(mBitmapInit);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		invalidate();
	}

	private void drawBitmapToCanvas(Bitmap bitmap){
		if(bitmap.getHeight() > height || bitmap.getWidth() > width){
			RectF rectF = new RectF(0,0,width,height);
			mCanvas.drawBitmap(bitmap, null, rectF, mBitmapPaint);
		}else {
			mCanvas.drawBitmap(bitmap, 0, 0, mBitmapPaint);
		}
	}

	/**
	 *  @author lfk_dsk@hotmail.com
	 *  @param file Pictures' file
	 * */
	public void BitmapToPicture(File file){
		FileOutputStream fileOutputStream;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			Date now = new Date();
			File tempfile = new File(file+"/"+formatter.format(now)+".jpg");
			fileOutputStream = new FileOutputStream(tempfile);
			Bitmap mBitmapbg = BitmapFactory.decodeResource(context.getResources(), mBitmapBackGround).
					copy(Bitmap.Config.ARGB_8888, false);
			mBitmapbg = Bitmap.createScaledBitmap(mBitmapbg,width,height,false);
			if(mBitmapInit != null){
				mBitmapbg = toConformBitmap(mBitmapbg,mBitmapInit);
				mBitmapbg = toConformBitmap(mBitmapbg,mBitmap);
			}else {
				mBitmapbg = toConformBitmap(mBitmapbg,mBitmap);
			}
			mBitmapbg.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
			showCustomToast(tempfile.getName() + "已保存");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void PathNodeToJson(PathNode pathNode,File file){
		ArrayList<PathNode.Node> arrayList = pathNode.getPathList();
		String json = "[";
		for(int i = 0;i < arrayList.size();i++){
			PathNode.Node node = arrayList.get(i);
			json += "{"+"\""+"x"+"\""+":"+node.x+"," +
					"\""+"y"+"\""+":"+node.y+","+
					"\""+"PenColor"+"\""+":"+node.PenColor+","+
					"\""+"PenWidth"+"\""+":"+node.PenWidth+","+
					"\""+"EraserWidth"+"\""+":"+node.EraserWidth+","+
					"\""+"TouchEvent"+"\""+":"+node.TouchEvent+","+
					"\""+"IsPaint"+"\""+":"+"\""+node.IsPaint+"\""+","+
					"\""+"time"+"\""+":"+node.time+
					"},";
		}
		json = json.substring(0,json.length()-1);
		json += "]";
		try {
			json = enCrypto(json, "lfk_dsk@hotmail.com");
		} catch (InvalidKeySpecException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date now = new Date();
		File tempfile = new File(file+"/"+formatter.format(now)+".lfk");
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(tempfile);
			byte[] bytes = json.getBytes();
			fileOutputStream.write(bytes);
			fileOutputStream.close();
			showCustomToast(tempfile.getName() + "已保存");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Bitmap toConformBitmap(Bitmap background, Bitmap foreground) {
		if( background == null ) {
			return null;
		}
		int bgWidth = background.getWidth();
		int bgHeight = background.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
		Canvas cv = new Canvas(bitmap);
		cv.drawBitmap(background, 0, 0, null);//在 0，0坐标开始画入bg
		cv.drawBitmap(foreground, 0, 0, null);//在 0，0坐标开始画入fg ，可以从任意位置画入
		cv.save(Canvas.ALL_SAVE_FLAG);//保存
		cv.restore();//存储
		return bitmap;
	}
	public void clearReUnList(){
		ReDoNodes.clear();
		mBitmapInit = null;
	}

	public void JsonToPathNodeToHandle(Uri uri){
		Message message = new Message();
		message.obj = uri.getPath();
		message.what = CHOOSEPATH;
		handler.sendMessage(message);
	}

	/**
	 *  @author lfk_dsk@hotmail.com
	 *  @param file the file of .lfk
	 * */
	private void JsonToPathNode(String file){
		String res = "";
		ArrayList<PathNode.Node> arrayList = new ArrayList<>();
		try {
			Log.e("绝对路径",file);
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream bufferOut = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			for(int i = in.read(buffer, 0, buffer.length); i > 0 ; i = in.read(buffer, 0, buffer.length)) {
				bufferOut.write(buffer, 0, i);
			}
			res = new String(bufferOut.toByteArray(), Charset.forName("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			res = deCrypto(res, "lfk_dsk@hotmail.com");
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		try {
			JSONArray jsonArray = new JSONArray(res);
			for(int i = 0;i < jsonArray.length();i++){
				JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
				PathNode.Node node = new PathNode().NewAnode();
				// dp
				node.x = jsonObject.getInt("x");
				node.y = jsonObject.getInt("y");
				node.TouchEvent = jsonObject.getInt("TouchEvent");
				node.PenWidth = jsonObject.getInt("PenWidth");
				node.PenColor = jsonObject.getInt("PenColor");
				node.EraserWidth = jsonObject.getInt("EraserWidth");
				node.IsPaint = jsonObject.getBoolean("IsPaint");
				node.time = jsonObject.getLong("time");
				arrayList.add(node);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		pathNode.setPathList(arrayList);
	}

	public int px2dip(float pxValue) {
		final float scale = this.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


	public int dip2px(float dpValue) {
		final float scale = this.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	@Override
	public boolean onTouchEvent(@NonNull MotionEvent event) {
		if(!isShowing()) {
			float x = event.getX();
			float y = event.getY();
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					drawBase.Touch_Down(x, y);
					invalidate();
					break;

				case MotionEvent.ACTION_MOVE:
					drawBase.Touch_Move(x, y);
					invalidate();
					break;

				case MotionEvent.ACTION_UP:
					if (IsPaint) {
						drawBase.Touch_Up();
					} else {
						drawBase.Touch_Up();
					}
					invalidate();
					break;
			}
		}
		return true;
	}

//	public void preview(ArrayList<PathNode.Node> arrayList) {
//		IsRecordPath = false;
//		PreviewThread previewThread = new PreviewThread(this, arrayList);
//		Thread thread = new Thread(previewThread);
//		thread.start();
//	}

	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case INDIVIDE:
					((View) msg.obj).invalidate();
					break;
				case CHOOSEPATH:
					JsonToPathNode(msg.obj.toString());
					break;
			}
			super.handleMessage(msg);
		}
		
	};

	public void showCustomToast(String toast) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.toast_item, (ViewGroup)findViewById(R.id.toast_item));
		TextView text = (TextView) view.findViewById(R.id.toast_text);
		text.setText(toast);
		Toast tempToast = new Toast(context);
		tempToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER, 0, 0);
		tempToast.setDuration(Toast.LENGTH_SHORT);
		tempToast.setView(view);
		tempToast.show();
	}

//	class PreviewThread implements Runnable{
//		private long time;
//		private ArrayList<PathNode.Node> nodes;
//		private View view;
//		public PreviewThread(View view, ArrayList<PathNode.Node> arrayList) {
//			this.view = view;
//			this.nodes = arrayList;
//		}
//		public void run() {
//			time = 0;
//			IsShowing = true;
//			clean();
//			if(mBitmapInit != null){
//				drawBitmapToCanvas(mBitmapInit);
//			}
//			for(int i = 0 ;i < nodes.size();i++) {
//                PathNode.Node node = nodes.get(i);
//				float x = dip2px(node.x);
//				float y = dip2px(node.y);
//                Log.e("pre"+x,"pre"+y);
//				if(i < nodes.size() - 1) {
//					time = nodes.get(i+1).time - node.time;
//				}
//				IsPaint = node.IsPaint;
//				if(node.IsPaint){
//					UserInfo.PaintColor = node.PenColor;
//					UserInfo.PaintWidth = node.PenWidth;
//					Init_Paint(node.PenColor,node.PenWidth);
//				}else {
//					UserInfo.EraserWidth = node.EraserWidth;
//					Init_Eraser(node.EraserWidth);
//				}
//				switch (node.TouchEvent) {
//					case MotionEvent.ACTION_DOWN:
//						Touch_Down(x,y);
//						break;
//					case MotionEvent.ACTION_MOVE:
//					    Touch_Move(x,y);
//						break;
//					case MotionEvent.ACTION_UP:
//						if(node.IsPaint){
//							Touch_Up(mPaint);
//						}else {
//							Touch_Up(mEraserPaint);
//						}
//						break;
//				}
//					Message msg = new Message();
//					msg.obj = view;
//					msg.what = INDIVIDE;
//					handler.sendMessage(msg);
//				if(!ReDoOrUnDoFlag) {
//					try {
//						Thread.sleep(time);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			ReDoOrUnDoFlag = false;
//			IsShowing = false;
//			IsRecordPath = true;
//		}
//	}

		/**
		 * 加密（使用DES算法）
		 *
		 * @param txt
		 *            需要加密的文本
		 * @param key
		 *            密钥
		 * @return 成功加密的文本
		 * @throws InvalidKeySpecException
		 * @throws InvalidKeyException
		 * @throws NoSuchPaddingException
		 * @throws IllegalBlockSizeException
		 * @throws BadPaddingException
		 */
	private static String enCrypto(String txt, String key)
				throws InvalidKeySpecException, InvalidKeyException,
				NoSuchPaddingException, IllegalBlockSizeException,
				BadPaddingException {
		StringBuffer sb = new StringBuffer();
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
		SecretKeyFactory skeyFactory = null;
		Cipher cipher = null;
		try {
			skeyFactory = SecretKeyFactory.getInstance("DES");
			cipher = Cipher.getInstance("DES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		SecretKey deskey = skeyFactory != null ? skeyFactory.generateSecret(desKeySpec) : null;
		if (cipher != null) {
			cipher.init(Cipher.ENCRYPT_MODE, deskey);
		}
		byte[] cipherText = cipher != null ? cipher.doFinal(txt.getBytes()) : new byte[0];
		for (int n = 0; n < cipherText.length; n++) {
			String stmp = (Integer.toHexString(cipherText[n] & 0XFF));

			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}
		}
		return sb.toString().toUpperCase();
	}

		/**
		 * 解密（使用DES算法）
		 *
		 * @param txt
		 *            需要解密的文本
		 * @param key
		 *            密钥
		 * @return 成功解密的文本
		 * @throws InvalidKeyException
		 * @throws InvalidKeySpecException
		 * @throws NoSuchPaddingException
		 * @throws IllegalBlockSizeException
		 * @throws BadPaddingException
		 */
	private static String deCrypto(String txt, String key)
				throws InvalidKeyException, InvalidKeySpecException,
				NoSuchPaddingException, IllegalBlockSizeException,
				BadPaddingException {
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
		SecretKeyFactory skeyFactory = null;
		Cipher cipher = null;
		try {
			skeyFactory = SecretKeyFactory.getInstance("DES");
			cipher = Cipher.getInstance("DES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		SecretKey deskey = skeyFactory != null ? skeyFactory.generateSecret(desKeySpec) : null;
		if (cipher != null) {
			cipher.init(Cipher.DECRYPT_MODE, deskey);
		}
		byte[] btxts = new byte[txt.length() / 2];
		for (int i = 0, count = txt.length(); i < count; i += 2) {
			btxts[i / 2] = (byte) Integer.parseInt(txt.substring(i, i + 2), 16);
		}
		return (new String(cipher.doFinal(btxts)));
	}

	public void ReDoORUndo(boolean flag){
		if(!IsShowing) {
			ReDoOrUnDoFlag = true;
			try {
				if (flag) {
					Log.e("redo","");
					ReDoNodes.add(pathNode.getTheLastNote());
					pathNode.deleteTheLastNote();
//					preview(pathNode.getPathList());
					invalidate();
//					ReDoOrUnDoFlag = true;
//					if(!isShowing())
//						preview(pathNode.getPathList());
				} else {
					Log.e("undo","");
					pathNode.addNode(ReDoNodes.get(ReDoNodes.size() - 1));
					ReDoNodes.remove(ReDoNodes.size() - 1);
//					preview(pathNode.getPathList());
//					ReDoOrUnDoFlag = true;
//					if(!isShowing())
//						preview(pathNode.getPathList());
				}

			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				showCustomToast("无法操作＝－＝");
			}
		}
	}

	public void setDrawBase(DrawBase drawBase) {
		this.drawBase = drawBase;
	}

	public void setDrawBase(int data){
		switch (data){
			case R.id.draw_rect:
				setDrawBase(new DrawRect(mCanvas));
				break;
			case R.id.draw_line:
				setDrawBase(new DrawLine(mCanvas));
				break;
			case R.id.draw_circle:
				setDrawBase(new DrawCircle(mCanvas));
				break;
		}
	}
}
