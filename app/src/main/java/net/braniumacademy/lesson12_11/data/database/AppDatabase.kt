package net.braniumacademy.lesson12_11.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import net.braniumacademy.lesson12_11.data.dao.UserDao
import net.braniumacademy.lesson12_11.data.model.User

@Database(entities = [User::class], version = 2,
autoMigrations = [
    AutoMigration(from = 1, to = 2, spec = AppDatabase.DbMigrationSpec::class)
])
abstract class AppDatabase : RoomDatabase() {
    @RenameTable(fromTableName = "users", toTableName = "user")
    class DbMigrationSpec: AutoMigrationSpec

    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                db
            }
        }
    }
}