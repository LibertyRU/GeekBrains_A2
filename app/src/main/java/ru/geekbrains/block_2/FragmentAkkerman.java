package ru.geekbrains.block_2;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ru.geekbrains.block_2.models.RequestMaker;

public class FragmentAkkerman extends Fragment {

    View v;
    TextView results;
    TextView working;
    Button btnAkk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.content_async, null);
        results = v.findViewById(R.id.akkermanResult);
        working = v.findViewById(R.id.akkermanText);
        btnAkk = v.findViewById(R.id.buttonAkkerman);

        results.setText("");
        working.setText("Нажмите кнопку, чтобы произвести расчет");

        final RequestMaker requestMaker = new RequestMaker(new RequestMaker.OnRequestListener() {
            // Обновим прогресс
            @Override
            public void onStatusProgress(String updateProgress) {
                String s = "Идет расчет... Шаг: " + updateProgress;
                working.setText(s);
            }
            // По окончании загрузки страницы вызовем этот метод, который и вставит текст
            @Override
            public void onComplete(String result) {
                String s = "Расчет завершен. Результат выполнения: " + result;
                results.setText(s);
            }
        });

        btnAkk.setOnClickListener(v -> {
            requestMaker.make(3,3);
        });

        return v;
    }

}
