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

import tiger.quanlylop.quanlylop.DAO.ClassDAO;
import tiger.quanlylop.quanlylop.DAO.StudentDAO;
import tiger.quanlylop.quanlylop.DTO.ClassDTO;
import tiger.quanlylop.quanlylop.DTO.StudentDTO;
import tiger.quanlylop.quanlylop.R;

import static tiger.quanlylop.quanlylop.FragmentApp.EditStudentFragment.IDClass;
import static tiger.quanlylop.quanlylop.FragmentApp.EditStudentFragment.IDStudent;
import static tiger.quanlylop.quanlylop.FragmentApp.EditStudentFragment.NameClass;

public class UpdateClass extends AppCompatActivity {

    EditText txtNameClassUp,txtFeeClassUp;
    Button btnUpClass,btnCancelUp;
    int ID;
    String NameStudent, FeeClass;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateclass);

        AnhXa();
        

        Intent intent = getIntent();
        ID = intent.getIntExtra("ID", -1);
        NameClass = intent.getStringExtra("NameClass");
        FeeClass = intent.getStringExtra("FeeClass");

        //Toast.makeText(this,NameClass, Toast.LENGTH_LONG).show();


           // Toast.makeText(this, Integer.toString(IDStudent) + Integer.toString(IDClass) + NameClass, Toast.LENGTH_LONG).show();

        txtNameClassUp.setText(NameClass);
        txtFeeClassUp.setText(FeeClass);


        btnUpClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showAlertDialogUpClass();
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
        txtNameClassUp = findViewById(R.id.txtNameClassUp);
        txtFeeClassUp = findViewById(R.id.txtFeeClassUp);

        btnUpClass =findViewById(R.id.btnUpClass);
        btnCancelUp = findViewById(R.id.btnCancelUp);
    }


    public void showAlertDialogUpClass(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cập nhật học sinh");

        String mess = "Bạn có muốn cập nhật lớp "+NameClass+" ("+FeeClass+ ") thành ";

        String messchange = txtNameClassUp.getText().toString()+" ("+txtFeeClassUp.getText().toString()+ ")  không?";

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
                if (txtNameClassUp.getText().toString().isEmpty() == false)
                {
                    ClassDTO classDTO = new ClassDTO(ID, txtNameClassUp.getText().toString(), txtFeeClassUp.getText().toString(), 0);

                    ClassDAO.getInstance().UpdateClass(getApplicationContext(), classDTO, new ClassDAO.ClassCRUDCallback() {
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
