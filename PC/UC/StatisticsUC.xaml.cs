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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace QuanLyLopPC.UC
{
    /// <summary>
    /// Interaction logic for StatisticsUC.xaml
    /// </summary>
    public partial class StatisticsUC : UserControl
    {
        ObservableCollection<ClassDTO> listclass;
        ObservableCollection<FeeDTO> listfee;
        ObservableCollection<ClassInfoFeeCheck> listfeeinfotake;
        ObservableCollection<ClassInfoFeeUnCheck> listfeeinfountake;


        public StatisticsUC()
        {
            InitializeComponent();
            LoadClass();
            LoadFee();
        }

        #region LoadData

        private void LoadClass()
        {
            listclass = ClassDAO.Instance.GetClass();

            cbclass.ItemsSource = listclass;
            cbclass.DisplayMemberPath = "NameClass";
        }

        private void LoadStudent()
        {
            

            lvstudent.ItemsSource = null;
        }

        private void LoadFee()
        {
            listfee = FeeDAO.Instance.GetFee();
            cbfee.ItemsSource = listfee;
            cbfee.DisplayMemberPath = "NameFee";
        }



        #endregion

        private void rdoTake_Checked(object sender, RoutedEventArgs e)
        {

            if (this.IsLoaded == false || cbclass.SelectedIndex < 0 || cbfee.SelectedIndex < 0) return;
            long IDClass = listclass.ElementAt(cbclass.SelectedIndex).id;
            long IDFee = listfee.ElementAt(cbfee.SelectedIndex).id;

            listfeeinfotake = TakeFeeDAO.Instance.StudentHaveFee(IDClass, IDFee);
            lvstudent.ItemsSource = listfeeinfotake;
            columnNote.Header = "Ngày đóng";
        }

        private void rdoUnTake_Checked(object sender, RoutedEventArgs e)
        {
            if (this.IsLoaded == false || cbclass.SelectedIndex < 0 || cbfee.SelectedIndex < 0) return;

            long IDClass = listclass.ElementAt(cbclass.SelectedIndex).id;
            long IDFee = listfee.ElementAt(cbfee.SelectedIndex).id;

            listfeeinfountake = TakeFeeDAO.Instance.StudentNotHaveFee(IDClass, IDFee);
            lvstudent.ItemsSource = listfeeinfountake;
            columnNote.Header = "Thông tin";
        }

        //private void rdoAll_Checked(object sender, RoutedEventArgs e)
        //{
        //    if (this.IsLoaded == false || cbclass.SelectedIndex < 0 || cbfee.SelectedIndex < 0) return;
        //    lvstudent.ItemsSource = null;
        //    columnNote.Header = "Trạng thái";
        //}

        private void cbclass_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (this.IsLoaded == false || cbclass.SelectedIndex < 0 || cbfee.SelectedIndex < 0) return;
            if (rdoTake.IsChecked == true) rdoTake_Checked(rdoTake, new RoutedEventArgs());
            if (rdoUnTake.IsChecked == true) rdoUnTake_Checked(rdoUnTake, new RoutedEventArgs());
            //if (rdoAll.IsChecked == true) rdoAll_Checked(rdoAll, new RoutedEventArgs());

        }

        private void cbfee_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (this.IsLoaded == false || cbclass.SelectedIndex < 0 || cbfee.SelectedIndex < 0) return;
            if (rdoTake.IsChecked == true) rdoTake_Checked(rdoTake, new RoutedEventArgs());
            if (rdoUnTake.IsChecked == true) rdoUnTake_Checked(rdoUnTake, new RoutedEventArgs());
            //if (rdoAll.IsChecked == true) rdoAll_Checked(rdoAll, new RoutedEventArgs());
        }



        private void lvstudent_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

        }
    }

}
