package in.turp.persistancetp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.turp.persistancetp.data.Assortiment;
import in.turp.persistancetp.data.Client;
import in.turp.persistancetp.data.Enseigne;
import in.turp.persistancetp.data.Famille;
import in.turp.persistancetp.data.FrontVente;

/**
 * Created by lumiru on 02/07/15.
 */
public class DAO<T extends Data> {
    private DatabaseHelper helper;
    private String tableName;
    private String[] fieldNames;
    private String[] colNames;
    private Class<T> klass;

    public DAO(Context context, Class<T> klass) {
        helper = new DatabaseHelper(context);
        this.klass = klass;
        tableName = klass.getSimpleName().toLowerCase();
        List<String> fieldList = new ArrayList<>();
        List<String> colList = new ArrayList<>();
        Field[] fields = klass.getDeclaredFields();
        for (Field field : fields) {
            if(field.getType().isPrimitive() || field.getType() == Date.class || field.getType() == String.class) {
                fieldList.add(field.getName());
                colList.add(field.getName().replaceAll("([A-Z])", "_$1").toLowerCase());
            }
        }
        fieldNames = fieldList.toArray(new String[fieldList.size()]);
        colNames = colList.toArray(new String[colList.size()]);
    }

    public T get(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(tableName, colNames,
                "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        try {
            return fill(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.close();
        }

        return null;
    }

    public <V> List<T> get(String selector, V value) {
        return get(selector, "=", value);
    }

    public <V> List<T> get(String selector, String operator, V value) {
        return query(selector.replaceAll("([A-Z])", "_$1").toLowerCase() + " " + operator + " ?",
                new String[]{String.valueOf(value)});
    }

    public <V> List<T> getIn(String selector, V[] values) {
        String[] placeholders = new String[values.length];
        String[] stringValues = new String[values.length];
        for (int i = values.length - 1; i >= 0; --i) {
            placeholders[i] = "?";
            stringValues[i] = String.valueOf(values[i]);
        }
        return query(
                selector.replaceAll("([A-Z])", "_$1").toLowerCase() +
                        " IN (" + TextUtils.join(",", placeholders) + ")",
                stringValues);
    }

    public List<T> getAll() {
        return query(null, null);
    }

    private List<T> query(String query, String[] values) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(tableName, colNames,
                query, values, null, null, null);

        List<T> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                list.add(fill(cursor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.close();
        }

        return list;
    }

    public void save(T object) {
        SQLiteDatabase db;
        ContentValues values = null;
        try {
            values = buildRow(object);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(object.getId() > 0 && get(object.getId()) != null) {
            db = helper.getWritableDatabase();
            db.update(tableName, values,
                    "id = ?", new String[]{String.valueOf(object.getId())});
            db.close();
        }
        else {
            db = helper.getWritableDatabase();
            int id = (int) db.insert(tableName, "id", values);
            db.close();

            if(id <= 0) {
                throw new Error("Error when inserting " + tableName + " object in database");
            }
            object.setId(id);
        }
    }

    public void save(List<T> objects) {
        for (T object : objects) {
            save(object);
        }
    }

    public void delete(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(tableName, "id = ?", new String[]{String.valueOf(id)});
    }

    public void delete(T object) {
        delete(object.getId());
    }

    private T fill(Cursor cursor) throws IllegalAccessException, InstantiationException,
            NoSuchMethodException, NoSuchFieldException, InvocationTargetException, ParseException {
        T object = (T) klass.newInstance();
        String attrName, methodName;
        Method method;
        Class<?> fieldType;

        for (int i = 0, l = fieldNames.length; i < l; i++) {
            attrName = fieldNames[i];
            methodName = "set" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
            fieldType = klass.getDeclaredField(attrName).getType();
            method = klass.getMethod(methodName, fieldType);

            if(fieldType == int.class) {
                method.invoke(object, cursor.getInt(i));
            }
            else if(fieldType == boolean.class) {
                method.invoke(object, cursor.getInt(i) == 1);
            }
            else if(fieldType == String.class) {
                method.invoke(object, cursor.getString(i));
            }
            else if(fieldType == Date.class) {
                if(!cursor.isNull(i)) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
                    method.invoke(object, df.parse(cursor.getString(i)));
                }
            }
        }

        return object;
    }

    private ContentValues buildRow(T object) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        ContentValues values = new ContentValues();
        String colName, attrName, methodName;
        Method method;

        for (int i = 0, colNamesLength = colNames.length; i < colNamesLength; i++) {
            colName = colNames[i];
            if(colName.equals("id")) continue;
            attrName = fieldNames[i];
            methodName = "get" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
            method = klass.getMethod(methodName);

            Object value = method.invoke(object);
            if(value != null) {
                if(value instanceof Date) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
                    values.put(colName, df.format(value));
                }
                else {
                    values.put(colName, String.valueOf(value));
                }
            }
        }

        return values;
    }

    public static <E extends Data> DAO<E> getDAO(Context context, Class<E> klass) {
        return new DAO<>(context, klass);
    }

    public static <E extends Data> void save(Context context, E object) {
        DAO<E> dao = new DAO<>(context, (Class<E>) object.getClass());
        dao.save(object);
    }

    public static <E extends Data> void save(Context context, List<E> list) {
        if(list.size() > 0) {
            DAO<E> dao = new DAO<>(context, (Class<E>) list.get(0).getClass());
            dao.save(list);
        }
    }
}