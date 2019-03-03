package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class ShelterProvider extends ContentProvider {
    /** URI matcher code for the content URI for the pets table */
    private static final int PETS = 100;

    /** URI matcher code for the content URI for a single pet in the pets table */
    private static final int PET_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        sUriMatcher.addURI(ShelterContract.CONTENT_AUTHORITY,ShelterContract.PATH_PETS,PETS);
        sUriMatcher.addURI(ShelterContract.CONTENT_AUTHORITY,ShelterContract.PATH_PETS+"/#",PET_ID);
    }
    /** Tag for the log messages */
    public static final String LOG_TAG = ShelterProvider.class.getSimpleName();

    private ShelterDbHelper helper;
    private SQLiteDatabase db;

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {

        helper = new ShelterDbHelper(getContext());

        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        db = helper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor cursor;
        switch (match){
            case PETS:
                cursor = db.query(ShelterContract.PetEntry.TABLE_NAME,projection,null,
                        selectionArgs,null,null,sortOrder);
                break;
            case PET_ID:
                selection = ShelterContract.PetEntry._ID +"= ?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(ShelterContract.PetEntry.TABLE_NAME,projection,selection,
                        selectionArgs,null,null,sortOrder);
                break;
                default: throw new IllegalArgumentException("Unrecognizable query");
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
    return  cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        db = helper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }
    private Uri insertPet(Uri uri, ContentValues values) {

        getContext().getContentResolver().notifyChange(uri,null);

        if(!values.containsKey(ShelterContract.PetEntry.COLUMN_PET_NAME)||
                values.getAsString(ShelterContract.PetEntry.COLUMN_PET_NAME)==null){
            throw  new IllegalArgumentException("No pet name has been provided");
        }

        if(values.containsKey(ShelterContract.PetEntry.COLUMN_PET_WEIGHT)&&
                (values.get(ShelterContract.PetEntry.COLUMN_PET_WEIGHT)!=null)){
            Integer weight = values.getAsInteger(ShelterContract.PetEntry.COLUMN_PET_WEIGHT);
            if(weight<0){
                throw  new IllegalArgumentException("Weight is less than 0");
            }
        }

        if(values.containsKey(ShelterContract.PetEntry.COLUMN_PET_GENDER)&&
                (values.get(ShelterContract.PetEntry.COLUMN_PET_GENDER)!=null))
        {
            Integer gender = values.getAsInteger(ShelterContract.PetEntry.COLUMN_PET_GENDER);

            if (gender == null || !ShelterContract.PetEntry.isValidGender(gender)) {
                throw new IllegalArgumentException("Pet requires valid gender");
            }
        }

        long id = db.insert(ShelterContract.PetEntry.TABLE_NAME, null, values);
        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);
    }
    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
       int updatedRows = 0;

        switch (sUriMatcher.match(uri)){
            case PETS : db = helper.getWritableDatabase();
                break;
            case PET_ID : db = helper.getWritableDatabase();
                selection = ShelterContract.PetEntry._ID + " =? ";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                break;
            default:
                throw new IllegalArgumentException("Bad data for update : " + uri);
        }

        updatedRows = updatePets(uri,contentValues,selection,selectionArgs);

        if(updatedRows>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return  updatedRows;
    }
    private  int updatePets(Uri uri ,ContentValues contentValues, String selection, String[] selectionArgs){

        if (contentValues.size()<0) {
            return 0;
        }
        if(contentValues.containsKey(ShelterContract.PetEntry.COLUMN_PET_NAME)){
            String name = contentValues.getAsString(ShelterContract.PetEntry.COLUMN_PET_NAME);
            if(name == null){
                throw new IllegalArgumentException("Name is not valid");
            }
        }
        if(contentValues.containsKey(ShelterContract.PetEntry.COLUMN_PET_WEIGHT) ){
            Integer weight =  contentValues.getAsInteger(ShelterContract.PetEntry.COLUMN_PET_WEIGHT);
            if(weight<0){
                throw new IllegalArgumentException("Weight is invalid");
            }
        }
        if(contentValues.containsKey(ShelterContract.PetEntry.COLUMN_PET_GENDER)){
            Integer gender = contentValues.getAsInteger(ShelterContract.PetEntry.COLUMN_PET_GENDER);
            if(gender == null || !ShelterContract.PetEntry.isValidGender(gender)){
                throw new IllegalArgumentException("Invalid gender");
            }
        }


        return db.update(ShelterContract.PetEntry.TABLE_NAME,contentValues,selection,selectionArgs);
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int deletedRows = 0;

        switch (sUriMatcher.match(uri)){
            case PETS :   db = helper.getWritableDatabase();
                          break;

            case PET_ID : db = helper.getWritableDatabase();
                          selection = ShelterContract.PetEntry._ID + " =? ";
                          selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                          break;

            default: throw new IllegalArgumentException("Bad uri for the delete : " + uri);
        }

        deletedRows = deletePets(uri,selection,selectionArgs);

        getContext().getContentResolver().notifyChange(uri,null);

        return  deletedRows;
    }
    private  int deletePets(Uri uri ,String selection ,String[] selectionArgs){
        return db.delete(ShelterContract.PetEntry.TABLE_NAME,selection,selectionArgs);
    }
    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return ShelterContract.PetEntry.CONTENT_LIST_TYPE;
            case PET_ID:
                return ShelterContract.PetEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
