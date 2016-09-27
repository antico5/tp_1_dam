package ar.edu.utn.frsf.isi.dam;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private int dias;
    private double monto;
    private double rendimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindSeekBar();
        bindButton();
    }

    private void bindButton() {
        Button button = (Button)findViewById(R.id.concretar);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "Plazo fijo realizado, recibira " + String.valueOf(rendimiento) + "$ al vencimiento.";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    private void bindSeekBar(){
        // comentario
        //y otro comentario
        SeekBar selectorDias = (SeekBar) findViewById(R.id.selectorDias);
        selectorDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textViewDias = (TextView)findViewById(R.id.cantidadDias);
                TextView textViewRendimiento = (TextView)findViewById(R.id.rendimiento);
                EditText editMonto = (EditText)findViewById(R.id.monto);

                dias = progress;
                monto = Double.parseDouble(editMonto.getText().toString());
                rendimiento = calcularRendimiento(monto, dias);

                DecimalFormat df = new DecimalFormat("#.##");

                textViewRendimiento.setText("$" + df.format(rendimiento));
                textViewDias.setText(String.valueOf(dias));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    //prueba de git en w7
    private double calcularRendimiento(double monto, int dias){
        String tasa;
        double tasa_interes;
        if(dias < 30){
            if(monto < 5000) { tasa = getString(R.string.de_0_a_5000_corto); }
            else if(monto < 100000) { tasa = getString(R.string.de_5000_a_99999_corto); }
            else { tasa = getString(R.string.mayor_99999_corto); }
        }
        else {
            if(monto < 5000) { tasa = getString(R.string.de_0_a_5000_largo); }
            else if(monto < 100000) { tasa = getString(R.string.de_5000_a_99999_largo); }
            else { tasa = getString(R.string.mayor_99999_largo); }
        }
        tasa_interes = Double.valueOf(tasa);
        double base = 1 + tasa_interes;
        double exponente = (double)dias/ 360;
        return monto * (Math.pow(base,exponente) - 1);
    }


}
