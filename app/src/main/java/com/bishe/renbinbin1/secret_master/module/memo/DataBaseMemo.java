package com.bishe.renbinbin1.secret_master.module.memo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bishe.renbinbin1.secret_master.module.memo.bean.MemoBean;

import java.util.ArrayList;

/**
 * Created by renbinbin1 on 2017/3/15.
 */

public class DataBaseMemo {
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db ;
    public  DataBaseMemo(Context context){
        dataBaseHelper=new DataBaseHelper(context);
        db=dataBaseHelper.getWritableDatabase();
    }


    //查询
    public ArrayList<MemoBean> query(){
        Cursor cursor=db.query("memo",null,null,null,null,null,null);
        ArrayList<MemoBean> memoBeanArrayList= new ArrayList<>();
        if(cursor!=null){
            while (cursor.moveToNext()){
                String id=cursor.getString(cursor.getColumnIndex("id"));
                String text=cursor.getString(cursor.getColumnIndex("content"));
                String time=cursor.getString(cursor.getColumnIndex("time"));
                MemoBean memoBean=new MemoBean();
                memoBean.setId(Integer.parseInt(id));
                memoBean.setText(text);
                memoBean.setTime(time);
                memoBeanArrayList.add(memoBean);
            }
        }
        cursor.close();
        return memoBeanArrayList;
    }
    //增加
    public long insert(MemoBean memoBean){
        ContentValues values=new ContentValues();
        values.put("content",memoBean.getText());
        values.put("time",memoBean.getTime());
        return db.insert("memo",null,values);
    }
    //更新
    public long update(MemoBean memoBean){
        ContentValues values = new ContentValues();
        values.put("content",memoBean.getText());
        values.put("time",memoBean.getTime());
        values.put("id",memoBean.getId());
        return db.update("memo", values, "id = ?", new String[]{memoBean.getId() + ""});
    }
    //删除
    public long delete(int id){
        return db.delete("memo","id = ?",new String[]{id+""});
    }
}

