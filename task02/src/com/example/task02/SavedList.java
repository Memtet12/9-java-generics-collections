package com.example.task02;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractList;

public class SavedList<E extends Serializable> extends AbstractList<E> {
    private final File file;
    private List<E> list;

    public SavedList(File file) {
        this.file = file;
        this.list = new ArrayList<>();
        loadFromFile();
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E oldElement = list.set(index, element);
        saveToFile();
        return oldElement;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        saveToFile();
    }

    @Override
    public E remove(int index) {
        E removedElement = list.remove(index);
        saveToFile();
        return removedElement;
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                list = (List<E>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}