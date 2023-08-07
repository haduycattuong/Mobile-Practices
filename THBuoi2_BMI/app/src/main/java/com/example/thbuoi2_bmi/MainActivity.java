package com.example.thbuoi2_bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_ChieuCao, et_CanNang;
    RadioButton rbNam, rbNu;
    Button btnTinh;
    TextView txtKetQua;
    Double chiSo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_CanNang = findViewById(R.id.et_CanNang);
        et_ChieuCao = findViewById(R.id.et_chieuCao);
        rbNam = findViewById(R.id.rbNam);
        rbNu  = findViewById(R.id.rbNu);
        btnTinh = findViewById(R.id.btnTinh);
        txtKetQua = findViewById(R.id.txtKetQua);

        btnTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double chieuCao = Double.parseDouble(et_ChieuCao.getText().toString()) / 100;
                double canNang = Double.parseDouble(et_CanNang.getText().toString());
                chiSo = Math.round((canNang/Math.pow(chieuCao, 2)) * 10.0)  / 10.0;

                if(rbNam.isChecked()) {
                    if(chiSo < 18.5) {
                        txtKetQua.setText(String.valueOf(chiSo) + " Bổ sung dinh dưỡng");
                    }
                    if(chiSo >= 18.5 && chiSo <= 24.9) {
                        txtKetQua.setText(String.valueOf(chiSo) + " BMI bình thường");
                    }
                    if(chiSo == 25 ) {
                        txtKetQua.setText(String.valueOf(chiSo) + " Thừa cân");
                    }
                    if(chiSo > 25 && chiSo <= 24.9) {
                        txtKetQua.setText(String.valueOf(chiSo) + " Tiền béo phì");
                    }
                    if(chiSo >= 25 && chiSo <= 29.9) {
                        txtKetQua.setText(String.valueOf(chiSo) + " Béo phì mức độ 1");
                    }
                }
                else if(rbNu.isChecked()) {
                    if(chiSo < 18.5) {
                        txtKetQua.setText(String.valueOf(chiSo) + " Bổ sung dinh dưỡng");
                    }
                    if(chiSo >= 18.5 && chiSo <= 22.9) {
                        txtKetQua.setText(String.valueOf(chiSo) + " BMI bình thường");
                    }
                    if(chiSo == 23 ) {
                        txtKetQua.setText(String.valueOf(chiSo) + " Thừa cân");
                    }
                    if(chiSo > 23 && chiSo <= 24.9) {
                        txtKetQua.setText(String.valueOf(chiSo) + " Tiền béo phì");
                    }
                    if(chiSo >= 25 && chiSo <= 29.9) {
                        txtKetQua.setText(String.valueOf(chiSo) + " Béo phì mức độ 1");
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn giới tính", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}