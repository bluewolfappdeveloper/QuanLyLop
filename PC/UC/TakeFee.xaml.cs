using QuanLyLopPC.DAO;
using QuanLyLopPC.Dialog;
using QuanLyLopPC.DTO;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace QuanLyLopPC.UC
{

    public partial class TakeFee : UserControl, INotifyPropertyChanged
    {

        ObservableCollection<ClassDTO> listclass;
        ObservableCollection<StudentInfoFeeDTO> liststudent;
        ObservableCollection<FeeDTO> listfee;
        ObservableCollection<StudentInfoFeeTakeDTO> listfeetake;


        public TakeFee()
        {
            InitializeComponent();
            this.DataContext = this;
            btndelete.IsEnabled = btnupdate.IsEnabled = false;
            
            LoadClass();
            
            LoadFee();

            getdatetake.SelectedDate = DateTime.Now.Date;
        }

        #region

        #region LoadData

        private void LoadClass()
        {
            listclass = ClassDAO.Instance.GetClass();

            cbclass.ItemsSource = listclass;
            cbclass.DisplayMemberPath = "NameClass";
        }

        private void LoadStudent()
        {
            liststudent = TakeFeeDAO.Instance.GetStudentFeeInfo(listclass.ElementAt(cbclass.SelectedIndex).id);

            lvstudent.ItemsSource = liststudent;
            cbfinding.ItemsSource = liststudent;
            cbfinding.DisplayMemberPath = "NameStudent";
        }

        private void LoadFee()
        {
            listfee = FeeDAO.Instance.GetFee();
            cbfee.ItemsSource = listfee;
            cbfee.DisplayMemberPath = "NameFee";
        }

        private void LoadFeeData()
        {
            listfeetake = TakeFeeDAO.Instance.GetStudentInfoFeeTake(liststudent.ElementAt(lvstudent.SelectedIndex).id);

            lvfee.ItemsSource = listfeetake;
        }

        #endregion

        private void LoadData()
        {
            LoadClass();
            getdatetake.SelectedDate = DateTime.Now;
        }

        private void cbclass_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            LoadStudent();
            lvfee.ItemsSource = null;
            lvfee.Items.Clear();
        }

        private void lvstudent_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (lvstudent.SelectedIndex != -1)
            {
                btnadd.IsEnabled = true;
                //txtNameStudent.Text = cbfee.Text = getdatetake.Text = "";
                LoadFee();
                LoadFeeData();
            }
            else
            {
                btnadd.IsEnabled = btndelete.IsEnabled = btnupdate.IsEnabled = false;
                cbfee.ItemsSource = null; cbfee.Items.Clear(); getdatetake.Text = "";
                getdatetake.SelectedDate = DateTime.Now;

                cbfinding.SelectedIndex = lvstudent.SelectedIndex;
                lvstudent.UpdateLayout();
                lvstudent.ScrollIntoView(lvstudent.SelectedItem);
            }

        }

        private void SetStudent()
        {
            int index = lvstudent.SelectedIndex;
            LoadStudent();
            lvstudent.SelectedIndex = index;
        }

        private void btnadd_Click(object sender, RoutedEventArgs e)
        {
            if (cbfee.SelectedIndex < 0 || getdatetake.SelectedDate.HasValue == false)
                MessageBox.Show("Vui lòng điền đủ thông tin", "Thông Báo", MessageBoxButton.OK, MessageBoxImage.Error);
            else
            {

                if (lvstudent.SelectedIndex < 0 || cbclass.SelectedIndex < 0 ) return;

                long IDStudent = liststudent.ElementAt(lvstudent.SelectedIndex).id;
                long IDClass = listclass.ElementAt(cbclass.SelectedIndex).id;
                long IDFee = listfee.ElementAt(cbfee.SelectedIndex).id;
                DateTime date = getdatetake.SelectedDate.Value;

                TakeFeeDAO.Instance.AddTakeFee(new StudentTakeFeeDTO(0,0,IDClass,IDStudent, IDFee, date));
               

                LoadFeeData();
                LoadFee();
                SetStudent();
            }
        }

        private void lvfee_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (lvfee.SelectedIndex >= 0)
            {
                btnupdate.IsEnabled = btndelete.IsEnabled = true;
            }
            else
            {
                btnupdate.IsEnabled = btndelete.IsEnabled = false;
            }
        }

        private void btnupdate_Click(object sender, RoutedEventArgs e)
        {
            if (lvstudent.SelectedIndex < 0 || cbclass.SelectedIndex < 0 || lvfee.SelectedIndex < 0) return;

            
            long IDStudent = liststudent.ElementAt(lvstudent.SelectedIndex).id;
            long IDClass = listclass.ElementAt(cbclass.SelectedIndex).id;
            long ID = listfeetake.ElementAt(lvfee.SelectedIndex).id;
            string NameFee = listfeetake.ElementAt(lvfee.SelectedIndex).NameFee;

            StudentTakeFeeDTO studentTakeFee = new StudentTakeFeeDTO(0, ID, IDClass, IDStudent, ID, getdatetake.SelectedDate.Value);
            DialogUpdate dialog = new DialogUpdate(studentTakeFee, txtClass.Text, txtNameStudent.Text);

            dialog.ShowDialog();
            dialog.Activate();
            LoadFeeData();
            LoadFee();
            SetStudent();



        }



        private void btndelete_Click(object sender, RoutedEventArgs e)
        {
           

            MessageBoxResult result = MessageBox.Show("Bạn có chắc chắn xóa học phí  " + listfeetake.ElementAt(lvfee.SelectedIndex).NameFee + " của học sinh " + txtNameStudent.Text + " - " + txtClass.Text, "Thông Báo", MessageBoxButton.YesNo, MessageBoxImage.Question);

            if (result == MessageBoxResult.Yes)
            {
                TakeFeeDAO.Instance.DelTakeFee(listfeetake.ElementAt(lvfee.SelectedIndex).id);

                LoadFeeData();
                LoadFee();
                SetStudent();
            }
        }

        private void cbfinding_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            lvstudent.SelectedIndex = cbfinding.SelectedIndex;
            lvstudent.UpdateLayout();
            lvstudent.ScrollIntoView(lvstudent.SelectedItem);
        }



        bool keydown;
        private void cbfinding_PreviewKeyDown(object sender, KeyEventArgs e)
        {
            (sender as ComboBox).IsDropDownOpen = true;
            keydown = true;


        }


        private void filterList(string text)
        {
            ObservableCollection<StudentInfoFeeDTO> tablesearch = TakeFeeDAO.Instance.FindStudent(liststudent, text);

            if (tablesearch.Count > 0)
            {
                cbfinding.SelectedIndex = (int)tablesearch.ElementAt(0).STT - 1;
            }
        }

        private string findText;

        public event PropertyChangedEventHandler PropertyChanged;

        public string SearchText
        {
            get => findText;
            set { findText = value; if (keydown) filterList(SearchText); keydown = false; }
        }

        protected virtual void OnPropertyChanged(string newName)
        {
            if (PropertyChanged != null)
            {

                PropertyChanged(this, new PropertyChangedEventArgs(newName));
            }
        }

        #endregion
    }
}
