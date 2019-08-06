package ru.geekbrains.block_2.models;

import android.os.AsyncTask;


public class RequestMaker {

    // Слушатель, при помощи которого отправим обратный вызов о готовности страницы
    private OnRequestListener listener;

    // В конструкторе примем слушателя, а в дальнейшем передадим его асинхронной задаче
    public RequestMaker(OnRequestListener onRequestListener) {
        listener = onRequestListener;
    }

    // Сделать запрос
    public void make(Integer m, Integer n) {
        // Создаем объект асинхронной задачи (передаем ей слушатель)
        Akkerman akk = new Akkerman(listener);
        // Запускаем асинхронную задачу
        akk.execute(m,n);
    }

    // Интерфейс слушателя с методами обратного вызова
    public interface OnRequestListener {
        void onStatusProgress(String updateProgress);   // Вызов для обновления прогресса

        void onComplete(String result);                 // Вызов при завершении обработки
    }

private static class Akkerman extends AsyncTask<Integer,String,String> {
    private int count = 0;
    private OnRequestListener listener;

    Akkerman(OnRequestListener listener) {
        this.listener = listener;
    }


    @Override
    protected String doInBackground(Integer... integers) {
        int m = integers[0];
        int n = integers[1];
        return Integer.toString(akr(m, n));
    }

    @Override
    protected void onPostExecute(String content) {
        listener.onComplete(content);
    }

    @Override
    protected void onProgressUpdate(String... strings) {
        listener.onStatusProgress(strings[0]);
    }

    // вычисление функции Аккермана
    private int akr(int m, int n) {
        if (m == 0) {
            count++;
            publishProgress("Текущий шаг: " + Integer.toString(getCount()));
            return n + 1;
        } // Шаг рекурсии по 1 условию
        else if (n == 0 && m > 0) {
            count++;
            publishProgress("Текущий шаг: " + Integer.toString(getCount()));
            return akr(m - 1, 1);
        } // Шаг рекурсии по 2 условию
        else {
            count++;
            publishProgress("Текущий шаг: " + Integer.toString(getCount()));
            return akr(m - 1, akr(m, n - 1));
        }
    }

    // получение текущего шага
    public int getCount() {
        return this.count;
    }
}
}
