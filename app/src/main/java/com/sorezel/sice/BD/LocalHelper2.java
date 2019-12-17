package com.sorezel.sice.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import com.sorezel.sice.Entities.*;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class LocalHelper2 extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    //replace com.binarybricks.shippingwithsqllite with you Application package nae
    //This should be same as which you used package section in your manifest
//    private static String DB_PATH = "/data/data/com.binarybricks.shippingwithsqllite/databases/";
    private static String DB_PATH = "";

    //replace this with name of your db file which you copied into asset folder
    private static String DB_NAME = "datos7.sqlite";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public LocalHelper2(Context context) {
        super(context, DB_NAME, null, 1);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.myContext = context;
        this.createDataBase();
    }
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase(){
        try {
            boolean dbExist = checkDataBase();
            if(dbExist){
                //do nothing - database already exist
            }else{
                //By calling this method and empty database will be created into the default system path
                //of your application so we are gonna be able to overwrite that database with our database.
                this.getReadableDatabase();
                copyDataBase();
            }
        }
        catch (Exception e) { }
    }
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){ /*database does't exist yet.*/ }

        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase(){
        try{
            //Open your local db as the input stream
            InputStream myInput = myContext.getAssets().open(DB_NAME);
            // Path to the just created empty db
            String outFileName = DB_PATH + DB_NAME;
            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }
            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (Exception e) { /*catch exception*/ }
    }
    public SQLiteDatabase openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        return myDataBase;
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
        { myDataBase.close();}
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) { }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public class Consultas{
        public void algo(){
            Toast.makeText(myContext,"Hello there!!",Toast.LENGTH_SHORT).show();
        }
        public Object retUser(String[] dat){
            Object user = null;
            Alumno al = retAlumno(dat);
            Coordinador co = retCoord(dat);
            Maestro ma = retMaestro(dat);
            JefeAcademia ja = retJefeAc(dat);
            JefeDepartamento jd = retJefeDep(dat);
            Escolares esc = retEsc(dat);

            if( al != null)
                user = al;
            else if( co != null)
                user = co;
            else if( ma != null )
                user = ma;
            else if( ja != null )
                user = ja;
            else if( jd != null )
                user = jd;
            else if( esc != null )
                user = esc;

            return user;
        }

        private Escolares retEsc(String[] dat) {
            return null;
        }

        private JefeDepartamento retJefeDep(String[] dat) {
            return null;
        }

        private JefeAcademia retJefeAc(String[] dat) {
            return null;
        }

        private Coordinador retCoord(String[] dat) {
            return null;
        }

        private Maestro retMaestro(String[] dat) {
            return null;
        }

        public Alumno retAlumno(String[] dat){
            Alumno al = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM Alumnos WHERE Matricula = ? and Contraseña = ?"
                    ,dat);
            if( c.moveToLast()){
                int id = c.getInt(0);
                String n = c.getString(1),
                ap=c.getString(2),
                am=c.getString(3),
                p=c.getString(7);
                short s=(short)c.getInt(4);
                Carrera ca = retCarrera(new String[]{""+c.getInt(5)});
                PlanEstudio pl = retPlan(new String[]{""+c.getInt(6)});

                al = new Alumno(id,n,ap,am,p,s,ca);
            }
            c.close();
            return al;
        }

        private PlanEstudio retPlan(String[] dat) {
            PlanEstudio pla = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM PlanEstudios WHERE PlanID = ?"
                    ,dat);
            if( c.moveToLast() ){
                short id = (short)c.getInt(0);
                String n = c.getString(1);

                pla = new PlanEstudio(id,n);
            }
            c.close();
            return pla;
        }

        private Carrera retCarrera(String[] dat) {
            Carrera car= null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM Carreras WHERE CarreraID = ?"
                    ,dat);
            if( c.moveToLast() ){
                int id=c.getInt(0);
                String n=c.getString(1);
                Coordinador co=retCoord(c.getInt(2));

                car = new Carrera(id,n,co);
            }
            c.close();
            return car;
        }

        private Coordinador retCoord(int anInt) {
            Coordinador coo = null;

            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM Coordinadores WHERE CoordID = ?"
                    ,new String[]{""+anInt});
            if( c.moveToLast() ){
                int id=c.getInt(0);
                String n=c.getString(1),
                        ap=c.getString(2),
                        am=c.getString(3),
                        p=c.getString(4);
            }
            c.close();
            return coo;
        }
    }
    public class Insersiones{

    }
    public class Actualizaciones{

    }
    public class Eliminaciones{

    }
}
