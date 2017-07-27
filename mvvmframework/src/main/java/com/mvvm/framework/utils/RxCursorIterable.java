package com.mvvm.framework.utils;

import android.database.Cursor;

import java.util.Iterator;

/**
 * Created by AboelelaA on 7/27/2017.
 * Create iterable for database cursor
 */

public class RxCursorIterable implements Iterable<Cursor> {

    private Cursor mIterableCursor;

    public RxCursorIterable(Cursor c) {
        mIterableCursor = c;
    }

    public static RxCursorIterable from(Cursor c) {
        return new RxCursorIterable(c);
    }

    @Override
    public Iterator<Cursor> iterator() {
        return RxCursorIterator.from(mIterableCursor);
    }

    static class RxCursorIterator implements Iterator<Cursor> {

        private final Cursor mCursor;

        public RxCursorIterator(Cursor cursor) {
            mCursor = cursor;
        }

        public static Iterator<Cursor> from(Cursor cursor) {
            return new RxCursorIterator(cursor);
        }

        @Override
        public boolean hasNext() {
            return !mCursor.isClosed() && mCursor.moveToNext();
        }

        @Override
        public Cursor next() {
            return mCursor;
        }
    }
}
