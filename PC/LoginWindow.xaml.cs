using QuanLyLop;
using QuanLyLopPC.DAO;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.IO;
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

namespace QuanLyLopPC
{
    /// <summary>
    /// Interaction logic for LoginWindow.xaml
    /// </summary>
    public partial class LoginWindow : Window
    {
        public LoginWindow()
        {
            InitializeComponent();
            ConnectToSQLite();
        }

        public void ConnectToSQLite()
        {
            //AppSetting app = new AppSetting();
            //if (app.GetProviderName("CSDL") == "System.Data.SQLite") return;

            if (File.Exists(AppDomain.CurrentDomain.BaseDirectory + "ClassManagement.db") == false)
            {
                MessageBox.Show("Connection Failed", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }


            string ConnectionSTRSQLite = String.Format("Data Source= {0}", AppDomain.CurrentDomain.BaseDirectory + "ClassManagement.db");

            AppSetting app = new AppSetting();
            app.SaveConnectiongString("CSDL", ConnectionSTRSQLite, "System.Data.SQLite");
        } 

        private void txtusername_LostFocus(object sender, RoutedEventArgs e)
        {
            EmptyText.SetValueColor(ref txtusername, ref IconAccount, (txtusername.Text == ""));
        }

        private void txtpassword_LostFocus(object sender, RoutedEventArgs e)
        {
            EmptyText.SetValueColor2(ref txtpassword, ref IconKey, (txtpassword.Password == ""));
        }

        public static long id;
        public static string username, password;
        public static string name;

        private bool checkdialog = true;

        private void btnlogin_Click(object sender, RoutedEventArgs e)
        {
            if (String.IsNullOrEmpty(password) || String.IsNullOrEmpty(txtusername.Text))
                MessageBox.Show("Vui lòng điền đủ thông tin tài khoản và mật khẩu", "Thông Báo", MessageBoxButton.OK, MessageBoxImage.Error);
            else
            {
                if (CheckLogin(txtusername.Text, password))
                {
                    username = txtusername.Text;
                    checkdialog = false;
                    MainWindow home = new MainWindow();
                    home.Show();
                    this.Hide();
                }
                else
                {
                    MessageBox.Show("Kiểm tra lại tài khoản và mật khẩu", "Thông Báo", MessageBoxButton.OK, MessageBoxImage.Information);
                }
            }


        }

        private bool CheckLogin(string username, string passwrod)
        {
             DataRow table = LoginDAO.Instance.DangNhap(username, password);

            if (table != null) name = table["name"].ToString() ;
            if (table != null) id = Int64.Parse(table["id"].ToString());

            return (table != null) ? true : false;

        }

        private void btnexit_Click(object sender, RoutedEventArgs e)
        {

            this.Close();
        }

        private void txtusername_TextChanged(object sender, TextChangedEventArgs e)
        {
            EmptyText.SetValueColor(ref txtusername, ref IconAccount, (txtusername.Text == ""));
        }

        private void Window_Closing(object sender, CancelEventArgs e)
        {
            if (checkdialog)
            {
                MessageBoxResult result = MessageBox.Show("Bạn có chắc chắn muốn thoát chương trình ?", "Thông báo", MessageBoxButton.YesNo, MessageBoxImage.Question);
                if (result == MessageBoxResult.No) e.Cancel = true;
            }

        }

        private void txtpassword_PasswordChanged(object sender, RoutedEventArgs e)
        {
            EmptyText.SetValueColor2(ref txtpassword, ref IconKey, (txtpassword.Password == ""));
            password = txtpassword.Password;
        }
    }
}
