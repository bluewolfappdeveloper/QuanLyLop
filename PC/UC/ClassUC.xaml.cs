using QuanLyLopPC.DAO;
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
    /// <summary>
    /// Interaction logic for ClassUC.xaml
    /// </summary>
    public partial class ClassUC : UserControl, INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;

        ObservableCollection<ClassDTO> listclass;

        public ClassUC()
        {
            InitializeComponent();
            this.DataContext = this;
            LoadData();
        }

        private void LoadData()
        {
            listclass = ClassDAO.Instance.GetClass() ;


            lvclass.ItemsSource = listclass;
            cbfinding.ItemsSource = listclass;
            cbfinding.DisplayMemberPath = "NameClass";

            
            
        }

        private void cbfinding_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            lvclass.SelectedIndex = cbfinding.SelectedIndex;
            lvclass.UpdateLayout();
            lvclass.ScrollIntoView(lvclass.SelectedItem);
        }

        private void lvclass_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            cbfinding.SelectedIndex = lvclass.SelectedIndex;
            lvclass.UpdateLayout();
            lvclass.ScrollIntoView(lvclass.SelectedItem);
        }

        private string FormatString(string k)
        {
            string a = "";
            for (int i= 0; i< k.Length; i++)
               if (k[i] >= '0' && k[i] <= '9') a = a + k[i].ToString();
            return a;
        }

        private void btnadd_Click(object sender, RoutedEventArgs e)
        {

            if (txtName.Text == "")
                MessageBox.Show("Vui lòng điền tên lớp", "Thông Báo", MessageBoxButton.OK, MessageBoxImage.Error);
            else
            {
                int fee;
                if (FormatString(txtFee.Text) == "") fee = 0; else fee = Convert.ToInt32(FormatString(txtFee.Text));

                ClassDTO classDTO = new ClassDTO(0, txtName.Text, fee.ToString());

                if (ClassDAO.Instance.AddClass(classDTO)) LoadData();
            }
        }

        private long GetID()
        {
            return listclass.ElementAt(lvclass.SelectedIndex).id;
        }

        private void btndelete_Click(object sender, RoutedEventArgs e)
        {
            if (lvclass.SelectedIndex < 0) return;

            MessageBoxResult result = MessageBox.Show("Bạn có chắc chắn xóa "+ listclass.ElementAt(lvclass.SelectedIndex).NameClass, "Thông Báo", MessageBoxButton.YesNo, MessageBoxImage.Question);

            if (result == MessageBoxResult.Yes)
            {
                ClassDAO.Instance.DeleteClass(GetID());

                LoadData();
            }
        }

        private void btnupdate_Click(object sender, RoutedEventArgs e)
        {
            if (lvclass.SelectedIndex < 0) return;

            MessageBoxResult result = MessageBox.Show("Bạn có chắc chắn cập nhật " + listclass.ElementAt(lvclass.SelectedIndex).NameClass, "Thông Báo", MessageBoxButton.YesNo, MessageBoxImage.Question);

            if (result == MessageBoxResult.Yes)
            {
                int fee;
                if (FormatString(txtFee.Text) == "") fee = 0; else fee = Convert.ToInt32(FormatString(txtFee.Text));

                ClassDTO classDTO = new ClassDTO( GetID(), txtName.Text,fee.ToString());
                ClassDAO.Instance.UpdateClass(classDTO);
                LoadData();
            }
        }


        bool keydown;
        private void cbfinding_PreviewKeyDown(object sender, KeyEventArgs e)
        {
            (sender as ComboBox).IsDropDownOpen = true;
            keydown = true;
           
            
        }


        private void filterList(string text)
        {
            ObservableCollection<ClassDTO> tablesearch = ClassDAO.Instance.FindClass(listclass, text);

            if (tablesearch.Count > 0)
            {
                  long f = tablesearch.ElementAt(0).STT;
                  cbfinding.SelectedIndex = (int)(f-1);
            }
          

            //
        }

        private string findText;

        public string SearchText
        {
            get => findText;
            set { findText = value; if (keydown)filterList(SearchText); keydown = false;}
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
