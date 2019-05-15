package tiger.quanlylop.quanlylop.CRUDActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tiger.quanlylop.quanlylop.DAO.ClassDAO;
import tiger.quanlylop.quanlylop.DAO.FeeDAO;
import tiger.quanlylop.quanlylop.DTO.ClassDTO;
import tiger.quanlylop.quanlylop.DTO.FeeDTO;
import tiger.quanlylop.quanlylop.R;

public class AddFee extends AppCompatActivity {

    EditText txtNameFeeAdd,txtFeeAdd;
    Button btnAddFee,btnCancelAdd;
    int IDClass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfee);

        AnhXa();


        btnAddFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNameFeeAdd.getText().toString().isEmpty() ==false)
                {
                    FeeDTO feeDTO = new FeeDTO(1, txtNameFeeAdd.getText().toString(), txtFeeAdd.getText().toString());

                    FeeDAO.getInstance().AddFee(getApplicationContext(), feeDTO, new FeeDAO.feeCRUDCallback() {
                        @Override
                        public void onSuccess() {

                            Intent itent = new Intent();
                            setResult(RESULT_OK, itent);
                            finish();
                        }

                        @Override
                        public void onFail() {
                            Toast.makeText(getApplicationContext(), "Vui lòng thử lại", Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnCancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void AnhXa()
    {
        txtNameFeeAdd = findViewById(R.id.txtNameFeeAdd);
        txtFeeAdd = findViewById(R.id.txtFeeAdd);

        btnAddFee =findViewById(R.id.btnAddFee);
        btnCancelAdd = findViewById(R.id.btnCancelAdd);
    }
}
