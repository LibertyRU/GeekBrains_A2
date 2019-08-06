package ru.geekbrains.block_2;

import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.content.Context.SENSOR_SERVICE;


public class FragmentMain extends Fragment {
    private TextView textTemp; // вывод температуры
    private TextView textWater; // вывод влажности
    SensorManager sensorManager;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.content_main, null);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        textTemp = (TextView) v.findViewById(R.id.sensorTemp);
        textWater = (TextView) v.findViewById(R.id.sensorWater);
        getAndRegSensor();
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorGone();
    }

    // получаем сенсоры, если таковые есть у пользователя на телефоне
    // и регистрируем слушателей
    public void getAndRegSensor(){
        // получаем менеджер
        sensorManager = (SensorManager) v.getContext().getSystemService(SENSOR_SERVICE);

        // получаем датчик влажности
        Sensor sensorWater = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        // получаем датчик температуры
        Sensor sensorTemp = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        // Регистрируем слушатели если таковые имеются на устройстве
        if(sensorWater == null){
            textWater.setVisibility(View.GONE);
        } else {
            sensorManager.registerListener(listenerWater, sensorWater,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(sensorTemp == null){
            textTemp.setVisibility(View.GONE);
        }else {
            sensorManager.registerListener(listenerTemp, sensorTemp,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // отключаем слушатели сенсоров
    public void sensorGone(){
        if(textWater.getVisibility() != View.GONE){
            sensorManager.unregisterListener(listenerWater);
        }

        if(textTemp.getVisibility() != View.GONE) {
            sensorManager.unregisterListener(listenerTemp);
        }
    }

    // Слушатель датчика освещенности
    SensorEventListener listenerTemp = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        @Override
        public void onSensorChanged(SensorEvent event) { showTempSensor(event); }
    };

    // Слушатель датчика влажности
    SensorEventListener listenerWater = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        @Override
        public void onSensorChanged(SensorEvent event) {
            showWaterSensor(event);
        }
    };

    // передаем в вьюшку информацию с сенсора температуры
    void showTempSensor(SensorEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Температура = ").append(event.values[0]);
        textTemp.setText(stringBuilder);
    }

    // передаем в вьюшку информацию с сенсора влажности
    void showWaterSensor(SensorEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Влажность = ").append(event.values[0]);
        textWater.setText(stringBuilder);
    }

}
