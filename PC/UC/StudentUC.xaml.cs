using QuanLyLopPC.DAO;
using QuanLyLopPC.DTO;
using QuanLyLopPC.Tool;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Data.OleDb;
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
    /// <summary>
    /// Interaction logic for StudentUC.xaml
    /// </summary>
    public partial class StudentUC : UserControl, INotifyPropertyChanged
    {
        ObservableCollection<ClassDTO> listclass;
        ObservableCollection<StudentDTO> liststudent;

        public StudentUC()
        {
            InitializeComponent();
            this.DataContext = this;
            LoadClass();
            LoadStudent();
        }


        private void LoadClass()
        {
            listclass = ClassDAO.Instance.GetClass();
            cbclass.ItemsSource = listclass;
            cbclass.DisplayMemberPath = "NameClass";
        }
        private void LoadStudent()
        {
            if (cbclass.SelectedIndex < 0) return;

            long IDClass = listclass.ElementAt(cbclass.SelectedIndex).id;

            liststudent = StudentDAO.Instance.GetStudent(IDClass);

            lvstudent.ItemsSource = cbfinding.ItemsSource = liststudent;
            cbfinding.DisplayMemberPath = "NameStudent";
        }


        private void cbclass_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            LoadStudent();
        }

        private void cbfinding_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            lvstudent.SelectedIndex = cbfinding.SelectedIndex;
            lvstudent.UpdateLayout();
            lvstudent.ScrollIntoView(lvstudent.SelectedItem);
        }
        private void lvclass_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            cbfinding.SelectedIndex = lvstudent.SelectedIndex;
            lvstudent.UpdateLayout();
            lvstudent.ScrollIntoView(lvstudent.SelectedItem);
        }

        private void btnadd_Click(object sender, RoutedEventArgs e)
        {
            if (txtName.Text == "")
                MessageBox.Show("Vui lòng điền tên học sinh", "Thông Báo", MessageBoxButton.OK, MessageBoxImage.Error);
            else
            {
                long IDClass = listclass.ElementAt(cbclass.SelectedIndex).id;

                StudentDAO.Instance.AddStudent(new StudentDTO(0, IDClass, txtName.Text, txtPhone.Text));
                LoadStudent();
            }
        }


        private void btndelete_Click(object sender, RoutedEventArgs e)
        {
            if (lvstudent.SelectedIndex < 0) return;

            MessageBoxResult result = MessageBox.Show("Bạn có chắc chắn xóa học sinh " + txtName.Text +" ở "+ cbclass.Text, "Thông Báo", MessageBoxButton.YesNo, MessageBoxImage.Question);

            if (result == MessageBoxResult.Yes)
            {
                long IDStudent = liststudent.ElementAt(lvstudent.SelectedIndex).id;
                StudentDAO.Instance.DeleteStudent(IDStudent);

                LoadStudent();
            }
        }

        private void btnupdate_Click(object sender, RoutedEventArgs e)
        {
            if (lvstudent.SelectedIndex < 0) return;

            MessageBoxResult result = MessageBox.Show("Bạn có chắc chắn cập nhật thông tin học sinh " + txtName.Text + " ở " + cbclass.Text, "Thông Báo", MessageBoxButton.YesNo, MessageBoxImage.Question);

            if (result == MessageBoxResult.Yes)
            {
                long IDStudent = liststudent.ElementAt(lvstudent.SelectedIndex).id;
                long IDClass = listclass.ElementAt(cbclass.SelectedIndex).id;

                StudentDAO.Instance.UpdateClass(new StudentDTO(IDStudent, IDClass, txtName.Text, txtPhone.Text));
                LoadStudent();
            }
        }

        private void btnimportexcel_Click(object sender, RoutedEventArgs e)
        {
            int vt = cbclass.SelectedIndex;
            ExelTool exel = new ExelTool();
            exel.ShowDialog();
            
            LoadClass();
            cbclass.SelectedIndex = vt;
            //LoadStudent();
            //lvstudent.ItemsSource = cbfinding.ItemsSource = null;
        }

        private void btnexportexcel_Click(object sender, RoutedEventArgs e)
        {
            ExportExelTool exel = new ExportExelTool();
            exel.ShowDialog();
        }

        bool keydown;
        private void cbfinding_PreviewKeyDown(object sender, KeyEventArgs e)
        {
            (sender as ComboBox).IsDropDownOpen = true;
            keydown = true;


        }


        private void filterList(string text)
        {
            ObservableCollection<StudentDTO> tablesearch = StudentDAO.Instance.FindStudent(liststudent, text);
      
            if (tablesearch.Count > 0)
            {
                cbfinding.SelectedIndex = (int)tablesearch.ElementAt(0).STT -1;
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
    }
}
