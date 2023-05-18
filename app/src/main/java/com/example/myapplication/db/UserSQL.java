package com.example.myapplication.db;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Tour;
import com.example.myapplication.model.User;
import com.example.myapplication.model.Account;

import java.util.ArrayList;
import java.util.List;

public class UserSQL extends SQLiteOpenHelper {

    private static final String DB_NAME = "usertb2";
    private static final int DB_VERSION = 2;

    private Context mContext;

    public UserSQL(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS user(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "birthday TEXT," +
                "gender TEXT," +
                "image TEXT," +
                "role TEXT," +
                "accountID INTEGER," +
                "FOREIGN KEY (accountID) REFERENCES account(id))";
        sqLiteDatabase.execSQL(sql);
        String sql1 = "CREATE TABLE IF NOT EXISTS account(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT)";
        sqLiteDatabase.execSQL(sql1);
        String sql2 = "CREATE TABLE IF NOT EXISTS tour(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "startDate TEXT," +
                "trans TEXT," +
                "duration TEXT," +
                "img TEXT," +
                "total TEXT)";
        sqLiteDatabase.execSQL(sql2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUser(User user, int accountID) {
        String sql = "INSERT INTO user (name, birthday, gender, image, role, accountID)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String agrs[] = {
                user.getName(),
                user.getBirthday(),
                user.getGender(),
                user.getImage(),
                "0",
                String.valueOf(accountID)
        };

        Log.d("Thuc","Test = " + user);

        SharedPreferences pref = mContext.getSharedPreferences("EST", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("first_time", 111);
        editor.apply();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL(sql, agrs);
    }
    public void addUserAdmin(User user, int accountID) {
        String sql = "INSERT INTO user (name, birthday, gender, image, role, accountID)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String agrs[] = {
                user.getName(),
                user.getBirthday(),
                user.getGender(),
                user.getImage(),
                "1",
                String.valueOf(accountID)
        };
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL(sql, agrs);
    }

    public void updateUser(User user) {
        String sql = "UPDATE user SET\n" +
                "name = ?,\n" +
                "birthday = ?," +
                "gender = ?," +
                "image = ?," +
                "role = ?" +
                "WHERE id = ?";
        String args[] = {
                user.getName(),
                user.getBirthday(),
                user.getGender(),
                user.getImage(),
                user.getRole(),
                String.valueOf(user.getId())
        };
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL(sql, args);
    }

    public void deleteUser(User user) {
        String sql = "DELETE FROM user WHERE id = ?";
        String[] agrs = {String.valueOf(user.getId())};
        SQLiteDatabase state = getReadableDatabase();
        Log.d("NM", "delete");
        state.execSQL(sql, agrs);
    }

    public List<User> getAll(int accountID) {
        List<User> list = new ArrayList<>();
        SQLiteDatabase state = getReadableDatabase();
        Cursor cursor = state.rawQuery("SELECT * FROM user WHERE user.accountID = " + accountID, null);
        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String birthday = cursor.getString(2);
            String gender = cursor.getString(3);
            String image = cursor.getString(4);
            String role = cursor.getString(5);
            int accountId = cursor.getInt(6);
            User user = new User(id, name, birthday, gender, image, role);
            Log.d("TAGLOG", user.toString() + " account: " + accountId);
            list.add(user);
        }
        return list;
    }
    public User getOne(int accountId1) {
        SQLiteDatabase state = getReadableDatabase();
        User user = new User();
        Cursor cursor = state.rawQuery("SELECT * FROM user WHERE user.accountID = " + accountId1, null);
        if(cursor != null && cursor.moveToFirst()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String birthday = cursor.getString(2);
            String gender = cursor.getString(3);
            String image = cursor.getString(4);
            String role = cursor.getString(5);
            int accountID1 = cursor.getInt(6);
            user = new User(id, name, birthday, gender, image, role,accountID1);
            cursor.close();
        }
        return user;
    }

    public List<User> search(String txtSearch) {
        List<User> listUser = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE name " +
                " LIKE '%" + txtSearch + "%'", null);
        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String birthday = cursor.getString(2);
            String gender = cursor.getString(3);
            String image = cursor.getString(4);
            String role = cursor.getString(5);
            int accountId = cursor.getInt(6);
            User user = new User(id, name, birthday, gender, image, role);
            Log.d("TAGLOG", user.toString() + " account: " + accountId);
            listUser.add(user);

        }
        return listUser;
    }

    public Account checkLogin(String username, String password) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM account WHERE username = '" + username + "' AND password = '" + password + "'", null);
        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String user = cursor.getString(1);
            String pass = cursor.getString(2);
            Account account = new Account(id, user, pass);
            return account;
        }
        return null;
    }

    public boolean checkUserName(String username) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM account WHERE username = '" + username + "'", null);
        while (cursor != null && cursor.moveToNext()) {
            return false;
        }
        return true;
    }

    public int addAccount(Account account) {
        String sql = "INSERT INTO account (username, password)" +
                "VALUES (?, ?)";
        String args[] = {
                account.getUsername(),
                account.getPassword()
        };
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL(sql, args);
        int id= 0;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM account ORDER BY id DESC LIMIT 1", null);
        while (cursor != null && cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        return id;
    }

    public void updateAccount(Account account) {
        String sql = "UPDATE account SET\n" +
                "username = ?,\n" +
                "password = ?\n" +
                "WHERE id = ?";
        String args[] = {
                account.getUsername(),
                account.getPassword(),
                String.valueOf(account.getId())
        };
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL(sql, args);

    }

    public List<Tour> searchTour(String txtSearch) {
        List<Tour> listUser = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tour WHERE name " +
                " LIKE '%" + txtSearch + "%'", null);
        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String startDate = cursor.getString(2);
            String trans = cursor.getString(3);
            String duration = cursor.getString(4);
            String image = cursor.getString(5);
            String total = cursor.getString(6);
            Tour tour = new Tour(id, name, startDate, trans, duration, image, total);
            Log.d("TAGLOG", tour.toString());
            listUser.add(tour);

        }
        return listUser;
    }

    public void updateTour(Tour tour) {
        String sql = "UPDATE tour SET\n" +
                "name = ?,\n" +
                "startDate = ?," +
                "trans = ?," +
                "duration = ?," +
                "img = ?," +
                "total = ?" +
                "WHERE id = ?";
        String args[] = {
                tour.getName(),
                tour.getStartDate(),
                tour.getTrans(),
                tour.getDuration(),
                tour.getImg(),
                tour.getTotal(),
                String.valueOf(tour.getId())
        };
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL(sql, args);
    }

    public List<Account> getAllAccount() {
        List<Account> list = new ArrayList<>();
        SQLiteDatabase state = getReadableDatabase();
        Cursor cursor = state.rawQuery("SELECT * FROM account", null);
        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            Account account = new Account(id, username);
            list.add(account);
        }
        return list;
    }

    public void deleteAccount(Account account) {
        String sql = "DELETE FROM account WHERE id = ?";
        String[] agrs = {String.valueOf(account.getId())};
        SQLiteDatabase state = getReadableDatabase();
        Log.d("NM", "delete");
        state.execSQL(sql, agrs);
    }

    public List<Tour> getAllTour() {
        List<Tour> list = new ArrayList<>();
        SQLiteDatabase state = getReadableDatabase();
        Cursor cursor = state.rawQuery("SELECT * FROM tour", null);
        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String startDate = cursor.getString(2);
            String trans = cursor.getString(3);
            String duration = cursor.getString(4);
            String img = cursor.getString(5);
            String total = cursor.getString(6);
            Tour tour = new Tour(id, name, startDate, trans, duration, img, total);
            list.add(tour);
        }
        return list;
    }

    public void addTour(Tour tour) {
        String sql = "INSERT INTO tour (name,  startDate,  trans,  duration, img,  total)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String agrs[] = {
                tour.getName(),
                tour.getStartDate(),
                tour.getTrans(),
                tour.getDuration(),
                tour.getImg(),
                tour.getTotal()
        };
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL(sql, agrs);
    }

    public List<User> test() {
        List<User> list = new ArrayList<>();
        SQLiteDatabase state = getReadableDatabase();
        Cursor cursor = state.rawQuery("SELECT * FROM user", null);
        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String birthday = cursor.getString(2);
            String gender = cursor.getString(3);
            String image = cursor.getString(4);
            String role = cursor.getString(5);
            int accountId = cursor.getInt(6);
            User user = new User(id, name, birthday, gender, image, role, accountId);
            list.add(user);
        }
        return list;
    }

    public void deleteTour(Tour tour) {
        String sql = "DELETE FROM tour WHERE id = ?";
        String[] agrs = {String.valueOf(tour.getId())};
        SQLiteDatabase state = getReadableDatabase();
        Log.d("NM", "delete");
        state.execSQL(sql, agrs);
    }


}
