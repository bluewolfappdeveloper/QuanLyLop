package tiger.quanlylop.quanlylop.CRUDActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import static tiger.quanlylop.quanlylop.FragmentApp.EditStudentFragment.NameClass;

public class UpdateFee extends AppCompatActivity {

    EditText txtNameFeeUp,txtFeeUp;
    Button btnUpFee,btnCancelUp;
    int ID;
    String NameFee, PriceFee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatefee);

        AnhXa();


        Intent intent = getIntent();
        ID = intent.getIntExtra("ID", -1);
        NameFee = intent.getStringExtra("NameFee");
        PriceFee = intent.getStringExtra("PriceFee");

        //Toast.makeText(this,NameClass, Toast.LENGTH_LONG).show();


           // Toast.makeText(this, Integer.toString(IDStudent) + Integer.toString(IDClass) + NameClass, Toast.LENGTH_LONG).show();

        txtNameFeeUp.setText(NameFee);
        txtFeeUp.setText(PriceFee);


        btnUpFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showAlertDialogUpFee();
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
        txtNameFeeUp = findViewById(R.id.txtNameFeeUp);
        txtFeeUp = findViewById(R.id.txtFeeUp);

        btnUpFee =findViewById(R.id.btnUpFee);
        btnCancelUp = findViewById(R.id.btnCancelUp);
    }


    public void showAlertDialogUpFee(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cập nhật học sinh");

        String mess = "Bạn có muốn cập nhật "+ NameFee +" ("+PriceFee+ ") thành ";

        String messchange = txtNameFeeUp.getText().toString()+" ("+txtFeeUp.getText().toString()+ ")  không?";

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
                if (txtNameFeeUp.getText().toString().isEmpty() == false)
                {
                    FeeDTO feeDTO = new FeeDTO(ID, txtNameFeeUp.getText().toString(), txtFeeUp.getText().toString());

                    FeeDAO.getInstance().UpdateFee(getApplicationContext(), feeDTO, new FeeDAO.feeCRUDCallback() {
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
