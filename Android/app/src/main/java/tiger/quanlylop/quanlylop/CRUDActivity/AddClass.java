package tiger.quanlylop.quanlylop.CRUDActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import tiger.quanlylop.quanlylop.DAO.ClassDAO;
import tiger.quanlylop.quanlylop.DTO.ClassDTO;
import tiger.quanlylop.quanlylop.DTO.StudentDTO;
import tiger.quanlylop.quanlylop.FragmentApp.EditStudentFragment;
import tiger.quanlylop.quanlylop.R;

public class AddClass extends AppCompatActivity {

    EditText txtNameClassAdd,txtClassAdd;
    Button btnAddClass,btnCancelAdd;
    int IDClass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclass);

        AnhXa();


        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNameClassAdd.getText().toString().isEmpty() ==false)
                {
                    ClassDTO classDTO = new ClassDTO(1, txtNameClassAdd.getText().toString(), txtClassAdd.getText().toString(), 0);

                    ClassDAO.getInstance().AddClass(getApplicationContext(), classDTO, new ClassDAO.ClassCRUDCallback() {
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
        txtNameClassAdd = findViewById(R.id.txtNameClassAdd);
        txtClassAdd = findViewById(R.id.txtClassAdd);

        btnAddClass =findViewById(R.id.btnAddClass);
        btnCancelAdd = findViewById(R.id.btnCancelAdd);
    }
}
