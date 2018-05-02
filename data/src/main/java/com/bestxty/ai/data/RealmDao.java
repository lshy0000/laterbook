package com.bestxty.ai.data;

import org.json.JSONArray;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class RealmDao {

    private static RealmDao instance;
    private Realm realm;

    private RealmDao(Realm realm) {
        if (realm == null) realm = Realm.getDefaultInstance();
        this.realm = realm;
    }

    public static RealmDao getInstance() {
        if (instance == null) instance = new RealmDao(null);
        return instance;
    }

    public void begin() {
        realm.beginTransaction();
    }

    public void commit() {
        realm.commitTransaction();
    }
//    public <T extends  RealmObject>List<T > findAll(Class<? extends RealmObject> clazz){
//        List<T>re  =new ArrayList<>();
//        RealmResults<? extends RealmObject> a = realm.where(clazz).findAll();
//    }

    /**
     * 添加(性能优于下面的saveOrUpdate（）方法)
     *
     * @param object
     * @return 保存或者修改是否成功
     */
    public boolean insert(RealmObject object) {
        try {
            realm.beginTransaction();
            realm.insert(object);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            return false;
        }
    }

    /**
     * 添加(性能优于下面的saveOrUpdateBatch（）方法)
     *
     * @param list
     * @return 批量保存是否成功
     */
    public boolean insert(List<? extends RealmObject> list) {
        try {
            realm.beginTransaction();
            realm.insert(list);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            return false;
        }
    }

    /**
     * 添加或者修改(性能优于下面的saveOrUpdate（）方法)
     *
     * @param object
     * @return 保存或者修改是否成功
     */
    public boolean insertOrUpdate(RealmObject object) {
        try {
            realm.beginTransaction();
            realm.insertOrUpdate(object);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            return false;
        }
    }


    /**
     * 添加或者修改(性能优于下面的saveOrUpdateBatch（）方法)
     *
     * @param list
     * @return 保存或者修改是否成功
     */
    public boolean insertOrUpdateBatch(List<? extends RealmObject> list) {
        try {
            realm.beginTransaction();
            realm.insertOrUpdate(list);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            return false;
        }
    }

    /**
     * 添加或者修改
     *
     * @param object
     * @return 已经保存的对象
     */
    public <E extends RealmObject> E saveOrUpdate(E object) {
        E savedE = null;
        try {
            realm.beginTransaction();
            savedE = realm.copyToRealmOrUpdate(object);
            realm.commitTransaction();
            return savedE;
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            return savedE;
        }
    }

    /**
     * 批量添加或者修改
     *
     * @param list
     * @return 全部保存成功 或 全部失败
     */
    public boolean saveOrUpdateBatch(List<? extends RealmObject> list) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(list);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            return false;
        }
    }


    /**
     * save or update RealmObject from json data
     *
     * @param jsonObject json数据
     * @param clazz      具体类型
     * @return 已经保存的对象
     */
    public RealmObject saveOrUpdateFromJSON(Class<? extends RealmObject> clazz, String jsonObject) {
        RealmObject RealmObject = null;
        try {
            realm.beginTransaction();
            RealmObject = realm.createOrUpdateObjectFromJson(clazz, jsonObject);
            realm.commitTransaction();
            return RealmObject;
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            return RealmObject;
        }
    }

    /**
     * batch save or update from json array
     *
     * @param json  json数组
     * @param clazz 类型
     * @return 批量保存json对象是否成功
     */
    public boolean saveOrUpdateFromJSONBatch(Class<? extends RealmObject> clazz, JSONArray json) {
        try {
            realm.beginTransaction();
            realm.createOrUpdateAllFromJson(clazz, json);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            return false;
        }
    }

    /**
     * 删除当前表中所有数据
     *
     * @param clazz
     * @return
     */
    public boolean deleteAll(Class<? extends RealmObject> clazz) {
        try {
            realm.beginTransaction();
            realm.delete(clazz);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            return false;
        }
    }

    /**
     * 按照id删除制定的记录
     *
     * @param clazz       类型
     * @param idFieldName id字段的名称
     * @param id          id字段值
     * @return
     */
    public boolean deleteById(Class<? extends RealmObject> clazz, String idFieldName, int id) {
        try {
            realm.beginTransaction();
            realm.where(clazz).equalTo(idFieldName, id).findAll().deleteFirstFromRealm();
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            return false;
        }
    }

    /**
     * 查询所有
     *
     * @return 返回结果集合
     */
    public <T extends RealmObject> RealmResults<T> findAll(Class<T> clazz) {
        return realm.where(clazz).findAll();
    }

    /**
     * 清空数据库
     *
     * @return
     */
    public boolean clearDatabase() {
        try {
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            return false;
        }
    }

    public void delete(RealmObject bookReadBean) {
        realm.beginTransaction();
        bookReadBean.deleteFromRealm();
        realm.commitTransaction();
    }

    public static <T extends RealmObject> Boolean sava(T userEntity) {
        if (userEntity == null) return false;
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(userEntity);
            realm.commitTransaction();
        }
        return Boolean.TRUE;
    }

    public static <T extends RealmObject> Boolean sava(List<T> userEntity) {
        if (userEntity == null) return false;
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(userEntity);
            realm.commitTransaction();
        }
        return Boolean.TRUE;
    }

    public static <T extends RealmObject> Boolean clearsava(List<T> userEntity) {
        if (userEntity == null) return false;
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(userEntity);
            realm.commitTransaction();
        }
        return Boolean.TRUE;
    }

    public static <T extends RealmObject> Boolean clearsava(T userEntity) {
        if (userEntity == null) return false;
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.delete(userEntity.getClass());
            realm.copyToRealmOrUpdate(userEntity);
            realm.commitTransaction();
        }
        return Boolean.TRUE;
    }
}