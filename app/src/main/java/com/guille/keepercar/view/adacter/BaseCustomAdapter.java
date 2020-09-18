package com.guille.keepercar.view.adacter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.BaseAdapter;

import com.guille.keepercar.data.dal.SQLHelper;

/**
 * Created by Guille on 09/22/2015.
 */
public abstract class BaseCustomAdapter extends BaseAdapter {

    private SQLHelper  sqlHelper;
    private SQLiteDatabase db;
    private Context context;

    public BaseCustomAdapter(Context context,SQLHelper sqlHelper){
        setContext(context);
        setSqlHelper(sqlHelper);

    }


    public void setSqlHelper(SQLHelper sqlHelper) {

        db= sqlHelper.getWritableDatabase();
        this.sqlHelper = sqlHelper;
    }


    protected SQLHelper getSqlHelper() {
        return sqlHelper;
    }

    protected SQLiteDatabase getDb() {
        return db;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
