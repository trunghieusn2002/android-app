package com.example.foodapp.cartitem;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CartItem.class}, version = 1, exportSchema = false)
public abstract class CartItemDatabase extends RoomDatabase {
    public abstract CartItemDao cartItemDao();

    private static volatile CartItemDatabase INSTANCE;

    public static CartItemDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CartItemDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CartItemDatabase.class, "cart_item_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
