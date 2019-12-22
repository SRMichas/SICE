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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                this.getWritableDatabase();
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
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
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
            Escolares esc = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM JefeServicios " +
                            "WHERE JefSID = ? and Contraseña = ?"
                    ,dat);
            if( c.moveToLast() ){
                int id=c.getInt(0);
                String n=c.getString(1),
                        ap=c.getString(2),
                        am=c.getString(3),
                        ps=c.getString(4);
                esc = new Escolares(id,n,ap,ap,ps);
            }
            c.close();
            return esc;
        }

        private JefeDepartamento retJefeDep(String[] dat) {
            JefeDepartamento jd = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM JefeDivisiones " +
                            "WHERE JefDID = ? and Contraseña = ?"
                    ,dat);
            if( c.moveToLast() ){
                int id=c.getInt(0);
                String n=c.getString(1),
                        ap=c.getString(2),
                        am=c.getString(3),
                        ps=c.getString(4);
                jd = new JefeDepartamento(id,n,ap,ap,ps);
            }
            c.close();
            return jd;
        }

        private JefeAcademia retJefeAc(String[] dat) {
            JefeAcademia ja = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM JefeAcademicos " +
                            "WHERE JefAID = ? and Contraseña = ?"
                    ,dat);
            if( c.moveToLast() ){
                int id=c.getInt(0);
                String n=c.getString(1),
                        ap=c.getString(2),
                        am=c.getString(3),
                        ps=c.getString(4);
                ja = new JefeAcademia(id,n,ap,ap,ps);
            }
            c.close();
            return ja;
        }

        private Coordinador retCoord(String[] dat) {
            Coordinador coo = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM Coordinadores " +
                            "WHERE CoordID = ? and Contraseña = ?"
                    ,dat);
            if( c.moveToLast() ){
                int id=c.getInt(0);
                String n=c.getString(1),
                        ap=c.getString(2),
                        am=c.getString(3),
                        ps=c.getString(4);
                coo = new Coordinador(id,n,ap,ap,ps);
            }
            c.close();
            return coo;
        }

        private Maestro retMaestro(String[] dat) {
            Maestro ma = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM Maestros " +
                            "WHERE MaestroID = ? and Contraseña = ?"
                    ,dat);
            if( c.moveToLast() ){
                int id=c.getInt(0);
                String n=c.getString(1),
                        ap=c.getString(2),
                        am=c.getString(3),
                        ps=c.getString(4);
                ma = new Maestro(id,n,ap,ap,ps);
            }
            c.close();
            return ma;
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

        public Solicitud retSolbyAlumn(String[] dat) {
            Solicitud sol = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM Solicitudes WHERE Matricula = ?"
                    ,dat);
            if( c.moveToLast() ){
                int id=c.getInt(0);
                String fchs=c.getString(1),fchr=c.getString(2);
                Alumno al = retAlumno(c.getInt(3));
                Carrera car = retCarrera(new String[]{""+c.getInt(4)});
                Status s=retStatus(c.getInt(5));
                sol = new Solicitud(id,fchs,fchr,al,car,s);
            }
            c.close();
            return sol;
        }

        private Status retStatus(int sid) {
            Status s = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM Status WHERE StatusID = ?",
                    new String[]{""+sid});
            if( c.moveToLast()){
                short id=(short)c.getInt(0);
                String sig=c.getString(1);
                s = new Status(id,sig);
            }
            c.close();
            return s;
        }

        private Alumno retAlumno(int sid) {
            Alumno al = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM Alumnos WHERE Matricula = ?"
                    ,new String[]{""+sid});
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

        public ArrayList<Carrera> retCarreras() {
            ArrayList<Carrera> carreras = null;
            Carrera car = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM Carreras",null);
            if( c.moveToFirst()){
                carreras = new ArrayList<>();
                int id;
                String n;
                Coordinador coo;
                while(!c.isAfterLast()){
                    id=c.getInt(0);
                    n=c.getString(1);
                    coo=retCoord(c.getInt(2));
                    car = new Carrera(id,n,coo);
                    carreras.add(car);
                    c.moveToNext();
                }
            }
            c.close();
            return carreras;
        }
        public ArrayList<Materia> retMaterias(int sid){
            ArrayList<Materia> materias = null;
            Materia mat = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT k.MateriaID,m.Nombre,k.Creditos,k.Calificacion " +
                            "FROM Kardex k " +
                            "INNER JOIN Materias m on k.MateriaID = m.MateriaID " +
                            "INNER JOIN Alumnos a on k.Matricula = a.Matricula " +
                            "WHERE k.Matricula = ?",
                    new String[]{""+sid});
            if( c.moveToFirst()){
                materias = new ArrayList<>();
                int id;
                String n;
                short ca,cr;
                short cred,cal;
                while(!c.isAfterLast()){
                    id=c.getInt(0);
                    n=c.getString(1);
                    cred=(short)c.getInt(2);
                    cal=(short)c.getInt(3);
                    mat = new Materia(id,n,cred,cal);
                    materias.add(mat);
                    c.moveToNext();
                }
            }
            c.close();
            return materias;
        }

        public ArrayList<Solicitud> retSolsbyAlum(int uid, char car) {
            ArrayList<Solicitud> sols = null;
            Solicitud sol;
            Cursor c = myDataBase.rawQuery(
                    "SELECT s.* " +
                            "FROM Solicitudes s " +
                            "INNER JOIN Carreras ca ON s.CarrSol = ca.CarreraID " +
                            "INNER JOIN Coordinadores co ON ca.CoordinadorID = co.CoordID " +
                            "WHERE co.CoordID = ? and s.Status = ?",
                    new String[]{""+uid,""+car});
            if( c.moveToFirst()){
                sols = new ArrayList<>();
                int id;
                String fh1,fh2;
                Alumno al;
                Carrera carr;
                Status s;

                while(!c.isAfterLast()){
                    id=c.getInt(0);
                    fh1=c.getString(1);
                    fh2=c.getString(2);
                    al=retAlumno(c.getInt(3));
                    carr=retCarrera(new String[]{""+c.getInt(4)});
                    s=retStatus(c.getInt(5));
                    sol = new Solicitud(id,fh1,fh2,al,carr,s);
                    sols.add(sol);
                    c.moveToNext();
                }
            }
            c.close();
            return sols;
        }
        public ArrayList<Solicitud> retSolsbyAlum2(int uid, char car) {
            ArrayList<Solicitud> sols = null;
            Solicitud sol;
            Cursor c = myDataBase.rawQuery(
                    "SELECT s.* " +
                            "FROM Solicitudes s " +
                            "INNER JOIN Carreras ca ON s.CarrSol = ca.CarreraID " +
                            "INNER JOIN Coordinadores co ON ca.CoordinadorID = co.CoordID " +
                            "WHERE s.Status = ?",
                    new String[]{""+car});
            if( c.moveToFirst()){
                sols = new ArrayList<>();
                int id;
                String fh1,fh2;
                Alumno al;
                Carrera carr;
                Status s;

                while(!c.isAfterLast()){
                    id=c.getInt(0);
                    fh1=c.getString(1);
                    fh2=c.getString(2);
                    al=retAlumno(c.getInt(3));
                    carr=retCarrera(new String[]{""+c.getInt(4)});
                    s=retStatus(c.getInt(5));
                    sol = new Solicitud(id,fh1,fh2,al,carr,s);
                    sols.add(sol);
                    c.moveToNext();
                }
            }
            c.close();
            return sols;
        }

        public ArrayList<Academia> retAcademias() {
            ArrayList<Academia> sols = null;
            Academia acad;
            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM Academia", null);
            if( c.moveToFirst()){
                sols = new ArrayList<>();
                int id;
                String n;
                JefeAcademia cad;
                while(!c.isAfterLast()){
                    id=c.getInt(0);
                    n=c.getString(1);
                    cad=retJefeAc(c.getInt(2));
                    acad = new Academia(id,n,cad);
                    sols.add(acad);
                    c.moveToNext();
                }
            }
            c.close();
            return sols;
        }

        private JefeAcademia retJefeAc(int uid) {
            JefeAcademia ja = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT * FROM JefeAcademicos " +
                            "WHERE JefAID = ?"
                    ,new String[]{""+uid});
            if( c.moveToLast() ){
                int id=c.getInt(0);
                String n=c.getString(1),
                        ap=c.getString(2),
                        am=c.getString(3),
                        ps=c.getString(4);
                ja = new JefeAcademia(id,n,ap,ap,ps);
            }
            c.close();
            return ja;
        }

        public Map<Character, Object> retMateriasC(String[] iData) {
            Map<Character, Object> data = null;
            ArrayList<Materia> m1,m2;
            ArrayList<Boolean> some;
            ArrayList<Integer> por;
            Materia ma1,ma2;
            boolean fua;
            Solicitud sol;
            boolean test = false;
            /*Cursor c = myDataBase.rawQuery(
                    "SELECT m.MateriaID,m.Nombre,k.Creditos,k.Calificacion," +
                            "ds.MateriaC,m2.Nombre,m2.Creditos,ds.Calificacion," +
                            "ds.Porcentaje,ds.Aceptada " +
                            "FROM DetalleSolicitud ds " +
                            "INNER JOIN Solicitudes s ON ds.SolID = s.SolID " +
                            "INNER JOIN Kardex k ON ds.MateriaID = k.MateriaID " +
                            "INNER JOIN Materias m ON k.MateriaID = m.MateriaID " +
                            "INNER JOIN Alumnos a ON k.Matricula = a.Matricula " +
                            "INNER JOIN Materias m2 ON ds.MateriaC = m2.MateriaID " +
                            "WHERE s.SolID = ? and s.Matricula = ? and s.Status = ?",
                    iData);*/
            Cursor c = myDataBase.rawQuery(
                    "SELECT 0,0,s.Matricula,ds.MateriaID," +
                            "m2.MateriaID,m2.Nombre,0,ds.Calificacion," +
                            "ds.Porcentaje,ds.Aceptada " +
                            "FROM DetalleSolicitud ds " +
                            "INNER JOIN Solicitudes s ON ds.SolID = s.SolID " +
                            "INNER JOIN Materias m2 ON ds.MateriaC = m2.MateriaID " +
                            "WHERE s.SolID = ? and s.Matricula = ? and s.Status = ?",
                    iData);
            if( c.moveToFirst()){
                data = new HashMap<>();
                m1 = new ArrayList<>();
                m2 = new ArrayList<>();
                some = new ArrayList<>();
                por = new ArrayList<>();
                int id;
                String n;
                short cr,ca,acept;
                //sol=retSol
                String msg = "Tamaño= "+c.getCount()
                        +"\nMatC = ";
                Toast.makeText(myContext,msg,Toast.LENGTH_SHORT).show();
                if( test )
                    return null;
                while(!c.isAfterLast()){
                    id=c.getInt(0);
                    n=c.getString(1);
                    cr=(short) c.getInt(2);
                    ca=(short)c.getInt(3);
                    //ma1=new Materia(id,n,cr,ca);
                    //ma1 = retMateriaByIdAl();
                    ma1 = retMateria(c.getInt(2),c.getInt(3));
                    m1.add(ma1);

                    id=c.getInt(4);
                    n=c.getString(5);
                    cr=(short) c.getInt(6);
                    ca=(short)c.getInt(7);
                    ma2=new Materia(id,n,cr,ca);
                    m2.add(ma2);

                    por.add(c.getInt(8));

                    acept = (short)c.getInt(9);
                    fua = (acept == 1);
                    some.add(fua);

                    c.moveToNext();
                }
                data.put('1',m1);
                data.put('2',m2);
                data.put('3',some);
                data.put('4',por);
            }
            c.close();

            return data;
        }

        public Map<Character, Object> retMateriasC2(String[] iData) {
            Map<Character, Object> data = null;
            ArrayList<Materia> m1,m2;
            ArrayList<Boolean> some;
            ArrayList<Integer> por;
            Materia ma1,ma2;
            boolean fua;
            Solicitud sol;
            boolean test = false;
            /*Cursor c = myDataBase.rawQuery(
                    "SELECT 0,0,s.Matricula,ds.MateriaID," +
                            "m2.MateriaID,m2.Nombre,0,ds.Calificacion," +
                            "ds.Porcentaje,ds.Aceptada " +
                            "FROM DetalleSolicitud ds " +
                            "INNER JOIN Solicitudes s ON ds.SolID = s.SolID " +
                            "INNER JOIN Materias m2 ON ds.MateriaC = m2.MateriaID " +
                            "WHERE s.SolID = ? and s.Matricula = ? and s.Status = ?",
                    iData);*/
            Cursor c = myDataBase.rawQuery(
                    "SELECT 0,0,s.Matricula,ds.MateriaID, " +
                            "m2.MateriaID,m2.Nombre,m2.Creditos,ds.Calificacion " +
                            "FROM DetalleConvalidacion dc " +
                            "INNER JOIN DetalleSolicitud ds ON dc.SolID = ds.SolID AND dc.MateriaID = ds.MateriaID " +
                            "INNER JOIN Solicitudes s ON ds.SolID = s.SolID " +
                            "INNER JOIN Materias m2 ON ds.MateriaC = m2.MateriaID " +
                            "WHERE s.SolID = ? and s.Matricula = ? and s.Status = ?",
                    iData);
            if( c.moveToFirst()){
                data = new HashMap<>();
                m1 = new ArrayList<>();
                m2 = new ArrayList<>();
                some = new ArrayList<>();
                por = new ArrayList<>();
                int id;
                String n;
                short cr,ca,acept;
                //sol=retSol
                String msg = "Tamaño= "+c.getCount()
                        +"\nMatC = ";
                Toast.makeText(myContext,msg,Toast.LENGTH_SHORT).show();
                if( test )
                    return null;
                while(!c.isAfterLast()){
                    id=c.getInt(0);
                    n=c.getString(1);
                    cr=(short) c.getInt(2);
                    ca=(short)c.getInt(3);
                    //ma1=new Materia(id,n,cr,ca);
                    //ma1 = retMateriaByIdAl();
                    ma1 = retMateria(c.getInt(2),c.getInt(3));
                    m1.add(ma1);

                    id=c.getInt(4);
                    n=c.getString(5);
                    cr=(short) c.getInt(6);
                    ca=(short)c.getInt(7);
                    ma2=new Materia(id,n,cr,ca);
                    m2.add(ma2);

                    c.moveToNext();
                }
                data.put('1',m1);
                data.put('2',m2);
                data.put('3',some);
                data.put('4',por);
            }
            c.close();

            return data;
        }

        private Materia retMateriaByIdAl(int anInt, int anInt1) {
            return null;
        }

        private Materia retMateria(int matr,int mid) {
            Materia mat = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT k.MateriaID,m.Nombre,k.Creditos,k.Calificacion " +
                            "FROM Kardex k " +
                            "INNER JOIN Materias m on k.MateriaID = m.MateriaID " +
                            "INNER JOIN Alumnos a on k.Matricula = a.Matricula " +
                            "WHERE k.Matricula = ? and m.MateriaID = ?",
                    new String[]{""+matr,""+mid});
            if( c.moveToFirst()){
                int id;
                String n;
                short ca,cr;
                short cred,cal;
                id=c.getInt(0);
                n=c.getString(1);
                cred=(short)c.getInt(2);
                cal=(short)c.getInt(3);
                mat = new Materia(id,n,cred,cal);
            }
            c.close();
            return mat;
        }


        public ArrayList<Maestro> retMaestrosPerAcade(int uid) {
            ArrayList<Maestro> mstros = null;
            Maestro mstro;
            Cursor c = myDataBase.rawQuery(
                    "SELECT m.* FROM MaestrosAcademia ma " +
                            "INNER JOIN Maestros m ON ma.MaestroID = m.MaestroID " +
                            "INNER JOIN Academia a ON ma.ID = a.ID " +
                            "WHERE a.JefeA = ?",
                    new String[]{""+uid});
            if( c.moveToFirst()){
                mstros = new ArrayList<>();
                int id;
                String n,ap,am,pa;
                while(!c.isAfterLast()){
                    id=c.getInt(0);
                    n=c.getString(1);
                    ap=c.getString(2);
                    am=c.getString(3);
                    pa=c.getString(4);
                    mstro = new Maestro(id,n,ap,am,pa);
                    mstros.add(mstro);
                    c.moveToNext();
                }
            }
            c.close();
            return mstros;

        }

        public int getAcadByBoss(int id) {
            int data = 0;
            Cursor c = myDataBase.rawQuery(
                    "SELECT ID FROM Academia WHERE JefeA = ?",
                    new String[]{""+id});
            if( c.moveToLast())
                data = c.getInt(0);
            c.close();
            return data;
        }

        public Maestro retDosId(String[] data) {
            Maestro ma = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT m.* " +
                            "FROM DetalleSolicitud ds " +
                            "INNER JOIN Solicitudes s ON ds.SolID = s.SolID " +
                            "INNER JOIN Maestros m ON ds.MaestroID = m.MaestroID " +
                            "WHERE s.SolID = ? and s.Matricula = ? and s.Status = ?",
                    data);

            if( c.moveToLast() ){
                int id=c.getInt(0);
                String n=c.getString(1),
                        ap=c.getString(2),
                        am=c.getString(3),
                        ps=c.getString(4);
                ma = new Maestro(id,n,ap,ap,ps);
            }
            c.close();
            return ma;
        }
        public int retLastConva() {
            int data = 0;
            Cursor c = myDataBase.rawQuery(
                    "SELECT Folio FROM Convalidaciones ", null);
            if( c.moveToLast())
                data = c.getInt(0);
            c.close();
            return data;
        }

        public JefeAcademia retJefeAca(String[] data) {
            JefeAcademia ja = null;
            Cursor c = myDataBase.rawQuery(
                    "SELECT ja.* FROM Convalidaciones c " +
                            "INNER JOIN JefeAcademicos ja ON c.JefeSub = ja.JefAID " +
                            "WHERE 0"
                    ,data);
            if( c.moveToLast() ){
                int id=c.getInt(0);
                String n=c.getString(1),
                        ap=c.getString(2),
                        am=c.getString(3),
                        ps=c.getString(4);
                ja = new JefeAcademia(id,n,ap,ap,ps);
            }
            c.close();
            return ja;
        }
        public int retJefeAca2(String[] data) {
            int ja = 0;
            Cursor c = myDataBase.rawQuery(
                    "SELECT ja.JefAID " +
                            "FROM DetalleConvalidacion dc " +
                            "INNER JOIN Convalidaciones c  ON  dc.Folio = c.Folio " +
                            "INNER JOIN JefeAcademicos ja ON c.JefeSub = ja.JefAID " +
                            "INNER JOIN Solicitudes s ON dc.SolID = s.SolID " +
                            "WHERE s.SolID = ? and s.Matricula = ? and s.Status = ?"
                    ,data);
            if( c.moveToLast() ){
                ja = c.getInt(0);
            }
            c.close();
            return ja;
        }
        public String retCred(int mid){
            String data = "";
            Cursor c = myDataBase.rawQuery("SELECT Creditos FROM Materias " +
                    "WHERE MateriaID = ?",new String[]{""+mid});
            if( c.moveToFirst() )
                data=""+c.getInt(0);
            c.close();
            return data;
        }
        public String retName(String[] data){
            String name = null;

            Cursor c = myDataBase.rawQuery(
                    "Select ja.Nombre,ja.ApePaterno,ApeMaterno " +
                            "FROM DetalleConvalidacion dc " +
                            "INNER JOIN Convalidaciones c ON dc.Folio = c.Folio " +
                            "INNER JOIN JefeAcademicos ja ON c.JefeSub = ja.JefAID " +
                            "INNER JOIN Solicitudes s ON dc.SolID = s.SolID " +
                            "WHERE s.SolID = ? and s.Matricula = ? and s.Status = ?",
                            data);
            if( c.moveToFirst()){
                name=c.getString(0)+" "+c.getString(1)+" ";
                if( c.getString(2) != null)
                    name+=c.getString(2);
            }
            c.close();
            return name;
        }
        public String retName2(String[] data){
            String name = null;

            Cursor c = myDataBase.rawQuery(
                    "Select ja.Nombre,ja.ApePaterno,ApeMaterno " +
                            "FROM DetalleConvalidacion dc " +
                            "INNER JOIN Convalidaciones c ON dc.Folio = c.Folio " +
                            "INNER JOIN JefeDivisiones ja ON c.JefeDiv = ja.JefDID " +
                            "INNER JOIN Solicitudes s ON dc.SolID = s.SolID " +
                            "WHERE s.SolID = ? and s.Matricula = ? and s.Status = ?",
                    data);
            if( c.moveToFirst()){
                name=c.getString(0)+" "+c.getString(1)+" ";
                if( c.getString(2) != null)
                    name+=c.getString(2);
            }
            c.close();
            return name;
        }

        public int retJefeDiv(String[] data) {
            int ja = 0;
            Cursor c = myDataBase.rawQuery(
                    "SELECT ja.JefDID " +
                            "FROM DetalleConvalidacion dc " +
                            "INNER JOIN Convalidaciones c  ON  dc.Folio = c.Folio " +
                            "INNER JOIN JefeDivisiones ja ON c.JefeDiv = ja.JefDID " +
                            "INNER JOIN Solicitudes s ON dc.SolID = s.SolID " +
                            "WHERE s.SolID = ? and s.Matricula = ? and s.Status = ?"
                    ,data);
            if( c.moveToLast() ){
                ja = c.getInt(0);
            }
            c.close();
            return ja;
        }
    }
    public class Insersiones{
        public void insertSol(String[] d){
            //Cursor c = myDataBase.rawQuery("INSERT INTO Solicitudes VALUES (?,?,?,?,?,?)",d);
            String sent = "INSERT INTO Solicitudes VALUES (?,?,?,?,?,?)";
            myDataBase.execSQL(sent,d);
        }

        public void insertAsig(String[] newData2) {
            String sent = "INSERT INTO Asignacion VALUES (?,?,0)";
            myDataBase.execSQL(sent,newData2);
        }

        public void insertDetSol(String[] newD2) {
            String sent = "INSERT INTO DetalleSolicitud VALUES (?,?,?,?,?,?,?)";
            myDataBase.execSQL(sent,newD2);
        }

        public void insertConva(String[] newD2) {
            Consultas selects = new Consultas();
            int id = selects.retLastConva() + 1;
            String sent = "INSERT INTO Convalidaciones VALUES ("+id+",0,0,?,?)";
            myDataBase.execSQL(sent,newD2);
        }

        public void inserDetConva(String[] nD3) {
            String sent = "INSERT INTO DetalleConvalidacion VALUES (?,?,?,?,?)";
            myDataBase.execSQL(sent,nD3);
        }
    }
    public class Actualizaciones{
        public void updateSol(String[] newData) {
            String sentence = "UPDATE Solicitudes SET Status = ? WHERE SolID = ? and Matricula = ? and Status = ?";
            myDataBase.execSQL(sentence,newData);
        }

        public void updateAsig(String[] newData2) {
            String sentence = "UPDATE Asignacion SET MaestroID = ? WHERE AcaID = ? and SolID = ?";
            myDataBase.execSQL(sentence,newData2);
        }
        public void updateConva(String[] data,boolean flag){
            String sentence;
            if( flag )
                sentence = "UPDATE Convalidaciones SET JefeDiv = ?, Fecha = ? WHERE JefeSub = ?";
            else
                sentence = "UPDATE Convalidaciones SET JefeServ = ?, Fecha = ? WHERE JefeSub = ? and JefeDiv = ?";
            myDataBase.execSQL(sentence,data);
        }
    }
    public class Eliminaciones{
        public void deleteSol(int solid){
            /*Cursor c = myDataBase.rawQuery(
                    "DELETE FROM Solicitudes WHERE SolID = ? "
                    ,new String[]{""+solid});*/
            myDataBase.delete("Solicitudes","SolID=?",new String[]{""+solid});
            clearAsignacion();
            DetSol();
            myDataBase.delete("DetalleConvalidacion",null,null);
            myDataBase.delete("Convalidaciones",null,null);

        }
        public void clearAsignacion(){
            myDataBase.delete("Asignacion",null,null);
        }
        public void DetSol(){
            myDataBase.delete("DetalleSolicitud",null,null);
        }
    }
}
