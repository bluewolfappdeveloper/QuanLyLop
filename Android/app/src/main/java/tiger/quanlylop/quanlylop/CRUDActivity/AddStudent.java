package tiger.quanlylop.quanlylop.CRUDActivity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import tiger.quanlylop.quanlylop.DAO.DataProvider;
import tiger.quanlylop.quanlylop.DAO.StudentDAO;
import tiger.quanlylop.quanlylop.DTO.StudentDTO;
import tiger.quanlylop.quanlylop.FragmentApp.EditStudentFragment;
import tiger.quanlylop.quanlylop.R;

public class AddStudent extends AppCompatActivity {

    TextView txtNameClassStudent;
    EditText txtNameStudentAdd,txtPhoneStudentAdd;
    Button btnAddStudent,btnCancelAdd;
    int IDClass;
    public static int SelectedIndex = 0;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);

        AnhXa();

        fragmentManager = getSupportFragmentManager();

        IDClass = EditStudentFragment.IDClass;
        txtNameClassStudent.setText(EditStudentFragment.NameClass);

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNameStudentAdd.getText().toString().isEmpty() ==false)
                {
                    StudentDTO studentDTO = new StudentDTO(1, IDClass,txtNameStudentAdd.getText().toString(), txtPhoneStudentAdd.getText().toString());

                    StudentDAO.getInstance().AddStudent(getApplicationContext(), studentDTO, new StudentDAO.StudentCRUDCallback() {
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
        txtNameClassStudent = findViewById(R.id.txtNameClassStudent);
        txtNameStudentAdd = findViewById(R.id.txtNameStudentAdd);
        txtPhoneStudentAdd = findViewById(R.id.txtPhoneStudentAdd);
        btnAddStudent =findViewById(R.id.btnAddStudent);
        btnCancelAdd = findViewById(R.id.btnCancelAdd);
    }
}
