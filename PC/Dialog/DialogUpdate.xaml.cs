using QuanLyLopPC.DAO;
using QuanLyLopPC.DTO;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace QuanLyLopPC.Dialog
{
    /// <summary>
    /// Interaction logic for DialogUpdate.xaml
    /// </summary>
    public partial class DialogUpdate : Window
    {
        private long ID, idfee, IDClass;
        string fee;
        ObservableCollection<FeeDTO> listfee;
        StudentTakeFeeDTO studenttakefee;

        public DialogUpdate(StudentTakeFeeDTO studenttake, string nameclass, string namestudent)
        {
            InitializeComponent();

            this.WindowState = WindowState.Normal;

            // only required for some scenarios
            this.Activate();

            this.Title = "Học sinh " + namestudent + " - " + nameclass;
            txtClass.Text = nameclass;
            txtNameStudent.Text = namestudent;
            getdatetake.SelectedDate = studenttake.datetime;

            studenttakefee = studenttake;

            LoadFee();
            
        }

        //private long IDFee()
        //{
        //    return db.FeeForStudent(ID).ToList().ElementAt(cbfee.SelectedIndex).id;
        //}

        private void LoadFee()
        {
            listfee = FeeDAO.Instance.GetFee();
            cbfee.ItemsSource = listfee;
            cbfee.DisplayMemberPath = "NameFee";
        }

        private void txtNameStudent_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private void btnupdate_Click(object sender, RoutedEventArgs e)
        {
            if (cbfee.SelectedIndex < 0 || getdatetake.SelectedDate.HasValue == false)
                MessageBox.Show("Vui lòng điền đủ thông tin", "Thông Báo", MessageBoxButton.OK, MessageBoxImage.Error);
            else
            {

                MessageBoxResult result = MessageBox.Show("Bạn có chắc chắn cập nhật " + fee + " của học sinh " + txtNameStudent.Text + " - " + txtClass.Text, "Thông Báo", MessageBoxButton.YesNo, MessageBoxImage.Question);

                if (result == MessageBoxResult.Yes)
                {
                    long idfee = listfee.ElementAt(cbfee.SelectedIndex).id;
                    TakeFeeDAO.Instance.UpTakeFee(new StudentTakeFeeDTO(0, studenttakefee.id, studenttakefee.idclass,studenttakefee.idstudent, idfee, getdatetake.SelectedDate.Value)); //idfee,IDClass, ID, idfee, getdatetake.SelectedDate.Value,1));
                    //db.UpdateStudentTakeFee(IDClass, ID, IDFee(), true, getdatetake.SelectedDate.Value);
                    //LoadFee();
                }
            }

        }

        private void btnExit_Click(object sender, RoutedEventArgs e)
        {
            this.Hide();
        }
    }
}
