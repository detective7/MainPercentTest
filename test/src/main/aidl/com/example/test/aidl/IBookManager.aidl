// IBookManager.aidl
package com.example.test.aidl;

import com.example.test.aidl.Book;

// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
