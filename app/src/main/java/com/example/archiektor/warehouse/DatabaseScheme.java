package com.example.archiektor.warehouse;

class DatabaseScheme {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    static final class SuppplyTable {
        static final String TABLE_NAME = "supplies";
    }

    /* Inner class that defines the table contents */

    static final class Cols {
        public static final String ID = "_id";
        static final String NAME = "name";
        static final String QUANTITY = "quantity";
        static final String CONDITION = "condition";
        static final String IMAGE_ID = "imageId";
        //public static final String FAVORITE = "favorite";
        //public static final String COLUMN_NAME_NULLABLE = ;
    }
}
