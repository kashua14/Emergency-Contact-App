package com.kashua14.emergencyapp.Database;

import android.provider.BaseColumns;

final class ContactReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ContactReaderContract() {
    }

    /* Inner class that defines the table contents */
    static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "emergency_contacts";
        static final String COLUMN_NAME_NAME = "name";
        static final String COLUMN_NAME_PHONE = "phone";
        static final String COLUMN_NAME_RELATIONSHIP = "relationship";
    }
}
