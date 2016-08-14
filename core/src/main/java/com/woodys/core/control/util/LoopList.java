package com.woodys.core.control.util;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 循环列表
 *
 * @author momo
 */
public class LoopList<E> implements List<E> {
    private Entry mFirst;
    private Entry mLast;
    private Entry mCurrentEntry;
    private int mSize;

    public LoopList() {
        super();
    }

    public void offer(E e) {
        Entry entry = null;
        if (null == mFirst) {
            entry = new Entry(e, null);
            mCurrentEntry = mFirst = mLast = entry;
        } else {
            entry = new Entry(e, mFirst);
            mLast.next = entry;
            mLast = entry;
        }
        mSize++;
    }

    public E next() {
        E e = mCurrentEntry.e;
        mCurrentEntry = mCurrentEntry.next;
        return e;
    }

    @Override
    public void add(int i, E e) {
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> es) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> es) {
        boolean result = false;
        if (null != es) {
            for (Iterator<? extends E> iterator = es.iterator(); iterator.hasNext(); ) {
                offer(iterator.next());
            }
            result = true;
        }
        return result;
    }

    @Override
    public void clear() {
        mSize = 0;
        this.mFirst = null;
        this.mLast = null;
        this.mCurrentEntry = null;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        return false;
    }

    @Override
    public E get(int i) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    public boolean isEmpty() {
        return null == mFirst;
    }

    @NonNull
    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator(int i) {
        return null;
    }

    @Override
    public E remove(int i) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        return false;
    }

    @Override
    public E set(int i, E e) {
        return null;
    }

    @Override
    public int size() {
        return mSize;
    }

    @NonNull
    @Override
    public List<E> subList(int i, int i2) {
        return null;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] ts) {
        return null;
    }

    public class Entry {
        private E e;
        public Entry next;

        public Entry(E e, Entry next) {
            super();
            this.e = e;
            this.next = next;
        }

    }

}
