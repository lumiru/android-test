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
    private Context context;
    private String tableName;
    private String[] fieldNames;
    private String[] colNames;
    private Class<T> klass;

    public DAO(Context context, Class<T> klass) {
        this.context = context;
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

    /**
     * Get a database object from its ID.
     * @param id The ID of the object
     * @return The loaded object
     */
    public T get(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(tableName, colNames,
                "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        try {
            if(cursor.moveToFirst()) {
                return fill(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.close();
        }

        return null;
    }

    /**
     * Get database objects from the value of a field
     * @param selector The field to use
     * @param value The requested value
     * @param <V> The requested value type
     * @return The loaded objects
     */
    public <V> List<T> get(String selector, V value) {
        return get(selector, "=", value);
    }

    /**
     * Get database objects from the value of a field
     * @param selector The field to use
     * @param operator The operator to select corresponding values
     * @param value The requested value
     * @param <V> The requested value type
     * @return The loaded objects
     */
    public <V> List<T> get(String selector, String operator, V value) {
        return query(selector.replaceAll("([A-Z])", "_$1").toLowerCase() + " " + operator + " ?",
                new String[]{String.valueOf(value)});
    }

    /**
     * Get database objects from a set of a field values
     * @param selector The field to use
     * @param values The accepted values
     * @param <V> The requested value type
     * @return The loaded objects
     */
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

    /**
     * Get all database objects for this DAO
     * @return The loaded objects
     */
    public List<T> getAll() {
        return query(null, null);
    }

    /**
     * Loads objects from a SQL WHERE statement and corresponding requested values
     * @param query The SQL WHERE statement
     * @param values The values replacing ?s in the previous parameter
     * @return The loaded objects
     */
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

    /**
     * Saves an object to the database
     * @param object The object to save. Its ID is updated if the object is new in this database.
     */
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

    /**
     * Saves several objects to the database
     * @param objects The objects to save
     */
    public void save(List<T> objects) {
        for (T object : objects) {
            save(object);
        }
    }

    /**
     * Removes an object from database
     * @param id The id of the object to delete
     */
    public void delete(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(tableName, "id = ?", new String[]{String.valueOf(id)});
    }

    /**
     * Removes an object from database
     * @param object The object to delete
     */
    public void delete(T object) {
        delete(object.getId());
    }

    private T fill(Cursor cursor) throws IllegalAccessException, InstantiationException,
            NoSuchMethodException, NoSuchFieldException, InvocationTargetException, ParseException {
        T object = klass.newInstance();
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
                else if(value instanceof Boolean) {
                    values.put(colName, (boolean) value ? 1 : 0);
                }
                else {
                    values.put(colName, String.valueOf(value));
                }
            }
        }

        return values;
    }

    /**
     * Loads objects of a ManyToOne association suffixed by -Object into the list
     */
    public List<T> loadAssociation(List<T> list, String associationFieldName) {
        try {
            String baseMethodName = associationFieldName.substring(0, 1).toUpperCase() + associationFieldName.substring(1);
            Method getMethod = klass.getMethod("get" + baseMethodName);

            // Get each associated IDs
            Integer[] ids = new Integer[list.size()];
            T v;
            for (int i = 0, visitesSize = list.size(); i < visitesSize; i++) {
                v = list.get(i);
                ids[i] = (Integer) getMethod.invoke(v);
            }

            // Get setMethod to populate the list
            Method getObject = klass.getMethod("get" + baseMethodName + "Object");
            Class<? extends Data> fieldType = (Class<? extends Data>) getObject.getReturnType();
            Method setMethod = klass.getMethod("set" + baseMethodName + "Object", fieldType);

            // Get associated objects
            DAO<?> dao = new DAO<>(context, fieldType);
            List<? extends Data> associated = dao.getIn("id", ids);

            // Associate the objects to the list
            for (Data data : associated) {
                for (T item : list) {
                    if((int) getMethod.invoke(item) == data.getId()) {
                        setMethod.invoke(item, data);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }

    /**
     * A static method to get a DAO
     */
    public static <E extends Data> DAO<E> getDAO(Context context, Class<E> klass) {
        return new DAO<>(context, klass);
    }

    /**
     * Save an object without loading a local DAO
     */
    public static <E extends Data> void save(Context context, E object) {
        DAO<E> dao = new DAO<>(context, (Class<E>) object.getClass());
        dao.save(object);
    }

    /**
     * Save several objects without loading a local DAO
     */
    public static <E extends Data> void save(Context context, List<E> list) {
        if(list.size() > 0) {
            DAO<E> dao = new DAO<>(context, (Class<E>) list.get(0).getClass());
            dao.save(list);
        }
    }
}