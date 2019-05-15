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
    /// Interaction logic for FeeUC.xaml
    /// </summary>
    public partial class FeeUC : UserControl, INotifyPropertyChanged
    {
        ObservableCollection<FeeDTO> listfee;

        public FeeUC()
        {
            InitializeComponent();
            this.DataContext = this;
            LoadData();
        }


        private void LoadData()
        {
            listfee = FeeDAO.Instance.GetFee();

            lvfee.ItemsSource = listfee;
            cbfinding.ItemsSource = listfee;
            cbfinding.DisplayMemberPath = "NameFee";
        }

        private void cbfinding_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            lvfee.SelectedIndex = cbfinding.SelectedIndex;
            lvfee.UpdateLayout();
            lvfee.ScrollIntoView(lvfee.SelectedItem);
        }
        private void lvclass_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            cbfinding.SelectedIndex = lvfee.SelectedIndex;
            lvfee.UpdateLayout();
            lvfee.ScrollIntoView(lvfee.SelectedItem);
        }

        private string FormatString(string k)
        {
            string a = "";
            for (int i = 0; i < k.Length; i++)
                if (k[i] >= '0' && k[i] <= '9') a = a + k[i].ToString();



            return a;
        }

        private void btnadd_Click(object sender, RoutedEventArgs e)
        {

            if (txtName.Text == "")
                MessageBox.Show("Vui lòng điền tên lớp", "Thông Báo", MessageBoxButton.OK, MessageBoxImage.Error);
            else
            {
                FeeDAO.Instance.AddFee(new FeeDTO(0, txtName.Text, txtPrice.Text));
                LoadData();
            }
        }

        private void btndelete_Click(object sender, RoutedEventArgs e)
        {
            if (lvfee.SelectedIndex < 0) return;
            MessageBoxResult result = MessageBox.Show("Bạn có chắc chắn xóa " +listfee.ElementAt(lvfee.SelectedIndex).NameFee, "Thông Báo", MessageBoxButton.YesNo, MessageBoxImage.Question);

            if (result == MessageBoxResult.Yes)
            {
                FeeDAO.Instance.DeleteFee(listfee.ElementAt(lvfee.SelectedIndex).id);

                LoadData();
            }
        }

        private void btnupdate_Click(object sender, RoutedEventArgs e)
        {
            if (lvfee.SelectedIndex < 0) return;
            MessageBoxResult result = MessageBox.Show("Bạn có chắc chắn cập nhật " + listfee.ElementAt(lvfee.SelectedIndex).NameFee, "Thông Báo", MessageBoxButton.YesNo, MessageBoxImage.Question);

            if (result == MessageBoxResult.Yes)
            {
                int fee;
                if (FormatString(txtPrice.Text) == "") fee = 0; else fee = Convert.ToInt32(FormatString(txtPrice.Text));

                FeeDAO.Instance.UpdateFee(new FeeDTO(listfee.ElementAt(lvfee.SelectedIndex).id, txtName.Text, txtPrice.Text));
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
            ObservableCollection<FeeDTO> tablesearch = FeeDAO.Instance.FindFee(listfee, text);

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
    }
}
