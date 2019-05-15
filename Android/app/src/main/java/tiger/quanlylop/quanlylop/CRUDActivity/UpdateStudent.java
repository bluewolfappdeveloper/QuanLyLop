package tiger.quanlylop.quanlylop.CRUDActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tiger.quanlylop.quanlylop.DAO.StudentDAO;
import tiger.quanlylop.quanlylop.DTO.StudentDTO;
import tiger.quanlylop.quanlylop.R;

import static tiger.quanlylop.quanlylop.FragmentApp.EditStudentFragment.IDClass;
import static tiger.quanlylop.quanlylop.FragmentApp.EditStudentFragment.NameClass;

public class UpdateStudent extends AppCompatActivity {

    TextView txtNameClassStudentUp;
    EditText txtNameStudentUp,txtPhoneStudentUp;
    Button btnUpStudent,btnCancelUp;
    int IDStudent; String NameStudent, PhoneStudent;
    FragmentManager fragmentManager;
    public static boolean CheckUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatestudent);

        AnhXa();

        CheckUpdate = false;
        fragmentManager = this.getSupportFragmentManager();

        Intent intent = getIntent();
        IDStudent = intent.getIntExtra("ID", -1);
        NameStudent = intent.getStringExtra("NameStudent");
        PhoneStudent = intent.getStringExtra("PhoneStudent");



        if (IDStudent != -1)
        {
           // Toast.makeText(this, Integer.toString(IDStudent) + Integer.toString(IDClass) + NameClass, Toast.LENGTH_LONG).show();

            txtNameClassStudentUp.setText(NameClass);
            txtNameStudentUp.setText(NameStudent);
            txtPhoneStudentUp.setText(PhoneStudent);
        }


        btnUpStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showAlertDialogUpStudent();
            }
        });


        btnCancelUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa()
    {
        txtNameClassStudentUp = findViewById(R.id.txtNameClassStudentUp);
        txtNameStudentUp = findViewById(R.id.txtNameStudentUp);
        txtPhoneStudentUp = findViewById(R.id.txtPhoneStudentUp);
        btnUpStudent =findViewById(R.id.btnUpStudent);
        btnCancelUp = findViewById(R.id.btnCancelUp);
    }


    public void showAlertDialogUpStudent(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cập nhật học sinh");

        String mess = "Bạn có muốn cập nhật học sinh "+NameStudent+" ("+PhoneStudent+ ") thành ";

        String messchange = txtNameStudentUp.getText().toString()+" ("+txtPhoneStudentUp.getText().toString()+ ")  không?";

        builder.setMessage(mess+ messchange);
        builder.setCancelable(true);

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (txtNameStudentUp.getText().toString().isEmpty() == false)
                {
                    StudentDTO studentDTO = new StudentDTO(IDStudent, IDClass, txtNameStudentUp.getText().toString(), txtPhoneStudentUp.getText().toString());

                    StudentDAO.getInstance().UpdateStudent(getApplicationContext(), studentDTO, new StudentDAO.StudentCRUDCallback() {
                        @Override
                        public void onSuccess() {
                            Intent itent = new Intent();
                            setResult(RESULT_OK, itent);

                            finish();
                        }

                        @Override
                        public void onFail() {
                            Toast.makeText(getApplicationContext(), "Cập nhật thất bại", Toast.LENGTH_LONG).show();
                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
